package shortening.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import shortening.model.TestShortening;
import shortening.utils.ProcessIdUtils;
import shortening.utils.RequestParamUtils;
import shortening.utils.ShorteningUtils;
import shortening.utils.TestLockUtils;

@Controller
public class TestController {
	private final Logger LOGGER;
	private final String SHORT_URL;
	private ConcurrentHashMap<String, String> shortHist;
	private ConcurrentHashMap<String, Long> shortReqtCnt;
	private ConcurrentHashMap<String, Long> redirectCnt;
	
	public TestController() {
		LOGGER = LogManager.getLogger(TestController.class);
		SHORT_URL = "http://localhost:25225/test/";
		shortHist = new ConcurrentHashMap<String, String>();
		shortReqtCnt = new ConcurrentHashMap<String, Long>();
		redirectCnt = new ConcurrentHashMap<String, Long>();
	}

	@RequestMapping (value = "/testShort.do")
	public String testShort() {
		ProcessIdUtils.get().getPid();
		LOGGER.info("URL Shortening Test Page Loading.");
		
		return "short";
	}

	@RequestMapping (value = "/testShortProc.do")
	public ModelAndView testShort(ModelAndView model, HttpServletRequest req, HttpServletResponse res) {
		ModelAndView view = new ModelAndView("jsonView");
		
		try {
			TestLockUtils.get().lock();
			ProcessIdUtils.get().getPid();
			
			Map<String, Object> reqMap = new HashMap<String, Object>();
			
			reqMap = RequestParamUtils.get().toMap(req);
			
			String url = (String) reqMap.get("siteUrl");
			
			LOGGER.info("Request Test Shortening. url : [{}]", url);
			LOGGER.info("Check Exist Test Shortening Key. url : [{}]", url);
			
			String key = shortHist.get(url);
			
			if (key != null) {
				LOGGER.info("Exist Test Shortening Key. url : [{}] / key : [{}]", url, key);

				LOGGER.info("Start Update URL Shortening Test Request Count. url : [{}]", url);
				
				Object obj = shortReqtCnt.get(url);
				
				if (obj != null) {
					shortReqtCnt.put(url, ((Long) obj) + 1);
					
					LOGGER.info("Update URL Shortening Test Request Count Success. url : [{}]", url);
					view.addObject("shortKey", key);
					view.addObject("shortUrl", SHORT_URL);
					view.addObject("resultCode", 0);
				} else {
					LOGGER.error("Update URL Shortening Test Request Count Failure. url : [{}]", url);
					view.addObject("resultCode", -1);
				}
			} else {
				LOGGER.info("Not Exist Test Shortening Key. url : [{}]", url);
				LOGGER.info("Start Make Test Shortening Key. url : [{}]", url);
				
				String shortKey = ShorteningUtils.get().process(url, 0L);
				
				LOGGER.info("Check Duplicate Test Shortening Key. url : [{}] / key : [{}]", url, shortKey);
				
				if (isDuplicateKey(shortKey) > 0) {
					LOGGER.warn("Duplicate Test Shortening Key. Start Make Test Shortening Key Again. url : [{}]", url);
					
					shortKey = ShorteningUtils.get().process(url, System.currentTimeMillis());
				}

				LOGGER.info("Start Insert Test Shortening Info. url : [{}] / key : [{}]", url, shortKey);
				
				if (insertShort(url, shortKey) == 1) {
					LOGGER.info("Insert Test Shortening Info Success. url : [{}] / key : [{}]", url, shortKey);
					view.addObject("shortKey", shortKey);
					view.addObject("shortUrl", SHORT_URL);
					view.addObject("resultCode", 0);
				} else {
					LOGGER.error("Insert Test Shortening Info Failure. url : [{}] / key : [{}]", url, shortKey);
					view.addObject("resultCode", -2);
				}
			}
		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		} finally {
			TestLockUtils.get().unlock();
		}
		
		return view;
	}
	
	@GetMapping (value = "/test/{shortKey}")
	public String testRedirect(Model model, HttpServletRequest req, HttpServletResponse res, @PathVariable String shortKey) {
		try {
			TestLockUtils.get().lock();
			ProcessIdUtils.get().getPid();
			
			LOGGER.info("Request Test Shortening URL. shortKey : [{}]", shortKey);
			
			LOGGER.info("Start Update Test Shortening URL Redirect Count. shortKey : [{}]", shortKey);
			
			if (updateRedirectCount(shortKey) == 1) {
				LOGGER.info("Update Test Shortening URL Redirect Count Success. shortKey : [{}]", shortKey);
				model.addAttribute("siteUrl", getShortUrl(shortKey));
				model.addAttribute("resultCode", 0);
			} else {
				LOGGER.error("Not Found Test Shortening Url Info. shortKey : [{}]", shortKey);
				model.addAttribute("resultCode", -2);
			}
		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		} finally {
			TestLockUtils.get().unlock();
		}
		
		return "redirect";
	}

	private String getShortUrl(String shortKey) {
		for (Entry<String, String> entry : shortHist.entrySet()) {
			if (entry.getValue().equals(shortKey)) {
				return entry.getKey();
			}
		}
		
		return "";
	}

	private int updateRedirectCount(String shortKey) {
		Object obj = redirectCnt.get(shortKey);
		
		if (obj != null) {
			redirectCnt.put(shortKey, ((Long) obj) + 1);
			
			return 1;
		}
		
		return 0;
	}

	private int insertShort(String url, String shortKey) {
		if (shortHist.size() + 1 > 10) {
			shortHist.clear();
			shortReqtCnt.clear();
			redirectCnt.clear();
		}
		
		shortHist.put(url, shortKey);
		shortReqtCnt.put(url, 1L);
		redirectCnt.put(shortKey, 0L);
		
		return 1;
	}

	private int isDuplicateKey(String shortKey) {
		for (Entry<String, String> entry : shortHist.entrySet()) {
			if (entry.getValue().equals(shortKey)) {
				return 1;
			}
		}
		
		return 0;
	}

	@RequestMapping (value = "/testHistory.do")
	public String testHistory() {
		ProcessIdUtils.get().getPid();
		LOGGER.info("History Test Page Loading.");
		
		return "history";
	}
	
	@RequestMapping (value = "/testHistoryList.do")
	public ModelAndView testHistoryList(ModelAndView model, HttpServletRequest req, HttpServletResponse res) {
		ModelAndView view = new ModelAndView("jsonView");
		
		try {
			TestLockUtils.get().lock();
			ProcessIdUtils.get().getPid();
			
			List<TestShortening> hists = new ArrayList<TestShortening>();
			
			for (Entry<String, String> entry : shortHist.entrySet()) {
				TestShortening hist = new TestShortening();
				
				hist.setSiteUrl(entry.getKey());
				hist.setShortKey(entry.getValue());
				hist.setShortCnt(shortReqtCnt.get(entry.getKey()));
				hist.setRedirectCnt(redirectCnt.get(entry.getValue()));
				
				hists.add(hist);
			}
			
			view.addObject("histList", hists);
			view.addObject("histCount", hists.size());
		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		} finally {
			TestLockUtils.get().unlock();
		}
		
		return view;
	}
}
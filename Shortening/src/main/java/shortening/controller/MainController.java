package shortening.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import shortening.mapper.ShorteningMapper;
import shortening.utils.ProcessIdUtils;
import shortening.utils.RequestParamUtils;
import shortening.utils.ShorteningUtils;

@Controller
public class MainController {
	private final Logger LOGGER;
	private final String SHORT_URL;
	@Autowired
	private ShorteningMapper dao;
	
	public MainController() {
		SHORT_URL = "http://localhost:25225/short/";
		LOGGER = LogManager.getLogger(MainController.class);
	}
	
	@RequestMapping (value = "/")
	public String main() {
		ProcessIdUtils.get().getPid();
		LOGGER.info("URL Shortening Main Page Loading.");
		
		return "index";
	}
	
	@GetMapping (value = "/short/{shortKey}")
	public String redirect(Model model, HttpServletRequest req, HttpServletResponse res, @PathVariable String shortKey) {
		try {
			ProcessIdUtils.get().getPid();
			
			Map<String, Object> reqMap = new HashMap<String, Object>();
			
			LOGGER.info("Request Shortening URL. shortKey : [{}]", shortKey);
			
			reqMap.put("shortKey", shortKey);

			LOGGER.info("Start Update Shortening URL Redirect Count. shortKey : [{}]", shortKey);
			
			if (dao.updateRedirectCount(reqMap) == 1) {
				LOGGER.info("Update Shortening URL Redirect Count Success. shortKey : [{}]", shortKey);
				model.addAttribute("siteUrl", dao.getShortUrl(reqMap));
				model.addAttribute("resultCode", 0);
			} else {
				LOGGER.error("Update Shortening URL Redirect Count Failure. shortKey : [{}]", shortKey);
				model.addAttribute("resultCode", -1);
			}
		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
		
		return "redirect";
	}
	
	@RequestMapping (value = "/shortening.do")
	public ModelAndView shortening(ModelAndView model, HttpServletRequest req, HttpServletResponse res) {
		ModelAndView view = new ModelAndView("jsonView");
		
		try {
			ProcessIdUtils.get().getPid();
			
			Map<String, Object> reqMap = new HashMap<String, Object>();
			
			reqMap = RequestParamUtils.get().toMap(req);
			
			LOGGER.info("Request Shortening. [{}]", reqMap);
			LOGGER.info("Check Exist Shortening Key. url : [{}]", reqMap.get("siteUrl"));
			
			Object obj = dao.alreadyShort(reqMap);
			
			if (obj != null) {
				LOGGER.info("Exist Shortening Key. url : [{}] / key : [{}]", reqMap.get("siteUrl"), obj);

				LOGGER.info("Start Update URL Shortening Request Count. url : [{}]", reqMap.get("siteUrl"));
				
				if (dao.updateRequestCount(reqMap) == 1) {
					LOGGER.info("Update URL Shortening Request Count Success. url : [{}]", reqMap.get("siteUrl"));
					view.addObject("shortKey", obj);
					view.addObject("shortUrl", SHORT_URL);
					view.addObject("resultCode", 0);
				} else {
					LOGGER.error("Update URL Shortening Request Count Failure. url : [{}]", reqMap.get("siteUrl"));
					view.addObject("resultCode", -1);
				}
			} else {
				LOGGER.info("Not Exist Shortening Key. url : [{}]", reqMap.get("siteUrl"));
				LOGGER.info("Start Make Shortening Key. url : [{}]", reqMap.get("siteUrl"));
				
				String shortKey = ShorteningUtils.get().process((String) reqMap.get("siteUrl"), 0L);
				
				reqMap.put("shortKey", shortKey);
				
				LOGGER.info("Check Duplicate Shortening Key. url : [{}] / key : [{}]", reqMap.get("siteUrl"), shortKey);
				
				if (dao.isDuplicateKey(reqMap) > 0) {
					LOGGER.warn("Duplicate Shortening Key. Start Make Shortening Key Again. url : [{}]", reqMap.get("siteUrl"));
					
					shortKey = ShorteningUtils.get().process((String) reqMap.get("siteUrl"), System.currentTimeMillis());
				}

				reqMap.put("shortKey", shortKey);
				reqMap.put("shortUrl", SHORT_URL);
				
				LOGGER.info("Start Insert Shortening Info. name : [{}] / url : [{}] / key : [{}] / prefix : [{}]", 
						reqMap.get("siteName"), reqMap.get("siteUrl"), shortKey, SHORT_URL);
				
				if (dao.insertShort(reqMap) == 1) {
					LOGGER.info("Insert Shortening Info Success. name : [{}] / url : [{}] / key : [{}] / prefix : [{}]", 
							reqMap.get("siteName"), reqMap.get("siteUrl"), shortKey, SHORT_URL);
					view.addObject("shortKey", reqMap.get("shortKey"));
					view.addObject("shortUrl", SHORT_URL);
					view.addObject("resultCode", 0);
				} else {
					LOGGER.error("Insert Shortening Info Failure. name : [{}] / url : [{}] / key : [{}] / prefix : [{}]", 
							reqMap.get("siteName"), reqMap.get("siteUrl"), shortKey, SHORT_URL);
					view.addObject("resultCode", -2);
				}
			}
		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
		
		return view;
	}
	
	@RequestMapping (value = "/history.do")
	public ModelAndView history(ModelAndView model, HttpServletRequest req, HttpServletResponse res) {
		ModelAndView view = new ModelAndView("jsonView");
		
		try {
			ProcessIdUtils.get().getPid();
			
			Map<String, Object> reqMap = new HashMap<String, Object>();
			
			reqMap = RequestParamUtils.get().toMap(req);
			
			LOGGER.info("Request Shortening History. [{}]", reqMap);
			
			LOGGER.info("Search {} Count. {} : [{}]", 
					((String) reqMap.get("flag")).equals("0") ? "URL Shortening" : "Shortening Redirect", 
					((String) reqMap.get("flag")).equals("0") ? "url" : "shortKey", 
					reqMap.get("search"));
			
			view.addObject("histData", dao.getHistory(reqMap));
		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
		
		return view;
	}
}
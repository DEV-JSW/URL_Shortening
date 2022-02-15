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
		LOGGER.info("URL Shortening Main Page Loading.");
		
		return "index";
	}
	
	@GetMapping (value = "/short/{shortKey}")
	public String redirect(Model model, HttpServletRequest req, HttpServletResponse res, @PathVariable String shortKey) {
		try {
			Map<String, Object> reqMap = new HashMap<String, Object>();
			
			reqMap.put("shortKey", shortKey);
			
			if (dao.updateRedirectCount(reqMap) == 1) {
				model.addAttribute("siteUrl", dao.getShortUrl(reqMap));
				model.addAttribute("resultCode", 0);
			} else {
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
			Map<String, Object> reqMap = new HashMap<String, Object>();
			
			reqMap = RequestParamUtils.get().toMap(req);
			
			Object obj = dao.alreadyShort(reqMap);
			
			if (obj != null) {
				if (dao.updateRequestCount(reqMap) == 1) {
					view.addObject("shortKey", obj);
					view.addObject("shortUrl", SHORT_URL);
					view.addObject("resultCode", 0);
				} else {
					view.addObject("resultCode", -1);
				}
			} else {
				String shortKey = ShorteningUtils.get().process((String) reqMap.get("siteUrl"), 0L);
				
				reqMap.put("shortKey", shortKey);
				
				if (dao.isDuplicateKey(reqMap) > 0) {
					shortKey = ShorteningUtils.get().process((String) reqMap.get("siteUrl"), System.currentTimeMillis());
				}

				reqMap.put("shortKey", shortKey);
				reqMap.put("shortUrl", SHORT_URL);
				
				if (dao.insertShort(reqMap) == 1) {
					view.addObject("shortKey", reqMap.get("shortKey"));
					view.addObject("shortUrl", SHORT_URL);
					view.addObject("resultCode", 0);
				} else {
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
			Map<String, Object> reqMap = new HashMap<String, Object>();
			
			reqMap = RequestParamUtils.get().toMap(req);
			
			view.addObject("histData", dao.getHistory(reqMap));
		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
		
		return view;
	}
}
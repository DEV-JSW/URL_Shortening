package shortening.utils;

import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

public class RequestParamUtils {
	private static class Instance {
		public static final RequestParamUtils inst = new RequestParamUtils();
	}
	
	private RequestParamUtils() {
		
	}
	
	public static RequestParamUtils get() {
		return Instance.inst;
	}
	
	public HashMap<String, Object> toMap(HttpServletRequest req) throws Exception{		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("ServletRequest", req);
		
		if(req.getParameterMap() != null && req.getParameterMap().size() > 0) {
			Iterator<String> it = req.getParameterMap().keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				String[] temp = req.getParameterValues(key);
				
				String value = ifNullBlank(temp[0]);
				resultMap.put(key, value);
			}
		}
		
		return resultMap;
	}
	
	private String ifNullBlank(String str) {
		String resultString = str;
		
		if (str == null) {
			resultString = "";
		} else if (str.equals("")) {
			resultString = "";
		} else if (str.length() == 0) {
			resultString = "";
		}
		
		return resultString;
	}
}
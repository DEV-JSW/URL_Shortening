package shortening.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShorteningUtils {
	private final Logger LOGGER;
	private final String[] CODE;
	
	private static class Instance {
		public static final ShorteningUtils inst = new ShorteningUtils();
	}
	
	public static ShorteningUtils get() {
		return Instance.inst;
	}
	
	private ShorteningUtils() {
		LOGGER = LogManager.getLogger(ShorteningUtils.class);
		CODE = new String[62];
		
		String asciiStr = "";
		int idx = 0;
		
		for (int i = 48 ; i <= 122 ; i++) {
			asciiStr = Character.toString((char) i);
			
			if (StringUtils.isAlphanumeric(asciiStr)) {
				CODE[idx] = asciiStr;
				
				idx++;
			}
		}
	}
	
	public String process(String url, long seed) {
		String result = "";
		
		int ascii = 0;
		
		LOGGER.debug("Calculate Ascii Code From Request Url. url : [{}]", url);
		
		for (char c : url.toCharArray()) {
			LOGGER.debug("Char : [{}] / Ascii Code : [{}] / Ascii Sum : [{}](Formula => 31 * totalSum + asciiCode)", 
					Character.toString(c), (int) c, ascii + (31 * ascii) + c);
			ascii += 31 * ascii + c;
		}
		
		ascii += seed;
		
		do {
			LOGGER.debug("Ascii Sum : [{}] / Ascii Mod : [{}] / Result : [{}]", 
					ascii, ascii % CODE.length, CODE[Math.abs(ascii % CODE.length)]);
			
			result = result.concat(CODE[Math.abs(ascii % CODE.length)]);
			
			ascii /= CODE.length;
		} while (ascii != 0);
		
		return result;
	}
}
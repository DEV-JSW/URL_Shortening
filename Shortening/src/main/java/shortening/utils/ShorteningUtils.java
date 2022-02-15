package shortening.utils;

import org.apache.commons.lang3.StringUtils;

public class ShorteningUtils {
	private final String[] CODE;
	
	private static class Instance {
		public static final ShorteningUtils inst = new ShorteningUtils();
	}
	
	public static ShorteningUtils get() {
		return Instance.inst;
	}
	
	private ShorteningUtils() {
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
		
		for (char c : url.toCharArray()) {
			ascii += 31 * ascii + c;
		}
		
		ascii += seed;
		
		int res = 0;
		
		do {
			res = ascii % CODE.length;
			
			result = result.concat(CODE[Math.abs(res)]);
			
			ascii /= CODE.length;
		} while (ascii != 0);
		
		return result;
	}
}
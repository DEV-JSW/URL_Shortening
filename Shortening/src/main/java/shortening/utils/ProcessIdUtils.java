package shortening.utils;

import java.lang.management.ManagementFactory;

import org.apache.logging.log4j.ThreadContext;

public class ProcessIdUtils {
	private static class Instance {
		public static final ProcessIdUtils inst = new ProcessIdUtils();
	}
	
	public static ProcessIdUtils get() {
		return Instance.inst;
	}
	
	public void getPid() {
		ThreadContext.put("pid", ManagementFactory.getRuntimeMXBean().getName().replaceAll("\\D", ""));
	}
}

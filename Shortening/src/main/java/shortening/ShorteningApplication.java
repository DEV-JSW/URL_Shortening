package shortening;

import java.lang.management.ManagementFactory;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ShorteningApplication extends SpringBootServletInitializer {
	static {
		String pid = ManagementFactory.getRuntimeMXBean().getName().replaceAll("\\D", "");
		
		ThreadContext.put("pid", pid);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ShorteningApplication.class, args);
	}
}
package shortening.utils;

import java.util.concurrent.locks.ReentrantLock;

public class TestLockUtils {
	private transient final ReentrantLock multiJob;
	
	private static class Instance {
		public static final TestLockUtils inst = new TestLockUtils();
	}
	
	public TestLockUtils() {
		multiJob = new ReentrantLock(false);
	}
	
	public static TestLockUtils get() {
		return Instance.inst;
	}
	
	public void lock() {
		multiJob.lock();
	}
	
	public void unlock() {
		multiJob.unlock();
	}
}
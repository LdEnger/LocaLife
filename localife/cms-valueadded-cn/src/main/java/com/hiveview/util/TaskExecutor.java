package com.hiveview.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Title：线程池工具类
 * Description：线程不安全的单例模式
 * Company：hiveview.com
 * Author：郝伟革 
 * Email：haoweige@hiveview.com 
 * Date:Sep 22, 2014
 */
public final class TaskExecutor {

	private static TaskExecutor INSTANCE = new TaskExecutor();
	private ExecutorService pool;

	private TaskExecutor() {
		super();
	}

	public static TaskExecutor getInstance() {
		return INSTANCE;
	}

	/**
	 * @return cpu核数
	 */
	public int init() {
		int cpu = Runtime.getRuntime().availableProcessors();
		pool = Executors.newFixedThreadPool(cpu + 1);
		return cpu;
	}

	public void execute(Runnable command) {
		pool.execute(command);
	}

	/**
	 * @return 1，成功；0，失败；-1，异常
	 */
	public int destroy() {
		try {
			pool.shutdownNow();
			//	while (!pool.awaitTermination(10, TimeUnit.SECONDS)) {
			//	}
			if (pool.isShutdown()) {
				return 1;
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
}

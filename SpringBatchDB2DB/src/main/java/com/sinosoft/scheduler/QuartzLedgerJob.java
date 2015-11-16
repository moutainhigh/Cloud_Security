package com.sinosoft.scheduler;

import org.springframework.stereotype.Component;

import com.sinosoft.thread.ParallelThread;

/**
 * 定时调度类
 */
@Component("quartzLedgerJob")
public class QuartzLedgerJob {

	/**
	 * 执行业务方法
	 * @throws Exception
	 */
	public void execute() throws Exception {
		QuartzLedgerJob quartzLedgerJob = new QuartzLedgerJob();
		//第一线程
		QuartzLedgerJob.Order firstThread=quartzLedgerJob.new Order();
        firstThread.start();
        //第二线程
        quartzLedgerJob.new Service().start();
        quartzLedgerJob.new User().start();
        quartzLedgerJob.new Task().start();
	}
	class Order extends Thread {
		public void run() {
			ParallelThread p = new ParallelThread("orderSelect","orderInsert","csOrderMapper");
			try {
				boolean flag = p.startRun();
				if(flag){
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	class Service extends Thread{
		public void run() {
			ParallelThread p = new ParallelThread("serviceSelect","serviceInsert","csServiceMapper");
			try {
				p.startRun();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	class User extends Thread{
		public void run() {
			ParallelThread p = new ParallelThread("userSelect","userInsert","csUserMapper");
			try {
				p.startRun();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	class Task extends Thread{
		public void run() {
			ParallelThread p = new ParallelThread("taskSelect","taskInsert","csTaskMapper");
			try {
				p.startRun();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

package com.cn.ctbri.scheduler;


/**
 * 定时调度类
 */
public class QuartzLedgerJob {
	private boolean exceRun;

	public boolean isExceRun() {
		return exceRun;
	}

	public void setExceRun(boolean exceRun) {
		this.exceRun = exceRun;
	}

	/**
	 * 执行业务方法
	 * @throws Exception
	 */
	public void execute() throws Exception {
		if(exceRun){
			SerialThread s = new SerialThread();
			s.getParams();
		}else{
			System.out.println("=====>>>>不执行……");
		}
	}
}

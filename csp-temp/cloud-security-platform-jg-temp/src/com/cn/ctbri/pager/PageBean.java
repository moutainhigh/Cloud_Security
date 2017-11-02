package com.cn.ctbri.pager;
import java.util.List;

public class PageBean<T> {
	/*
	 * servlet从页面获取，如果页面没有传，那么就是为1
	 */
	private int pageCode;//当前页码
//	private int totalPage;//总页数,通过总记录和每页记录数计算而来
	/*
	 * 在service中通过dao来获取，pageCode和pageSize
	 */
	private List<T> datas;//当前页的记录
	/*
	 * 在service中调用dao的count()来获取
	 */
	private int totalRecord;//总记录数
	/*
	 * 不用动，固定值
	 */
	private int pageSize = 25;//每页记录数
	
	public PageBean() {}
	
	public PageBean(int pageCode, int totalRecord) {
		this(pageCode, totalRecord, 25);
	}
	
	public PageBean(int pageCode, int totalRecord, int pageSize) {
		this.pageCode = pageCode;
		this.totalRecord = totalRecord;
		this.pageSize = pageSize;
	}

	public int getPageCode() {
		return pageCode;
	}

	public void setPageCode(int pageCode) {
		this.pageCode = pageCode;
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		// 计算totalPage
		int totalPage = this.totalRecord / pageSize;
		return totalRecord % pageSize == 0 ? totalPage : totalPage + 1;
	}
}

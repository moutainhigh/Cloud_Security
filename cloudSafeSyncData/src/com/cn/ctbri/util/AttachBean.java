package com.cn.ctbri.util;
import java.io.File;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-4
 * 描        述：  附件实体类
 * 版        本：  1.0
 */
public class AttachBean {
	private File file;
	private String fileName;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public AttachBean() {

	}

	public AttachBean(File file, String fileName) {
		super();
		this.file = file;
		this.fileName = fileName;
	}
}

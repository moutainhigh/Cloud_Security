package com.ctbri.exception;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ctbri.util.ErrMap;

public class CloudException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 错误码 **/
	private String code;

	private Map<String, String> errors = new HashMap<String, String>();

	// 为RCP的Hessian做特殊处理。Hessian在传输对象时，并不是用序列化和反序列化，而是采用构造器的方式生成对象，此构造器是防止DSErrMap静态块加载文件出错
	protected CloudException() {
	}

	/**
	 * 构造
	 * 
	 * @param code
	 */
	public CloudException(String code) {
		super(ErrMap.getProperty(code, code));
		this.code = code;
	}

	public CloudException(String code, Throwable cause) {
		super(ErrMap.getProperty(code, code), cause);
		this.code = code;
	}

	/**
	 * 信息可格式化
	 * 
	 * @param code
	 * @param parameters
	 */
	public CloudException(String code, Object... parameters) {
		super(parseMessage(ErrMap.getProperty(code, code), parameters));
		this.code = code;
	}

	public CloudException(String code, String[] messages) {
		super(parseMessage(ErrMap.getProperty(code, code), messages));
		this.code = code;
	}

	public CloudException(Map<String, String> errors) {
		super(formatMessages(errors));
		this.errors = errors;
	}

	/**
	 * 获取异常码
	 */
	public String getCode() {
		return this.code;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

	private static String parseMessage(String template, Object[] parameters) {
		return String.format(template, parameters);
	}

	/**
	 * 将模板中的{}按顺序填入参数
	 * 
	 * @param template
	 * @param args
	 * @return
	 */
	private static String parseMessage(String template, String[] args) {
		if (template.indexOf("{}") < 0)
			return template;
		StringBuilder sb = new StringBuilder();
		String[] clips = (template + " ").split("\\{\\}");
		for (int i = 0; i < clips.length; i++) {
			sb.append(clips[i]);
			if (args != null && i < args.length && i < clips.length - 1)
				sb.append(args[i]);
		}
		return sb.substring(0, sb.length() - 1);
	}

	private static String formatMessages(Map<String, String> errors) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : errors.entrySet()) {
			sb.append(entry.getValue());
			sb.append(";");
		}
        if(sb.length() > 0){
            return StringUtils.removeEnd(sb.toString(), ";");
        }
		return sb.toString();
	}

}

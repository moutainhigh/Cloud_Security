package com.sinosoft.listener;

import org.apache.log4j.Logger;
import org.springframework.batch.core.listener.ItemListenerSupport;
import org.springframework.stereotype.Component;


/**
 * 检查是读出错还是写出错
 */
@Component("itemFailureLoggerListener")
public class ItemFailureLoggerListener extends ItemListenerSupport<Object, Object> {
	private final static Logger LOG = Logger.getLogger(ItemFailureLoggerListener.class);

	public void onReadError(Exception ex) {
		LOG.error("Encountered error on read", ex);
	}

	public void onWriteError(Exception ex, Object item) {
		LOG.error("Encountered error on write", ex);
	}

}

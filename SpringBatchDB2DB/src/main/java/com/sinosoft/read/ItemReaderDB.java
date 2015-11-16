package com.sinosoft.read;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class ItemReaderDB implements  ItemReader {

	public Object read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {
		
		return null;
	}

}

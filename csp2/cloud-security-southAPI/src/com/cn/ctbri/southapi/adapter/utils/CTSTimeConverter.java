package com.cn.ctbri.southapi.adapter.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * xstream日期转换器，用于将标准时转换为东八区时间
 * @author shaozhenya
 *
 */
public class CTSTimeConverter implements Converter {
    //判断字段是否属于要转换的类型
   @Override
    public boolean canConvert(Class paramClass) {
        return Date.class.isAssignableFrom(paramClass);
    }

    //对象转化为xml
    @Override
    public void marshal(Object object, HierarchicalStreamWriter writer,
                                    MarshallingContext context) {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        writer.setValue(format.format(object));
    }

    //xml转化为对象
   @Override
    public Object unmarshal(HierarchicalStreamReader reader,
                UnmarshallingContext context) {
        try {
                  Date date= DateFormat.getInstance().parse(reader.getValue());
                  return date;
        } catch (ParseException e) {
                  return null;
        }
    }
}

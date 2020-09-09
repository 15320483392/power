package com.wt.auth.server.listenter;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * @author WTar
 * @date 2020/3/24 17:04
 */
public class CDATAConvert implements Converter {

    /**
     * java对象转换为xml
     */
    @Override
    public void marshal(Object object, HierarchicalStreamWriter writer,
                        MarshallingContext context) {

        String prefix = "<![CDATA[";
        String suffix = "]]>";
        String trans = prefix + object + suffix;
        writer.setValue(trans);
    }

    /**
     * xml转换成JAVA对象
     */
    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        return reader.getValue();
    }

    /**
     * 判断字段是否是需要转换的类型
     */
    @Override
    public boolean canConvert(Class paramClass) {
        return String.class.isAssignableFrom(paramClass);
    }

}

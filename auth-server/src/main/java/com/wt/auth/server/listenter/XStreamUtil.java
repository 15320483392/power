package com.wt.auth.server.listenter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.wt.auth.server.entity.Activation;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * @author WTar
 * @date 2020/3/18 13:54
 */
public class XStreamUtil {
    private static XStream xStream;

    //JVM加载类时会执行这些静态的代码块，如果static代码块有多个，JVM将按照它们在类中出现的先后顺序依次执行它们，每个代码块只会被执行一次。
    static{
        xStream = new XStream(new DomDriver());
        /*
         * 使用xStream.alias(String name, Class Type)为任何一个自定义类创建到类到元素的别名
         * 如果不使用别名，则生成的标签名为类全名
         */
        xStream.alias("activation", Activation.class);
        //将某一个类的属性，作为xml头信息的属性，而不是子节点
        //xStream.useAttributeFor(Activation.class, "country");
        //对属性取别名
        xStream.aliasField("code", Activation.class,"code");
        xStream.aliasField("time", Activation.class,"createTime");
        xStream.aliasField("key",Activation.class,"companyKey");
    }

    //xml转java对象
    public static Object xmlToBean(String xml){
        return xStream.fromXML(xml);
    }
    // xml转pojo
    public static <T> T deSerizalizeFromXml(String xmlstr, Class<T> cls) throws Exception {
        //注意：不是new Xstream();否则报错：java.lang.NoClassDefFoundError:
        //org/xmlpull/v1/XmlPullParserFactory
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        T obj = (T) xstream.fromXML(xmlstr);
        return obj;
    }

    //java对象转xml
    public static String beanToXml(Object obj){
        StringBuffer xmlS = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xmlS.append("<root>\n");
        xStream.processAnnotations(Activation.class);
        xmlS.append(xStream.toXML(obj));
        xmlS.append("\n</root>");
        return StringEscapeUtils.unescapeXml(xmlS.toString());
    }
}

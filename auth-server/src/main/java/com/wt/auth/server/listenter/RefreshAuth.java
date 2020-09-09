package com.wt.auth.server.listenter;

import com.wt.auth.server.context.AuthContext;
import com.wt.auth.server.entity.Activation;
import com.wt.auth.server.entity.OauthClientExt;
import com.wt.auth.server.server.impl.InMemoryClientDetailsService;
import com.wt.auth.server.utils.Analysis;
import com.wt.auth.server.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author WTar
 * @date 2020/3/16 18:18
 */
@Slf4j
@Component
public class RefreshAuth {

    @Autowired
    private InMemoryClientDetailsService inMemory;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 初始化激活码
     * @author wangtao
     * @date 2020/9/3 13:59
     * @param  * @param
     * @return void
     */
    @PostConstruct
    public void initAuth(){
        // 取出激活码
        String codes = redisUtils.getActivation();
        String key = redisUtils.getCompanyKey();
        if (codes == null || "".equals(codes)) {
            Activation activation = getCodeToXmlFile();
            codes = activation.getCode();
            key = activation.getCompanyKey();
        }
        if (StringUtils.isEmpty(codes)) {
            Activation activation = httpGetActivationCode();
            codes = activation.getCode();
            key = activation.getCompanyKey();
        }
        // 启动读取激活码文件
        Map<String,ClientDetails> init = new HashMap<>();
        //解析激活码
        List<OauthClientExt> list = Analysis.analysisActivationCode(codes,key);
        for (OauthClientExt client : list) {
            init.put(client.getClientId(),client);
        }
        // 存在激活码文件 解析激活码 配置权限
        inMemory.setClientDetailsStore(init);
    }

    // 定时任务 每天凌晨2点半执行
    @Scheduled(cron = "0 30 2 * * ?")
    public void RefreshSystemAuth(){
        // 取出激活码
        String codes = redisUtils.getActivation();
        String key = redisUtils.getCompanyKey();
        if (StringUtils.isEmpty(codes)) {
            Activation activation = getCodeToXmlFile();
            codes = activation.getCode();
            key = activation.getCompanyKey();
        }
        // 启动读取激活码文件
        Map<String,ClientDetails> init = new HashMap<>();
        //解析激活码
        List<OauthClientExt> list = Analysis.analysisActivationCode(codes,key);
        for (OauthClientExt client : list) {
            init.put(client.getClientId(),client);
        }
        // 存在激活码文件 解析激活码 配置权限
        inMemory.setClientDetailsStore(init);
    }

    /**
     * 每月10号凌晨2点检查激活码版本
     * @author wangtao
     * @date 2020/3/24 15:36
     * @param  * @param
     * @return void
     */
    @Scheduled(cron = "0 0 2 10 * ?")
    public void RefreshSystemAuthToCenter(){
        this.httpGetActivationCode();
    }

    /**
     * 解析xml文件
     * @author wangtao
     * @date 2020/3/19 16:54
     * @param  * @param
     * @return java.lang.String
     */
    private Activation getCodeToXmlFile(){
        Activation activation = new Activation();
        String code = null;
        String key = null;
        Element root = useDom4JReadXml(AuthContext.ACTIVATION_FILE_PATH);
        if (root == null) {
            // 不存在 访问汉国权限中心获取激活码
            return httpGetActivationCode();
        }
        Element foo;
        for (Iterator i = root.elementIterator("activation"); i.hasNext();) {
            foo = (Element) i.next();
            code = foo.elementText("code");
            key = foo.elementText("key");
        }
        if (!StringUtils.isEmpty(code)) {
            activation.setCode(code);
            redisUtils.saveActivation(code);
        }
        if (!StringUtils.isEmpty(key)) {
            activation.setCompanyKey(key);
            redisUtils.saveCompanyKey(key);
        }
        return activation;
    }

    /**
     * 请求汉国权鉴中心获取激活码
     * @author wangtao
     * @date 2020/3/19 18:21
     * @param  * @param
     * @return java.lang.String
     */
    private Activation httpGetActivationCode(){
        // 得到激活码 并返回 假设我们已经得到了
        String actCode = "eyJhbGciOiJIUzI1NiJ9.eyJoZ3N3X3BjX2FkbWluX2NsaWVudCI6eyJncmFudFR5cGVzIjpbInBhc3N3b3JkIiwicm" +
                "VmcmVzaF90b2tlbiIsImF1dGhvcml6YXRpb25fY29kZSJdLCJzY29wZSI6InNlcnZlciIsInNlY3JldCI6IiQyYSQxMCRLUnJyWl" +
                "c5dmdlU0xCNUd3cURJeEtPR0pLbWk1b2laZDl6SFlqUWZneXdveDhTRUtsd0s2YSIsInRpbWUiOjE2MTkzNDU0NzE0NDV9LCJzb2" +
                "1vcm5fYWRtaW5pc3RyYXRvcl9jbGllbnQiOnsiZ3JhbnRUeXBlcyI6WyJwYXNzd29yZCIsInJlZnJlc2hfdG9rZW4iLCJhdXRob3" +
                "JpemF0aW9uX2NvZGUiXSwic2NvcGUiOiJzZXJ2ZXIiLCJzZWNyZXQiOiIkMmEkMTAkSUtKXC9WVzZ2V2ZjLkVPc0pod0ZwUGUyTk" +
                "ViU2tLY2xjVnRwNGZuaTU5WEJHUkxMM0FjODZXIiwidGltZSI6MTYxOTM0NTQ3MTQ0NX19.zte3S4FWuS_VJmWsHxyDmYyhEoyd3" +
                "m9rVfYQGOw8S9A";
        redisUtils.saveActivation(actCode);
        // 写入文件
        Activation act = new Activation();
        act.setCreateTime(String.valueOf(System.currentTimeMillis()));
        act.setCode(actCode);
        act.setCompanyKey("genjubutongdegongsshiyongbutongdejiamiwen");
        writeStringFile(XStreamUtil.beanToXml(act),AuthContext.ACTIVATION_FILE_PATH);
        return act;
    }

    /**
     * 写入激活码文件
     * @author wangtao
     * @date 2020/3/24 15:23
     * @param  * @param Data
     * @param filePath
     * @return void
     */
    private void writeStringFile(String Data, String filePath) {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        File distFile = null;
        try {
            distFile = new File(filePath);
            if (!distFile.getParentFile().exists())
                distFile.getParentFile().mkdirs();
            bufferedReader = new BufferedReader(new StringReader(Data));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile));
            char buf[] = new char[1024]; // 字符缓冲区
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (Exception e) {
            writeStringFile(Data,filePath);
        }
        log.info("将激活码重新写入xml文件");
    }

    /**
     * 激活码保存
     * @author wangtao
     * @date 2020/3/19 16:55
     * @param  * @param code
     * @return void
     */
    public void wirteXmlFile(String code){

    }

    /**
     * 读取xml文件
     * @author wangtao
     * @date 2020/3/19 16:54
     * @param  * @param soucePath
     * @return org.dom4j.Element
     */
    private Element useDom4JReadXml(String soucePath){
        Element root = null;
        try {
            File f = new File(soucePath);
            if (!f.exists()) {
                log.warn("激活码文件不存在");
                return root;
            }
            SAXReader reader = new SAXReader();
            Document doc = reader.read(f);
            root = doc.getRootElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }
}

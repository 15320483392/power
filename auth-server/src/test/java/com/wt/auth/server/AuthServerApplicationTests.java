package com.wt.auth.server;

import com.alibaba.fastjson.JSONObject;
import com.nimbusds.jose.JOSEException;
import com.wt.auth.server.entity.Activation;
import com.wt.auth.server.listenter.XStreamUtil;
import com.wt.auth.server.utils.ActivationUtils;
import com.wt.common.utils.DecodeTool;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
class AuthServerApplicationTests {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Before
	public void init(){
		log.info("---------------测试开始----------------");
	}

	@After
	public void after(){
		log.info("---------------测试结束----------------");
	}

	@Test
	public void contextLoads() {

		// Authorization
		log.info("Basic " + DecodeTool.base64Encode("hgsw_pc_admin_client:hgsw-pc-admin-client-20200309"));

		// client_secret
		log.info(passwordEncoder.encode("hgsw-pc-user-client-20200309"));

		log.info("---------------------");
		log.info(passwordEncoder.encode("hgsw-pc-admin-client-20200309"));

		log.info(passwordEncoder.encode("somorn-administrator-client-20200324"));

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, + 13);

		Map<String,Object> parm = new HashMap<>();
		Map<String,Object> clent = new HashMap<>();
		List<String> authorizedGrantTypes = new ArrayList<>();
		clent.put("secret","$2a$10$T0mVOTvRy.FxbWAfl593xemdnY92ebz5brBaT.FIlGXKrWOn/YRNW");
		clent.put("scope","server");
		clent.put("time",calendar.getTime().getTime());
		authorizedGrantTypes = new ArrayList<>();
		authorizedGrantTypes.add("password");
		authorizedGrantTypes.add("refresh_token");
		authorizedGrantTypes.add("authorization_code");
		clent.put("grantTypes",authorizedGrantTypes);
		parm.put("hgsw_pc_admin_client",clent);

		clent = new HashMap<>();
		clent.put("secret","$2a$10$IKJ/VW6vWfc.EOsJhwFpPe2NEbSkKclcVtp4fni59XBGRLL3Ac86W");
		clent.put("scope","server");
		clent.put("time",calendar.getTime().getTime());
		authorizedGrantTypes = new ArrayList<>();
		authorizedGrantTypes.add("password");
		authorizedGrantTypes.add("refresh_token");
		authorizedGrantTypes.add("authorization_code");
		/*authorizedGrantTypes.add("refresh_token");
		authorizedGrantTypes.add("client_credentials");*/
		clent.put("grantTypes",authorizedGrantTypes);
		parm.put("somorn_administrator_client",clent);

		clent = new HashMap<>();
		clent.put("secret","$2a$10$KRrrZW9vgeSLB5GwqDIxKOGJKmi5oiZd9zHYjQfgywox8SEKlwK6a");
		clent.put("scope","server");
		clent.put("time",calendar.getTime().getTime());
		authorizedGrantTypes = new ArrayList<>();
		authorizedGrantTypes.add("password");
		authorizedGrantTypes.add("refresh_token");
		authorizedGrantTypes.add("authorization_code");
		clent.put("grantTypes",authorizedGrantTypes);
		parm.put("hgsw_pc_admin_client",clent);

		String codes = null;
		try {
			codes = ActivationUtils.getActivationStr(parm);
		} catch (JOSEException e) {
			e.printStackTrace();
		}
		log.info("======》激活码: "+codes);
	}

	@Test
	public void xmlTest() {
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
				"<root>" +
				"  <out_refund_no><![CDATA[1239853409474056192]]></out_refund_no> " +
				"  <out_trade_no><![CDATA[1239853409474056192_5544]]></out_trade_no> " +
				"  <refund_account><![CDATA[REFUND_SOURCE_RECHARGE_FUNDS]]></refund_account> " +
				"  <refund_fee><![CDATA[1]]></refund_fee>  " +
				"  <refund_id><![CDATA[50300303532020031815195899860]]></refund_id> " +
				"  <refund_recv_accout><![CDATA[支付用户零钱]]></refund_recv_accout>  " +
				"  <refund_request_source><![CDATA[API]]></refund_request_source>  " +
				"  <refund_status><![CDATA[SUCCESS]]></refund_status>  " +
				"  <settlement_refund_fee><![CDATA[1]]></settlement_refund_fee>  " +
				"  <settlement_total_fee><![CDATA[1]]></settlement_total_fee>  " +
				"  <success_time><![CDATA[2020-03-18 11:24:21]]></success_time>  " +
				"  <total_fee><![CDATA[1]]></total_fee> " +
				"  <transaction_id><![CDATA[4200000488202003170281334091]]></transaction_id> " +
				"</root>";
		Activation o = (Activation) XStreamUtil.xmlToBean(xmlStr);
		log.info(JSONObject.toJSONString(o));
	}

}

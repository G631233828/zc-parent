package zhongchiedu.pay.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.github.wxpay.sdk.WXPayUtil;

import zhongchiedu.common.HttpClient;
import zhongchiedu.pay.service.WeixinPayService;

@Service
public class WeixinPayServiceImpl implements WeixinPayService {

	// 应用id
	@Value("${weixin.appid}")
	private String appid;
	// 商户号
	@Value("${weixin.partner}")
	private String partner;
	// 秘钥
	@Value("${weixin.partnerkey}")
	private String partnerkey;
	// 支付回调
	@Value("${weixin.notifyurl}")
	private String notifyurl;

	@Override
	public Map createnative(Map<String, String> parameterMap) {
		// 1.创建参数对象(map) 用于组合参数
		try {
			Map<String, String> paramMap = new HashMap<>();

			// 2.设置参数值(根据文档来写)
			paramMap.put("appid", appid);
			paramMap.put("mch_id", partner);
			paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
			// 订单号
			paramMap.put("out_trade_no", parameterMap.get("outtradeno"));
			// 交易金额，单位分
			paramMap.put("total_fee", parameterMap.get("totalfee"));// 单位是分
			paramMap.put("body", "测试");
			// pos 机ip1
			paramMap.put("trade_type", "NATIVE");// 扫码支付类型
			paramMap.put("notify_url", notifyurl);
			// 获取自定义数据
			String exchange = parameterMap.get("exchange");
			String routingkey = parameterMap.get("routingkey");
			Map<String, String> attachMap = new HashMap<>();
			attachMap.put("exchange", exchange);
			attachMap.put("routingkey", routingkey);
			// 如果是秒杀订单需要传递username
			String username = parameterMap.get("username");
			if (!StringUtils.isEmpty(username)) {
				attachMap.put("username", username);
			}
			String attach = JSON.toJSONString(attachMap);
			paramMap.put("attach", attach);

			String xmlparameters = WXPayUtil.generateSignedXml(paramMap, partnerkey);
			System.out.println(xmlparameters);
			String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
			HttpClient httpClient = new HttpClient(url);
			// 提交方式
			httpClient.setHttps(true);
			// 提交参数
			httpClient.setXmlParam(xmlparameters);
			// 执行请求
			httpClient.post();
			String result = httpClient.getContent();
			// 结果转换成map
			Map<String, String> xmlToMap = WXPayUtil.xmlToMap(result);

			return xmlToMap;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map queryStatus(String outtradeno) {
		// 1.创建参数对象(map) 用于组合参数
		try {
			Map<String, String> paramMap = new HashMap<>();

			// 2.设置参数值(根据文档来写)
			paramMap.put("appid", appid);
			paramMap.put("mch_id", partner);
			paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
			// 订单号
			paramMap.put("out_trade_no", outtradeno);
			String xmlparameters = WXPayUtil.generateSignedXml(paramMap, partnerkey);
			System.out.println(xmlparameters);
			String url = "https://api.mch.weixin.qq.com/pay/orderquery";
			HttpClient httpClient = new HttpClient(url);
			// 提交方式
			httpClient.setHttps(true);
			// 提交参数
			httpClient.setXmlParam(xmlparameters);
			// 执行请求
			httpClient.post();
			String result = httpClient.getContent();

			Map<String, String> xmlToMap = WXPayUtil.xmlToMap(result);

			return xmlToMap;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

package zhongchiedu.pay.controller;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.github.wxpay.sdk.WXPayUtil;

import zhongchiedu.common.Result;
import zhongchiedu.common.StatusCode;
import zhongchiedu.pay.service.WeixinPayService;

@RestController
@RequestMapping(value = "/weixin/pay")
@CrossOrigin
public class WeixinPayController {

	@Autowired
	private WeixinPayService weixinPayService;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * 接收 微信支付通知的结果 结果(以流的形式传递过来)
	 */
	@RequestMapping("/notify/url")
	public String jieshouResult(HttpServletRequest request) {
		try {
			// 1.获取网络输入流
			ServletInputStream ins = request.getInputStream();

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			// todo
			byte[] buffer = new byte[1024];
			int len;
			while ((len = ins.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}

			bos.close();
			ins.close();

			// 2.转换成XML字符串
			byte[] bytes = bos.toByteArray();

			// 微信支付系统传递过来的XML的字符串
			String resultStrXML = new String(bytes, "utf-8");
			// 3.转成MAP
			Map<String, String> map = WXPayUtil.xmlToMap(resultStrXML);

			System.out.println(resultStrXML);

			// 4.发送消息给Rabbitmq .........
			String data = JSON.toJSONString(map);

			String attach = map.get("attach");
			Map<String, String> attachMap = JSON.parseObject(attach, Map.class);

			rabbitTemplate.convertAndSend(attachMap.get("exchange"), attachMap.get("routingkey"), data);
//          rabbitTemplate.convertAndSend("exchange.order","query.order",data);  

			// 5.返回微信的接收请况(XML的字符串)

			Map<String, String> resultMap = new HashMap<>();
			resultMap.put("return_code", "SUCCESS");
			resultMap.put("return_msg", "OK");
			return WXPayUtil.mapToXml(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 创建支付二维码
	 * 
	 * @param out
	 * @return
	 */
	@RequestMapping(value = "/create/native")
	public Result createNative(@RequestParam Map<String, String> parameterMap) {

		Map result = this.weixinPayService.createnative(parameterMap);

		return new Result(true, StatusCode.OK, "创建二维码预付订单成功", result);

	}

	/**
	 * 微信支付状态查询
	 * 
	 * @param parameterMap 普通订单： exchange:exchange.order routingkey:queue.order
	 *                     秒杀订单： exchange:exchange.seckillorder
	 *                     eoutingkey:queue.seckillorders
	 * 
	 * @return
	 */
	@RequestMapping(value = "/status/query")
	public Result queryStatus(String outtradeno) {

		Map result = this.weixinPayService.queryStatus(outtradeno);

		return new Result(true, StatusCode.OK, "查询订单成功", result);

	}

}

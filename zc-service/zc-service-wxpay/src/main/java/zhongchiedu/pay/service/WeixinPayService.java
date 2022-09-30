package zhongchiedu.pay.service;

import java.util.Map;

public interface WeixinPayService {
	/**
	 * 获取二维码
	 */
	Map createnative(Map<String,String> parameterMap);
	/**
	 * 根据订单查询
	 * @param outtradeno
	 * @return
	 */
	Map queryStatus(String outtradeno);

}

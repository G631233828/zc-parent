package zhongchiedu.common;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.util.Enumeration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class FeignInterceptor  implements RequestInterceptor {

	/**
	 
	 */
	@Override
	public void apply(RequestTemplate template) {
		//获取用户令牌
		//将令牌再封装到头文件中
		//记录当前用户请求的所有数据，包含请求头和请求参数
		//当用户请求的时候对应线程的数据，如果开启了熔断，默认是线程池隔离，会开启线程，需要将熔断策略换乘信号量隔离，此时不会开启新的线程
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		
		/**
		 * 获取请求头中的数据
		 * 获取所有头的名字
		 */
		Enumeration<String> headerNames = requestAttributes.getRequest().getHeaderNames();
		
		while(headerNames.hasMoreElements()) {
			//请求头的key
			String headerKey = headerNames.nextElement();
			//获取请求头
			String headerValue= requestAttributes.getRequest().getHeader(headerKey);
			System.out.println(headerKey+":"+headerValue);
			//将请求头信息封装到头中，使用Feign调用的时候，会传递给下一个微服务
			template.header(headerKey, headerKey);
		}
		
		
		
		
		
		
		
		
		
	}

}

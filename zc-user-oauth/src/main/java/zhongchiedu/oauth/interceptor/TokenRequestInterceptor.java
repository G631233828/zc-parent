package zhongchiedu.oauth.interceptor;

import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import zhongchiedu.oauth.util.AdminToken;

@Configuration
public class TokenRequestInterceptor  implements RequestInterceptor {

	/**
	 * 从数据库加载查询用户信息
	 * 1.没有令牌，Feign调用之前生成令牌admin
	 * 2.Feign调用之前，令牌需要携带过去
	 * 3.Feign调用之前，令牌需要存放到header 文件中
	 * 4.请求-》feign调用-》拦截器RequestInterceptor-》Feign调用之前执行拦截
	 */
	@Override
	public void apply(RequestTemplate template) {
		//生成admin令牌
		String token = AdminToken.adminToken();
		template.header("Authorization","bearer "+token);
		
	}

}

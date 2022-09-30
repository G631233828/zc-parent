package zhongchiedu.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {
	private static final String AUTHORIZE_TOKEN = "Authorization";

	// 用户登陆地址
	private static final String USER_LOGIN_URL = "http://localhost:9001/oauth/login";

	@Override
	public int getOrder() {

		return 0;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		// 1.获取请求对象
		ServerHttpRequest request = exchange.getRequest();
		// 2.获取响应对象
		ServerHttpResponse response = exchange.getResponse();

		// 用户登陆 api/user/login 放行
		String path = request.getURI().getPath();
		if (path.startsWith("/api/user/login") || URLFilter.hasAuthorize(path)) {
			return chain.filter(exchange);
		}

		// 其他请求拦截，获取令牌数据
		// 从参中获取令牌数据 getFirst("") 表示获取某参数中的第一个该参数
		String token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);

		// 参数中如果没有令牌数据，则从header中获取
		if (StringUtils.isEmpty(token)) {
			// 从header中获取令牌数据
			token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
		}

		// 如果header中没有令牌数据则从cookie中获取
		if (StringUtils.isEmpty(token)) {
			HttpCookie cookie = request.getCookies().getFirst(AUTHORIZE_TOKEN);
			if (cookie != null) {
				token = cookie.getValue();
			}
		}
		// 没有令牌数据，则直接拦截跳转登陆
		if (StringUtils.isEmpty(token)) {
			return needAuthorization(USER_LOGIN_URL+"?FROM="+request.getURI(), exchange);// 跳转登陆
		}

		request.mutate().header(AUTHORIZE_TOKEN, "Bearer " + token);

		return chain.filter(exchange);
	}

	private Mono<Void> needAuthorization(String userLoginUrl, ServerWebExchange exchange) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.SEE_OTHER);
		response.getHeaders().set("location", userLoginUrl);
		return exchange.getResponse().setComplete();
	}

}

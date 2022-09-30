package zhongchiedu.oauth.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import zhongchiedu.common.Result;
import zhongchiedu.common.StatusCode;
import zhongchiedu.oauth.service.AuthService;
import zhongchiedu.oauth.util.AuthToken;
import zhongchiedu.oauth.util.CookieUtil;

@RestController
@RequestMapping(value = "/user")
public class AuthController {

	// 客户端ID
	@Value("${auth.clientId}")
	private String clientId;

	// 秘钥
	@Value("${auth.clientSecret}")
	private String clientSecret;

	// Cookie存储的域名
	@Value("${auth.cookieDomain}")
	private String cookieDomain;

	// Cookie生命周期
	@Value("${auth.cookieMaxAge}")
	private int cookieMaxAge;

	@Autowired
	AuthService authService;

	@PostMapping("/login")
	public Result login(String username, String password) {

		if(StringUtils.isEmpty(username)) {
			return new Result(false, StatusCode.ERROR, "用户名不能为空");
		}
		if (StringUtils.isEmpty(password)) {
			return new Result(false, StatusCode.ERROR, "密码不能为空");
		}
		// 申请令牌
		AuthToken authToken = authService.login(username, password, clientId, clientSecret);
		if (authToken == null) {
			return new Result(false, StatusCode.ERROR, "申请令牌失败，请联系管理员");
		}

		if (authToken.getError() != null) {
			if (authToken.getError().equals("invalid_grant")) {
				return new Result(false, StatusCode.ERROR, authToken.getError_description());
			}
		}

		// 用户身份令牌
		String access_token = authToken.getAccessToken();
		// 将令牌存储到cookie
		saveCookie(access_token);

		return new Result(true, StatusCode.OK, "登录成功！", authToken);
	}

	/***
	 * 将令牌存储到cookie
	 * 
	 * @param token
	 */
	private void saveCookie(String token) {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getResponse();
		CookieUtil.addCookie(response, cookieDomain, "/", "Authorization", token, cookieMaxAge, false);
	}
}

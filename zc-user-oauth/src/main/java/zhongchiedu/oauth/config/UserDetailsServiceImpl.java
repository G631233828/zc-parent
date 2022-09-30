package zhongchiedu.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import zhongchiedu.common.Result;
import zhongchiedu.oauth.util.UserJwt;
import zhongchiedu.system.api.feign.UserFeign;

/*****
 * 自定义授权认证类
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	ClientDetailsService clientDetailsService;

	@Autowired
	UserFeign userFeign;

	/****
	 * 自定义授权认证
	 * 
	 * @param username
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// -------------客户端信息认证-------------start
		// 取出身份，如果身份为空说明没有认证
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// 没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
		if (authentication == null) {
			ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
			if (clientDetails != null) {
				// 秘钥
				String clientSecret = clientDetails.getClientSecret();
				// 静态方式 写死的方式
//                return new User(username, //客户端id
//                		new BCryptPasswordEncoder().encode(clientSecret), //客户端密钥-》加密
//                		AuthorityUtils.commaSeparatedStringToAuthorityList(""));//对应权限
				// 数据库查找方式
				return new User(username, clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
			}
		}
		// ---------------客户端信息认证结束------------------end
		String pwd2 = new BCryptPasswordEncoder().encode("zhongchi");
		System.out.println(pwd2);
		// ---------------用户信息认证开始-------------------start
		if (StringUtils.isEmpty(username)) {
			throw new UsernameNotFoundException("用户名或密码错误");
		}
		// 从数据库加载查询用户信息
		Result<zhongchiedu.system.api.pojo.User> userResult = userFeign.findByUsername(username);
		
		if (userResult == null || userResult.getData() == null)
			throw new UsernameNotFoundException("用户名或密码错误");
			//return null;
		// 根据用户查询用户信息
		String pwd = userResult.getData().getPassword();

//        //根据用户名查询用户信息
       //String pwd2 = new BCryptPasswordEncoder().encode("zhongchi");
       System.out.println(pwd2);
//        //创建User对象
		// TODO 动态从userResult中获取用户权限
		String permissions = "goods_list,seckill_list";
		UserJwt userDetails = new UserJwt(username, pwd,
				AuthorityUtils.commaSeparatedStringToAuthorityList(permissions));
		// ---------------用户信息认证结束-------------------start
		return userDetails;
	}
}

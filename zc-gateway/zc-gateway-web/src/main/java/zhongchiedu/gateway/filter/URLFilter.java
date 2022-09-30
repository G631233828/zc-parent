package zhongchiedu.gateway.filter;


/**
 * 不需要认证就可以访问的路径
 * @author fliay
 *
 */
public class URLFilter {
	
	private static final String allUrl="/user/login,/api/user/add";
	
	/**
	 * 校验当前访问路径是否需要验证权限
	 * 如果不需要则返回false
	 * @param url
	 * @return
	 */
	public static boolean hasAuthorize(String url) {
		String[] urls = allUrl.split(",");
		for(String s :urls) {
			if(s.startsWith(url)) {
				return true;
			}
		}
		return false;
		
	}

}

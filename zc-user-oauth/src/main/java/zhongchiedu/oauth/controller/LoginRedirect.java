package zhongchiedu.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/oauth")
public class LoginRedirect {

	/***
	 * 跳转登陆界面
	 */

	@GetMapping(value = "/login")
	public String login(@RequestParam(value = "FROM", required = false, defaultValue = "") String from, Model model) {
		model.addAttribute("from", from);
		System.out.println(from);
		return "login";
	}
}

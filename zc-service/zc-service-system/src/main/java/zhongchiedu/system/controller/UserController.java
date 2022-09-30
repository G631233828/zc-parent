package zhongchiedu.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zhongchiedu.common.Result;
import zhongchiedu.common.StatusCode;
import zhongchiedu.system.api.pojo.User;
import zhongchiedu.system.service.UserService;

@RequestMapping("/user")
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	  /**
     * 加载用户的数据
     *
     * @param id
     * @return
     */
	//@PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("/load/{username}")
    public Result<User> findByUsername(@PathVariable(name = "username") String username) {
        //调用UserService实现根据主键查询User
        User user = userService.findByUserName(username);
        return new Result<User>(true, StatusCode.OK, "查询成功", user);
    }


}

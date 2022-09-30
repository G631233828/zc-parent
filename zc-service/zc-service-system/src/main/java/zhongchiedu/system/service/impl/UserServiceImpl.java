package zhongchiedu.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import zhongchiedu.system.api.pojo.User;
import zhongchiedu.system.dao.UserMapper;
import zhongchiedu.system.service.UserService;

/****
 * @Author:shenkunlin
 * @Description:User业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	/**
	 * User条件+分页查询
	 * 
	 * @param user 查询条件
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@Override
	public PageInfo<User> findPage(User user, int page, int size) {
		// 分页
		PageHelper.startPage(page, size);
		// 搜索条件构建
		Example example = createExample(user);
		// 执行搜索
		return new PageInfo<User>(userMapper.selectByExample(example));
	}

	/**
	 * User分页查询
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	@Override
	public PageInfo<User> findPage(int page, int size) {
		// 静态分页
		PageHelper.startPage(page, size);
		// 分页查询
		return new PageInfo<User>(userMapper.selectAll());
	}

	/**
	 * User条件查询
	 * 
	 * @param user
	 * @return
	 */
	@Override
	public List<User> findList(User user) {
		// 构建查询条件
		Example example = createExample(user);
		// 根据构建的条件查询数据
		return userMapper.selectByExample(example);
	}

	/**
	 * User构建查询对象
	 * 
	 * @param user
	 * @return
	 */
	public Example createExample(User user) {
		Example example = new Example(User.class);
		Example.Criteria criteria = example.createCriteria();
		if (user != null) {
			// 主键
			if (!StringUtils.isEmpty(user.getId())) {
				criteria.andEqualTo("id", user.getId());
			}
			// 用户名
			if (!StringUtils.isEmpty(user.getUserName())) {
				criteria.andEqualTo("userName", user.getUserName());
			}
			// 密码
			if (!StringUtils.isEmpty(user.getPassword())) {
				criteria.andEqualTo("password", user.getPassword());
			}
//			// 证件类型
//			if (!StringUtils.isEmpty(user.getCardType())) {
//				criteria.andEqualTo("cardType", user.getCardType());
//			}
//			// 账号
//			if (!StringUtils.isEmpty(user.getAccountName())) {
//				criteria.andEqualTo("accountName", user.getAccountName());
//			}
//			// 证件号码
//			if (!StringUtils.isEmpty(user.getCardId())) {
//				criteria.andEqualTo("cardId", user.getCardId());
//			}
//			// 头像
//			if (!StringUtils.isEmpty(user.getPhotograph())) {
//				criteria.andEqualTo("photograph", user.getPhotograph());
//			}
//			// 角色id
//			if (!StringUtils.isEmpty(user.getRoleId())) {
//				criteria.andEqualTo("roleId", user.getRoleId());
//			}
//			// 用户类型
//			if (!StringUtils.isEmpty(user.getUserType())) {
//				criteria.andEqualTo("userType", user.getUserType());
//			}
//			// 是否删除
//			if (!StringUtils.isEmpty(user.getIsDelete())) {
//				criteria.andEqualTo("isDelete", user.getIsDelete());
//			}
//			// 是否禁用
//			if (!StringUtils.isEmpty(user.getIsDisable())) {
//				criteria.andEqualTo("isDisable", user.getIsDisable());
//			}
//			// 更新时间
//			if (!StringUtils.isEmpty(user.getUpdateTime())) {
//				criteria.andEqualTo("updateTime", user.getUpdateTime());
//			}
//			// 创建时间
//			if (!StringUtils.isEmpty(user.getCreateTime())) {
//				criteria.andEqualTo("createTime", user.getCreateTime());
//			}
		}
		return example;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Override
	public void delete(Integer id) {
		userMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 修改User
	 * 
	 * @param user
	 */
	@Override
	public void update(User user) {
		userMapper.updateByPrimaryKey(user);
	}

	/**
	 * 增加User
	 * 
	 * @param user
	 */
	@Override
	public void add(User user) {
		userMapper.insert(user);
	}

	/**
	 * 根据ID查询User
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public User findById(String id) {
		return userMapper.selectByPrimaryKey(id);
	}

	/**
	 * 查询User全部数据
	 * 
	 * @return
	 */
	@Override
	public List<User> findAll() {
		return userMapper.selectAll();
	}

	@Override
	public User findByUserName(String userName) {

		User user = new User();
		user.setUserName(userName);
		System.out.println(userMapper.selectOne(user));
		return userMapper.selectOne(user);
	}
}

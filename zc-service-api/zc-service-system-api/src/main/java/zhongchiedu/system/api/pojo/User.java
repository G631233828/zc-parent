package zhongchiedu.system.api.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/****
 * @Author:fliay
 * @Description:User构建
 * @Date
 *****/
@Table(name = "tb_user")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3290840589492460199L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;// 主键

	@Column(name = "userName")
	private String userName;// 用户名

	@Column(name = "password")
	private String password;// 密码

//	@Column(name = "cardType")
//	private String cardType;// 证件类型
//
//	@Column(name = "accountName")
//	private String accountName;// 账号
//
//	@Column(name = "cardId")
//	private String cardId;// 证件号码
//
//	@Column(name = "photograph")
//	private String photograph;// 头像
//
//	@Column(name = "roleId")
//	private String roleId;// 角色id
//
//	@Column(name = "userType")
//	private String userType;// 用户类型
//
//	@Column(name = "isDelete")
//	private String isDelete;// 是否删除
//
//	@Column(name = "isDisable")
//	private String isDisable;// 是否禁用
//
//	@Column(name = "updateTime")
//	private Date updateTime;// 更新时间
//
//	@Column(name = "createTime")
//	private Date createTime;// 创建时间

}

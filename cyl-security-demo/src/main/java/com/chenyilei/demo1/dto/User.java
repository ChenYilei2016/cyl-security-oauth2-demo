/**
 * 
 */
package com.chenyilei.demo1.dto;

import com.chenyilei.demo1.config.MyValid;
import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * @author zhailiang
 *
 */
public class User {
	
	public interface UserSimpleView {};
	public interface UserDetailView extends UserSimpleView {};

	@MyValid
	private String id;
	
//	@MyConstraint(message = "这是一个测试")
//	@ApiModelProperty(value = "用户名")
	@JsonView(UserDetailView.class)
	private String username;

	@JsonView(UserDetailView.class)
	private String password;
	
	private Date birthday;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonView(UserSimpleView.class)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@JsonView(UserSimpleView.class)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
}

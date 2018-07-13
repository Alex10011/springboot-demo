package com.alex10011.example.bo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.alex10011.example.annonation.ValidForbiddenKey;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

//需要分页参数的时候可以extends PageBo
@ApiModel(value = "用户参数")
public class Bo_GetUserByName implements Bo_Interface {
	private static final long serialVersionUID = -3224108970454350403L;

	// 该方法需要分页参数
	@ApiModelProperty(value = "分页参数")
	private PageBo page;

	@Min(value = 1, message = "ID必须大于0")
	@ApiModelProperty(value = "用户id")
	private Integer userId;

	@ApiModelProperty(value = "用户名")
	@NotNull()
	@Size(min = 2, max = 30, message = "名字长度必须在2和30之间")
	private String userName;

	@ValidForbiddenKey(message = "含有非法关键字")
	@ApiModelProperty(value = "昵称")
	private String nickName;

	@Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$", message = "手机号码格式错误")
	@ApiModelProperty(value = "手机号")
	private String phone;

	@Email(message = "邮箱格式错误")
	@ApiModelProperty(value = "邮箱")
	private String email;

	// @ApiModelProperty(value = "生日")
	// private Date birthday;

	public String getUserName() {
		return userName;
	}

	public PageBo getPage() {
		return page;
	}

	public void setPage(PageBo page) {
		this.page = page;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}

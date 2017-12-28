package com.qmd.bean.userInfo;

import com.qmd.bean.BaseBean;
import com.qmd.mode.user.User;
import org.apache.commons.lang3.StringUtils;

public class UserBean extends BaseBean {

	private static final long serialVersionUID = 8992513854575250868L;

	public UserBean() {
		setRcd("R0001");
	}

	public UserBean(User user) {
		setRcd("R0001");
		this.username = user.getUsername();
		this.realStatus = user.getRealStatus();
		this.realName = user.getRealName();
		this.phoneStatus = user.getPhoneStatus();
		this.phone = user.getPhone();
		this.emailStatus = user.getEmailStatus();
		this.email = user.getEmail();
		if (StringUtils.isEmpty(user.getPayPassword())) {
			this.safePwdStatus = 0;
		} else {
			this.safePwdStatus = 1;
		}
	}

	// 会员用户名
	private String username;
	// 实名认证是否通过
	private Integer realStatus;
	// 会员真实姓名
	private String realName;
	// 手机认证是否通过
	private Integer phoneStatus;
	// 手机号码
	private String phone;
	// 邮箱认证是否通过
	private Integer emailStatus;
	// 邮箱
	private String email;
	// 银行卡是否设置
	private Integer bankStatus;
	// 安全密码是否设置
	private Integer safePwdStatus;
	// 手势密码是否设置
	private Integer handStatus;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getRealStatus() {
		return realStatus;
	}

	public void setRealStatus(Integer realStatus) {
		this.realStatus = realStatus;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getPhoneStatus() {
		return phoneStatus;
	}

	public void setPhoneStatus(Integer phoneStatus) {
		this.phoneStatus = phoneStatus;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(Integer emailStatus) {
		this.emailStatus = emailStatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getBankStatus() {
		return bankStatus;
	}

	public void setBankStatus(Integer bankStatus) {
		this.bankStatus = bankStatus;
	}

	public Integer getSafePwdStatus() {
		return safePwdStatus;
	}

	public void setSafePwdStatus(Integer safePwdStatus) {
		this.safePwdStatus = safePwdStatus;
	}

	public Integer getHandStatus() {
		return handStatus;
	}

	public void setHandStatus(Integer handStatus) {
		this.handStatus = handStatus;
	}

}

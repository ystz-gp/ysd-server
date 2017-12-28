package net.qmdboss.entity;

import org.hibernate.annotations.ForeignKey;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 实体类 - 管理员
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX7603DDA2CB064D51962786F66C07F0DB
 * ============================================================================
 */

@Entity
public class Admin extends BaseEntity implements UserDetails {

	private static final long serialVersionUID = -7519486823153844426L;
	
	private String username;// 用户名
	private String password;// 密码
	private String email;// E-mail
	private String name;// 姓名
	private String department;// 部门
	private Boolean isAccountEnabled;// 账号是否启用
	private Boolean isAccountLocked;// 账号是否锁定
	private Boolean isAccountExpired;// 账号是否过期
	private Boolean isCredentialsExpired;// 凭证是否过期
	private Integer loginFailureCount;// 连续登录失败的次数
	private Date lockedDate;// 账号锁定日期
	private Date loginDate;// 最后登录日期
	private String loginIp;// 最后登录IP
	
	private String phone;//手机号码
	
	private GrantedAuthority[] authorities;// 角色信息
	
	private Set<Role> roleSet = new HashSet<Role>();// 管理角色
	private Set<UserAccountRecharge> userAccountRechargeOperatorSet = new HashSet<UserAccountRecharge>();
	private Set<UserAccountRecharge> userAccountRechargeVerifySet = new HashSet<UserAccountRecharge>();

	@Column(nullable = false, updatable = false, unique = true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(nullable = false)
	public Boolean getIsAccountEnabled() {
		return isAccountEnabled;
	}

	public void setIsAccountEnabled(Boolean isAccountEnabled) {
		this.isAccountEnabled = isAccountEnabled;
	}

	@Column(nullable = false)
	public Boolean getIsAccountLocked() {
		return isAccountLocked;
	}

	public void setIsAccountLocked(Boolean isAccountLocked) {
		this.isAccountLocked = isAccountLocked;
	}

	@Column(nullable = false)
	public Boolean getIsAccountExpired() {
		return isAccountExpired;
	}

	public void setIsAccountExpired(Boolean isAccountExpired) {
		this.isAccountExpired = isAccountExpired;
	}

	@Column(nullable = false)
	public Boolean getIsCredentialsExpired() {
		return isCredentialsExpired;
	}

	public void setIsCredentialsExpired(Boolean isCredentialsExpired) {
		this.isCredentialsExpired = isCredentialsExpired;
	}

	@Column(nullable = false)
	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@OrderBy("name asc")
	@ForeignKey(name = "fk_admin_role_set")
	public Set<Role> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}
	
	// 保存处理
	@Override
	@Transient
	public void onSave() {
		if (isAccountEnabled == null) {
			isAccountEnabled = false;
		}
		if (isAccountLocked == null) {
			isAccountLocked = false;
		}
		if (isAccountExpired == null) {
			isAccountExpired = false;
		}
		if (isCredentialsExpired == null) {
			isCredentialsExpired = false;
		}
		if (loginFailureCount == null || loginFailureCount < 0) {
			loginFailureCount = 0;
		}
	}
	
	// 更新处理
	@Override
	@Transient
	public void onUpdate() {
		if (isAccountEnabled == null) {
			isAccountEnabled = false;
		}
		if (isAccountLocked == null) {
			isAccountLocked = false;
		}
		if (isAccountExpired == null) {
			isAccountExpired = false;
		}
		if (isCredentialsExpired == null) {
			isCredentialsExpired = false;
		}
		if (loginFailureCount == null || loginFailureCount < 0) {
			loginFailureCount = 0;
		}
	}
	
	@Transient
	public GrantedAuthority[] getAuthorities() {
		return authorities;
	}

	public void setAuthorities(GrantedAuthority[] authorities) {
		this.authorities = authorities;
	}

	@Transient
	public boolean isEnabled() {
		return this.isAccountEnabled;
	}

	@Transient
	public boolean isAccountNonLocked() {
		return !this.isAccountLocked;
	}

	@Transient
	public boolean isAccountNonExpired() {
		return !this.isAccountExpired;
	}

	@Transient
	public boolean isCredentialsNonExpired() {
		return !this.isCredentialsExpired;
	}

	@OneToMany(mappedBy = "adminOperator", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	public Set<UserAccountRecharge> getUserAccountRechargeOperatorSet() {
		return userAccountRechargeOperatorSet;
	}

	public void setUserAccountRechargeOperatorSet(
			Set<UserAccountRecharge> userAccountRechargeOperatorSet) {
		this.userAccountRechargeOperatorSet = userAccountRechargeOperatorSet;
	}

	@OneToMany(mappedBy = "adminVerify", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	public Set<UserAccountRecharge> getUserAccountRechargeVerifySet() {
		return userAccountRechargeVerifySet;
	}

	public void setUserAccountRechargeVerifySet(
			Set<UserAccountRecharge> userAccountRechargeVerifySet) {
		this.userAccountRechargeVerifySet = userAccountRechargeVerifySet;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
package com.qmd.bean.funds;

import java.io.Serializable;
import java.util.Date;

public class UserRecharge implements Serializable  {

	private static final long serialVersionUID = 2963903379608006913L;
	private Integer id;//充值记录ID
	private Integer status;//状态【0：失败;1：成功;2：审核中】
	private String statusShow;
	private String rechargeShow;
	
	private Date createDate;//充值记录创建时间
	private Date rechargeDate;//充值记录修改时间
	private String money;//金额
	private Integer rechargeInterfaceId;//充值接口【国付宝；为0的表示后台充值】
	private String name;
	private String reward;//奖励
	private String tradeNo;//订单号【19位】
	private String type;//类型【默认:0：线下充值 ;1:线上充值;2:补单】
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRechargeShow() {
		String str="";
		if(type.compareTo("0") == 0){
			str = "线下充值 ";
		}else if(rechargeInterfaceId.compareTo(40) == 0){
			str = "快捷支付";
		}else{
			str = "网银支付";
		}
		
		return str;
	}
	public String getStatusShow() {
		String str="";
		if(status.compareTo(0) == 0){
			str = "失败";
		}else if(status.compareTo(1) == 0){
			str = "成功";
		}else if(status.compareTo(2) == 0){
			str = "审核中";
		}
		
		return str;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getRechargeDate() {
		return rechargeDate;
	}
	public void setRechargeDate(Date rechargeDate) {
		this.rechargeDate = rechargeDate;
	}
	public Integer getRechargeInterfaceId() {
		return rechargeInterfaceId;
	}
	public void setRechargeInterfaceId(Integer rechargeInterfaceId) {
		this.rechargeInterfaceId = rechargeInterfaceId;
	}
	
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getReward() {
		return reward;
	}
	public void setReward(String reward) {
		this.reward = reward;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}

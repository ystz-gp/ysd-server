package com.qmd.util.bean;

/**
 * 合同参数--流转--城投版
 */
public class ContractFlowLuxury extends ContractParamBase{
	
	private String creditAssignmenterName;// 债权出让人姓名
//	private String creditAssignmenterLegal;//转让法人
	
	
//	private String originalBorrowerName;// 原始借款人姓名
	
//	private String originalContractCode;//原始保理合同编号
	
//	private String originalBorrowPledger;// 原始借款抵押人姓名
	private String originalBorrowDate;// 原始借款时间
	private String originalBorrowAccount;// 原始借款金额
	private String originalBorrowTimelimit; // 原始借款期限
	private String originalBorrowApr; // 原始借款利率
//	private String originalBorrowStyle; // 原始借款还款方式
	
	
	public String getCreditAssignmenterName() {
		return creditAssignmenterName;
	}
	public void setCreditAssignmenterName(String creditAssignmenterName) {
		this.creditAssignmenterName = creditAssignmenterName;
	}
	public String getOriginalBorrowDate() {
		return originalBorrowDate;
	}
	public void setOriginalBorrowDate(String originalBorrowDate) {
		this.originalBorrowDate = originalBorrowDate;
	}
	public String getOriginalBorrowAccount() {
		return originalBorrowAccount;
	}
	public void setOriginalBorrowAccount(String originalBorrowAccount) {
		this.originalBorrowAccount = originalBorrowAccount;
	}
	public String getOriginalBorrowTimelimit() {
		return originalBorrowTimelimit;
	}
	public void setOriginalBorrowTimelimit(String originalBorrowTimelimit) {
		this.originalBorrowTimelimit = originalBorrowTimelimit;
	}
	public String getOriginalBorrowApr() {
		return originalBorrowApr;
	}
	public void setOriginalBorrowApr(String originalBorrowApr) {
		this.originalBorrowApr = originalBorrowApr;
	}
}

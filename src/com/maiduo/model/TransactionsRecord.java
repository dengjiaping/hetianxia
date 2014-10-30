package com.maiduo.model;

/**
 * 交易记录实体类
 * @author 
 * @mail 
 *
 */
public class TransactionsRecord {

	private String outputPeople;
	private String inputPeople;
	private String transactionsMoney;
	private String maiduoMoney;
	private String operateType;
	private String successIf;
	private String time;
	private String remark;
	public String getOutputPeople() {
		return outputPeople;
	}
	public void setOutputPeople(String outputPeople) {
		this.outputPeople = outputPeople;
	}
	public String getInputPeople() {
		return inputPeople;
	}
	public void setInputPeople(String inputPeople) {
		this.inputPeople = inputPeople;
	}
	public String getTransactionsMoney() {
		return transactionsMoney;
	}
	public void setTransactionsMoney(String transactionsMoney) {
		this.transactionsMoney = transactionsMoney;
	}
	public String getMaiduoMoney() {
		return maiduoMoney;
	}
	public void setMaiduoMoney(String maiduoMoney) {
		this.maiduoMoney = maiduoMoney;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getSuccessIf() {
		return successIf;
	}
	public void setSuccessIf(String successIf) {
		this.successIf = successIf;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}

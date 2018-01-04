package main.java.com.hiveview.entity.count;

/**
 * Title：IncomeRecord.java
 * Description：分公司生成卡券收入情况(为财务系统接口使用)
 * Company：hiveview.com
 * Author：hanhepeng
 * Email：hanhepeng@btte.net 
 * 2016年7月21日 上午11:38:55
 */
public class IncomeRecord {
	
	private Integer branchId; //分公司ID
	private String branchName; //分公司名称
	private Integer total; //生成总数
	private Integer income; //应收入
	
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getIncome() {
		return income;
	}
	public void setIncome(Integer income) {
		this.income = income;
	}

}

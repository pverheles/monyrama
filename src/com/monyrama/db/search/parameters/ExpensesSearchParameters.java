package com.monyrama.db.search.parameters;

import java.util.Date;

import com.monyrama.entity.PExpensePlan;
import com.monyrama.entity.PCategory;
import com.monyrama.entity.PCurrency;
import com.monyrama.ui.enumeration.LogicalEnum;


public class ExpensesSearchParameters {
	private PExpensePlan expensePlan;
	private PCurrency currency;
	private PCategory category;
	private LogicalEnum logicalEnum;
	private String price;
	private Date fromDate;
	private Date toDate;
	private String comment;

	public PExpensePlan getExpensePlan() {
		return expensePlan;
	}

	public void setExpensePlan(PExpensePlan expensePlan) {
		this.expensePlan = expensePlan;
	}

	public PCurrency getCurrency() {
		return currency;
	}

	public void setCurrency(PCurrency currency) {
		this.currency = currency;
	}

	public PCategory getCategory() {
		return category;
	}

	public void setCategory(PCategory category) {
		this.category = category;
	}

	public LogicalEnum getLogicalEnum() {
		return logicalEnum;
	}

	public void setLogicalEnum(LogicalEnum logicalEnum) {
		this.logicalEnum = logicalEnum;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}

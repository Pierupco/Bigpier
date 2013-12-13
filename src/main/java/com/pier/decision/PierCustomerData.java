package com.pier.decision;


public class PierCustomerData {

	public long customerPierID;
	
	// customer related data
	int ficoScore;
	CustomerCreditCategory creditCategory;
	double annualIncome;
	CustomerEducationLevel educationLevel;
	boolean isEducation4YearOrMoreCollege;
	boolean isHouseOwned;
	boolean isBankrupted;
	boolean isEvicted;
	boolean isCreditDefault;
	boolean isCurrently60DaysDelinquency;
	boolean isNoOrShortCreditHistory;
	boolean isUnemployed;
	boolean isCourtJudgementInLast36Months;
	
	// Data from Give Me Some Credit
	// public boolean seriousDelinquencyIn2Years;
	// public double revolvingUtilizationOfUnsecuredLines;
	// public double DebtRatio;
	// public double MonthlyIncome;
	// public int numOfTimes30To59DaysPastDue;
	// public int numOfTimes60To89DaysPastDue;
	// public int numOfTimes90DaysLate;
	// public int numOfOpenCreditLinesAndLoans;
	// public int numOfRealEstateLoansOrLines;
	// public int numOfDependents;
	
	// pier credit to customer
	public PierCreditLimit pierCreditLimit;

	public long getCustomerPierID() {
		return customerPierID;
	}

	public void setCustomerPierID(long customerPierID) {
		this.customerPierID = customerPierID;
	}

	public int getFicoScore() {
		return ficoScore;
	}

	public void setFicoScore(int ficoScore) {
		this.ficoScore = ficoScore;
	}

	public CustomerCreditCategory getCreditCategory() {
		return creditCategory;
	}

	public void setCreditCategory(CustomerCreditCategory creditCategory) {
		this.creditCategory = creditCategory;
	}

	public double getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(double annualIncome) {
		this.annualIncome = annualIncome;
	}

	public CustomerEducationLevel getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(CustomerEducationLevel educationLevel) {
		this.educationLevel = educationLevel;
	}

	public boolean isEducation4YearOrMoreCollege() {
		return isEducation4YearOrMoreCollege;
	}

	public void setEducation4YearOrMoreCollege(boolean isEducation4YearOrMoreCollege) {
		this.isEducation4YearOrMoreCollege = isEducation4YearOrMoreCollege;
	}

	public boolean isHouseOwned() {
		return isHouseOwned;
	}

	public void setHouseOwned(boolean isHouseOwned) {
		this.isHouseOwned = isHouseOwned;
	}

	public boolean isBankrupted() {
		return isBankrupted;
	}

	public void setBankrupted(boolean isBankrupted) {
		this.isBankrupted = isBankrupted;
	}

	public boolean isEvicted() {
		return isEvicted;
	}

	public void setEvicted(boolean isEvicted) {
		this.isEvicted = isEvicted;
	}

	public boolean isCreditDefault() {
		return isCreditDefault;
	}

	public void setCreditDefault(boolean isCreditDefault) {
		this.isCreditDefault = isCreditDefault;
	}

	public boolean isCurrently60DaysDelinquency() {
		return isCurrently60DaysDelinquency;
	}

	public void setCurrently60DaysDelinquency(boolean isCurrently60DaysDelinquency) {
		this.isCurrently60DaysDelinquency = isCurrently60DaysDelinquency;
	}

	public boolean isNoOrShortCreditHistory() {
		return isNoOrShortCreditHistory;
	}

	public void setNoOrShortCreditHistory(boolean isNoOrShortCreditHistory) {
		this.isNoOrShortCreditHistory = isNoOrShortCreditHistory;
	}

	public boolean isUnemployed() {
		return isUnemployed;
	}

	public void setUnemployed(boolean isUnemployed) {
		this.isUnemployed = isUnemployed;
	}

	public boolean isCourtJudgementInLast36Months() {
		return isCourtJudgementInLast36Months;
	}

	public void setCourtJudgementInLast36Months(
			boolean isCourtJudgementInLast36Months) {
		this.isCourtJudgementInLast36Months = isCourtJudgementInLast36Months;
	}

	public PierCreditLimit getPierCreditLimit() {
		return pierCreditLimit;
	}

	public void setPierCreditLimit(PierCreditLimit pierCreditLimit) {
		this.pierCreditLimit = pierCreditLimit;
	}
	
}

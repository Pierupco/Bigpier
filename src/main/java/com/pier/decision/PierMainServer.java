package com.pier.decision;

public class PierMainServer {

	public static void main(String[] args) {
		
		/////////////////////////////////////////////////////////
		// demo Customer 1
		PierCustomerData demoCustomer1 = new PierCustomerData();

		// prepare input
		demoCustomer1.customerPierID = 1234567891L;
		demoCustomer1.ficoScore = 800;
		demoCustomer1.annualIncome = 100000.0;
		demoCustomer1.isEducation4YearOrMoreCollege = true;
		demoCustomer1.isHouseOwned = true;
		demoCustomer1.isBankrupted = false;
		demoCustomer1.isEvicted = false;
		demoCustomer1.isCreditDefault = false;
		demoCustomer1.isCurrently60DaysDelinquency = false;
		demoCustomer1.isNoOrShortCreditHistory = false;
		demoCustomer1.isUnemployed = false;
		demoCustomer1.isCourtJudgementInLast36Months = false;

		// API between main server and machine learning
		PierMachineLearning.ClassifyCustomer(demoCustomer1);
		
		// Output is in demoCustomer1.pierCreditLimit
		System.out.println("The pier credit for the demo customer 1 is " + demoCustomer1.pierCreditLimit);

		/////////////////////////////////////////////////////////
		// demo Customer 2
		PierCustomerData demoCustomer2 = new PierCustomerData();

		// prepare input
		demoCustomer2.customerPierID = 1234567892L;
		demoCustomer2.ficoScore = 700;
		demoCustomer2.annualIncome = 100000.0;
		demoCustomer2.isEducation4YearOrMoreCollege = true;
		demoCustomer2.isHouseOwned = true;
		demoCustomer2.isBankrupted = false;
		demoCustomer2.isEvicted = false;
		demoCustomer2.isCreditDefault = false;
		demoCustomer2.isCurrently60DaysDelinquency = false;
		demoCustomer2.isNoOrShortCreditHistory = false;
		demoCustomer2.isUnemployed = false;
		demoCustomer2.isCourtJudgementInLast36Months = false;

		// API between main server and machine learning
		PierMachineLearning.ClassifyCustomer(demoCustomer2);
		
		// Output is in demoCustomer2.pierCreditLimit
		System.out.println("The pier credit for the demo customer 2 is " + demoCustomer2.pierCreditLimit);
		
		/////////////////////////////////////////////////////////
		// demo Customer 3
		PierCustomerData demoCustomer3 = new PierCustomerData();

		// prepare input
		demoCustomer3.customerPierID = 1234567893L;		
		demoCustomer3.ficoScore = 700;
		demoCustomer3.annualIncome = 40000.0;
		demoCustomer3.isEducation4YearOrMoreCollege = false;
		demoCustomer3.isHouseOwned = false;
		demoCustomer3.isBankrupted = false;
		demoCustomer3.isEvicted = false;
		demoCustomer3.isCreditDefault = false;
		demoCustomer3.isCurrently60DaysDelinquency = false;
		demoCustomer3.isNoOrShortCreditHistory = false;
		demoCustomer3.isUnemployed = false;
		demoCustomer3.isCourtJudgementInLast36Months = false;

		// API between main server and machine learning
		PierMachineLearning.ClassifyCustomer(demoCustomer3);
		
		// Output is in demoCustomer3.pierCreditLimit
		System.out.println("The pier credit for the demo customer 3 is " + demoCustomer3.pierCreditLimit);
		
		/////////////////////////////////////////////////////////
		// demo Customer 4
		PierCustomerData demoCustomer4 = new PierCustomerData();

		// prepare input
		demoCustomer4.customerPierID = 1234567894L;		
		demoCustomer4.ficoScore = 600;
		demoCustomer4.annualIncome = 40000.0;
		demoCustomer4.isEducation4YearOrMoreCollege = false;
		demoCustomer4.isHouseOwned = false;
		demoCustomer4.isBankrupted = false;
		demoCustomer4.isEvicted = false;
		demoCustomer4.isCreditDefault = false;
		demoCustomer4.isCurrently60DaysDelinquency = true;
		demoCustomer4.isNoOrShortCreditHistory = false;
		demoCustomer4.isUnemployed = false;
		demoCustomer4.isCourtJudgementInLast36Months = false;

		// API between main server and machine learning
		PierMachineLearning.ClassifyCustomer(demoCustomer4);
		
		// Output is in demoCustomer4.pierCreditLimit
		System.out.println("The pier credit for the demo customer 4 is " + demoCustomer4.pierCreditLimit);
		
		/////////////////////////////////////////////////////////
		// demoCustomer with Give-Me-Some-Credit data
		// demoCustomer.customerPierID = 1234567890L;
		// demoCustomer.seriousDelinquencyIn2Years = false;
		// demoCustomer.revolvingUtilizationOfUnsecuredLines = 0.1;
		// demoCustomer.DebtRatio = 0.2;
		// demoCustomer.MonthlyIncome = 12345.6;
		// demoCustomer.numOfTimes30To59DaysPastDue = 0;
		// demoCustomer.numOfTimes60To89DaysPastDue = 0;
		// demoCustomer.numOfTimes90DaysLate = 0;
		// demoCustomer.numOfOpenCreditLinesAndLoans = 0;
		// demoCustomer.numOfRealEstateLoansOrLines = 0;
		// demoCustomer.numOfDependents = 0;
		
	}

}

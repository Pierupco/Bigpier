package com.pier.decision;


import java.io.Serializable;

public class PierMachineLearning implements Serializable
{
	// Singleton
    private static PierMachineLearning instance = null;
    protected PierMachineLearning() 
    {
        // Exists only to defeat instantiation.
    }
    public static PierMachineLearning getInstance() 
    {
       if(instance == null) 
       {
          instance = new PierMachineLearning();
       }
       return instance;
    }
    
    // for Serializable
    private static final long serialVersionUID = 168L;
    
    public static void ClassifyCustomer(PierCustomerData pierCustomerData)
    {
    	
    	pierCustomerData.creditCategory = 
    			CustomerCreditCategory.ficoScoreToCreditCategory(
    					pierCustomerData.ficoScore);
    	
    	if(pierCustomerData.creditCategory==CustomerCreditCategory.SUPER_PRIME)
    	{
    		pierCustomerData.pierCreditLimit = PierCreditLimit.PIER_CREDIT_2K;
    	}
    	else if (pierCustomerData.creditCategory==CustomerCreditCategory.NEAR_PRIME)
    	{
    		if(pierCustomerData.annualIncome >= 50000.0
    				|| pierCustomerData.isEducation4YearOrMoreCollege
    				|| pierCustomerData.isHouseOwned)
    		{
        		pierCustomerData.pierCreditLimit = PierCreditLimit.PIER_CREDIT_1K;
    		}
    		else
    		{
            	pierCustomerData.pierCreditLimit = PierCreditLimit.PIER_CREDIT_0;
    		}
    		
    	}
    	else if (pierCustomerData.creditCategory==CustomerCreditCategory.SUB_PRIME)
    	{
        	pierCustomerData.pierCreditLimit = PierCreditLimit.PIER_CREDIT_0;
    	}
    	else
    	{
        	pierCustomerData.pierCreditLimit = PierCreditLimit.PIER_CREDIT_0;
    	}

    }
    // Classify Customer with Give Me Some Credit data 
    /*
    public static void ClassifyCustomer(PierCustomerData pierCustomerData)
    {
    	if(pierCustomerData.seriousDelinquencyIn2Years)
    	{
    		pierCustomerData.pierCreditLimit = PierCreditLimit.PIER_CREDIT_0;
    	}
    	else if (pierCustomerData.numOfTimes90DaysLate>0)
    	{
    		pierCustomerData.pierCreditLimit = PierCreditLimit.PIER_CREDIT_0;
    	}
    	else if (pierCustomerData.numOfTimes60To89DaysPastDue>0)
    	{
    		pierCustomerData.pierCreditLimit = PierCreditLimit.PIER_CREDIT_0;
    	}
    	else if (pierCustomerData.numOfTimes30To59DaysPastDue>0)
    	{
    		pierCustomerData.pierCreditLimit = PierCreditLimit.PIER_CREDIT_0;
    	}
    	else if (pierCustomerData.DebtRatio>0.5)
    	{
    		pierCustomerData.pierCreditLimit = PierCreditLimit.PIER_CREDIT_0;
    	}
    	else if (pierCustomerData.MonthlyIncome > 0.0)
    	{
    		double pierCreditDollars =  (pierCustomerData.MonthlyIncome) * 0.1;
    		pierCustomerData.pierCreditLimit = PierCreditLimit.dollarsToCreditLimit(pierCreditDollars);
    	}
    	else
    	{
    		pierCustomerData.pierCreditLimit = PierCreditLimit.PIER_CREDIT_0;
    	}
    }
    */
}

package com.pier.decision;

public enum PierCreditLimit
{
	NO_PIER_Label(-1),   // not label yet
	PIER_CREDIT_0(0),    // no pier credit, go to pre-payment card 
	PIER_CREDIT_100(100), // $100 pier credit 
	PIER_CREDIT_200(200),
	PIER_CREDIT_500(500),
	PIER_CREDIT_1K(1000),
	PIER_CREDIT_2K(2000),
	PIER_CREDIT_5K(5000),
	PIER_CREDIT_10K(10000),
	PIER_CREDIT_20K(20000)
	; 

	// members
    private int pierCreditLimit_;
    private PierCreditLimit(int value) 
    {
            this.pierCreditLimit_ = value;
    }
    public void setpierCreditLimit_(int value)
    {
    	pierCreditLimit_ = value;
    }
    public int getpierCreditLimit_()
    {
    	return pierCreditLimit_;
    }
    public static PierCreditLimit dollarsToCreditLimit(double dollars)
    {
    	if (dollars >= 20000.0)
    	{
    		return PIER_CREDIT_20K;
    	}
    	else if (dollars >= 10000.0)
    	{
    		return PIER_CREDIT_10K;
    	}
    	else if (dollars >= 5000.0)
    	{
    		return PIER_CREDIT_5K;
    	}
    	else if (dollars >= 2000.0)
    	{
    		return PIER_CREDIT_2K;
    	}
    	else if (dollars >= 1000.0)
    	{
    		return PIER_CREDIT_1K;
    	}
    	else if (dollars >= 500.0)
    	{
    		return PIER_CREDIT_500;
    	}
    	else if (dollars >= 200.0)
    	{
    		return PIER_CREDIT_200;
    	}
    	else if (dollars >= 100.0)
    	{
    		return PIER_CREDIT_100;
    	}
    	else
    	{
    		return PIER_CREDIT_0;
    	}
    }
}

package com.pier.decision;

public enum CustomerCreditCategory {
	PRIME_NO_CATEGORY(-1),
	SUB_PRIME(0), //	<680
	NEAR_PRIME(680), // Near Prime	  // >= 680, <750
	SUPER_PRIME(750); //	>= 750
	
	// members
    private int ficoScore_;
    
   CustomerCreditCategory( int ficoScore) 
    {
            this.ficoScore_ = ficoScore;
    }
    public void setficoScore_(int ficoScore)
    {
    	this.ficoScore_ = ficoScore;
    }
    public int getficoScore()
    {
    	return ficoScore_;
    }
    public static CustomerCreditCategory ficoScoreToCreditCategory(int fico)
    {
    	if (fico >= 750)
    	{
    		return SUPER_PRIME;
    	}
    	else if (fico >= 680)
    	{
    		return NEAR_PRIME;
    	}
    	else if (fico >= 0)
    	{
    		return SUB_PRIME;
    	}
    	else
    	{
    		return PRIME_NO_CATEGORY;
    	}
    }
}

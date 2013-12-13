package com.pier.decision;



public class PierRandomForest extends PierLearningAndDecision{
	
	public PierMachineDecision getPierMachineDecision(PierCustomerData newCustomerData)
	{
		PierMachineDecision machineDecision = new PierMachineDecision();
		// ...
		return machineDecision;
	}

	public void buildPierKnowledge(PierCustomerData[] historicalCustomerData)
	{
		// Use Weka
	}
}

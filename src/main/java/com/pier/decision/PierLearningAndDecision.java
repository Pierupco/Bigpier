package com.pier.decision;



public abstract class PierLearningAndDecision {

	public abstract PierMachineDecision getPierMachineDecision(PierCustomerData newCustomerData);

	public abstract void buildPierKnowledge(PierCustomerData[] historicalCustomerData);
}

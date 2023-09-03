package helloworld;

import agents.LARVAFirstAgent;

public class AgentLARVA extends LARVAFirstAgent{
    
    @Override
    public void setup() {
        super.setup();
        Message("Hello my name is "+this.getLocalName());        
        logger.onTabular();
        Info("Configuring...");        
    }
    
    @Override
    public void Execute () {
        Info("Executing..");
        doExit();        
    }
    
    @Override
    public void takeDown() {
        Info("Taking down..");
        super.takeDown();
    }
    
}

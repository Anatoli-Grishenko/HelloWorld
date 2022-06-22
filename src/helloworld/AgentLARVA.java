package helloworld;

import agents.LARVAFirstAgent;

public class AgentLARVA extends LARVAFirstAgent{
    
    @Override
    public void setup() {
        super.setup();
        Info("Configuring...");        
    }
    
    @Override
    public void Execute () {
        Info("Executing..");
        this.doLARVACheckin();
        this.doLARVACheckout();
        doExit();        
    }
    
    @Override
    public void takeDown() {
        Info("Taking down..");
        this.saveSequenceDiagram("seq.seqd");
        super.takeDown();
    }
    
}

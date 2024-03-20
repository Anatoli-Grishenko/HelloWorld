package helloworld;

import Agents.LARVAAgent;



public class AgentLARVABasic extends LARVAAgent{
    
    @Override
    public void setup() {
        super.setup();
        Info("Configuring...");   
        Info("This agent belongs to "+getAgentMemory());
    }
    
    @Override
    public void Execute () {
        Info("Executing..");
        Exit();        
    }
    
    @Override
    public void takeDown() {
        Info("Taking down..");
        super.takeDown();
    }
    
}

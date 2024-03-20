package helloworld;

import Agents.LARVAAgent;

public class AgentLARVARepetitive extends LARVAAgent {

    @Override
    public void setup() {
        super.setup();
        Info("Configuring...");
    }

    @Override
    public void Execute() {
        Info("Executing... cycle "+this.getAgentMemory().getnCycles());      
        if (this.getAgentMemory().getnCycles() > 125) {
            Exit();
        }
    }

    @Override
    public void takeDown() {
        Info("Taking down..");
        super.takeDown();
    }

}

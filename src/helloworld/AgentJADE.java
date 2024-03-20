package helloworld;

import jade.core.Agent;

public class AgentJADE extends Agent {
    
    @Override
    public void setup(){
        System.out.println("Hello my name is "+this.getLocalName());
        doDelete();
    }
    
    @Override
    public void takeDown() {
        System.out.println(this.getLocalName()+" says Goodbye!");        
    }
}

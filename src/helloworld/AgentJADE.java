package helloworld;

import jade.core.Agent;
import swing.SwingTools;

public class AgentJADE extends Agent {
    
    @Override
    public void setup(){
        SwingTools.Message("Hello my name is "+this.getLocalName());        
        doDelete();
    }
    
}

package helloworld;

import appboot.JADEBoot;
import appboot.LARVABoot;

public class HelloWorld {

    public static void main(String[] args) {
        // Basic JADE boot (console)
        JADEBoot boot=new JADEBoot();
        // LARVA boot (GUI-based)
//        LARVABoot boot = new LARVABoot();
        
        // In both cases, Boot( ) creates a container for future agents
        // Should JADE be running in local
        boot.Boot("localhost", 1099);
        // Otherwise our server always run JADE  ;-)
        // boot.Boot("150.214.190.126", 1099);
        
        // Create the agent and executes it
        boot.launchAgent("Smith", AgentJADE.class);
        
        // Closes the container and exits
        boot.WaitToShutDown();
    }
    
}

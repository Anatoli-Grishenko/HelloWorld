package helloworld;

import appboot.JADEBoot;
import appboot.LARVABoot;
import static crypto.Keygen.getHexaKey;
import swing.SwingTools;

public class HelloWorld {

    public static void main(String[] args) {
        bootJADE(); 
//        bootLARVA(); 
    }

    public static void bootJADE() {
        // Basic JADE boot (console)
        JADEBoot boot = new JADEBoot(); 
        String hostname = "isg2.ugr.es";
                // boot.inputSelect("Select host", new String[]{"localhost","isg2.ugr.es","150.214.190.126"}, "isg2.ugr.es");

        // Boot( ) creates a container for future agents
        boot.Boot(hostname, 1099);

        // Create the agent and executes it. 
        // (Version 1) It adds a random suffix to the name to avoid duplicate name clash. 
        // It is a pure, emnpty JADE Agent      
        boot.launchAgent("Smith-" + getHexaKey(4), AgentJADE.class);

        // Closes the container and exits
        boot.WaitToShutDown();

    }

    public static void bootLARVA() {
        // LARVA boot (GUI-based)
        LARVABoot boot = new LARVABoot();
        String hostname= "isg2.ugr.es";
                //boot.inputSelect("Select host", new String[]{"localhost","isg2.ugr.es","150.214.190.126"}, "isg2.ugr.es");
        
        // Boot( ) creates a container for future agents
        boot.Boot(hostname, 1099);

        // Create the agent and executes it. Please uncomment to use
        
        // (Version 2) This version does not require passport neither interacts with LARVA.
        //boot.launchAgent("Smith-"+getHexaKey(4), AgentLARVA.class);

        // (Version 3) This version requires passport, interacts with LARVA, acquire milestones
        // and receive report from DBA Droid
        // boot.launchAgent("Smith-"+getHexaKey(4), AgentLARVAFull.class); 
       

        // Closes the container and exits
        boot.WaitToShutDown();

    }

}

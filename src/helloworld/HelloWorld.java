package helloworld;

import appboot.JADEBoot;
import appboot.LARVABoot;
import static crypto.Keygen.getHexaKey;
import swing.SwingTools;

public class HelloWorld {

    public static void main(String[] args) {
//        bootJADE();
        bootLARVA();
    }

    public static void bootJADE() {
        // Basic JADE boot (console)
        JADEBoot boot = new JADEBoot();

        // Boot( ) creates a container for future agents
        // Should JADE be running in local
        boot.Boot(SwingTools.inputLine("Host"), 1099);
        // Otherwise our server always run JADE  ;-)
//         boot.Boot("isg2.ugr.es", 1099);  // Our server is isg2
//         boot.Boot("150.214.190.126", 1099);  // Should there be problems with DNS

        // Create the agent and executes it
        boot.launchAgent("Smith-" + getHexaKey(4), AgentJADE.class);

        // Closes the container and exits
        boot.WaitToShutDown();

    }

    public static void bootLARVA() {
        // LARVA boot (GUI-based)
        LARVABoot boot = new LARVABoot();
        int nProfilers = 8;
        // Boot( ) creates a container for future agents
        // Should JADE be running in local
//        boot.Boot("localhost", 1099);
//        boot.Boot(boot.inputLine("Host"), 1099);
        boot.Boot(SwingTools.inputSelect("Please select sever", new String[]{"localhost", "isg2.ugr.es", ""}, ""), 1099);
        // Otherwise our server always run JADE  ;-)
//         boot.Boot("isg2.ugr.es", 1099);  // Our server is isg2
        // boot.Boot("150.214.190.126", 1099);  // Should there be problems with DNS

        // Create the agent and executes it
//        boot.launchAgent("Smith-"+getHexaKey(4), AgentLARVA.class);
//        boot.launchAgent("Smith-"+getHexaKey(4), AgentLARVAFull.class);
//        boot.launchAgent("Smith-"+getHexaKey(4), AgentDialogicFull.class);
        for (int i = 0; i < nProfilers; i++) {
            boot.launchAgent("SP" + i, AgentLinearProfiler.class);
        }

        // Closes the container and exits
        boot.WaitToShutDown();

    }

}

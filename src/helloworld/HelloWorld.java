package helloworld;


import Boot.LARVABoot;
import static Crypto.LKeygen.getHexaKey;
import jade.core.Agent;

public class HelloWorld {

    public static void main(String[] args) {
        LARVABoot boot = new LARVABoot();

        // Create the container 
        boot.boot();
        
        // Create and run agents 
// /*1*/        boot.launchAgent("Smith-"+getHexaKey(4), AgentJADE.class);
// /*2*/        boot.launchAgent("Smith-"+getHexaKey(4), AgentLARVABasic.class);
// /*3*/        boot.loadAgent("Smith-"+getHexaKey(4), AgentLARVABasic.class);
 /*4*/        boot.loadAgent("Smith-"+getHexaKey(4), AgentLARVARepetitive.class);

        // Closes the container and exits
        boot.shutDown();

    }

}

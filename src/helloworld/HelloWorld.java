package helloworld;

import appboot.JADEBoot;
import appboot.LARVABoot;

public class HelloWorld {

    public static void main(String[] args) {
        LARVABoot boot = new LARVABoot();
        boot.Boot("localhost", 1099);
        boot.launchAgent("Smith", AgentLARVAFull.class);
        boot.WaitToShutDown();
    }
    
}

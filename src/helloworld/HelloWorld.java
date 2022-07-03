package helloworld;

import appboot.JADEBoot;
import appboot.LARVABoot;

public class HelloWorld {

    public static void main(String[] args) {
        LARVABoot boot = new LARVABoot();
        boot.Boot("150.214.190.126", 1099);
        boot.launchAgent("Smith", AgentLARVA.class);
        boot.WaitToShutDown();
    }
    
}

# HelloWorld

## Este va a ser nuestro primer proyecto de agentes (ver Videotutorial).
En NetBeans crear un nuevo proyecto, colocado en la misma carpeta en la que hemos instalado es.ugr.larva.core. 



Añadir al nuevo proyecto todas las librerías que se encuentran en 

es.ugr.larva.core/src/resources/dependencies

y añadir, también, el proyecto que hemos creado para LARVA

Crear una nueva clase llamada AgenteJADE.java que va a contener nuestro primer agente




package helloworld;

import jade.core.Agent;

public class AgenteJADE extends Agent {
    @Override
    public void setup() {
        System.out.println("Hello!, my name is "+getLocalName());
        doDelete();
    }
}

Crear el main() con una clase de arranque de conexión (JADEBoot). Esta instancia se conecta al servidor en el que está el contenedor principal de JADE, crea un contenedor nuevo y lanza los agentes dentro del contenedor.


package helloworld;

import appboot.JADEBoot;

public class HelloWorld {

    public static void main(String[] args) {
        JADEBoot boot = new JADEBoot();
        boot.Boot("isg2.ugr.es", 1099);
        boot.launchAgent("Smith", AgentJADE.class);
        boot.WaitToShutDown();
    }
    
}




Y ya sólo queda ejecutar el proyecto y comprobar que todo es correcto



run:
Jade BOOT: Processing arguments:
Jade BOOT: AppBoot v1.0  25% Completed
Jade BOOT: Configuring boot:
Jade BOOT: AppBoot v1.0  50% Completed
Trying to connecto to Jade (MicroBoot) @isg2.ugr.es:1099
Jade BOOT: jade.MicroBoot Host: isg2.ugr.es[1099] <null>
jun. 19, 2022 10:48:15 A. M. jade.imtp.leap.JICP.BIFEDispatcher createBackEnd
INFO: Creating BackEnd on jicp://isg2.ugr.es:1099
jun. 19, 2022 10:48:15 A. M. jade.imtp.leap.JICP.BIFEDispatcher createBackEnd
INFO: BackEnd OK: mediator-id = BE-150.214.190.126_1099-10
Jade BOOT: AppBoot v1.0  75% Completed
jun. 19, 2022 10:48:15 A. M. jade.imtp.leap.JICP.BIFEDispatcher$InputManager run
INFO: IM-0 started
jun. 19, 2022 10:48:15 A. M. jade.imtp.leap.JICP.BIFEDispatcher connectInp
INFO: Connecting to isg2.ugr.es:1099 0 (INP)
Jade BOOT: Launching agent Smith
jun. 19, 2022 10:48:15 A. M. jade.core.FrontEndContainer start
INFO: --------------------------------------
Agent container BE-150.214.190.126_1099-10 is ready.
--------------------------------------------
jun. 19, 2022 10:48:15 A. M. jade.imtp.leap.JICP.BIFEDispatcher connectInp
INFO: Connect OK (INP)
Hello!, my name is Smith
Jade BOOT: AppBoot v1.0  100% Completed
Jade BOOT: Waiting for agents to close
Jade BOOT: Shutting down
Jade BOOT: Killing all remaining agents
Jade BOOT: Turning off JadeBoot
Jade BOOT: Shutting down container BE-150.214.190.126_1099-10
jun. 19, 2022 10:48:15 A. M. jade.core.FrontEndContainer getLocalAgent
INFO:  localAgent Smith not found: try with its mapped name null
jun. 19, 2022 10:48:15 A. M. jade.core.FrontEndContainer exit
INFO: Container shut down activated
jun. 19, 2022 10:48:15 A. M. jade.imtp.leap.JICP.BIFEDispatcher shutdown
INFO: Sending termination notification
Jade BOOT: Container BE-150.214.190.126_1099-10 shut down
jun. 19, 2022 10:48:15 A. M. jade.imtp.leap.JICP.BIFEDispatcher$InputManager run
INFO: IM-0 terminated
BUILD SUCCESSFUL (total time: 1 second)



MENSAJES DE JADE     (jade.jar)
MENSAJES DE JADEBoot (es.ugr.larva.core.jar)
MENSAJES DE AGENTES  (jade.jar)

Lo mismo ocurre con los agentes de LARVA, son agentes que heredan de jade.Agent pero que tienen muchos servicios útiles y más simplificados que el propio JADE, de la misma forma, se puede  usar un booter distinto de JADEBoot, que se llama LARVABoot, el cual tiene también muchas funciones útiles para la asignatura. Ver videotutorial.



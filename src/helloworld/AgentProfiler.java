/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helloworld;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import java.nio.file.Files;
import profiling.Profiler;
import tools.NetworkCookie;
import tools.TimeHandler;

/**
 *
 * @author Anatoli Grishenko <Anatoli.Grishenko@gmail.com>
 */
public class AgentProfiler extends AgentLARVAFull {

    public static final int MAXSIZE_BYTES = 1024 * 1024,
            INITIALSIZE_BYTES = 512,
            SERIESIZE = 10;

    protected String service = "NETMONITOR", Payload, myNetworkMonitor, label, label2, stime;
    protected int payloadSize, serie;
    protected AID aidNetMon;
    protected boolean zip = false;

    @Override
    public void setup() {
        disableDeepLARVAMonitoring();
        super.setup();
        deactivateSequenceDiagrams();
        logger.offEcho();
        closeRemote();
        if (DFGetAllProvidersOf(service).isEmpty()) {
            Alert("Service " + service + " not found");
            doDelete();
        } else {
            myNetworkMonitor = DFGetAllProvidersOf(service).get(0);
            aidNetMon = new AID(myNetworkMonitor, AID.ISLOCALNAME);
        }
        activateMyCPUProfiler("AGENT-CPU.tsv");
        activateMyNetworkProfiler("AGENT-NETWORK.tsv");
        serie = 1;
        payloadSize = INITIALSIZE_BYTES;
    }
    
 @Override
    public void Execute() {
        getMyCPUProfiler().setSeries(myStatus.name()+(E.getCurrentMission()!= null? ","+E.getCurrentGoal():""));
        getMyNetworkProfiler().setSeries(myStatus.name()+(E.getCurrentMission()!= null? ","+E.getCurrentGoal():""));
        super.Execute();
    }

    @Override
    public Status MySolveProblem() {
        Info("Starting Profiler");
        label = "Profiling Serie " + serie + " " + payloadSize;
        System.out.println(label);
        for (int serierep = 1; serierep <= SERIESIZE; serierep++) {
            lastCookie = new NetworkCookie();
            lastCookie.setSerie(serie);
            lastCookie.setSize(payloadSize);
            zip = serie > 10;
            lastCookie.setZipped(zip);
            outbox = new ACLMessage(ACLMessage.REQUEST);
            outbox.setSender(getAID());
            outbox.addReceiver(aidNetMon);
            outbox.setContent("");
            outbox.setReplyWith(label);
            lastCookie.settUpstream(TimeHandler.Now());
            outbox = Profiler.injectProfiler(outbox, lastCookie);
            LARVAsend(outbox);
            inbox = LARVAblockingReceive();
//            stime = TimeHandler.Now();
//            if (Profiler.isProfiler(inbox)) {
//                nc = Profiler.extractProfiler(inbox);
//                lastCookie.settReceive(stime);
//                label = "PING\t" + nc.getSerie() + "\tSIZE\t" + payloadSize + "\tPAYLOAD\t" + inbox.getContent().length() + "\tZIP\t" + zip + "\t";
//                label2 = "UPSTREAM\t" + nc.getLatencyUp()
//                        + "\tSERVER\t" + nc.getLatencyServer()
//                        + "\tDOWNSTREAM\t" + nc.getLatencyDown();
//                getMyNetworkProfiler().profileThis(label, label2, () -> {
//                    System.out.println(label + "" + label2);
//                });
//            }
        }
        if (serie < 20) {
            serie++;
            if (serie == 11) {
                payloadSize = INITIALSIZE_BYTES;
            } else {
                payloadSize *= 2;
            }
            lastCookie=null;
            return Status.SOLVEPROBLEM;
        } else {
            return Status.CLOSEPROBLEM;
        }
    }
    

}

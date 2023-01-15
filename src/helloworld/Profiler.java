/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helloworld;

import agents.LARVAFirstAgent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import tools.Monitor;
import tools.NetworkCookie;

/**
 *
 * @author Anatoli Grishenko <Anatoli.Grishenko@gmail.com>
 */
public class Profiler extends LARVAFirstAgent {

    // The execution on any agent might be seen as a finite state automaton
    // whose states are these
    protected enum Status {
        START, // Begin execution
        CHECKIN, // Send passport to Identity Manager
        OPENPROBLEM, // ASks Problem Manager to open an instance of a problem
        SOLVEPROBLEM, // Really do this!
        CLOSEPROBLEM, // After that, ask Problem Manager to close the problem
        CHECKOUT, // ASk Identity Manager to leave out
        EXIT
    }
    protected Status myStatus;    // The current state
    protected String service = "PMANAGER", // How to find Problem Manager in DF
            problem = "HelloWorld", // Name of the problem to solve
            problemManager = "", // Name of the Problem Manager, when it woudl be knwon
            sessionManager, // Same for the Session Manager
            content, // Content of messages
            sessionKey; // The key for each work session 
    protected ACLMessage open, session; // Backup of relevant messages
    protected String[] contentTokens; // To parse the content
    protected int latency;

    // This is the firs method executed by any agent, right before its creation
    @Override
    public void setup() {
        // Deep monitoring of the execution of each agent. It is a kind of
        // guardrail only for small problems. In large projects it is preferrable
        // comment it since it consumes many messages and time
        // this must be called before the super.setup();
        this.disableDeepLARVAMonitoring();

        // The constructor of the superclass
        super.setup();

        // This feature allows the automatic generation of sequence diagrams for the running program
        // It is very costly in terms of executoin time, so it must be used carefully in large problems
        this.deactivateSequenceDiagrams();
        //this.deactivateSequenceDiagrams();

        // This feature enables a logger of all the activity of the agent.
        // When it is ON, all Info( ) messages are displayed on screen
        // When it is OFF, it executes quietly, without showing any screen output
        logger.onEcho();
        //logger.offEcho();

        // ACtivates a tabular-like output of agents
        logger.onTabular();

        // First status of execution
        myStatus = Status.START;

//        openRemote();
        activateProfiling("PLAINNETMON");
    }

    // Main execution body andter the executoin of setup( ). It executes continuously until 
    // the exact execution of doExit() after which it executes
    // takeDown()
    @Override
    public void Execute() {
        Info("Status: " + myStatus.name());
        switch (myStatus) {
            case START:
                myStatus = Status.CHECKIN;
                break;
            case CHECKIN:
                // The execution of a state (as a method) returns
                // the next state
                myStatus = MyCheckin();
                break;
            case OPENPROBLEM:
                myStatus = MyOpenProblem();
                break;
            case SOLVEPROBLEM:
                myStatus = MySolveProblem();
                break;
            case CLOSEPROBLEM:
                myStatus = MyCloseProblem();
                break;
            case CHECKOUT:
                myStatus = MyCheckout();
                break;
            case EXIT:
            default:
                doExit();
                break;
        }
    }

    // It only executes when the agent dies programmatically, that is, under control
    @Override
    public void takeDown() {
        Info("Taking down...");
        // Save the Sequence Diagram
        this.saveSequenceDiagram("./" + getLocalName() + ".seqd");
        super.takeDown();
    }

    // It loads the passport selected in the GUI and send it to the Identity manager
    public Status MyCheckin() {
        return Status.OPENPROBLEM;
    }

    // Says good by to the Identity Manager and leaves LARVA
    public Status MyCheckout() {
        return Status.EXIT;
    }

    public Status MyOpenProblem() {
        outbox = null;
        return Status.SOLVEPROBLEM;
    }

    public Status MySolveProblem() {
        if (Confirm("Go onto Test 1/2?")) {
            test1();
        }
        if (Confirm("Go onto Test 2/2?")) {
            test2();
        }
        Message("End of profiling. Thank you!");
        return Status.CLOSEPROBLEM;
    }

    public void test1() {
        if (outbox == null) {
            outbox = new ACLMessage(ACLMessage.SUBSCRIBE);
            outbox.setSender(getAID());
            outbox.addReceiver(new AID(netMon, AID.ISLOCALNAME));
            outbox.setContent("");
            outbox=Monitor.hideSubscribe(outbox, nap);
            LARVAsend(outbox);
            logger.offEcho();
            setProfileDescription("loggerOff");
        }
        inbox = LARVAblockingReceive();
        while (inbox.getPerformative() != ACLMessage.CANCEL) {
            if (inbox.getPerformative() == ACLMessage.QUERY_REF) {
                if (lastCookie != null) {
                    System.out.println("Received " + lastCookie.getSize() + " bytes "
                            + (lastCookie.isZipped() ? " Zipped (" + lastCookie.getRealSize() + " bytes" : "")
                            + " latency " + lastCookie.getLatency() + " ms");
                }
            }
            inbox = LARVAblockingReceive();
        }
        outbox = null;
        logger.onEcho();
    }

    public void test2() {
        if (outbox == null) {
            outbox = new ACLMessage(ACLMessage.SUBSCRIBE);
            outbox.setSender(getAID());
            outbox.addReceiver(new AID(netMon, AID.ISLOCALNAME));
            outbox.setContent("");
            LARVAsend(outbox);
            logger.onEcho();
            setProfileDescription("loggerOn");
        }
        inbox = LARVAblockingReceive();
        while (inbox.getPerformative() != ACLMessage.CANCEL) {
            if (inbox.getPerformative() == ACLMessage.QUERY_REF) {
                if (lastCookie != null) {
                    Info("Received " + lastCookie.getSize() + " bytes "
                            + (lastCookie.isZipped() ? " Zipped (" + lastCookie.getRealSize() + " bytes" : "")
                            + " latency " + lastCookie.getLatency() + " ms");
                }
            }
            inbox = LARVAblockingReceive();
        }
        outbox = null;
    }

    public Status MyCloseProblem() {
        return Status.CHECKOUT;
    }

}

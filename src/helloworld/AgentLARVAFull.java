/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helloworld;

import agents.LARVAFirstAgent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Anatoli Grishenko <Anatoli.Grishenko@gmail.com>
 */
public class AgentLARVAFull extends LARVAFirstAgent {

    // The execution on any agent might be seen as a finite state automaton
    // whose states are these
    enum Status {
        START, // Begin execution
        CHECKIN, // Send passport to Identity Manager
        OPENPROBLEM, // ASks Problem Manager to open an instance of a problem
        SOLVEPROBLEM, // Really do this!
        CLOSEPROBLEM, // After that, ask Problem Manager to close the problem
        CHECKOUT, // ASk Identity Manager to leave out
        EXIT
    }
    Status myStatus;    // The current state
    String service = "PMANAGER", // How to find Problem Manager in DF
            problem = "HelloWorld", // Name of the problem to solve
            problemManager = "", // Name of the Problem Manager, when it woudl be knwon
            sessionManager, // Same for the Session Manager
            content, // Content of messages
            sessionKey; // The key for each work session 
    ACLMessage open, session; // Backup of relevant messages
    String[] contentTokens; // To parse the content

    @Override
    public void setup() {
        // Deep monitoring of the execution of each agent. It is a kind of
        // guardrail only for small problems. In large projects it is preferrable
        // comment it since it consumes many messages and time
        this.enableDeepLARVAMonitoring();
        // The constructor of the superclass
        super.setup();
        // ACtivates a tabular-like output of agents
        logger.onTabular();
        // First status
        myStatus = Status.START;
    }

    // Main execution body. It executes continuously until 
    // the exac execution of doExit() after which it executes
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

    @Override
    public void takeDown() {
        Info("Taking down...");
        this.saveSequenceDiagram("./" + getLocalName() + ".seqd");
        super.takeDown();
    }

    public Status MyCheckin() {
        Info("Loading passport and checking-in to LARVA");
        //this.loadMyPassport("config/ANATOLI_GRISHENKO.passport");
        if (!doLARVACheckin()) {
            Error("Unable to checkin");
            return Status.EXIT;
        }
        return Status.OPENPROBLEM;
    }

    public Status MyCheckout() {
        this.doLARVACheckout();
        return Status.EXIT;
    }

    public Status MyOpenProblem() {

        if (this.DFGetAllProvidersOf(service).isEmpty()) {
            Error("Service PMANAGER is down");
            return Status.CHECKOUT;
        }
        problemManager = this.DFGetAllProvidersOf(service).get(0);
        Info("Found problem manager " + problemManager);
        this.outbox = new ACLMessage();
        outbox.setSender(getAID());
        outbox.addReceiver(new AID(problemManager, AID.ISLOCALNAME));
        outbox.setContent("Request open " + problem);
        this.LARVAsend(outbox);
        Info("Request opening problem " + problem + " to " + problemManager);
        open = LARVAblockingReceive();
        Info(problemManager + " says: " + open.getContent());
        content = open.getContent();
        contentTokens = content.split(" ");
        if (contentTokens[0].toUpperCase().equals("AGREE")) {
            sessionKey = contentTokens[4];
            session = LARVAblockingReceive();
            sessionManager = session.getSender().getLocalName();
            Info(sessionManager + " says: " + session.getContent());
            return Status.CLOSEPROBLEM;
        } else {
            Error(content);
            return Status.CHECKOUT;
        }
    }

    public Status MySolveProblem() {
        return Status.CLOSEPROBLEM;
    }

    public Status MyCloseProblem() {
        outbox = open.createReply();
        outbox.setContent("Cancel session " + sessionKey);
        Info("Closing problem Helloworld, session " + sessionKey);
        this.LARVAsend(outbox);
        inbox = LARVAblockingReceive();
        Info(problemManager + " says: " + inbox.getContent());
        return Status.CHECKOUT;
    }

}

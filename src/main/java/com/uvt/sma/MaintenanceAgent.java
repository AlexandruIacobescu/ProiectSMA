package com.uvt.sma;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class MaintenanceAgent extends Agent {

    private String maintenanceSchedule; // Maintenance schedule information

    @Override
    protected void setup() {
        // Get the initial maintenance schedule from the agent arguments (if provided)
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            maintenanceSchedule = (String) args[0];
        } else {
            maintenanceSchedule = "No maintenance scheduled.";
        }

        System.out.println("MaintenanceAgent " + getAID().getName() + " with initial schedule: " + maintenanceSchedule);

        // Add the behaviour to handle incoming messages
        addBehaviour(new MessageHandlingBehaviour());
    }

    private class MessageHandlingBehaviour extends CyclicBehaviour {
        @Override
        public void action() {
            // Template to filter messages intended for this agent
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
            ACLMessage msg = receive(mt);

            if (msg != null) {
                // Process the incoming message
                String content = msg.getContent();
                System.out.println("MaintenanceAgent " + getAID().getName() + " received message: " + content);

                // Example of handling a specific message type
                if (content.equalsIgnoreCase("REQUEST_SCHEDULE")) {
                    // Send the current maintenance schedule back to the sender
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent("Current maintenance schedule is " + maintenanceSchedule);
                    send(reply);
                } else if (content.startsWith("UPDATE_SCHEDULE")) {
                    // Update maintenance schedule based on message content
                    String[] parts = content.split(":");
                    if (parts.length == 2) {
                        maintenanceSchedule = parts[1];
                        System.out.println("Updated maintenance schedule to: " + maintenanceSchedule);
                    }
                }
            } else {
                block();
            }
        }
    }

    @Override
    protected void takeDown() {
        // Cleanup operations before the agent is terminated
        System.out.println("MaintenanceAgent " + getAID().getName() + " terminating.");
    }
}

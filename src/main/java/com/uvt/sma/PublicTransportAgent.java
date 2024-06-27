package com.uvt.sma;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class PublicTransportAgent extends Agent {

    private String transportType; // e.g., "bus" or "train"
    private String route;
    private String schedule;

    @Override
    protected void setup() {
        // Get the transport type from the agent arguments (if provided)
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            transportType = (String) args[0];
        } else {
            transportType = "bus";
        }

        System.out.println("PublicTransportAgent " + getAID().getName() + " managing: " + transportType);

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
                System.out.println("PublicTransportAgent " + getAID().getName() + " received message: " + content);

                // Example of handling a specific message type
                if (content.equalsIgnoreCase("REQUEST_SCHEDULE")) {
                    // Send the current schedule back to the sender
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent("Current schedule is " + schedule);
                    send(reply);
                } else if (content.startsWith("UPDATE_SCHEDULE")) {
                    // Update schedule based on message content
                    String[] parts = content.split(":");
                    if (parts.length == 2) {
                        schedule = parts[1];
                        System.out.println("Updated schedule to: " + schedule);
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
        System.out.println("PublicTransportAgent " + getAID().getName() + " terminating.");
    }
}

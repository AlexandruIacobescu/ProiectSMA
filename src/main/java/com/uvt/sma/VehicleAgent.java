package com.uvt.sma;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class VehicleAgent extends Agent {

    private String destination;
    private String currentRoute;

    @Override
    protected void setup() {
        // Get the destination from the agent arguments (if provided)
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            destination = (String) args[0];
        } else {
            destination = "unknown";
        }

        System.out.println("VehicleAgent " + getAID().getName() + " heading to: " + destination);

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
                System.out.println("VehicleAgent " + getAID().getName() + " received message: " + content);

                // Example of handling a specific message type
                if (content.equalsIgnoreCase("REQUEST_ROUTE")) {
                    // Send the current route back to the sender
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent("Current route is " + currentRoute);
                    send(reply);
                } else if (content.startsWith("UPDATE_ROUTE")) {
                    // Update route based on message content
                    String[] parts = content.split(":");
                    if (parts.length == 2) {
                        currentRoute = parts[1];
                        System.out.println("Updated route to: " + currentRoute);
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
        System.out.println("VehicleAgent " + getAID().getName() + " terminating.");
    }
}

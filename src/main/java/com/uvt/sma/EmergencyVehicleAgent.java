package com.uvt.sma;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class EmergencyVehicleAgent extends Agent {

    private String emergencyType; // e.g., "ambulance" or "fire truck"
    private String emergencyRoute;

    @Override
    protected void setup() {
        // Get the emergency type from the agent arguments (if provided)
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            emergencyType = (String) args[0];
        } else {
            emergencyType = "ambulance";
        }

        System.out.println("EmergencyVehicleAgent " + getAID().getName() + " handling emergency: " + emergencyType);

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
                System.out.println("EmergencyVehicleAgent " + getAID().getName() + " received message: " + content);

                // Example of handling a specific message type
                if (content.equalsIgnoreCase("REQUEST_PRIORITY_ROUTE")) {
                    // Send the current emergency route back to the sender
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent("Current emergency route is " + emergencyRoute);
                    send(reply);
                } else if (content.startsWith("UPDATE_EMERGENCY_ROUTE")) {
                    // Update emergency route based on message content
                    String[] parts = content.split(":");
                    if (parts.length == 2) {
                        emergencyRoute = parts[1];
                        System.out.println("Updated emergency route to: " + emergencyRoute);
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
        System.out.println("EmergencyVehicleAgent " + getAID().getName() + " terminating.");
    }
}

package com.uvt.sma;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class TrafficSignalAgent extends Agent {

    private int greenTime;  // Duration of the green light
    private int yellowTime; // Duration of the yellow light
    private int redTime;    // Duration of the red light
    private String intersectionId; // Identifier for the intersection this agent manages

    @Override
    protected void setup() {
        // Initialize the traffic signal timings (default values)
        greenTime = 30;  // 30 seconds green light
        yellowTime = 5;  // 5 seconds yellow light
        redTime = 30;    // 30 seconds red light

        // Get the intersection ID from the agent arguments (if provided)
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            intersectionId = (String) args[0];
        } else {
            intersectionId = "default-intersection";
        }

        System.out.println("TrafficSignalAgent " + getAID().getName() + " is ready to manage intersection: " + intersectionId);

        // Add the behaviour to handle traffic signal management
        addBehaviour(new TrafficSignalManagementBehaviour());

        // Add the behaviour to handle incoming messages
        addBehaviour(new MessageHandlingBehaviour());
    }

    private class TrafficSignalManagementBehaviour extends CyclicBehaviour {
        @Override
        public void action() {
            // Simulate traffic signal timing (green, yellow, red cycle)
            System.out.println("Intersection " + intersectionId + ": Green light for " + greenTime + " seconds.");
            block(greenTime * 1000); // Simulate green light duration

            System.out.println("Intersection " + intersectionId + ": Yellow light for " + yellowTime + " seconds.");
            block(yellowTime * 1000); // Simulate yellow light duration

            System.out.println("Intersection " + intersectionId + ": Red light for " + redTime + " seconds.");
            block(redTime * 1000); // Simulate red light duration
        }
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
                System.out.println("TrafficSignalAgent " + getAID().getName() + " received message: " + content);

                // Example of handling a specific message type
                if (content.equalsIgnoreCase("REQUEST_GREEN_TIME")) {
                    // Send the current green light duration back to the sender
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent("Green light duration is " + greenTime + " seconds.");
                    send(reply);
                } else if (content.startsWith("UPDATE_TIMINGS")) {
                    // Update signal timings based on message content
                    String[] parts = content.split(":");
                    if (parts.length == 4) {
                        try {
                            greenTime = Integer.parseInt(parts[1]);
                            yellowTime = Integer.parseInt(parts[2]);
                            redTime = Integer.parseInt(parts[3]);
                            System.out.println("Updated signal timings: Green=" + greenTime + ", Yellow=" + yellowTime + ", Red=" + redTime);
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid timing values received.");
                        }
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
        System.out.println("TrafficSignalAgent " + getAID().getName() + " terminating.");
    }
}

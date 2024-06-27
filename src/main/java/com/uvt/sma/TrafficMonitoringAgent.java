package com.uvt.sma;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class TrafficMonitoringAgent extends Agent {

    @Override
    protected void setup() {
        System.out.println("TrafficMonitoringAgent " + getAID().getName() + " is monitoring traffic.");

        // Add the behaviour to handle traffic monitoring and incoming messages
        addBehaviour(new TrafficMonitoringBehaviour());
        addBehaviour(new MessageHandlingBehaviour());
    }

    private class TrafficMonitoringBehaviour extends CyclicBehaviour {
        @Override
        public void action() {
            // Simulate traffic monitoring by collecting data (e.g., from sensors or cameras)
            String trafficData = "Traffic data at " + System.currentTimeMillis();
            System.out.println("TrafficMonitoringAgent collected: " + trafficData);

            // For demonstration, broadcast the collected traffic data to all agents
            ACLMessage trafficInfo = new ACLMessage(ACLMessage.INFORM);
            trafficInfo.setContent(trafficData);

            // Adding known agent AIDs
            trafficInfo.addReceiver(new AID("TrafficSignalAgent", AID.ISLOCALNAME));
            trafficInfo.addReceiver(new AID("CoordinatorAgent", AID.ISLOCALNAME));
            // Add other agents' AIDs as needed
            trafficInfo.addReceiver(new AID("VehicleAgent1", AID.ISLOCALNAME));
            trafficInfo.addReceiver(new AID("VehicleAgent2", AID.ISLOCALNAME));
            trafficInfo.addReceiver(new AID("VehicleAgent3", AID.ISLOCALNAME));
            trafficInfo.addReceiver(new AID("PublicTransportAgent", AID.ISLOCALNAME));
            trafficInfo.addReceiver(new AID("EmergencyVehicleAgent", AID.ISLOCALNAME));
            trafficInfo.addReceiver(new AID("MaintenanceAgent", AID.ISLOCALNAME));

            send(trafficInfo);

            block(5000); // Simulate time interval for data collection
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
                System.out.println("TrafficMonitoringAgent " + getAID().getName() + " received message: " + content);

                // Example of handling a specific message type
                if (content.equalsIgnoreCase("REQUEST_TRAFFIC_DATA")) {
                    // Send the latest traffic data back to the sender
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent("Latest traffic data is ..."); // Replace with actual data
                    send(reply);
                }
            } else {
                block();
            }
        }
    }

    @Override
    protected void takeDown() {
        // Cleanup operations before the agent is terminated
        System.out.println("TrafficMonitoringAgent " + getAID().getName() + " terminating.");
    }
}

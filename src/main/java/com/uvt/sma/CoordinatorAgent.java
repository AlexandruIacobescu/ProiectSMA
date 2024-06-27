package com.uvt.sma;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class CoordinatorAgent extends Agent {

    @Override
    protected void setup() {
        System.out.println("CoordinatorAgent " + getAID().getName() + " is ready.");

        // Add the behaviour to handle incoming messages and coordinate activities
        addBehaviour(new CoordinationBehaviour());
    }

    private class CoordinationBehaviour extends CyclicBehaviour {
        @Override
        public void action() {
            // Template to filter messages intended for this agent
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
            ACLMessage msg = receive(mt);

            if (msg != null) {
                // Process the incoming message
                String content = msg.getContent();
                System.out.println("CoordinatorAgent " + getAID().getName() + " received message: " + content);

                // Example of handling different types of messages
                if (content.startsWith("REQUEST_RESOLVE_CONFLICT")) {
                    // Extract details from the message to resolve the conflict
                    String[] parts = content.split(":");
                    if (parts.length == 3) {
                        String agent1 = parts[1];
                        String agent2 = parts[2];
                        resolveConflict(agent1, agent2);
                    }
                } else if (content.startsWith("REQUEST_POLICY_UPDATE")) {
                    // Handle policy update requests
                    String[] parts = content.split(":");
                    if (parts.length == 2) {
                        String newPolicy = parts[1];
                        updatePolicy(newPolicy);
                    }
                }
            } else {
                block();
            }
        }

        private void resolveConflict(String agent1, String agent2) {
            // Logic to resolve conflicts between agent1 and agent2
            System.out.println("Resolving conflict between " + agent1 + " and " + agent2);

            // Notify the agents involved about the resolution
            ACLMessage notifyAgent1 = new ACLMessage(ACLMessage.INFORM);
            notifyAgent1.addReceiver(getAgentAID(agent1));
            notifyAgent1.setContent("Conflict with " + agent2 + " has been resolved.");
            send(notifyAgent1);

            ACLMessage notifyAgent2 = new ACLMessage(ACLMessage.INFORM);
            notifyAgent2.addReceiver(getAgentAID(agent2));
            notifyAgent2.setContent("Conflict with " + agent1 + " has been resolved.");
            send(notifyAgent2);
        }

        private void updatePolicy(String newPolicy) {
            // Logic to update traffic management policies
            System.out.println("Updating policy to: " + newPolicy);

            // Broadcast the new policy to all relevant agents
            ACLMessage policyUpdate = new ACLMessage(ACLMessage.INFORM);
            policyUpdate.setContent("New policy: " + newPolicy);
            // Assuming agents are known by their names, otherwise maintain a list of known agents
            // Example: add all known agents' AIDs
            // policyUpdate.addReceiver(new AID("TrafficSignalAgent1", AID.ISLOCALNAME));
            // policyUpdate.addReceiver(new AID("VehicleAgent1", AID.ISLOCALNAME));
            send(policyUpdate);
        }

        private AID getAgentAID(String agentName) {
            // Helper method to create an AID for a given agent name
            return new AID(agentName, AID.ISLOCALNAME);
        }
    }

    @Override
    protected void takeDown() {
        // Cleanup operations before the agent is terminated
        System.out.println("CoordinatorAgent " + getAID().getName() + " terminating.");
    }
}

package com.uvt.sma;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class TrafficManagementSystem {
    public static void main(String[] args) {
        // Get a hold on JADE runtime
        Runtime rt = Runtime.instance();

        // Create a default profile
        Profile profile = new ProfileImpl();
        ContainerController container = rt.createMainContainer(profile);

        try {
            // Create and start the TrafficSignalAgent
            AgentController trafficSignalAgent = container.createNewAgent("TrafficSignalAgent", TrafficSignalAgent.class.getName(), null);
            trafficSignalAgent.start();

            // Create and start multiple VehicleAgents with different destinations
            for (int i = 1; i <= 3; i++) {
                Object[] vehicleArgs = new Object[]{"Destination" + i};
                AgentController vehicleAgent = container.createNewAgent("VehicleAgent" + i, VehicleAgent.class.getName(), vehicleArgs);
                vehicleAgent.start();
            }

            // Create and start the PublicTransportAgent
            Object[] publicTransportArgs = new Object[]{"bus"};
            AgentController publicTransportAgent = container.createNewAgent("PublicTransportAgent", PublicTransportAgent.class.getName(), publicTransportArgs);
            publicTransportAgent.start();

            // Create and start the EmergencyVehicleAgent
            Object[] emergencyVehicleArgs = new Object[]{"ambulance"};
            AgentController emergencyVehicleAgent = container.createNewAgent("EmergencyVehicleAgent", EmergencyVehicleAgent.class.getName(), emergencyVehicleArgs);
            emergencyVehicleAgent.start();

            // Create and start the MaintenanceAgent
            Object[] maintenanceArgs = new Object[]{"No maintenance scheduled."};
            AgentController maintenanceAgent = container.createNewAgent("MaintenanceAgent", MaintenanceAgent.class.getName(), maintenanceArgs);
            maintenanceAgent.start();

            // Create and start the TrafficMonitoringAgent
            AgentController trafficMonitoringAgent = container.createNewAgent("TrafficMonitoringAgent", TrafficMonitoringAgent.class.getName(), null);
            trafficMonitoringAgent.start();

            // Create and start the CoordinatorAgent
            AgentController coordinatorAgent = container.createNewAgent("CoordinatorAgent", CoordinatorAgent.class.getName(), null);
            coordinatorAgent.start();

        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}

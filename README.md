# Sistem de Gestionare a Traficului cu JADE

Acest proiect demonstrează un sistem simplu de gestionare a traficului utilizând JADE (Java Agent DEvelopment Framework). Implică mai mulți agenți care simulează semafoare, vehicule, transport public, vehicule de urgență, întreținere, monitorizare a traficului și un agent coordonator. Sistemul este conceput pentru a arăta cum agenții pot comunica și coordona pentru a gestiona eficient traficul.

## Cuprins

- [Structura Proiectului](#structura-proiectului)
- [Prezentare Generală a Agenților](#prezentare-general%C4%83-a-agen%C8%9Bilor)
    - [TrafficSignalAgent](#trafficsignalagent)
    - [VehicleAgent](#vehicleagent)
    - [PublicTransportAgent](#publictransportagent)
    - [EmergencyVehicleAgent](#emergencyvehicleagent)
    - [MaintenanceAgent](#maintenanceagent)
    - [TrafficMonitoringAgent](#trafficmonitoringagent)
    - [CoordinatorAgent](#coordinatoragent)
- [Setare și Rulare](#setare-%C8%99i-rulare)
- [Utilizare](#utilizare)
- [Licență](#licen%C8%9B%C4%83)

## Structura Proiectului

- `src/`
    - `TrafficSignalAgent.java`
    - `VehicleAgent.java`
    - `PublicTransportAgent.java`
    - `EmergencyVehicleAgent.java`
    - `MaintenanceAgent.java`
    - `TrafficMonitoringAgent.java`
    - `CoordinatorAgent.java`
    - `TrafficManagementSystem.java` (Clasa principală pentru lansarea sistemului)

## Prezentare Generală a Agenților

### TrafficSignalAgent

Gestionează semafoarele și comunică cu alți agenți pentru a controla fluxul traficului.

### VehicleAgent

Simulează vehicule individuale. Sunt create mai multe instanțe cu destinații diferite.

### PublicTransportAgent

Simulează vehiculele de transport public, cum ar fi autobuzele. Gestionează rutele și orarele.

### EmergencyVehicleAgent

Simulează vehicule de urgență, cum ar fi ambulanțele. Depășește regulile normale de trafic pentru a ajunge rapid la destinații.

### MaintenanceAgent

Se ocupă de activitățile de întreținere a drumurilor și notifică alți agenți despre programele de întreținere și zonele de lucru.

### TrafficMonitoringAgent

Colectează date despre trafic și le transmite altor agenți. Monitorizează condițiile de trafic.

### CoordinatorAgent

Coordonează activitățile tuturor celorlalți agenți. Rezolvă conflictele și actualizează politicile de gestionare a traficului.

## Setare și Rulare

1. **Clonează repository-ul**:

```sh
git clone https://github.com/AlexandruIacobescu/ProiectSMA.git
```

2. **Asigură-te că ai instalat JADE și Java**:
    - Asigură-te că este instalat Java (preferabil JDK 8 sau mai nou).

3. **Rulează proiectul**: Deschideți proiectul într-un IDE, cum ar fi `intelliJ IDEA`, și rulează aplicația din clasa `TrafficManagementSystem.java`.


## Utilizare

La rularea proiectului, următorii agenți vor fi lansați:

- `TrafficSignalAgent`
- `VehicleAgent1`, `VehicleAgent2`, `VehicleAgent3`
- `PublicTransportAgent`
- `EmergencyVehicleAgent`
- `MaintenanceAgent`
- `TrafficMonitoringAgent`
- `CoordinatorAgent`
- `rma` (Agent de Monitorizare la Distanță pentru GUI)

### Monitorizare

- RMA oferă o interfață grafică pentru a monitoriza starea platformei și a agenților.

## Autor

- Iacobescu Alexandru
    - [GitHub](https://github.com/AlexandruIacobescu)
    - [Email](mailto:alexandru.iacobescu01@e-uvt.ro)

## Licență

Acest proiect este licențiat sub Licența MIT. Vezi fișierul LICENSE pentru detalii.
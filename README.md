# 🚗 Sistema di Noleggio Mezzi

Progetto realizzato per il corso di **Ingegneria del Software** (A.A. 2025/2026). 
Il sistema informatizza la gestione operativa e telematica di una compagnia di noleggio veicoli (auto, furgoni, veicoli elettrici), coordinando le prenotazioni web dei clienti e le operazioni fisiche al banco gestite dallo staff.

## 👥 Autori
* **Anastasia Alida Salamanca**
* **Lorenzo Pantano**
* **Sara Scavone**

---

## 🛠️ Tecnologie Utilizzate
* **Linguaggio:** Java 
* **Framework Backend:** Spring Boot (Web, MVC)
* **Motore di Template (Frontend):** Thymeleaf
* **Build Automation:** Maven
* **Testing:** JUnit 5 
* **Architettura:** Layered Architecture (Controller, Service, Repository, Model) con Domain-Driven Design.

---

## ✨ Funzionalità Principali (Casi d'Uso)
Il sistema è stato sviluppato in modo iterativo e incrementale coprendo i seguenti Casi d'Uso:

1. **UC1 - Autentica Cassiere:** Autenticazione sicura dello staff per l'accesso alla dashboard del sistema gestionale.
2. **UC2 - Registrazione Cliente:** Registrazione di un nuovo utente nel sistema con validazione formale dei dati.
3. **UC3 - Visualizza Disponibilità Mezzi:** Ricerca e visualizzazione dello stato della flotta (filtrando i veicoli in base allo stato logico e di carica).
4. **UC4 - Avvia Noleggio:** Assegnazione fisica del veicolo al cliente in filiale, con controllo in tempo reale dell'affidabilità dell'utente.
5. **UC5 - Concludi Noleggio:** Registrazione del rientro del veicolo, calcolo automatico dinamico delle tariffe (inclusi extra) e addebito sul credito.
6. **UC6 - Segnala Guasto:** Interfaccia per la segnalazione di problemi fisici, sinistri o furti del veicolo.
7. **UC7 - Aggiungi Nuovo Mezzo:** Inserimento di nuovi veicoli nel parco auto della flotta tramite costrutti sicuri e validati (Pattern Builder).
8. **UC8 - Effettua Manutenzione:** Gestione del ciclo di vita sfortunato del mezzo (invio in officina, registrazione interventi nello storico e dismissione).
9. **UC9 - Effettua Prenotazione WEB:** Motore di ricerca lato cliente per i mezzi disponibili, con incrocio dinamico delle date per evitare sovrapposizioni e generazione del codice PNR.
10. **UC10 - Gestisci Blacklist:** Sospensione e riabilitazione degli account clienti in tempo reale (RBAC) in caso di insolvenza o comportamenti illeciti.

---

## 🧩 Design Pattern Applicati
Il sistema fa largo uso dei pattern GoF per garantire un'architettura manutenibile e scalabile:
* **State Pattern:** Gestisce il ciclo di vita fisico del veicolo (`Disponibile`, `Noleggiato`, `In Manutenzione`, ecc.), bloccando transizioni illecite.
* **Strategy Pattern:** Permette di iniettare a runtime algoritmi di calcolo tariffario differenti (es. Tariffa Oraria o Giornaliera).
* **Decorator Pattern:** Utilizzato per "avvolgere" le tariffe base aggiungendo dinamicamente costi extra (es. Assicurazione Furto, Penale Giovani).
* **Builder Pattern:** Semplifica e mette in sicurezza la creazione dell'oggetto complesso `Mezzo` validandone i parametri di immatricolazione.

---

## 🚀 Come avviare l'applicazione

### Prerequisiti
* Java Development Kit (JDK) 17 o superiore installato.
* Apache Maven installato (opzionale se si utilizza un IDE con Maven integrato come IntelliJ o Eclipse).

### Avvio da Terminale (con Maven)
1. Aprire il terminale nella directory radice del progetto (dove si trova il file `pom.xml`).
2. Eseguire il comando per pulire e compilare il progetto:   mvn clean install
3. Lanciare il server: mvn spring-boot:run
4. Esecuzione dei test: mvn test

## Credenziali di test:
(Nota: Al primo avvio, l'applicazione popola automaticamente il database in memoria nel file App.java con alcuni dati di test pronti all'uso).
Nel progetto non è stato implementato un database per lo storage di dati persistenti

**Area Staff (Cassiere / Amministratore):**

Username: admin

Password: admin123

**Area Cliente (Prenotazioni Web):**

Puoi effettuare il login utilizzando uno qualsiasi dei seguenti account pre-registrati:

Cliente 1:
Email: gattopancrazio@gmail.com
Password: pancrazio123

Cliente 2:
Email: sarocane@gmail.com
Password: saro123

Cliente 3:
Email: alfioconiglio@gmail.com
Password: alfio123

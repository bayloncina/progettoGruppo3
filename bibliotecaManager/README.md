# Biblioteca Digitale — Progetto OOP di Gruppo 3

## Descrizione
Sistema Java per la gestione di una Biblioteca Digitale che permette di catalogare risorse digitali (Libri, Riviste, Ebook), gestire utenti e prestiti, e consultare l'inventario completo. Il sistema utilizza un database MySQL per la persistenza dei dati.

## Obiettivi
Il progetto dimostra le tre regole fondamentali dell'OOP:
- **Incapsulamento** — tutti gli attributi sono `private` con getter/setter con controlli sui valori
- **Ereditarietà** — `Libro`, `Rivista`, `Ebook` estendono la classe base astratta `Risorsa`
- **Polimorfismo** — `visualizzaDettagli()` si comporta diversamente per ogni tipo di risorsa

---

## Struttura del Progetto
```
src/
├── controller/
│   └── Controller.java      → logica CRUD risorse e gestione utenti/prestiti
├── model/
│   ├── Risorsa.java         → classe astratta base
│   ├── Libro.java           → estende Risorsa, attributo: autore
│   ├── Rivista.java         → estende Risorsa, attributo: numero
│   ├── Ebook.java           → estende Risorsa, attributo: formato
│   ├── Utente.java          → gestione utente e lista risorse in prestito
│   ├── Biblioteca.java      → contenitore risorse e utenti
│   └── Prestito.java        → rappresenta un prestito tra utente e risorsa
├── service/
│   ├── DBConn.java          → connessione MySQL e metodi CRUD sul db
│   └── DBSetup.java         → creazione automatica database e tabelle
├── utility/
│   └── Utility.java         → metodi di supporto per l'input utente
├── view/
│   └── View.java            → interfaccia utente a console
└── App.java                 → avvio applicazione
```

---

## Divisione dei Compiti

| Membro | Area di Responsabilità |
|--------|----------------------|
| **Luca** | `service/` — connessione MySQL, creazione database e tabelle, metodi CRUD sul db |
| **Maria** | `model/` — `Risorsa` (astratta), `Libro`, `Rivista`, `Ebook`, `Utente`, `Biblioteca` — incapsulamento ed ereditarietà |
| **Dennis** | `controller/` + `view/` — logica CRUD, gestione prestiti e interfaccia utente |

---

## Funzionalità
- Aggiunta di risorse digitali (Libro, Rivista, Ebook)
- Aggiunta e gestione utenti
- Prestito e restituzione di risorse
- Ricerca per codice identificativo
- Aggiornamento titolo e anno di una risorsa
- Eliminazione di una risorsa
- Salvataggio persistente su database MySQL
- Caricamento automatico delle risorse dal DB all'avvio
- Stampa inventario completo con polimorfismo
- Stampa lista risorse per utente

---

## Requisiti Tecnici
- Java 17+
- MySQL + XAMPP
- IDE: VS Code
- Driver: `mysql-connector-java` nella cartella `lib/`

---

## Come Eseguire
1. Clona il repository
2. Avvia XAMPP e MySQL
3. Apri il progetto in VS Code
4. Aggiungi `mysql-connector-java.jar` al classpath dalla cartella `lib/`
5. Esegui `App.java` — il database viene creato automaticamente al primo avvio

---

## Autori
- **Luca**
- **Dennis**
- **Maria**
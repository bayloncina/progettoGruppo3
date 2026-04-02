# 📚 Biblioteca Digitale — Progetto OOP di Gruppo 3

## Descrizione
Sistema Java per la gestione di una Biblioteca Digitale che permette di catalogare risorse digitali (Libri, Riviste, Ebook), gestire utenti e consultare l'inventario completo. Il sistema utilizza un database MySQL per la persistenza dei dati.

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
│   └── Controller.java      → CRUD risorse (aggiungi, cerca, aggiorna, elimina)
├── model/
│   ├── Risorsa.java         → classe astratta base
│   ├── Libro.java           → estende Risorsa, attributo: autore
│   ├── Rivista.java         → estende Risorsa, attributo: numero
│   └── Ebook.java           → estende Risorsa, attributo: formato
├── service/
│   └── DBConn.java          → connessione MySQL, creazione db/tabelle, salva/aggiorna/elimina
├── view/
│   └── (interfaccia utente)
└── App.java                 → avvio applicazione
```

---

## Divisione dei Compiti

| Membro | Area di Responsabilità |
|--------|----------------------|
| **Luca** | `service/DBConn.java` — connessione MySQL, creazione database e tabelle, metodi CRUD sul db |
| **Miriam** | `model/` — `Risorsa` (astratta), `Libro`, `Rivista`, `Ebook` — incapsulamento ed ereditarietà |
| **Dennis** | `controller/Controller.java` + `view/` — logica CRUD e interfaccia utente |

---

## Funzionalità
- Aggiunta di risorse digitali (Libro, Rivista, Ebook)
- Ricerca per codice identificativo
- Aggiornamento titolo e anno di una risorsa
- Eliminazione di una risorsa
- Salvataggio persistente su database MySQL
- Stampa inventario completo con polimorfismo

---

## Requisiti Tecnici
- Java 17+
- MySQL + XAMPP
- IDE: VS Code / IntelliJ
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

---

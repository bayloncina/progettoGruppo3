# Biblioteca Digitale — Progetto OOP di Gruppo 3


## Descrizione
Sistema Java per la gestione di una Biblioteca Digitale che permette di catalogare risorse digitali (Libri, Riviste, Ebook), gestire utenti e prestiti, e consultare l'inventario completo.

## Obiettivi
Il progetto dimostra le tre regole fondamentali dell'OOP:
- **Incapsulamento** — tutti gli attributi sono `private` con getter/setter
- **Ereditarietà** — `Libro`, `Rivista`, `Ebook` estendono la classe base `Risorsa`
- **Polimorfismo** — `visualizzaDettagli()` si comporta diversamente per ogni tipo di risorsa

---

## Struttura del Progetto
```
src/
├── controller/
│   └── Controller.java      → logica di controllo
├── model/
│   ├── Risorsa.java         → classe base
│   ├── Libro.java           → estende Risorsa
│   ├── Rivista.java         → estende Risorsa
│   └── Ebook.java           → estende Risorsa
├── service/
│   └── DBConn.java          → connessione database
├── view/
│   └── (da definire)
└── App.java                 → avvio applicazione
```


---

## Divisione dei Compiti

## Divisione dei Compiti

| Membro | Area di Responsabilità |
|--------|----------------------|
| **Luca** | `service/DBConn.java` — connessione MySQL e gestione database |
| **Miriam** | `model/` — `Risorsa`, `Libro`, `Rivista`, `Ebook` — classi base e ereditarietà |
| **Dennis** | `controller/` + `view/` — logica di controllo e interfaccia utente |
---

## Requisiti Tecnici
- Java 17+
- MySQL + XAMPP (per il database)
- IDE: VS Code / IntelliJ

---

## Come Eseguire
1. Clona il repository
2. Avvia XAMPP e MySQL
3. Apri il progetto in VS Code o IntelliJ
4. Esegui `Main.java`

---

## Autori
- **Luca** 
- **Dennis** 
- **Maria** 

---

> Progetto sviluppato per il corso di **Programmazione ad Oggetti**.  
> Ogni membro ha lavorato su un file/classe principale separata seguendo le linee guida OOP.
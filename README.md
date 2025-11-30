
# FXNotes – JavaFX Notes App (Learning Template)

Template JavaFX application using a Model, Controller, Service, Repository pattern + Strategy layering.

## Features

- Java 17
- JavaFX 21
- Architecture:
  - model
  - repository (Interfaces, Implementation + persistent strategies)
  - services
  - ui
- CSV persistence or InMemory repository
- Save strategies:
  - ImmediateSaveStrategy
  - ExitSaveStrategy

## Run

```bash
mvn clean javafx:run

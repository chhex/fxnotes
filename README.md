
# FXNotes – JavaFX Notes App (Learning Template)

Template JavaFX application using a Model, Controller, Service, Repository pattern + Strategy for persistence timing.

## Features

- Java 17
- JavaFX 21
- No FXML, pure Java UI
- Clean architecture:
  - model
  - data (repositories + strategies)
  - services
  - ui
- CSV persistence or InMemory repository
- Save strategies:
  - ImmediateSaveStrategy
  - ExitSaveStrategy

## Run

```bash
mvn clean javafx:run

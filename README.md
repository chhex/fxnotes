[![Java CI with Maven](https://github.com/chhex/fxnotes/actions/workflows/maven.yml/badge.svg)](https://github.com/chhex/fxnotes/actions/workflows/maven.yml)
# FXNotes – JavaFX Notes App Template

Template JavaFX application using a Model, Controller, Service, Repository pattern + Strategy layering and no additional dependencies except for JavaFx

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

# Java RMI Parts Repository Project

This project is a client-server application using Java RMI (Remote Method Invocation). The system consists of an RMI server that manages a parts repository and an RMI client that interacts with this server to perform CRUD (Create, Read, Update, Delete) operations on parts.

## Project Structure

- **`src/`**: Contains the source code of the project divided into packages for client, server, model, repository, service, and utilities.
    - **`br/com/parts/client/`**: Contains the RMI client code.
    - **`br/com/parts/server/`**: Contains the RMI server code.
    - **`br/com/parts/model/`**: Contains the interfaces and implementation classes related to parts.
    - **`br/com/parts/repository/`**: Contains the interface and implementation of the parts repository.
    - **`br/com/parts/service/`**: Contains the service class for managing parts operations.
    - **`br/com/parts/utils/`**: Contains utility classes, such as the logger manager and the `Result` class.

## Features

### Server
- **Starts an RMI server** that exposes a parts repository.
- **Manages** operations such as adding, updating, retrieving, and deleting parts.

### Client
- **Connects to the RMI server** and interacts with the parts repository.
- **Allows** users to list, retrieve, add, update, and delete parts.
- **Manages** subparts associated with parts.

## Requirements

- Java JDK 11 or higher
- RMI Registry (can be started automatically by the server)

## Execution

### Start the Server
To start the RMI server, use the server.bat script. Navigate to the run-server directory and execute the **bat**:
```bash
server.bat
```
The run-server.bat script will compile all Java files and then start the RMI server, and the RMI Registry.

### Start the Client
In another terminal, use the client.bat script. Navigate to the run-client directory and execute the **bat**:
```bash
client.bat
```

## Client Commands
- listp - Lists all parts in the repository.
- getp - Retrieves a part by its code.
- addp - Adds a new part.
- updatep - Updates an existing part.
- deletep - Deletes a part by its code.
- showp - Displays details of the currently selected part.
- clearlist - Clears the current subparts list.
- addsubpart - Adds a subpart to the current part.
- quit - Exits the client.

## Logs
Logs are configured to display detailed information about the execution of the client and server. Be sure to check the console output for information on errors and performed operations.

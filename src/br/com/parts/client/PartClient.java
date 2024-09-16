package br.com.parts.client;

import br.com.parts.connection.RMIConnectionManager;
import br.com.parts.factory.PartFactory;
import br.com.parts.model.IPart;
import br.com.parts.model.Impl.SubPart;
import br.com.parts.repository.IPartRepository;
import br.com.parts.service.PartService;
import br.com.parts.utils.LoggerConfig;
import br.com.parts.utils.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class PartClient {
    private static final Logger logger = Logger.getLogger(PartClient.class.getName());
    private static PartService partService;
    private static IPart currentPart;
    private final static List<SubPart> currentSubParts = new ArrayList<>();

    public static void main(String[] args) {
        LoggerConfig.configureLogger();
        RMIConnectionManager connectionManager =
                new RMIConnectionManager("rmi://127.0.0.1", "PartRepository");

        Result<IPartRepository> connectionResult = connectionManager.connect();
        if (connectionResult.isSuccess()) {
            partService = new PartService(connectionResult.getValue());
            menu();
        } else {
            logger.severe("Failed to connect to the repository: " + connectionResult.getErrorMessage());
            System.exit(1);
        }
    }

    public static void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nCommands: listp, getp, addp, updatep, deletep, showp, clearlist, addsubpart, quit");
            String command = scanner.nextLine();
            switch (command) {
                case "listp":
                    listParts();
                    break;
                case "getp":
                    getPart(scanner);
                    break;
                case "addp":
                    addPart(scanner);
                    break;
                case "updatep":
                    updatePart(scanner);
                    break;
                case "deletep":
                    deletePart(scanner);
                    break;
                case "showp":
                    showPart();
                    break;
                case "clearlist":
                    clearSubParts();
                    break;
                case "addsubpart":
                    addSubPart(scanner);
                    break;
                case "quit":
                    shutdown();
                    break;
                default:
                    System.out.println("Unknown command. Please enter a valid command.");
            }
        }
    }

    private static void updatePart(Scanner scanner) {
        if (currentPart != null) {
            System.out.print("New name for part: ");
            String newName = scanner.nextLine();
            System.out.print("New description for part: ");
            String newDescription = scanner.nextLine();

            currentPart = PartFactory.createPart(currentPart.getCode(), newName, newDescription, currentPart.isPrimitive());
            addSubPartsToPart(currentPart);

            Result<Void> updateResult = partService.updatePart(currentPart);
            if (updateResult.isSuccess()) {
                System.out.println("Part updated successfully!");
            } else {
                System.out.println(updateResult.getErrorMessage());
            }
        } else {
            System.out.println("No part selected to update. Please select a part first.");
        }
    }

    private static void deletePart(Scanner scanner) {
        System.out.print("Enter the part code to delete: ");
        int code = scanner.nextInt();
        scanner.nextLine();
        Result<Void> deleteResult = partService.deletePart(code);
        if (deleteResult.isSuccess()) {
            System.out.println("Part deleted successfully!");
        } else {
            System.out.println(deleteResult.getErrorMessage());
        }
    }

    private static void listParts() {
        Result<List<IPart>> result = partService.getAllParts();
        if (result.isSuccess()) {
            result.getValue().forEach(part ->
                    System.out.println("Part ID: " + part.getCode() + ", Name: " + part.getName()));
        } else {
            System.out.println(result.getErrorMessage());
        }
    }

    private static void getPart(Scanner scanner) {
        System.out.print("Enter part code: ");
        int code = scanner.nextInt();
        scanner.nextLine();
        Result<IPart> result = partService.getPartByCode(code);
        if (result.isSuccess()) {
            currentPart = result.getValue();
            System.out.println("Part found: " + currentPart.getName());
        } else {
            System.out.println(result.getErrorMessage());
        }
    }

    private static void addPart(Scanner scanner) {
        System.out.print("Part name: ");
        String name = scanner.nextLine();
        System.out.print("Part description: ");
        String description = scanner.nextLine();

        boolean isPrimitive = currentSubParts.isEmpty();
        Result<Integer> partCountResult = partService.getPartCount();

        if (partCountResult.isSuccess()) {
            IPart newPart = PartFactory.createPart
                    (partCountResult.getValue() + 1, name, description, isPrimitive);

            addSubPartsToPart(newPart);

            Result<Void> addPartResult = partService.addPart(newPart);
            if (addPartResult.isSuccess()) {
                System.out.println("Part added successfully!");
                clearSubParts();
            } else {
                System.out.println(addPartResult.getErrorMessage());
            }
        } else {
            System.out.println(partCountResult.getErrorMessage());
        }
    }

    private static void addSubPartsToPart(IPart newPart) {
        if (!currentSubParts.isEmpty()) {
            for (SubPart subPart : currentSubParts) {
                Result<IPart> subComponentResult =
                        partService.getPartByCode(subPart.getPart().getCode());
                if (subComponentResult.isFailure()) {
                    System.out.println(subComponentResult.getErrorMessage());
                    return;
                }
                newPart.addSubPart(subComponentResult.getValue(), subPart.getQuantity());
            }
        }
    }

    private static void showPart() {
        if (currentPart != null) {
            System.out.println("Part ID: " + currentPart.getCode());
            System.out.println("Name: " + currentPart.getName());
            System.out.println("Description: " + currentPart.getDescription());
            System.out.println("Is Primitive: " + currentPart.isPrimitive());
            System.out.println("Subparts:");
            currentPart.getSubParts().forEach(subPart ->
                    System.out.println("Subpart ID: " + subPart.getPart().getCode() +
                            ", Quantity: " + subPart.getQuantity()));
        } else {
            System.out.println("No current part selected. Please select a part to show.");
        }
    }

    private static void clearSubParts() {
        currentSubParts.clear();
        System.out.println("SubParts list cleaned Success!");
    }

    private static void addSubPart(Scanner scanner) {
        System.out.print("SubPart code: ");
        int partCode = Integer.parseInt(scanner.nextLine());

        Result<IPart> partResult = partService.getPartByCode(partCode);
        if (partResult.isSuccess()) {
            System.out.print("SubPart quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            SubPart subPart = PartFactory.createSubPart(partResult.getValue(), quantity);
            currentSubParts.add(subPart);
            System.out.println("SubPart added successfully!");
        } else {
            System.out.println(partResult.getErrorMessage());
        }
    }

    private static void shutdown() {
        System.out.println("Shutting down...");
        System.exit(0);
    }
}
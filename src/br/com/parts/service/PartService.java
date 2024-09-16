package br.com.parts.service;

import br.com.parts.model.IPart;
import br.com.parts.repository.IPartRepository;
import br.com.parts.utils.Result;

import java.rmi.RemoteException;
import java.util.List;

public class PartService {
    private final IPartRepository repository;

    public PartService(IPartRepository repository) {
        this.repository = repository;
        Result<String> result = getRepositoryName();
        if (result.isSuccess()) {
            System.out.println("Connected to repository: " + result.getValue());
        }
    }

    public Result<String> getRepositoryName() {
        try {
            return Result.success(repository.getRepositoryName());
        } catch (RemoteException e) {
            return Result.failure("Error getting repository name: " + e.getMessage());
        }
    }

    public Result<Integer> getPartCount() {
        try {
            return Result.success(repository.getPartCount());
        } catch (RemoteException e) {
            return Result.failure("Error getting part count: " + e.getMessage());
        }
    }

    public Result<Void> addPart(IPart part) {
        try {
            repository.addPart(part);
            return Result.success(null);
        } catch (RemoteException e) {
            return Result.failure("Error adding part: " + e.getMessage());
        }
    }

    public Result<IPart> getPartByCode(int code) {
        try {
            IPart part = repository.getPartByCode(code);
            if (part != null) {
                return Result.success(part);
            } else {
                return Result.failure("Part not found");
            }
        } catch (RemoteException e) {
            return Result.failure("Error getting part: " + e.getMessage());
        }
    }

    public Result<Void> updatePart(IPart part) {
        try {
            repository.updatePart(part);
            return Result.success(null);
        } catch (RemoteException e) {
            return Result.failure("Error updating part: " + e.getMessage());
        }
    }

    public Result<Void> deletePart(int code) {
        try {
            repository.deletePart(code);
            return Result.success(null);
        } catch (RemoteException e) {
            return Result.failure("Error deleting part: " + e.getMessage());
        }
    }

    public Result<List<IPart>> getAllParts() {
        try {
            return Result.success(repository.getAllParts());
        } catch (RemoteException e) {
            return Result.failure("Error fetching parts: " + e.getMessage());
        }
    }
}
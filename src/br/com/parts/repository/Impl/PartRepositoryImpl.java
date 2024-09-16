package br.com.parts.repository.Impl;

import br.com.parts.model.IPart;
import br.com.parts.repository.IPartRepository;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class PartRepositoryImpl extends UnicastRemoteObject implements IPartRepository {
    private final List<IPart> parts = new ArrayList<>();
    private final String repositoryName;

    public PartRepositoryImpl(String name) throws RemoteException {
        super();
        this.repositoryName = name;
    }

    @Override
    public synchronized String getRepositoryName() throws RemoteException {
        return repositoryName;
    }

    @Override
    public synchronized int getPartCount() throws RemoteException {
        return parts.size();
    }

    @Override
    public synchronized void addPart(IPart part) throws RemoteException {
        parts.add(part);
    }

    @Override
    public synchronized IPart getPartByCode(int code) throws RemoteException {
        return parts.stream()
                .filter(part -> part.getCode() == code)
                .findFirst()
                .orElse(null);
    }

    @Override
    public synchronized List<IPart> getAllParts() throws RemoteException {
        return new ArrayList<>(parts);
    }

    @Override
    public synchronized void updatePart(IPart updatedPart) throws RemoteException {
        IPart existingPart = getPartByCode(updatedPart.getCode());
        if (existingPart != null) {
            parts.remove(existingPart);
            parts.add(updatedPart);
        }
    }

    @Override
    public synchronized void deletePart(int code) throws RemoteException {
        parts.removeIf(part -> part.getCode() == code);
    }
}
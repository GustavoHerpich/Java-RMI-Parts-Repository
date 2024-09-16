package br.com.parts.repository;

import br.com.parts.model.IPart;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IPartRepository extends Remote {
    String getRepositoryName() throws RemoteException;
    int getPartCount() throws RemoteException;
    void addPart(IPart part) throws RemoteException;
    IPart getPartByCode(int code) throws RemoteException;
    List<IPart> getAllParts() throws RemoteException;
    void updatePart(IPart part) throws RemoteException;
    void deletePart(int code) throws RemoteException;
}

package br.com.parts.server;

import br.com.parts.repository.IPartRepository;
import br.com.parts.repository.Impl.PartRepositoryImpl;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class PartServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            IPartRepository partRepository = new PartRepositoryImpl("Central Repository");
            Naming.rebind("rmi://127.0.0.1/PartRepository", partRepository);
            System.out.println("RMI server is running...");
        } catch (RemoteException e) {
            System.err.println("RMI error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
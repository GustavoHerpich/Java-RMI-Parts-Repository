package br.com.parts.connection;

import br.com.parts.repository.IPartRepository;
import br.com.parts.utils.Result;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.util.logging.Logger;

public class RMIConnectionManager {
    private static final Logger logger = Logger.getLogger(RMIConnectionManager.class.getName());
    private final String serverAddress;
    private final String serviceName;

    public RMIConnectionManager(String serverAddress, String serviceName) {
        this.serverAddress = serverAddress;
        this.serviceName = serviceName;
    }

    public Result<IPartRepository> connect() {
        try {
            IPartRepository partRepository =
                    (IPartRepository) Naming.lookup(serverAddress + "/" + serviceName);
            return Result.success(partRepository);
        } catch (NotBoundException e) {
            logger.severe("Service not bound: " + e.getMessage());
            return Result.failure("Service not bound: " + e.getMessage());
        } catch (RemoteException e) {
            logger.severe("Remote exception: " + e.getMessage());
            return Result.failure("Remote exception: " + e.getMessage());
        } catch (Exception e) {
            logger.severe("Connection error: " + e.getMessage());
            return Result.failure("Connection error: " + e.getMessage());
        }
    }
}
package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.communication_message.MessageNet;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.UpdateModel;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This is the remote interface of Network handler RMI that being downloaded and used by
 * server to send the request messages an to obtain the responses from a client.
 * The client if it will decides to use RMI , it create the Network Handler RMI that creates the RMI register with this interface
 * @author Cysko7927
 */
public interface InterfaceNetworkHandlerRMI extends Remote
{
    public MessageNet getResponseMessage() throws RemoteException;
    public void receiveMessageRequest(MessageNet message) throws RemoteException, Exception;
    public void updateModel(UpdateModel message) throws RemoteException;
}

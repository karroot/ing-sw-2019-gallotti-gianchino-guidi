package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.communication_message.MessageNet;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.UpdateModel;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceNetworkHandlerRMI extends Remote
{
    public MessageNet getResponseMessage() throws RemoteException;
    public void receiveMessageRequest(MessageNet message) throws RemoteException, Exception;
    public void updateModel(UpdateModel message) throws RemoteException;
}

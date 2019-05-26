package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.communication_message.MessageNet;
import it.polimi.deib.se2018.adrenalina.communication_message.UpdateModel;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class NetworkHandlerRMI extends UnicastRemoteObject implements InterfaceNetworkHandlerRMI
{

    protected NetworkHandlerRMI() throws RemoteException
    {

    }

    @Override
    public MessageNet getResponseMessage() {
        return null;
    }

    @Override
    public void receiveMessageRequest(MessageNet message) {

    }

    @Override
    public void updateModel(UpdateModel message) {

    }
}

package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.communication_message.MessageNet;
import it.polimi.deib.se2018.adrenalina.communication_message.UpdateModel;

public interface InterfaceNetworkHandlerRMI
{
    public MessageNet getResponseMessage();
    public void receiveMessageRequest(MessageNet message);
    public void updateModel(UpdateModel message);
}

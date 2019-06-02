package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.communication_message.MessageNet;
import it.polimi.deib.se2018.adrenalina.communication_message.RequestInput;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;
import it.polimi.deib.se2018.adrenalina.communication_message.UpdateModel;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrivateView extends Observable<ResponseInput> implements Observer<RequestInput>
{

    public PrivateView() throws RemoteException
    {

    }



    public void showMenu()
    {

    }

    public void newGame()
    {

    }

    public void showBoard()
    {

    }

    public void startRound()
    {

    }

    public void showPowerUp()
    {

    }

    public void selectPowerUp()
    {

    }

    public void showWeapons()
    {

    }

    public void selectWeapon()
    {

    }

    public void showAction()
    {

    }

    public void selectAction()
    {

    }

    public void startFrenesy()
    {

    }

    public void showFinalScore()
    {

    }

    public void showMessage(String message)
    {

    }

    public void showError(String message)
    {

    }

    public void showPlayerBoard()
    {

    }

    /**
     * Thi method being called by Network Handler to ask at view to update its copy of the model to show
     * at the user
     * @param message message that contains the copy immutable of the model that arrives from server
     */
    public void updateModelCopy(UpdateModel message)
    {

    }

    /**
     * This method being used to ask some inputs to user
     * The inputs depends by type of message of the request input that arrives from Server
     * @param message message that will use to ask the inputs at the user
     * @throws Exception
     */
    @Override
    public void update(RequestInput message) throws Exception
    {

    }


}

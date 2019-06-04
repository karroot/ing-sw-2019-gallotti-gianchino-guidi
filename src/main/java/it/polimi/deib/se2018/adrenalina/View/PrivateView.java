package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.communication_message.RequestInput;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.UpdateModel;

import java.rmi.RemoteException;

public class PrivateView extends Observable<ResponseInput> implements Observer<RequestInput>
{

    Terminal terminal;
    private boolean firstTurn;

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
        if (firstTurn)
        {
         //Chiedi al controller di pescare due powerUP
            notify(/*Messaggio per chiedere al controllore d'invocare il metodo che pesca un powerUP*/);
         //Chiedi al giocatore quale carta PowerUp usare per il respawn
            showPowerUp();
            int choice = selectPowerUp();
            notify(/*Messaggio per chiedere al controllore d'invocare il metodo che usa un PowerUP per il respawn*/);

        }

        //Mostra le azioni e fanne scegliere una all'utente
        showAction();
        int choice = selectAction();


    }

    public void showPowerUp()
    {

    }

    public int selectPowerUp()
    {
        return 0;
    }

    public void showWeapons()
    {

    }

    public int selectWeapon()
    {
        return 0;

    }

    public void showAction()
    {

    }

    public int selectAction()
    {
        return 0;
    }

    public void startFrenesy()
    {

    }

    public void showFinalScore(String messageWithFinalScore)
    {

    }

    public void showMessage(String message)
    {

    }

    public void showError(String message)
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

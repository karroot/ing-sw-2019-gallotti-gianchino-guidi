package it.polimi.deib.se2018.adrenalina.Controller;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Model;
import it.polimi.deib.se2018.adrenalina.View.Observer;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Controller implements Observer<ResponseInput>
{
    private Model model;
    //Controller deve avere un riferimento alla virtual view
    /*
    Quando il controller deve richiedere un input prima crea un thread che
    esegue in parallelo i metodi requestInput e getResponseWithInputs della virtual view e poi passa la risposta
    attraverso il metodo update
    Poi si mette in pausa fino a quando il thread non finisce
    */
    public Controller()
    {

    }

    public void createNewGame (int idArena, ColorId[] players, boolean isFrenetic, boolean onTerminator, int numSkulls, ColorId firstPlayer)
    {

    }






    public void finalScore ()
    {

    }

    public void finalFrenesy ()
    {

    }

    @Override
    public void update(ResponseInput message)
    {

    }
}

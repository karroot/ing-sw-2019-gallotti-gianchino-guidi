package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.List;

public class RequestShootTerminator extends  RequestInput {
    //Attribute for the request
    protected List<ColorId> enemies;

    //Attribute for the response
    protected ColorId target;

    @Override
    public void printActionsAndReceiveInput(Terminal terminal) {
        inputBasicMode();
        responseIsReady = true;
    }

    public RequestShootTerminator(List<ColorId> enemies) {
        this.enemies = enemies;
    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        return new ResponseShootPeopleTerminator(target);
    }
    protected void inputBasicMode()
    {
        int i = 1;

        System.out.println("Scegli un nemico a cui sparare:");

        for (ColorId t: enemies)
        {
            System.out.println(i + ":" + t);
            i++;
        }

        int anInt = inputInt(1, i - 1);

        target= enemies.get(anInt);

    }
}

package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.View.Terminal;

import java.util.List;
/**
 *   This class represents the request that the conroller sends at the client if the player decided
 *  to shot with terminator
 * @author Karroot
 */
public class RequestShootTerminator extends  RequestInput {
    //Attribute for the request
    protected List<ColorId> enemies;

    //Attribute for the response
    protected ColorId target;
    /**
     * Ask at the user who want to shot with terminator
     * @param terminal terminal of the private view to print and ask the inputs
     */
    @Override
    public void printActionsAndReceiveInput(Terminal terminal) { this.terminal=terminal;
        inputBasicMode();
        responseIsReady = true;
    }

    /**
     * this method set the enemies attribute
     * @param enemies list of enemies
     */
    public RequestShootTerminator(List<ColorId> enemies) {
        this.enemies = enemies;
    }

    /**
     * this method return the response for shoot people terminator
     * @return response for shoot people terminator
     * @throws IllegalStateException in input is not take yet
     */
    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        return new ResponseShootPeopleTerminator(target);
    }


    public List<ColorId> getEnemies() {
        return enemies;
    }

    protected void inputBasicMode()
    {
        int i = 1;

        terminal.addTextInput("Scegli un nemico a cui sparare:");

        for (ColorId t: enemies)
        {
            terminal.addOptionInput(i + ":" + t);
            i++;
        }

        int anInt = terminal.inputInt(1, i - 1);

        target= enemies.get(anInt-1);

    }
}

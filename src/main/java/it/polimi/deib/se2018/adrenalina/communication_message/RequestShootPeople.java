package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;

import java.util.List;

public class RequestShootPeople extends RequestInput
{
    //Attribute for the request
    protected List<WeaponCard> playersBasicMode;//Targets for the basic mode

    //Attribute for the response
    protected WeaponCard targetBasicMode;//Target chosen for the basic mode

    @Override
    public void printActionsAndReceiveInput() {
        inputBasicMode();
        responseIsReady=true;
    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        return new ResponseShootPeople(targetBasicMode);
    }
    @Override
    protected void  inputBasicMode()
    {
            int i = 1;
            boolean atLeastOne=false;

            for (WeaponCard q: playersBasicMode)
            {
                if(q.isLoaded()){
                    atLeastOne=true;
                    }
            }
        if(atLeastOne)
        {

            System.out.println("Scegli l'arma che vuoi usare:");

            for (WeaponCard w : playersBasicMode)
            {
                if (w.isLoaded())
                {
                    System.out.println(i + ": " + w.getName());
                    i++;
                }
            }

            int anInt = inputInt(1, i - 1);

            targetBasicMode = playersBasicMode.get(anInt - 1);

        }
        else
        {
            System.out.println("nessun'arma disponibile");
        }
    }
}

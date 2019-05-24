package it.polimi.deib.se2018.adrenalina.communication_message;


import java.util.List;

public class RequestShootPeople extends RequestInput
{
    //Attribute for the request
    protected List<String> weaponCardsName;//Targets for the basic mode

    //Attribute for the response
    protected String weaponCardName;//Target chosen for the basic mode

    @Override
    public void printActionsAndReceiveInput() {
        inputBasicMode();
        responseIsReady=true;
    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        return new ResponseShootPeople(weaponCardName);
    }

    protected void  inputBasicMode()
    {
            int i = 1;

            System.out.println("Scegli l'arma che vuoi usare:");

            for (String w : weaponCardsName)
            {

                    System.out.println(i + ": " + w);
                    i++;

            }

            int anInt = inputInt(1, i - 1);

    weaponCardName = weaponCardsName.get(anInt - 1);


    }
}


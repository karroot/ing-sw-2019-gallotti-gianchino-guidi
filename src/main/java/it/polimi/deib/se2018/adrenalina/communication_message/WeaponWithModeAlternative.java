package it.polimi.deib.se2018.adrenalina.communication_message;

public abstract class WeaponWithModeAlternative extends RequestInput
{

    //Attribute for the request
    protected boolean[] avaiableMethod;
    protected String nameAlternaivemode;

    //Attribute for the response
    protected boolean mode;

    @Override
    public void printActionsAndReceiveInput()
    {
        int choice = 0;

        System.out.println("Scegli modalità Arma:");

        if (avaiableMethod[0])
        {
            System.out.println("1:modalità base");
        }

        if (avaiableMethod[1])
        {
            System.out.println("2:"+nameAlternaivemode);
        }

        int j = 0;

        if (avaiableMethod[0] && avaiableMethod[1])
            choice = inputInt(1, 2);

        if (!avaiableMethod[0] && avaiableMethod[1])
            choice = inputInt(2, 2);
        if (avaiableMethod[0] && !avaiableMethod[1])
            choice = inputInt(1, 1);

        if (choice == 1)
        {
            inputBasicMode();
            mode = false;
        }
        else
        {
            inputAlternativeMode();
            mode = true;
        }

    }


    protected abstract void inputAlternativeMode();
    protected abstract void inputBasicMode();
}

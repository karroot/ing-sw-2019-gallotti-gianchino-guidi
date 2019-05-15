package it.polimi.deib.se2018.adrenalina.communication_message;

/**
 * @author Cysko7927
 */
public abstract class WeaponWithModeAlternative extends RequestInput
{

    //Attribute for the request
    protected boolean[] avaiableMethod; //Represent which mode are possible
    protected String nameAlternaivemode; //Name of the alternative mode

    //Attribute for the response
    protected boolean mode; //Represent if the user choices the basic mode(false) or the alternative mode(true)

    /**
     * Method that handles the inputs for the use of a weapon with two alternative mode
     */
    @Override
    public void printActionsAndReceiveInput()
    {
        int choice = 0;
        //Print the possible mode of the weapon

        System.out.println("Scegli modalità Arma:");

        if (avaiableMethod[0])
        {
            System.out.println("1:modalità base");
        }

        if (avaiableMethod[1])//If the alternative mode can be used
        {
            System.out.println("2:"+nameAlternaivemode);
        }

        int j = 0;

        //Handle the possible choice of the users asking the correct inputs
        if (avaiableMethod[0] && avaiableMethod[1])
            choice = inputInt(1, 2);

        if (!avaiableMethod[0] && avaiableMethod[1])
            choice = inputInt(2, 2);
        if (avaiableMethod[0] && !avaiableMethod[1])
            choice = inputInt(1, 1);


        if (choice == 1) //If the user choices the basic mode
        {
            inputBasicMode();//Ask all the information necessary to use the basic mode
            mode = false; //Set the attribute mode
        }
        else
        {
            inputAlternativeMode();//Ask all the information necessary to use the alternative mode
            mode = true;//Set the attribute mode
        }

        responseIsReady = true;

    }

    //Methods for to obtain all necessary information to use the two modes and built the response message
    protected abstract void inputAlternativeMode();
    protected abstract void inputBasicMode();
}

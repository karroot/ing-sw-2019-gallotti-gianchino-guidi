package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Square;

import java.util.List;
import java.util.stream.Collectors;

public class RequestSledgehammer extends WeaponWithModeAlternative
{

    //Attribute for the request
    private List<ColorId> players;//Targets for the basic mode
    private List<String> movementPossible;//Square where can be moved the target chosen by user

    //Attribute for the response
    private ColorId target;//Target chosen for the basic mode or the alternative mode
    boolean move;//Indicates if the user has chosen to move the target
    int x; //Coordinates of the squares where the target will move
    int y;

    /**
     * Create a message of request for the weapon Sledgehammer
     * @param avaiableMethod mode available
     * @param players targets for the basic mode
     * @param movementPossible Square where can be moved the target chosen by user
     * The square will traduce in strings that represent the coordinates
     */
    public RequestSledgehammer(boolean[] avaiableMethod, List<ColorId> players, List<Square> movementPossible)
    {
        this.nameAlternaivemode = "modalità polverizzare";
        this.avaiableMethod = avaiableMethod;
        //Traduce the squares into coordinates
        this.movementPossible = movementPossible.stream().map(Square::toStringCoordinates).collect(Collectors.toList());
        this.players = players;
        responseIsReady = false;
    }

    //Ask at the user to choice the square where to move the target chosen before
    @Override
    protected void inputAlternativeMode()
    {
        inputBasicMode();

        //Ask if the user wants move the target
        System.out.println("Vuoi Spostare il bersaglio?");
        System.out.println("1:Si");
        System.out.println("2:No");

        int anInt = inputInt(1, 2);

        if (anInt == 2)//If the user has chosen no
        {
            move = false; //Close the method
            return;
        }

        //else it continues asking the square where to move target
        move = true;

        System.out.println("Scegli un quadrato dove spostare il tuo bersaglio:");

        int i = 1;

        for (String t:movementPossible)
        {
            System.out.println(i+":"+ t);
            i++;
        }

        int choice = inputInt(1, i - 1);

        x = Integer.parseInt(movementPossible.get(choice -1).substring(4,5));//Works if the coordinates are between 1 and 9
        y = Integer.parseInt(movementPossible.get(choice -1).substring(11));

    }

    //Ask at the user to choice a target for the basic mode
    @Override
    protected void inputBasicMode()
    {
        System.out.println("Scegli bersaglio nel tuo square:");

        int i = 1;

        for (ColorId t:players)
        {
            System.out.println(i+":"+ t);
            i++;
        }

        int choice = inputInt(1, i - 1);

        target = players.get(choice -1);
    }

    /**
     *Generate the response message for the Sledgehammer with all user's choice
     * @return response message
     * @throws IllegalStateException if the method printAction wasn't called yet
     */
    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        if (mode)
            return new ResponseSledgehammer(target,x,y,move);

        return new ResponseSledgehammer(target);
    }
}

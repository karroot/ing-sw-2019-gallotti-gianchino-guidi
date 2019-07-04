package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Square;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Cysko7927
 */
public class RequestShotgun extends WeaponWithModeAlternative
{
    //Attribute for the request
    private List<ColorId> targetsBasicMode;//Targets for the basic mode
    private List<ColorId> targetsAlternativeMode;//Targets for the basic mode
    private List<String> movesPossible;

    //Attribute for the response
    private ColorId targetBasicMode;//Target chosen for the basic mode
    private ColorId targetAlternativeMode;//Target chosen for the basic mode
    private int x;//Coordinates of the squares where the target will move
    private int y;
    private boolean move;//Indicates if the user has chosen to move the target

    /**
     *Create a message of Request of input for weapon Shotgun
     * @param availableMethod Represent the possible mode that can be used
     * @param playersBasicMode All player available for basic mode
     * @param playersAlternativeMode All player available for alternative mode
     * @param movesPossible Square where can be moved the target chosen by user
     */
    public RequestShotgun(boolean[] availableMethod, List<ColorId> playersBasicMode, List<ColorId> playersAlternativeMode,List<Square> movesPossible)
    {
        nameAlternaivemode = "modalità canna lunga";
        this.availableMethod = availableMethod;
        this.targetsBasicMode = playersBasicMode;
        this.targetsAlternativeMode = playersAlternativeMode;
        this.movesPossible = movesPossible.stream().map(Square::toStringCoordinates).collect(Collectors.toList());
        responseIsReady = false;
    }


    //Ask at the user to choice a target for the alternative mode
    @Override
    protected void inputAlternativeMode()
    {
        int  k = 1;

        terminal.addTextInput("Scegli un bersaglio:");

        for (ColorId b:targetsAlternativeMode)
        {
            terminal.addOptionInput(k + ":" + b);
            k++;
        }

        int choice = terminal.inputInt(1,k-1);

        targetAlternativeMode = targetsAlternativeMode.get(choice-1);
    }

    //Ask at the user to choice a target for the basic mode
    @Override
    protected void inputBasicMode()
    {
        int j = 1;

        terminal.addTextInput("Scegli un bersaglio:");

        for (ColorId z :targetsBasicMode)
        {
            terminal.addOptionInput(j + ":" + z);
            j++;
        }

        int choice = terminal.inputInt(1,j-1);

        targetBasicMode = targetsBasicMode.get(choice-1);

        //Ask if the user wants move the target
        terminal.addTextInput("Vuoi Spostare il bersaglio?");
        terminal.addOptionInput("1:Si");
        terminal.addOptionInput("2:No");

        choice = terminal.inputInt(1, 2);

        if (choice == 2)//If the user has chosen no
        {
            move = false; //Close the method
            return;
        }

        //else it continues asking the square where to move target
        move = true;

        terminal.addTextInput("Scegli un quadrato dove spostare il tuo bersaglio:");

        int w = 1;

        for (String b:movesPossible)
        {
            terminal.addOptionInput(w+":"+ b);
            w++;
        }

        choice = terminal.inputInt(1, w - 1);

        x = Integer.parseInt(movesPossible.get(choice -1).substring(4,5));//Works if the coordinates are between 1 and 9
        y = Integer.parseInt(movesPossible.get(choice -1).substring(11));
    }

    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException
    {

        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        if (mode)
            return new ResponseShotgun(targetAlternativeMode,mode,move,x,y);

        return new ResponseShotgun(targetBasicMode,mode,move,x,y);
    }
}

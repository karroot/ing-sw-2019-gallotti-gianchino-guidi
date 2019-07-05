package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorRoom;

import java.util.List;


/**
 * @author gioguidi
 */
public class RequestFurnace extends WeaponWithModeAlternative {

    //Attribute for the request
    private List<ColorRoom> roomsBasicMode;//Targets for the basic mode
    private List<String> squaresAlternativeMode;//Targets for the basic mode

    //Attribute for the response
    private ColorRoom targetBasicMode;//Target chosen for the basic mode
    private String targetAlternativeMode;//Target chosen for the alternative mode


    public RequestFurnace(boolean[] availableMethod, List<ColorRoom> roomsBasicMode, List<String> squaresAlternativeMode)
    {
        this.availableMethod = availableMethod;
        this.nameAlternaivemode = "modalità fuoco confortevole";
        this.roomsBasicMode = roomsBasicMode;
        this.squaresAlternativeMode = squaresAlternativeMode;
        responseIsReady = false;
    }


    @Override
    public ResponseInput generateResponseMessage()
    {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi.");

        if (mode)
            return new ResponseFurnace(targetAlternativeMode);

        return new ResponseFurnace(targetBasicMode);
    }


    /**
     *
     */
    @Override
    protected void inputBasicMode()
    {

        int i = 1;

        List<ColorRoom> colorRoomList = roomsBasicMode;

        terminal.addTextInput("Scegli una stanza bersaglio:");

        for (ColorRoom colorRoomIterate : colorRoomList) {
            terminal.addOptionInput(i + " : " + colorRoomIterate);
            i++;
        }

        int choice = terminal.inputInt(1, i - 1);

        targetBasicMode = colorRoomList.get(choice - 1);
        responseIsReady = true;
    }


    /**
     *
     */
    @Override
    protected void inputAlternativeMode()
    {

        int i = 1;

        List<String> stringList = squaresAlternativeMode;

        terminal.addTextInput("Scegli uno square bersaglio:");

        for (String squareAsStringIterate : stringList) {
            terminal.addOptionInput(i + " : " + squareAsStringIterate);
            i++;
        }

        int choice = terminal.inputInt(1, i - 1);

        targetAlternativeMode = stringList.get(choice - 1);
        responseIsReady = true;
    }


}

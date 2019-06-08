package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.GameBoard;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Cysko7927
 */
public class RequestTeleporter extends RequestInput
{
    //Attribute for the request
    private List<String> allSquaresPossible;

    //Attribute for the response
    private int x =0;//Coordinates for the square chosen by user
    private int y = 0;

    /**
     * Create a request message to ask the inputs to use the teleporter
     * @param board board used by method to obtain all square of the arena
     * @param playerThatHasThePowerUp player that will use the teleporter
     */
    public RequestTeleporter(GameBoard board, Player playerThatHasThePowerUp)
    {
        List<Square> temp = new ArrayList<>(board.getArena().getAllSquares());
        temp.remove(playerThatHasThePowerUp.getSquare());

        this.allSquaresPossible = temp  //obtain all squares of the board
                .stream()
                .map(Square::toStringCoordinates)
                .collect(Collectors.toList());

        responseIsReady = false;
    }

    /**
     * Ask at the user in which square he wants to move
     */
    @Override
    public void printActionsAndReceiveInput()
    {

        System.out.println("Scegli un quadrato dove spostarti:");

        int i = 1;

        for (String t:allSquaresPossible)//Ask the square at the user
        {
            System.out.println(i+":"+t);
            i++;
        }

        int choice = inputInt(1, i - 1);

        //Save the coordinate
        x = Integer.parseInt(allSquaresPossible.get(choice -1).substring(4,5));//Works if the coordinates are between 1 and 9
        y = Integer.parseInt(allSquaresPossible.get(choice -1).substring(11));

        responseIsReady = true;
    }

    /**
     * Generate the response message for the teleporter with the square chosen by user
     * @return response message
     * @throws IllegalStateException if the method printAction wasn't called yet
     */
    @Override
    public ResponseInput generateResponseMessage() throws IllegalStateException
    {
        if (!responseIsReady)
            throw new IllegalStateException("Input non ancora presi");

        return new ResponseTeleporter(x,y);
    }
}

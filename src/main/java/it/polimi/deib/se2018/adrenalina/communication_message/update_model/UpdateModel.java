package it.polimi.deib.se2018.adrenalina.communication_message.update_model;

import it.polimi.deib.se2018.adrenalina.Model.GameBoard;
import it.polimi.deib.se2018.adrenalina.communication_message.MessageNet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is a container that contains a copy immutable of all the model
 * It will be send at the private view and being used by CLI or GUI
 * @author Cysko7927
 */
public class UpdateModel extends MessageNet
{
    private List<PlayerImmutable> dataOfAllPlayer;
    private List<SquareImmutable> dataOfAllSquare;
    private BoardImmutable dataOfBoard;

    /**
     * Create the object that contains all the model
     * @param board board of the model
     */
    public UpdateModel(GameBoard board)
    {
        this.dataOfAllPlayer = board.getAllPlayer().stream() //obtain the dates of all player
                .map(player -> new PlayerImmutable(player))
                .collect(Collectors.toList());

        this.dataOfAllSquare = board.getArena().getAllSquares()//Obtain the dates of all squares of the arena
                .stream()
                .map(square -> new SquareImmutable(square))
                .collect(Collectors.toList());

        this.dataOfBoard = new BoardImmutable(board);//Obtain the dates of the Game board
    }

    /**
     * Getter for the list of all the players
     * @return the list of all the players
     */
    public List<PlayerImmutable> getDataOfAllPlayer()
    {
        return new ArrayList<>(dataOfAllPlayer);
    }

    /**
     * Getter for the list of all the squares
     * @return the list of all the squares
     */
    public List<SquareImmutable> getDataOfAllSquare()
    {
        return new ArrayList<>(dataOfAllSquare);
    }

    /**
     * Getter for the copy immutable of all the game board
     * @return copy immutable of all the game board
     */
    public BoardImmutable getDataOfBoard()
    {
        return dataOfBoard;
    }
}

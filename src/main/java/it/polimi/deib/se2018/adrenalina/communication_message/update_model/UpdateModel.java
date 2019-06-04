package it.polimi.deib.se2018.adrenalina.communication_message.update_model;

import it.polimi.deib.se2018.adrenalina.Model.GameBoard;
import it.polimi.deib.se2018.adrenalina.communication_message.MessageNet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Cysko7927
 */
public abstract class UpdateModel extends MessageNet
{
    List<PlayerImmutable> dataOfAllPlayer;
    List<SquareImmutable> dataOfAllSquare;
    BoardImmutable dataOfBoard;

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

    public List<PlayerImmutable> getDataOfAllPlayer()
    {
        return new ArrayList<>(dataOfAllPlayer);
    }

    public List<SquareImmutable> getDataOfAllSquare()
    {
        return new ArrayList<>(dataOfAllSquare);
    }

    public BoardImmutable getDataOfBoard()
    {
        return dataOfBoard;
    }
}

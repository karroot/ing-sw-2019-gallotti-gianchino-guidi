package it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.GameBoard;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

public class ZX2Test
{
    GameBoard board = new GameBoard(new Stack<>(),new Stack<>(),4,8,new Stack<>());
    Player p1 = new Player(ColorId.YELLOW,"caso","ciao",true);;
    Player p2 = new Player(ColorId.GREY,"caso","ciao",false);;
    Player p3 = new Player(ColorId.GREEN,"caso","ciao",false);;
    Player p4 = new Player(ColorId.PURPLE,"caso","ciao",false);;
    Player p5 = new Player(ColorId.BLUE,"caso","ciao",false);

    @Before
    public void setUp() throws Exception
    {
        p2.setSquare(board.getArena().getSquare(2,1));
        board.getArena().getSquare(4,1);
        board.getArena().getSquare(1,2);
        p4.setSquare(board.getArena().getSquare(2,3));
        p1.setSquare(board.getArena().getSquare(3,3));
    }

    @Test
    public void checkAvaliableMode() {
    }

    @Test
    public void checkBasicMode() {
    }

    @Test
    public void basicMode() {
    }

    @Test
    public void checkInScannerMode() {
    }

    @Test
    public void inScannerMode() {
    }
}
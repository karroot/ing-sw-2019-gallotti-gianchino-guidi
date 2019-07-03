import it.polimi.deib.se2018.adrenalina.Controller.Controller;
import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.*;
import jdk.nashorn.internal.runtime.ECMAException;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * This class is a case test for the possibles states of the player.
 *
 * @author giovanni
 */

public class TestStatePlayer {
    GameBoard board = new GameBoard(new Stack<>(), new Stack<>(), 4, 8, new Stack<>());
    Player pYellow = new Player(ColorId.YELLOW, "caso", "ciao", true);
    Player pGrey = new Player(ColorId.GREY, "caso", "ciao", false);
    Player pGreen = new Player(ColorId.GREEN, "caso", "ciao", false);
    Player pPurple = new Player(ColorId.PURPLE, "caso", "ciao", false);
    Player pBlue = new Player(ColorId.BLUE, "caso", "ciao", false);

    Square start;

    Controller controller;


    @Before
    public void setUp() throws Exception {
        start = board.getArena().getSquare(1, 1);

        board.setAllPlayer(pYellow);
        board.setAllPlayer(pGrey);
        board.setAllPlayer(pGreen);
        board.setAllPlayer(pPurple);
        board.setAllPlayer(pBlue);

        pYellow.setSquare(start);
    }


    @Test
    public void statePlayer () throws Exception
    {
        pYellow.setNumberOfDamagePoint(0);

        Set<Square> squareSet = new HashSet<>();
        squareSet.addAll(pYellow.lookForRunAround(pYellow));

        assertEquals(9, squareSet.size());

        Set<Square> squareSet1 = new HashSet<>();
        squareSet1.addAll(pYellow.lookForGrabStuff(pYellow));

        assertEquals(0, squareSet1.size()); //it is 0 because i did not setup the board, so there are no ammotiles or weapons to draw.
                                                            // Checking the debugging with Breakpoint on model.normal line 52 i see that the size it's 3, that is correct

        pYellow.doDamage(pBlue.getColor());
        pYellow.doDamage(pBlue.getColor());
        pYellow.doDamage(pBlue.getColor());
        pYellow.doDamage(pBlue.getColor());
        pYellow.doDamage(pBlue.getColor());

        Set<Square> squareSet2 = new HashSet<>();
        squareSet2.addAll(pYellow.lookForRunAround(pYellow));

        assertEquals(9, squareSet2.size());

        Set<Square> squareSet3 = new HashSet<>();
        squareSet3.addAll(pYellow.lookForGrabStuff(pYellow));

        assertEquals(0, squareSet3.size()); //it is 0 because i did not setup the board, so there are no ammotiles or weapons to draw.
                                                        // Checking the debugging with Breakpoint on model.adrenalized1 line 47 i see that the size it's 3, that is correct

        pYellow.doDamage(pBlue.getColor());
        pYellow.doDamage(pBlue.getColor());
        pYellow.doDamage(pBlue.getColor());
        pYellow.doDamage(pBlue.getColor());
        pYellow.doDamage(pBlue.getColor());

        Set<Square> squareSet4 = new HashSet<>();
        squareSet4.addAll(pYellow.lookForRunAround(pYellow));

        assertEquals(9, squareSet4.size());

        Set<Square> squareSet5 = new HashSet<>();
        squareSet5.addAll(pYellow.lookForGrabStuff(pYellow));

        assertEquals(0, squareSet5.size()); //it is 0 because i did not setup the board, so there are no ammotiles or weapons to draw.
                                                        // Checking the debugging with Breakpoint on model.adrenalized2 line 49 i see that the size it's 3, that is correct

        pYellow.setFrenzy(true);

        Set<Square> squareSet6 = new HashSet<>();
        squareSet6.addAll(pYellow.lookForRunAround(pYellow));

        pBlue.setSquare(start);
        MethodsWeapons.moveTarget(pYellow,1,1);
        MethodsWeapons.moveTarget(pBlue,2,1);

        assertTrue(pYellow.lookForShootPeople(pYellow).contains(pBlue));
        assertEquals(1, pYellow.lookForShootPeople(pYellow).size());

        assertEquals(11, squareSet6.size());

        pYellow.setFrenzy(false);



        pYellow.doDamage(pBlue.getColor());

        assertNull(null, pYellow.lookForRunAround(pYellow));
        assertNull(null, pYellow.lookForGrabStuff(pYellow));
        assertNull(null, pYellow.lookForShootPeople(pYellow));
        assertNull(null, pYellow.checkReload(pYellow));

    }



}

import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Shotgun;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Sledgehammer;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseSledgehammer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.Assert.*;

public class SledgehammerTest {

    GameBoard board = new GameBoard(new Stack<>(),new Stack<>(),4,8,new Stack<>());
    Player pYellow = new Player(ColorId.YELLOW,"caso","ciao",true);;
    Player pGrey = new Player(ColorId.GREY,"caso","ciao",false);;
    Player pGreen = new Player(ColorId.GREEN,"caso","ciao",false);;
    Player pPurple = new Player(ColorId.PURPLE,"caso","ciao",false);;
    Player pBlue = new Player(ColorId.BLUE,"caso","ciao",false);
    Sledgehammer weap = new Sledgehammer(Color.YELLOW,0,true);
    Square start;

    //Create an arena with the player put in some squares
    @Before
    public void setUp() throws Exception
    {
        start = board.getArena().getSquare(1,1);
        pYellow.setSquare(start);
        pGrey.setSquare(start);
        pGreen.setSquare(start);
        pPurple.setSquare(start);
        pBlue.setSquare(start);


        MethodsWeapons.moveTarget(pYellow,4,1);
        MethodsWeapons.moveTarget(pGrey,2,1);
        MethodsWeapons.moveTarget(pGreen,2,1);
        MethodsWeapons.moveTarget(pPurple,4,3);
        MethodsWeapons.moveTarget(pBlue,2,1);

        board.setAllPlayer(pYellow);
        board.setAllPlayer(pGrey);
        board.setAllPlayer(pGreen);
        board.setAllPlayer(pPurple);
        board.setAllPlayer(pBlue);
    }

    /*
        Test if the calculate of the available mode of the weapon works if
        The weapons is given at different players
     */
    @Test
    public void checkAvailableMode()
    {
        pPurple.addWeapon(weap);
        weap.setPlayer(pPurple);

        assertTrue(!weap.checkAvailableMode()[0] && !weap.checkAvailableMode()[1]);


        pGrey.addWeapon(weap);
        weap.setPlayer(pGrey);

        assertTrue(weap.checkAvailableMode()[0] && weap.checkAvailableMode()[1]);

        pBlue.addWeapon(weap);
        weap.setPlayer(pBlue);

        pBlue.setAmmoRed(0);

        assertTrue(weap.checkAvailableMode()[0] && !weap.checkAvailableMode()[1]);
    }

    /*
        Test if the calculate of the targets for the two modes of the weapon
     */
    @Test
    public void checkTargetForModes()
    {
        pPurple.addWeapon(weap);
        weap.setPlayer(pPurple);

        try
        {
            weap.checkTargetForModes();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        pGrey.addWeapon(weap);
        weap.setPlayer(pGrey);

        List<ColorId> effOut = new ArrayList<>();
        effOut.add(pBlue.getColor());
        effOut.add(pGreen.getColor());

        List<ColorId> targets = weap.checkTargetForModes();

        assertTrue(targets.containsAll(effOut) && targets.size()==effOut.size());
    }

    /*
        Test if the basic mode of the weapon works
    */
    @Test
    public void basicMode()
    {
        pPurple.addWeapon(weap);
        weap.setPlayer(pPurple);

        try
        {
            weap.basicMode(pYellow);
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        pGrey.addWeapon(weap);
        weap.setPlayer(pGrey);

        weap.useWeapon(new ResponseSledgehammer(ColorId.BLUE));

        assertEquals(2,pBlue.getNumberOfDamagePoint());
    }

    /*
        Test if the calculate of the square for the alternative mode works
     */
    @Test
    public void checkMoveForAlternativeMode() throws Exception
    {
        pPurple.addWeapon(weap);
        weap.setPlayer(pPurple);

        try
        {
            weap.checkMoveForAlternativeMode();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        pGrey.addWeapon(weap);
        weap.setPlayer(pGrey);

        List<Square> effOut = new ArrayList<>();
        effOut.add(board.getArena().getSquare(1,1));
        effOut.add(board.getArena().getSquare(3,1));
        effOut.add(board.getArena().getSquare(4,1));
        effOut.add(board.getArena().getSquare(2,2));
        effOut.add(board.getArena().getSquare(2,3));

        List<Square> targets = weap.checkMoveForAlternativeMode();

        assertTrue(targets.containsAll(effOut) && targets.size()==effOut.size());
    }

    /*
        Test if the in pulverize mode of the weapon works
     */
    @Test
    public void inPulverizeMode() throws Exception
    {
        pPurple.addWeapon(weap);
        weap.setPlayer(pPurple);

        try
        {
            weap.inPulverizeMode(pYellow,1,1);
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        pGrey.addWeapon(weap);
        weap.setPlayer(pGrey);

        weap.useWeapon(new ResponseSledgehammer(pGreen.getColor(),2,3,true));


        assertEquals(3,pGreen.getNumberOfDamagePoint());
        assertEquals(3,pGreen.getNumberOfDamagePoint());
        assertEquals(0,pGrey.getAmmoRed());
        assertEquals(board.getArena().getSquare(2,3),pGreen.getSquare());
    }
}
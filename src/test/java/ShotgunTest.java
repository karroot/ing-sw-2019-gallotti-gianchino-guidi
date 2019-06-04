import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Shotgun;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.ZX2;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseShotgun;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ShotgunTest {

    GameBoard board = new GameBoard(new Stack<>(),new Stack<>(),4,8,new Stack<>());
    Player pYellow = new Player(ColorId.YELLOW,"caso","ciao",true);;
    Player pGrey = new Player(ColorId.GREY,"caso","ciao",false);;
    Player pGreen = new Player(ColorId.GREEN,"caso","ciao",false);;
    Player pPurple = new Player(ColorId.PURPLE,"caso","ciao",false);;
    Player pBlue = new Player(ColorId.BLUE,"caso","ciao",false);
    Shotgun weap = new Shotgun(Color.YELLOW,0,true);
    Square start;

    @Before
    public void setUp() throws Exception
    {
        start = board.getArena().getSquare(1,1);
        pYellow.setSquare(start);
        pGrey.setSquare(start);
        pGreen.setSquare(start);
        pPurple.setSquare(start);
        pBlue.setSquare(start);


        MethodsWeapons.moveTarget(pYellow,4,2);
        MethodsWeapons.moveTarget(pGrey,4,1);
        MethodsWeapons.moveTarget(pGreen,3,2);
        MethodsWeapons.moveTarget(pPurple,4,2);
        MethodsWeapons.moveTarget(pBlue,1,1);

        board.setAllPlayer(pYellow);
        board.setAllPlayer(pGrey);
        board.setAllPlayer(pGreen);
        board.setAllPlayer(pPurple);
        board.setAllPlayer(pBlue);
    }

    @Test
    public void checkAvaliableMode()
    {
        pBlue.addWeapon(weap);
        weap.setPlayer(pBlue);

        assertTrue(!weap.checkAvaliableMode()[0] && !weap.checkAvaliableMode()[1]);

        pGrey.addWeapon(weap);
        weap.setPlayer(pGrey);
        assertTrue(!weap.checkAvaliableMode()[0] && weap.checkAvaliableMode()[1]);

        pYellow.addWeapon(weap);
        weap.setPlayer(pYellow);
        assertTrue(weap.checkAvaliableMode()[0] && weap.checkAvaliableMode()[1]);

        MethodsWeapons.moveTarget(pGrey,1,1);
        MethodsWeapons.moveTarget(pGreen,1,1);

        assertTrue(weap.checkAvaliableMode()[0] && !weap.checkAvaliableMode()[1]);
    }

    @Test
    public void checkBasicMode()
    {
        pBlue.addWeapon(weap);
        weap.setPlayer(pBlue);

        try
        {
            weap.checkBasicMode();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        pYellow.addWeapon(weap);
        weap.setPlayer(pYellow);

        List<Player> effOut = new LinkedList<>();

        effOut.add(pPurple);


        List<Player> targets = weap.checkBasicMode();

        assertTrue(effOut.containsAll(targets)&& effOut.size() ==targets.size());

    }

    @Test
    public void checkMoveBasicMode() throws Exception
    {
        pBlue.addWeapon(weap);
        weap.setPlayer(pBlue);

        try
        {
            weap.checkMoveBasicMode();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        pYellow.addWeapon(weap);
        weap.setPlayer(pYellow);

        Set<Square> effOut = new HashSet<>();

        effOut.add(board.getArena().getSquare(3,2));
        effOut.add(board.getArena().getSquare(4,1));
        effOut.add(board.getArena().getSquare(4,3));

        Set<Square> targets = weap.checkMoveBasicMode();


        assertTrue(effOut.containsAll(targets)&& effOut.size() ==targets.size());
    }

    @Test
    public void basicMode() throws Exception
    {
        pBlue.addWeapon(weap);
        weap.setPlayer(pBlue);

        try
        {
            weap.basicMode(pGrey,true,2,3);
            fail();
        }
        catch (IllegalStateException| SquareNotInGameBoard e)
        {
            System.out.println(e);
        }

        pYellow.addWeapon(weap);
        weap.setPlayer(pYellow);

        weap.useWeapon(new ResponseShotgun(pPurple.getColor(),false,false,0,0));


        assertEquals(3,pPurple.getNumberOfDamagePoint());
        assertEquals(board.getArena().getSquare(4,2),pPurple.getSquare());

        weap.setLoaded(true);

        weap.useWeapon(new ResponseShotgun(pPurple.getColor(),false,true,4,3));


        assertEquals(6,pPurple.getNumberOfDamagePoint());
        assertEquals(board.getArena().getSquare(4,3),pPurple.getSquare());
    }

    @Test
    public void checkInLongBarrelMode() throws Exception
    {
        pBlue.addWeapon(weap);
        weap.setPlayer(pBlue);

        try
        {
            weap.checkInLongBarrelMode();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        pGrey.addWeapon(weap);
        weap.setPlayer(pGrey);

        List<Player> effOut = new LinkedList<>();

        effOut.add(pYellow);
        effOut.add(pPurple);

        List<Player> targets = weap.checkInLongBarrelMode();


        assertTrue(effOut.containsAll(targets)&& effOut.size() ==targets.size());
    }

    @Test
    public void inLongBarrelMode() throws Exception
    {
        pBlue.addWeapon(weap);
        weap.setPlayer(pBlue);

        try
        {
            weap.inLongBarrelMode(pYellow);
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        pGrey.addWeapon(weap);
        weap.setPlayer(pGrey);

        weap.useWeapon(new ResponseShotgun(pYellow.getColor(),true,false,0,0));


        assertEquals(2,pYellow.getNumberOfDamagePoint());
        assertEquals(board.getArena().getSquare(4,2),pPurple.getSquare());
    }
}
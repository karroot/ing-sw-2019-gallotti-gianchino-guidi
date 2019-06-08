import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.ZX2;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseZX2;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ZX2Test
{
    GameBoard board = new GameBoard(new Stack<>(),new Stack<>(),4,8,new Stack<>());
    Player p1 = new Player(ColorId.YELLOW,"caso","ciao",true);;
    Player p2 = new Player(ColorId.GREY,"caso","ciao",false);;
    Player p3 = new Player(ColorId.GREEN,"caso","ciao",false);;
    Player p4 = new Player(ColorId.PURPLE,"caso","ciao",false);;
    Player p5 = new Player(ColorId.BLUE,"caso","ciao",false);
    ZX2 zx2 = new ZX2(Color.BLUE,0,true);
    Square start;



    @Before
    public void setUp() throws Exception
    {
        start = board.getArena().getSquare(1,1);
        p1.setSquare(start);
        p2.setSquare(start);
        p3.setSquare(start);
        p4.setSquare(start);
        p5.setSquare(start);


        MethodsWeapons.moveTarget(p1,3,3);
        MethodsWeapons.moveTarget(p2,2,1);
        MethodsWeapons.moveTarget(p3,1,2);
        MethodsWeapons.moveTarget(p4,2,3);
        MethodsWeapons.moveTarget(p5,4,1);

        board.setAllPlayer(p1);
        board.setAllPlayer(p2);
        board.setAllPlayer(p3);
        board.setAllPlayer(p4);
        board.setAllPlayer(p5);
    }

    @Test
    public void checkAvailableModeTest()
    {
        p5.addWeapon(zx2);
        zx2.setPlayer(p5);

        assertTrue(!zx2.checkAvailableMode()[0] && !zx2.checkAvailableMode()[1]);

        p1.addWeapon(zx2);
        zx2.setPlayer(p1);
        assertTrue(zx2.checkAvailableMode()[0] && !zx2.checkAvailableMode()[1]);

        MethodsWeapons.moveTarget(p2,3,1);
        assertTrue(zx2.checkAvailableMode()[0] && zx2.checkAvailableMode()[1]);
    }

    @Test
    public void checkBasicMode()
    {
        List<Player> effOut = new ArrayList<>();
        p5.addWeapon(zx2);
        zx2.setPlayer(p5);

        try
        {
            List<Player> players = zx2.checkBasicMode();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        p1.addWeapon(zx2);
        zx2.setPlayer(p1);

        effOut.add(p4);
        effOut.add(p5);


        assertTrue(effOut.containsAll(zx2.checkBasicMode()) & effOut.size()==zx2.checkBasicMode().size());

    }

    @Test
    public void basicMode()
    {
        try
        {
            zx2.basicMode(p1);
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        p1.addWeapon(zx2);
        zx2.setPlayer(p1);

        zx2.useWeapon(new ResponseZX2(p4.getColor()));

        assertEquals(1,p4.getNumberOfDamagePoint());
        assertEquals(2,p4.checkMarker(p1.getColor()));
    }

    @Test
    public void checkInScannerMode()
    {
        List<Player> effOut = new LinkedList<>();

        p1.addWeapon(zx2);
        zx2.setPlayer(p1);

        try
        {
            List<Player> players = zx2.checkInScannerMode();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        MethodsWeapons.moveTarget(p2,3,1);
        effOut.add(p2);
        effOut.add(p4);
        effOut.add(p5);

        List<Player> players = zx2.checkInScannerMode();

        assertTrue(effOut.containsAll(players) & effOut.size()==players.size());
    }

    @Test
    public void inScannerMode()
    {
        MethodsWeapons.moveTarget(p2,3,1);

        List<Player> players = new LinkedList<>();

        try
        {
            zx2.inScannerMode(players);
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        p1.addWeapon(zx2);
        zx2.setPlayer(p1);

        players = zx2.checkInScannerMode();

        zx2.useWeapon(new ResponseZX2(players.stream().map(Player::getColor).collect(Collectors.toList())));

        for (Player t:players)
        {
            assertEquals(1,t.checkMarker(p1.getColor()));
        }
    }
}
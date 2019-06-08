import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Shockwave;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Shotgun;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseRailgun;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseShockwave;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ShockwaveTest {

    GameBoard board = new GameBoard(new Stack<>(),new Stack<>(),4,8,new Stack<>());
    Player pYellow = new Player(ColorId.YELLOW,"caso","ciao",true);;
    Player pGrey = new Player(ColorId.GREY,"caso","ciao",false);;
    Player pGreen = new Player(ColorId.GREEN,"caso","ciao",false);;
    Player pPurple = new Player(ColorId.PURPLE,"caso","ciao",false);;
    Player pBlue = new Player(ColorId.BLUE,"caso","ciao",false);
    Shockwave weap = new Shockwave(Color.YELLOW,0,true);
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
        MethodsWeapons.moveTarget(pGrey,3,2);
        MethodsWeapons.moveTarget(pGreen,4,1);
        MethodsWeapons.moveTarget(pPurple,4,3);
        MethodsWeapons.moveTarget(pBlue,4,1);

        board.setAllPlayer(pYellow);
        board.setAllPlayer(pGrey);
        board.setAllPlayer(pGreen);
        board.setAllPlayer(pPurple);
        board.setAllPlayer(pBlue);
    }

    @Test
    public void checkAvailableMode()
    {

        MethodsWeapons.moveTarget(pGrey,1,1);
        pGrey.addWeapon(weap);
        weap.setPlayer(pGrey);

        assertTrue(!weap.checkAvailableMode()[0] && !weap.checkAvailableMode()[1]);

        MethodsWeapons.moveTarget(pGrey,3,2);


        assertTrue(!weap.checkAvailableMode()[0] && weap.checkAvailableMode()[1]);

        pYellow.addWeapon(weap);
        weap.setPlayer(pYellow);

        assertTrue(weap.checkAvailableMode()[0] && weap.checkAvailableMode()[1]);

    }

    @Test
    public void checkBasicMode()
    {
        pGrey.addWeapon(weap);
        weap.setPlayer(pGrey);

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

        List<String> keyOut = new LinkedList<>();
        keyOut.add("x = "+4+": y = "+3);
        keyOut.add("x = "+4+": y = "+1);
        keyOut.add("x = "+3+": y = "+2);


        Map<String, List<ColorId>> targets = weap.checkBasicMode();

        assertTrue(targets.keySet().containsAll(keyOut) && targets.size() == keyOut.size());


        assertTrue(targets.get("x = "+4+": y = "+3).contains(pPurple.getColor()) && targets.get("x = "+4+": y = "+3).size() ==1);
        assertTrue(targets.get("x = "+4+": y = "+1).contains(pGreen.getColor()) && targets.get("x = "+4+": y = "+1).contains(pBlue.getColor()) && targets.get("x = "+4+": y = "+1).size() ==2);
        assertTrue(targets.get("x = "+3+": y = "+2).contains(pGrey.getColor()) && targets.get("x = "+3+": y = "+2).size() == 1);
    }

    @Test
    public void basicMode()
    {
        pGrey.addWeapon(weap);
        weap.setPlayer(pGrey);

        try
        {
            weap.basicMode(new LinkedList<>());
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        pYellow.addWeapon(weap);
        weap.setPlayer(pYellow);

        List<Player> target = new ArrayList<>();
        target.add(pPurple);
        target.add(pBlue);
        target.add(pGrey);

        weap.useWeapon(new ResponseShockwave(target.stream().map(Player::getColor).collect(Collectors.toList())));

        assertEquals(1,pPurple.getNumberOfDamagePoint());
        assertEquals(1,pBlue.getNumberOfDamagePoint());
        assertEquals(1,pGrey.getNumberOfDamagePoint());
    }

    @Test
    public void inTsunamirMode()
    {
        MethodsWeapons.moveTarget(pGrey,1,1);
        pGrey.addWeapon(weap);
        weap.setPlayer(pGrey);

        try
        {
            weap.inTsunamirMode();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        MethodsWeapons.moveTarget(pGrey,3,2);

        weap.useWeapon(new ResponseShockwave());

        assertEquals(1,pYellow.getNumberOfDamagePoint());

        weap.setLoaded(true);
        pYellow.addWeapon(weap);
        weap.setPlayer(pYellow);

        weap.useWeapon(new ResponseShockwave());


        assertEquals(1,pPurple.getNumberOfDamagePoint());
        assertEquals(1,pBlue.getNumberOfDamagePoint());
        assertEquals(1,pGrey.getNumberOfDamagePoint());
        assertEquals(1,pGreen.getNumberOfDamagePoint());

    }
}
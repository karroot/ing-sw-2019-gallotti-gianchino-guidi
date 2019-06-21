import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.PowerGlow;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Shotgun;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponsePowerGlove;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class PowerGlowTest {

    GameBoard board = new GameBoard(new Stack<>(),new Stack<>(),4,8,new Stack<>());
    Player pYellow = new Player(ColorId.YELLOW,"caso","ciao",true);;
    Player pGrey = new Player(ColorId.GREY,"caso","ciao",false);;
    Player pGreen = new Player(ColorId.GREEN,"caso","ciao",false);;
    Player pPurple = new Player(ColorId.PURPLE,"caso","ciao",false);;
    Player pBlue = new Player(ColorId.BLUE,"caso","ciao",false);
    PowerGlow weap = new PowerGlow(Color.YELLOW,0,true);
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


        MethodsWeapons.moveTarget(pYellow,4,1);
        MethodsWeapons.moveTarget(pGrey,1,1);
        MethodsWeapons.moveTarget(pGreen,3,2);
        MethodsWeapons.moveTarget(pPurple,3,3);
        MethodsWeapons.moveTarget(pBlue,3,1);

        board.setAllPlayer(pYellow);
        board.setAllPlayer(pGrey);
        board.setAllPlayer(pGreen);
        board.setAllPlayer(pPurple);
        board.setAllPlayer(pBlue);
    }

    @Test
    public void checkAvailableMode()
    {
        pGrey.addWeapon(weap);
        weap.setPlayer(pGrey);

        assertTrue(!weap.checkAvailableMode()[0] && !weap.checkAvailableMode()[1]);


        pYellow.addWeapon(weap);
        weap.setPlayer(pYellow);

        assertTrue(weap.checkAvailableMode()[0] && !weap.checkAvailableMode()[1]);

        pBlue.addWeapon(weap);
        weap.setPlayer(pBlue);

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

        List<ColorId> effOut = new LinkedList<>();

        effOut.add(pBlue.getColor());

        List<ColorId> targets = weap.checkBasicMode();

        assertTrue(effOut.containsAll(targets)&& effOut.size() == targets.size());

        effOut.clear();

        pBlue.addWeapon(weap);
        weap.setPlayer(pBlue);

        effOut.add(pGreen.getColor());
        effOut.add(pYellow.getColor());

        targets = weap.checkBasicMode();

        assertTrue(effOut.containsAll(targets)&& effOut.size() == targets.size());

    }


    @Test
    public void basicMode()
    {
        pGrey.addWeapon(weap);
        weap.setPlayer(pGrey);

        try
        {
            weap.basicMode(pBlue);
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        pYellow.addWeapon(weap);
        weap.setPlayer(pYellow);



        weap.useWeapon(new ResponsePowerGlove(ColorId.BLUE));

        assertEquals(1,pBlue.getNumberOfDamagePoint());
        assertEquals(2,pBlue.checkMarker(pYellow.getColor()));
        assertEquals(pBlue.getSquare(),pYellow.getSquare());



        pBlue.addWeapon(weap);
        weap.setPlayer(pBlue);
        weap.setLoaded(true);

        weap.useWeapon(new ResponsePowerGlove(ColorId.GREEN));

        assertEquals(1,pGreen.getNumberOfDamagePoint());
        assertEquals(2,pGreen.checkMarker(pBlue.getColor()));
        assertEquals(pGreen.getSquare(),pBlue.getSquare());

    }

    @Test
    public void checkInRocketFistMode()
    {
        pGrey.addWeapon(weap);
        weap.setPlayer(pGrey);


        assertTrue(weap.checkInRocketFistMode().keySet().size() == 0);

        pBlue.addWeapon(weap);
        weap.setPlayer(pBlue);


        Map<String,List<List<ColorId>>> targets = weap.checkInRocketFistMode();

        assertTrue(targets.keySet().contains("Nord"));

        assertTrue(targets.keySet().size() == 1);


        assertTrue(targets.get("Nord").get(0).contains(ColorId.GREEN) && targets.get("Nord").get(0).size() == 1);
        assertTrue(targets.get("Nord").get(1).contains(pPurple.getColor()) && targets.get("Nord").get(1).size() == 1);

        pPurple.addWeapon(weap);
        weap.setPlayer(pPurple);

        targets = weap.checkInRocketFistMode();

        assertTrue(targets.keySet().contains("Sud"));

        assertTrue(targets.keySet().size() == 1);


        assertTrue(targets.get("Sud").get(0).contains(pGreen.getColor()) && targets.get("Sud").get(0).size() == 1);
        assertTrue(targets.get("Sud").get(1).contains(pBlue.getColor()) && targets.get("Sud").get(1).size() == 1);

        MethodsWeapons.moveTarget(pGreen,2,1);
        MethodsWeapons.moveTarget(pPurple,1,1);

        pBlue.addWeapon(weap);
        weap.setPlayer(pBlue);

        targets = weap.checkInRocketFistMode();

        assertTrue(targets.keySet().contains("Ovest"));

        assertTrue(targets.keySet().size() == 1);


        assertTrue(targets.get("Ovest").get(0).contains(pGreen.getColor()) && targets.get("Ovest").get(0).size() == 1);
        assertTrue(targets.get("Ovest").get(1).contains(pPurple.getColor()) && targets.get("Ovest").get(1).size() == 2);
        assertTrue(targets.get("Ovest").get(1).contains(pGrey.getColor()));
    }

    @Test
    public void inRocketFistMode()
    {
        pGrey.addWeapon(weap);
        weap.setPlayer(pGrey);

        try
        {
            weap.inRocketFistMode(pBlue,pBlue);
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        pBlue.addWeapon(weap);
        weap.setPlayer(pBlue);

        List<ColorId> targets = new ArrayList<>();
        targets.add(pGreen.getColor());
        targets.add(pPurple.getColor());

        weap.useWeapon(new ResponsePowerGlove(targets));

        assertEquals(2,pGreen.getNumberOfDamagePoint());
        assertEquals(2,pPurple.getNumberOfDamagePoint());
        assertEquals(pPurple.getSquare(),pBlue.getSquare());
    }
}
import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Railgun;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Shotgun;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static org.junit.Assert.*;

public class RailgunTest {

    GameBoard board = new GameBoard(new Stack<>(),new Stack<>(),4,8,new Stack<>());
    Player pYellow = new Player(ColorId.YELLOW,"caso","ciao",true);
    Player pGrey = new Player(ColorId.GREY,"caso","ciao",false);
    Player pGreen = new Player(ColorId.GREEN,"caso","ciao",false);
    Player pPurple = new Player(ColorId.PURPLE,"caso","ciao",false);
    Player pBlue = new Player(ColorId.BLUE,"caso","ciao",false);
    Railgun weap = new Railgun(Color.YELLOW,0,true);
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


        MethodsWeapons.moveTarget(pYellow,3,2);
        MethodsWeapons.moveTarget(pGrey,1,1);
        MethodsWeapons.moveTarget(pGreen,2,2);
        MethodsWeapons.moveTarget(pPurple,4,2);
        MethodsWeapons.moveTarget(pBlue,1,2);
    }

    @Test
    public void checkAvaliableMode()
    {

        pBlue.addWeapon(weap);
        weap.setPlayer(pBlue);

        assertTrue(weap.checkAvaliableMode()[0] && weap.checkAvaliableMode()[1]);

        pGrey.addWeapon(weap);
        weap.setPlayer(pGrey);

        assertTrue(weap.checkAvaliableMode()[0] && !weap.checkAvaliableMode()[1]);
    }

    @Test
    public void checkBasicModeOrPiercingMode()
    {
        pBlue.addWeapon(weap);
        weap.setPlayer(pBlue);

        Map<String, List<ColorId>> targets = weap.checkBasicModeOrPiercingMode();

        assertTrue(targets.keySet().contains("Est") && targets.keySet().contains("Sud") && targets.keySet().size() == 2);

        List<ColorId> effOut = new LinkedList<>();

        effOut.add(pGreen.getColor());
        effOut.add(pYellow.getColor());
        effOut.add(pPurple.getColor());

        assertTrue(targets.get("Est").containsAll(effOut) && targets.get("Est").size() == 3);
        assertTrue(targets.get("Sud").contains(pGrey.getColor()) && targets.get("Sud").size() == 1);

        pGrey.addWeapon(weap);
        weap.setPlayer(pGrey);
        effOut.clear();

        targets = weap.checkBasicModeOrPiercingMode();

        assertTrue(targets.keySet().contains("Nord") && targets.keySet().size() == 1);

        effOut.add(pBlue.getColor());
        assertTrue(targets.get("Nord").containsAll(effOut) && targets.get("Nord").size() == 1);


    }

    @Test
    public void basicMode()
    {
        pBlue.addWeapon(weap);
        weap.setPlayer(pBlue);

        weap.basicMode(pYellow);

        assertEquals(3,pYellow.getNumberOfDamagePoint());
    }

    @Test
    public void inPiercingMode()
    {
        pGrey.addWeapon(weap);
        weap.setPlayer(pGrey);

        try
        {
            weap.inPiercingMode(pYellow,pYellow);
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        pBlue.addWeapon(weap);
        weap.setPlayer(pBlue);

        weap.inPiercingMode(pGreen,pPurple);

        assertEquals(2,pGreen.getNumberOfDamagePoint());
        assertEquals(2,pPurple.getNumberOfDamagePoint());
    }
}
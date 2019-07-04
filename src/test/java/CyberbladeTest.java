import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Cyberblade;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseCyberblade;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CyberbladeTest
{

    GameBoard board = new GameBoard(new Stack<>(),new Stack<>(),4,8,new Stack<>());
    Player pYellow = new Player(ColorId.YELLOW,"caso","ciao",true);;
    Player pGrey = new Player(ColorId.GREY,"caso","ciao",false);;
    Player pGreen = new Player(ColorId.GREEN,"caso","ciao",false);;
    Player pPurple = new Player(ColorId.PURPLE,"caso","ciao",false);;
    Player pBlue = new Player(ColorId.BLUE,"caso","ciao",false);
    Cyberblade weap = new Cyberblade(Color.YELLOW,0,true);
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


        MethodsWeapons.moveTarget(pYellow,3,1);
        MethodsWeapons.moveTarget(pGrey,4,2);
        MethodsWeapons.moveTarget(pGreen,3,3);
        MethodsWeapons.moveTarget(pPurple,4,1);
        MethodsWeapons.moveTarget(pBlue,4,1);

        board.setAllPlayer(pYellow);
        board.setAllPlayer(pGrey);
        board.setAllPlayer(pGreen);
        board.setAllPlayer(pPurple);
        board.setAllPlayer(pBlue);
    }

    //Test if the calculate of the available mode of the weapon works if
    //The weapons is given at different players
    @Test
    public void checkAvailableMode()
    {
        pGreen.addWeapon(weap);
        weap.setPlayer(pGreen);

        assertTrue(!weap.checkAvailableMode()[0] && !weap.checkAvailableMode()[1] && !weap.checkAvailableMode()[2]);


        pYellow.addWeapon(weap);
        weap.setPlayer(pYellow);

        assertTrue(weap.checkAvailableMode()[0] && weap.checkAvailableMode()[1] && weap.checkAvailableMode()[2]);

        pPurple.addWeapon(weap);
        weap.setPlayer(pPurple);
        assertTrue(weap.checkAvailableMode()[0] && weap.checkAvailableMode()[1] && weap.checkAvailableMode()[2]);

        MethodsWeapons.moveTarget(pGrey,2,3);

        pGrey.addWeapon(weap);
        weap.setPlayer(pGrey);

        assertTrue(weap.checkAvailableMode()[0] && weap.checkAvailableMode()[1] && !weap.checkAvailableMode()[2]);

        MethodsWeapons.moveTarget(pGrey,3,3);

        assertTrue(weap.checkAvailableMode()[0] && weap.checkAvailableMode()[1] && !weap.checkAvailableMode()[2]);
    }

    //Test if the target calculated for the basic mode are corrects
    @Test
    public void checkBasicModeAllTargetPossible()
    {
        pGreen.addWeapon(weap);
        weap.setPlayer(pGreen);

        try
        {
            weap.checkBasicModeAllTargetPossible();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        pYellow.addWeapon(weap);
        weap.setPlayer(pYellow);

        List<ColorId> p1 = new LinkedList<>();
        p1.add(pPurple.getColor());
        p1.add(pBlue.getColor());

        Map<String, List<ColorId>> effOut = new Hashtable<>();

        effOut.putIfAbsent("x = " + 4 +", y = "+ 1,new LinkedList<>(p1));

        p1.clear();

        Map<String, List<ColorId>> targets = weap.checkBasicModeAllTargetPossible();

        System.out.println(targets);

        assertTrue(effOut.keySet().containsAll(targets.keySet())&& effOut.keySet().size() ==targets.keySet().size());


        effOut = new Hashtable<>();

        pBlue.addWeapon(weap);
        weap.setPlayer(pBlue);

        p1.add(pPurple.getColor());

        effOut.putIfAbsent("x = " + 4 +", y = "+ 1,new LinkedList<>(p1));

        p1.clear();

        p1.add(pYellow.getColor());

        effOut.putIfAbsent("x = " + 3 +", y = "+ 1,new LinkedList<>(p1));

        p1.clear();

        p1.add(pGrey.getColor());

        effOut.putIfAbsent("x = " + 4 +", y = "+ 2,new LinkedList<>(p1));

        targets = weap.checkBasicModeAllTargetPossible();

        System.out.println(targets);

        assertTrue(effOut.keySet().containsAll(targets.keySet())&& effOut.keySet().size() ==targets.keySet().size());
    }

    //Test if the square calculated for the effect WithShadowStep are corrects
    @Test
    public void checkWithShadowStep()
    {
        pGreen.addWeapon(weap);
        weap.setPlayer(pGreen);

        try
        {
            weap.checkWithShadowStep();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        pYellow.addWeapon(weap);
        weap.setPlayer(pYellow);

        List<String> effOut = new LinkedList<>();

        effOut.add("x = " + 4 +", y = "+ 1);
        effOut.add("x = " + 2 +", y = "+ 1);
        effOut.add("x = " + 3 +", y = "+ 2);

        List<String> target = weap.checkWithShadowStep();

        assertTrue(effOut.containsAll(target)&& effOut.size() ==target.size());
    }

    //Test if the basic mode of the weapon works(Case 1)
    @Test
    public void basicMode1() throws Exception
    {
        pGreen.addWeapon(weap);
        weap.setPlayer(pGreen);

        try
        {
            weap.checkWithShadowStep();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        pYellow.addWeapon(weap);
        weap.setPlayer(pYellow);

        String[] ordf = new String[3];
        ordf[0] = "with shadowstep";
        ordf[1] = "basic";
        ordf[2] = "with slice and dice";

        weap.useWeapon(new ResponseCyberblade(pPurple.getColor(),pBlue.getColor(),4,1,ordf));


        assertEquals(2,pPurple.getNumberOfDamagePoint());
        assertEquals(2,pBlue.getNumberOfDamagePoint());
        assertEquals(0,pYellow.getAmmoYellow());
        assertEquals(board.getArena().getSquare(4,1),pYellow.getSquare());
    }

    //Test if the basic mode of the weapon works(Case 2)
    @Test
    public void basicMode2() throws Exception
    {
        pPurple.addWeapon(weap);
        weap.setPlayer(pPurple);

        String[] ordf = new String[3];
        ordf[0] = "basic";
        ordf[1] = "with shadowstep";
        ordf[2] = "with slice and dice";

        weap.setLoaded(true);

        weap.basicMode(pBlue,ordf,pGrey,4,2);

        assertEquals(2,pGrey.getNumberOfDamagePoint());
        assertEquals(0,pPurple.getAmmoYellow());
        assertEquals(board.getArena().getSquare(4,2),pPurple.getSquare());
        assertEquals(2,pBlue.getNumberOfDamagePoint());
    }
}
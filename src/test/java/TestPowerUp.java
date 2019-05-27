import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TestPowerUp
{
    private Player test;
    private Player enemy;


    private GameBoard g1= new GameBoard(null,null,1,10,null);
    private TagbackGranade p5 = new TagbackGranade(Color.BLUE,5);
    private TargettingScope p6 = new TargettingScope(Color.BLUE,6);

    private TargettingScope p8 = new TargettingScope(Color.YELLOW,8);
    private TargettingScope p9 = new TargettingScope(Color.RED,9);

    @Before
    public void setup()
    {
        test = new Player(ColorId.YELLOW,"Claudio","Stringa di prova",false);
        enemy = new Player(ColorId.GREY,"Carlo","Stringa di prova",false);

    }




    @After
    public void reset()
    {
        test.setNumberOfDamagePoint(0);
        enemy.setNumberOfDamagePoint(0);

    }

    @Test
    public void testAmmoScopePowerUp() throws SquareNotInGameBoard {
        List<Color> listOfColor;
        g1.setAllPlayer(enemy);
        g1.setAllPlayer(test);

        test.setSquare(g1.getArena().getSquare(1,1));
        enemy.setSquare(g1.getArena().getSquare(2,1));

        MethodsWeapons.moveTarget(test,1,1);
        MethodsWeapons.moveTarget(enemy,2,1);
        test.addPowerUp(p6);
        test.setAmmoBlue(1);
        assertEquals(p6,test.getPowerupCardList().get(0));
        p6.setPlayer(test);
        listOfColor=p6.check();
        assertTrue(listOfColor.contains(Color.BLUE));
       p6.usePowerUp(enemy.getColor(),Color.BLUE);

        assertEquals(0,test.getAmmoBlue()); //check if blue ammo are 0
        try{
            p6.usePowerUp(enemy.getColor(),Color.BLUE); //try to use targetting scope without ammo
            fail();

        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        test.addPowerUp(p9);
        test.setAmmoRed(1);
        assertEquals(p9,test.getPowerupCardList().get(1));
        p9.setPlayer(test);
        listOfColor=p6.check();
        assertTrue(listOfColor.contains(Color.RED));
        p6.usePowerUp(enemy.getColor(),Color.RED);

        assertEquals(0,test.getAmmoBlue()); //check if blue ammo are 0
        try{
            p9.usePowerUp(enemy.getColor(),Color.RED); //try to use targetting scope without ammo
            fail();

        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        test.addPowerUp(p8);
        test.setAmmoYellow(1);
        assertEquals(1,test.getAmmoYellow());
        p8.setPlayer(test);
        listOfColor=p6.check();
        assertTrue(listOfColor.contains(Color.YELLOW));
        p6.usePowerUp(enemy.getColor(),Color.YELLOW);
        assertEquals(0,test.getAmmoYellow()); //check if an ammo has been removed
        try{
            p8.usePowerUp(enemy.getColor(),Color.YELLOW); //use it again to check if give error
            fail();

        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }


    }

    @Test
    public void testAmmoGranadePowerUp() throws SquareNotInGameBoard {

        g1.setAllPlayer(enemy);
        g1.setAllPlayer(test);

        test.setSquare(g1.getArena().getSquare(1,1));
        enemy.setSquare(g1.getArena().getSquare(2,1));

        MethodsWeapons.moveTarget(test,1,1);
        MethodsWeapons.moveTarget(enemy,2,1);
        test.addPowerUp(p5);
        assertEquals(p5,test.getPowerupCardList().get(0));
        p5.setPlayer(test);
        test.setAmmoBlue(1);

        assertEquals(1,test.getAmmoBlue()); //check if an ammo has been removed
        p5.usePowerUp(enemy); //use it again to check if give error
        assertEquals(1,enemy.checkMarker(test.getColor()));

    }
}

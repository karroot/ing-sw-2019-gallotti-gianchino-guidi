import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestPowerUp
{
    private Player test;
    private Player enemy;



    private TagbackGranade p5 = new TagbackGranade(Color.BLUE,5);
    private TargettingScope p6 = new TargettingScope(Color.BLUE,6);
    private TagbackGranade p7 = new TagbackGranade(Color.RED,7);
    private TargettingScope p8 = new TargettingScope(Color.YELLOW,8);
    private TargettingScope p9 = new TargettingScope(Color.RED,9);

    @Before
    public void setup()
    {
        test = new Player(ColorId.YELLOW,"Claudio","Stringa di prova",false);
        enemy = new Player(ColorId.GREY,"Carlo","Stringa di prova",false);

    }
    @Test
    public void testAmmoScopePowerUp()
    {
        test.addPowerUp(p6);
        test.setAmmoBlue(1);
        assertEquals(p6,test.getPowerupCardList().get(0));
        p6.setPlayer(test);
       p6.usePowerUp(enemy);

        assertEquals(0,test.getAmmoBlue()); //check if blue ammo are 0
        try{
            p6.usePowerUp(enemy); //try to use targetting scope without ammo
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
        p9.usePowerUp(enemy);


        assertEquals(0,test.getAmmoBlue()); //check if blue ammo are 0
        try{
            p9.usePowerUp(enemy); //try to use targetting scope without ammo
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
        p8.usePowerUp(enemy); //try to use targettingscope with 1 ammo
        assertEquals(0,test.getAmmoYellow()); //check if an ammo has been removed
        try{
            p8.usePowerUp(enemy); //use it again to check if give error
            fail();

        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }


    }

    @Test
    public void testAmmoGranadePowerUp()
    {
        test.addPowerUp(p5);
        assertEquals(p5,test.getPowerupCardList().get(0));
        p5.setPlayer(test);
        test.setAmmoBlue(1);

        assertEquals(1,test.getAmmoBlue()); //check if an ammo has been removed
        p5.usePowerUp(enemy); //use it again to check if give error
        assertEquals(1,enemy.checkMarker(test.getColor()));

    }
}

import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.Newton;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.PowerUpCard;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.Teleporter;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import java.util.List;

public class TestWeaponPowerupInPlayer
{
    private Player test;

    private Square s = new AmmoPoint(1,1,null,ColorRoom.WHITE,null);

    private WeaponCard w1 = new Cyberblade(Color.RED,0,true);
    private WeaponCard w2 = new EletroSchyte(Color.RED,1,true);
    private WeaponCard w3 = new Flamethrower(Color.RED,2,true);
    private WeaponCard w4 = new Furnace(Color.RED,4,true);

    private PowerUpCard p1 = new Newton(Color.YELLOW,1);
    private PowerUpCard p2 = new Teleporter(Color.YELLOW,2);
    private PowerUpCard p3 = new Newton(Color.BLUE,3);
    private PowerUpCard p4 = new Teleporter(Color.RED,4);


    @Before
    public void setup()
    {
        test = new Player(ColorId.YELLOW,"Claudio","Stringa di prova",false);
        w1.setName("Cyberblade");
    }

    @Test
    public void testInsertWeapon()
    {
        //Check if player has'nt weapons
        assertTrue(test.getWeaponCardList().isEmpty());

        test.addWeapon(w1);//Add a weapon

        //Check if player has the weapons
        assertTrue(!test.getWeaponCardList().isEmpty() && test.getWeaponCardList().contains(w1));
    }

    @Test
    public void testChangeWeapon()
    {
        test.addWeapon(w1);//Add a weapon

        test.changeWeapon(w2,"Cyberblade");

        //Check if player has the new weapon and he hasn't more the old too
        assertTrue(!test.getWeaponCardList().isEmpty() && test.getWeaponCardList().contains(w2) && !test.getWeaponCardList().contains(w1));

    }

    @Test
    public void testMax3Weapon()
    {
        test.addWeapon(w1);//Add a weapon
        test.addWeapon(w2);//Add a weapon
        test.addWeapon(w3);//Add a weapon

        try
        {
            test.addWeapon(w4);
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        List<WeaponCard> temp = test.getWeaponCardList();

        assertTrue(temp.contains(w3) && temp.contains(w2) && temp.contains(w1) &&  !temp.contains(w4));


    }

    @Test
    public void testAddPowerUp()
    {
        //Check if player has'nt PowerUp
        assertTrue(test.getPowerupCardList().isEmpty());

        test.addPowerUp(p1);//Add a PowerUp

        //Check if player has the weapons
        assertTrue(!test.getPowerupCardList().isEmpty() && test.getPowerupCardList().contains(p1));
    }

    /*
    @Test
    public void testRemovePowerUp()
    {
        test.addPowerUp(p1);//Add a weapon

        try //check with an index wrong
        {
            test.usePowerUp(2);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e);
        }

        try //check with an index wrong
        {
            test.usePowerUp(4); //test
            fail();
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e);
        }

        test.usePowerUp(0); //finally use the correct index

        //Check if player has not more the power up
        assertTrue(test.getPowerupCardList().isEmpty());

    }
*/
    @Test
    public void testMax3PowerUp()
    {
        test.addPowerUp(p1);//Add 3 power up cards
        test.addPowerUp(p2);
        test.addPowerUp(p3);

        try //Check if it doesn't add an other card
        {
          test.addPowerUp(p4);
          fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        List<PowerUpCard> temp = test.getPowerupCardList();

        assertTrue(temp.contains(p1) && temp.contains(p2) && temp.contains(p3) &&  !temp.contains(p4));

    }


    @Test
    public void testGetterAndSetter()
    {
        //Testing set get square
        test.setSquare(s);

        assertEquals(s,test.getSquare());

        try
        {
            test.setSquare(null);
            fail();
        }
        catch (NullPointerException e)
        {
            assertEquals(s,test.getSquare());
        }

        //Test set get Score

        test.setScore(16);

        assertEquals(16,test.getScore());

        try
        {
            test.setScore(-3);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            assertEquals(16,test.getScore());

        }

        //Test set and get ammo

        test.setAmmoBlue(2);

        assertEquals(2,test.getAmmoBlue());


        test.setAmmoBlue(100);
        assertEquals(3,test.getAmmoBlue());


        test.setAmmoRed(2);

        assertEquals(2,test.getAmmoRed());

        test.setAmmoRed(100);


        assertEquals(3,test.getAmmoRed());

        test.setAmmoYellow(2);

        assertEquals(2,test.getAmmoYellow());



        test.setAmmoYellow(100);
        assertEquals(3,test.getAmmoYellow());



    }
}

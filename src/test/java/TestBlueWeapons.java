import it.polimi.deib.se2018.adrenalina.Model.AmmoPoint;
import it.polimi.deib.se2018.adrenalina.Model.ColorRoom;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.EletroSchyte;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.LockRifle;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.*;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class TestBlueWeapons {
    private Player test;
    private Player enemy;
    private Player enemy2;
    private  Square s;
    private  Square s1;
    private  Square s2;
    private  Square s3;
    private  Square s4;
    private GameBoard g1= new GameBoard(null,null,1,10,null);




    private EletroSchyte  w0 = new EletroSchyte(Color.BLUE,0,true);

    private LockRifle w1 = new LockRifle(Color.BLUE,1,true);
    private TractorBeam w2 = new TractorBeam(Color.BLUE,2,true);
    private THOR w3 = new THOR(Color.BLUE,3,true);
    private Whisper w4 = new Whisper(Color.BLUE,4,true);
    private PlasmaGun w5 = new PlasmaGun(Color.BLUE,5,true);
    private MachineGun w6 = new MachineGun(Color.BLUE,6,true);

    @Before
    public void setup()
    {

        try {
            s = g1.getArena().getSquare(1,1);
        } catch (SquareNotInGameBoard squareNotInGameBoard) {
            squareNotInGameBoard.printStackTrace();
        }





        try {
            s1 = g1.getArena().getSquare(2,1);
        } catch (SquareNotInGameBoard squareNotInGameBoard) {
            squareNotInGameBoard.printStackTrace();
        }





        try {
            s2 = g1.getArena().getSquare(1,2);
        } catch (SquareNotInGameBoard squareNotInGameBoard) {
            squareNotInGameBoard.printStackTrace();
        }





        try {
            s3 = g1.getArena().getSquare(1,3);
        } catch (SquareNotInGameBoard squareNotInGameBoard) {
            squareNotInGameBoard.printStackTrace();
        }





        try {
            s4 = g1.getArena().getSquare(2,2);
        } catch (SquareNotInGameBoard squareNotInGameBoard) {
            squareNotInGameBoard.printStackTrace();
        }


        test = new Player(ColorId.YELLOW,"Claudio","Stringa di prova",false);
        enemy = new Player(ColorId.GREY,"Carlo","Stringa di prova",false);
        enemy2 = new Player(ColorId.BLUE,"Marco","Stringa di prova",false);
        w0.setName("EletroSchyte");
        w1.setName("LockRifle");
        w2.setName("TractorBeam");
        w3.setName("THOR");
        w4.setName("Whisper");
        w5.setName("PlasmaGun");
        w6.setName("MachineGun");

    }

    @Test
    public void testInsertWeapon()
    {
        //Check if player has'nt weapons
        assertTrue(test.getWeaponCardList().isEmpty());

        test.addWeapon(w0);//Add a weapon

        //Check if player has the weapons
        assertTrue(!test.getWeaponCardList().isEmpty() && test.getWeaponCardList().contains(w0));
    }
    @Test
    public void testChangeWeapon()
    {
        test.addWeapon(w0);//Add a weapon

        test.changeWeapon(w2,"EletroSchyte");

        //Check if player has the new weapon and he hasn't more the old too
        assertTrue(!test.getWeaponCardList().isEmpty() && test.getWeaponCardList().contains(w2) && !test.getWeaponCardList().contains(w0));

    }

    @Test
    public void testDeathSchytle()
    {


        g1.setAllPlayer(enemy);
        g1.setAllPlayer(test);

        test.setSquare(s);
        enemy.setSquare(s1);

        s.addPlayer(test);
        s1.addPlayer(enemy);
        test.addWeapon(w0);//Add a weapon
        try
        {
            w0.checkBasicMode();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println("player is null");
        }

        w0.setPlayer(test);
        w0.setLoaded(true);
        boolean[] avaiableMethod = w0.checkAvaliableMode();
        assertFalse(avaiableMethod[0]);
        assertFalse(avaiableMethod[1]);
        try
        {
            w0.checkBasicMode();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println("modalità base non utilizzabile");
        }

        try
        {
            w0.checkReaper();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println("modalità avanzata non utilizzabile");
        }

        //tutto disponibile

        test.setSquare(s);
        enemy.setSquare(s);
        s.addPlayer(test);
        s.addPlayer(enemy);
        test.setAmmoBlue(2);
        boolean[] avaiableMethod1 = w0.checkAvaliableMode();
        assertEquals(avaiableMethod1[0],true);
        assertEquals(avaiableMethod1[1],true);

        List<Player> list1 = w0.checkBasicMode();
        List<Player> list2 = w0.checkReaper();
        assertTrue(list1.contains(enemy));
        assertTrue(list2.contains(enemy));
        w0.basicMode(list1);
        assertEquals(enemy.getNumberOfDamagePoint(),1);
        assertFalse(w0.isLoaded());
        w0.setLoaded(true);
        w0.reaper(list2);
        assertEquals(enemy.getNumberOfDamagePoint(),3);

        //solo basic disponibil

        w0.setLoaded(true);
        boolean[] avaiableMethod2 = w0.checkAvaliableMode();
        assertEquals(avaiableMethod2[0],true);
        assertEquals(avaiableMethod2[1],false);

        List<Player> list3 = w0.checkBasicMode();

        assertTrue(list3.contains(enemy));
        w0.basicMode(list3);
        assertEquals(enemy.getNumberOfDamagePoint(),4);
        assertFalse(w0.isLoaded());
        w0.setLoaded(true);
        try
        {
            w0.reaper(list3);
        }
        catch (IllegalStateException e)
        {
            System.out.println("modalità avanzata non utilizzabile");
        }




    }


    @Test
    public void testLockRifle()
    {
        g1.setAllPlayer(enemy2);
        g1.setAllPlayer(enemy);
        g1.setAllPlayer(test);
        test.setSquare(s);
        enemy.setSquare(s4);
        enemy2.setSquare(s4);

        s.addPlayer(test);
        s4.addPlayer(enemy);
        s4.addPlayer(enemy2);
        test.addWeapon(w1);//Add a weapon
        try
        {
            w1.checkBasicMode();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println("player is null");
        }

        w1.setPlayer(test);
        w1.setLoaded(true);
        boolean[] avaiableMethod = w1.checkAvaliableMode();
        assertFalse(avaiableMethod[0]);
        assertFalse(avaiableMethod[1]);
        try
        {
            w1.checkBasicMode();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println("modalità base non utilizzabile");
        }

        try
        {
            w1.checkSecondLock();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println("modalità avanzata non utilizzabile");
        }

        //tutto disponibile

        enemy.setSquare(s4);
        enemy2.setSquare(s4);

        s.addPlayer(test);
        s2.addPlayer(enemy);
        s3.addPlayer(enemy2);
        test.setAmmoRed(2);
        boolean[] avaiableMethod1 = w1.checkAvaliableMode();
        assertEquals(avaiableMethod1[0],true);
        assertEquals(avaiableMethod1[1],true);

        List<Player> list1 = w1.checkBasicMode();
        List<Player> list2 = w1.checkSecondLock();
        assertTrue(list1.contains(enemy));
        assertTrue(list2.contains(enemy));
        assertTrue(list1.contains(enemy2));
        assertTrue(list2.contains(enemy2));
        w1.basicMode(list1.get(0),list2.get(0),true);
        assertEquals(enemy.getNumberOfDamagePoint(),2);
        assertEquals(enemy.checkMarker(test.getColor()),1);
        assertEquals(enemy2.checkMarker(test.getColor()),1);
        assertFalse(w1.isLoaded());
        //solo basic disponibile

        w1.setLoaded(true);
        boolean[] avaiableMethod2 = w1.checkAvaliableMode();
        assertEquals(avaiableMethod2[0],true);
        assertEquals(avaiableMethod2[1],false);

        List<Player> list3 = w1.checkBasicMode();

        assertTrue(list3.contains(enemy));
        w1.basicMode(list3.get(2),null,true);
        assertEquals(enemy.getNumberOfDamagePoint(),4);
        assertFalse(w1.isLoaded());
        w1.setLoaded(true);
        try
        {
            w1.basicMode(list3.get(0),null,true);
        }
        catch (IllegalStateException e)
        {
            System.out.println("modalità avanzata non utilizzabile");
        }

        w1.basicMode(list3.get(0),null,false);
        assertFalse(w1.isLoaded());
        assertEquals(enemy2.getNumberOfDamagePoint(),2);
        assertEquals(enemy2.checkMarker(test.getColor()),2);
    }
}

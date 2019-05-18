import it.polimi.deib.se2018.adrenalina.Model.AmmoPoint;
import it.polimi.deib.se2018.adrenalina.Model.ColorRoom;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.EletroSchyte;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.LockRifle;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.*;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
<<<<<<< Updated upstream
=======
import org.junit.After;
>>>>>>> Stashed changes
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class TestBlueWeapons {
    private Player test;
    private Player enemy;
<<<<<<< Updated upstream
=======
    private Player enemy3;
>>>>>>> Stashed changes
    private Player enemy2;
    private  Square s;
    private  Square s1;
    private  Square s2;
    private  Square s3;
    private  Square s4;
<<<<<<< Updated upstream
    private GameBoard g1= new GameBoard(null,null,1,10,null);

=======
    private  Square s5;

    private GameBoard g1= new GameBoard(null,null,1,10,null);
    private Player getPl(List<Player> pl,Player pg) throws  IllegalArgumentException
    {
        for(Player i : pl)
        {
            if (i.equals(pg))
            {
                return pg;
            }

        }
        throw new IllegalArgumentException("player not in list");
    }
>>>>>>> Stashed changes



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

<<<<<<< Updated upstream
=======
        try {
            s5 = g1.getArena().getSquare(2,3);
        } catch (SquareNotInGameBoard squareNotInGameBoard) {
            squareNotInGameBoard.printStackTrace();
        }

>>>>>>> Stashed changes

        test = new Player(ColorId.YELLOW,"Claudio","Stringa di prova",false);
        enemy = new Player(ColorId.GREY,"Carlo","Stringa di prova",false);
        enemy2 = new Player(ColorId.BLUE,"Marco","Stringa di prova",false);
<<<<<<< Updated upstream
=======
        enemy3 = new Player(ColorId.GREEN,"Alice","Stringa di prova",false);
>>>>>>> Stashed changes
        w0.setName("EletroSchyte");
        w1.setName("LockRifle");
        w2.setName("TractorBeam");
        w3.setName("THOR");
        w4.setName("Whisper");
        w5.setName("PlasmaGun");
        w6.setName("MachineGun");

    }
<<<<<<< Updated upstream
=======
    @After
    public void reset()
    {
        test.setNumberOfDamagePoint(0);
        enemy.setNumberOfDamagePoint(0);
        enemy2.setNumberOfDamagePoint(0);
        enemy3.setNumberOfDamagePoint(0);
    }
>>>>>>> Stashed changes

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
<<<<<<< Updated upstream
        enemy.setSquare(s4);
        enemy2.setSquare(s4);

        s.addPlayer(test);
        s4.addPlayer(enemy);
        s4.addPlayer(enemy2);
=======
        s.addPlayer(test);
        s.getRoom().updatePlayerRoomList();
        enemy.setSquare(s4);
        s4.addPlayer(enemy);
        s4.getRoom().updatePlayerRoomList();
        s4.addPlayer(enemy2);
        enemy2.setSquare(s4);
        s4.getRoom().updatePlayerRoomList();
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
=======
        assertTrue(s.getPlayerList().contains(test));
        assertTrue(s4.getPlayerList().contains(enemy));
        assertTrue(s4.getPlayerList().contains(enemy2));
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
        enemy.setSquare(s4);
        enemy2.setSquare(s4);

        s.addPlayer(test);
        s2.addPlayer(enemy);
        s3.addPlayer(enemy2);
        test.setAmmoRed(2);
=======
        s.addPlayer(test);
        s.getRoom().updatePlayerRoomList();
        enemy.setSquare(s2);
        s2.addPlayer(enemy);
        s2.getRoom().updatePlayerRoomList();
        s3.addPlayer(enemy2);
        enemy2.setSquare(s3);
        s3.getRoom().updatePlayerRoomList();
        w1.setLoaded(true);
        test.setAmmoRed(2);
        test.setAmmoBlue(2);
>>>>>>> Stashed changes
        boolean[] avaiableMethod1 = w1.checkAvaliableMode();
        assertEquals(avaiableMethod1[0],true);
        assertEquals(avaiableMethod1[1],true);

        List<Player> list1 = w1.checkBasicMode();
        List<Player> list2 = w1.checkSecondLock();
<<<<<<< Updated upstream
        assertTrue(list1.contains(enemy));
        assertTrue(list2.contains(enemy));
        assertTrue(list1.contains(enemy2));
        assertTrue(list2.contains(enemy2));
        w1.basicMode(list1.get(0),list2.get(0),true);
=======


        w1.basicMode(getPl(list1,enemy),getPl(list2,enemy2),true);
>>>>>>> Stashed changes
        assertEquals(enemy.getNumberOfDamagePoint(),2);
        assertEquals(enemy.checkMarker(test.getColor()),1);
        assertEquals(enemy2.checkMarker(test.getColor()),1);
        assertFalse(w1.isLoaded());
        //solo basic disponibile
<<<<<<< Updated upstream
=======
        test.setAmmoBlue(0);
        test.setAmmoRed(0);
        test.setAmmoYellow(0);
>>>>>>> Stashed changes

        w1.setLoaded(true);
        boolean[] avaiableMethod2 = w1.checkAvaliableMode();
        assertEquals(avaiableMethod2[0],true);
        assertEquals(avaiableMethod2[1],false);

        List<Player> list3 = w1.checkBasicMode();

        assertTrue(list3.contains(enemy));
<<<<<<< Updated upstream
        w1.basicMode(list3.get(2),null,true);
        assertEquals(enemy.getNumberOfDamagePoint(),4);
        assertFalse(w1.isLoaded());
        w1.setLoaded(true);
        try
        {
            w1.basicMode(list3.get(0),null,true);
=======

        try
        {
            w1.basicMode(list3.get(0),null,true);
            fail();
>>>>>>> Stashed changes
        }
        catch (IllegalStateException e)
        {
            System.out.println("modalità avanzata non utilizzabile");
        }

<<<<<<< Updated upstream
        w1.basicMode(list3.get(0),null,false);
        assertFalse(w1.isLoaded());
        assertEquals(enemy2.getNumberOfDamagePoint(),2);
        assertEquals(enemy2.checkMarker(test.getColor()),2);
=======
        w1.basicMode(getPl(list3,enemy2),null,false);
        assertFalse(w1.isLoaded());
        assertEquals(enemy2.getNumberOfDamagePoint(),3);
        assertEquals(enemy2.checkMarker(test.getColor()),1);
    }

    @Test
    public void testWhisper()
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

        s.getRoom().updatePlayerRoomList();
        s4.getRoom().updatePlayerRoomList();
        test.addWeapon(w4);//Add a weapon

        try
        {
            w4.checkBasicMode();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }
        try
        {
            w4.basicMode(enemy2);
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }


        w4.setPlayer(test);
        try
        {
            w4.basicMode(enemy2);
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }
        w4.setLoaded(true);
        boolean[] avaiableMethod = w4.checkAvaliableMode();
        assertFalse(avaiableMethod[0]);
        try
        {
            w4.checkBasicMode();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }
        //tutto disponibile

        enemy.setSquare(s2);
        enemy2.setSquare(s3);

        s.addPlayer(test);
        s2.addPlayer(enemy);
        s3.addPlayer(enemy2);

        s.getRoom().updatePlayerRoomList();
        s2.getRoom().updatePlayerRoomList();
        s3.getRoom().updatePlayerRoomList();
        assertTrue(w4.isLoaded());
        boolean[] avaiableMethod1 = w4.checkAvaliableMode();
        assertEquals(avaiableMethod1[0],true);


        List<Player> list1 = w4.checkBasicMode();

        assertTrue(list1.contains(enemy2));

        w4.basicMode(getPl(list1,enemy2));
        assertEquals(enemy.getNumberOfDamagePoint(),0);
        assertEquals(enemy2.getNumberOfDamagePoint(),3);
        assertEquals(enemy2.checkMarker(test.getColor()),1);
        assertFalse(w4.isLoaded());

    }


    @Test
    public void testThor()
    {
        g1.setAllPlayer(enemy2);
        g1.setAllPlayer(enemy);
        g1.setAllPlayer(test);
        g1.setAllPlayer(enemy3);


        test.setSquare(s);
        enemy.setSquare(s4);
        enemy2.setSquare(s4);
        enemy3.setSquare(s4);

        MethodsWeapons.
        s.addPlayer(test);
        s4.addPlayer(enemy);
        s4.addPlayer(enemy2);
        s4.addPlayer(enemy3);

        s.getRoom().updatePlayerRoomList();
        s4.getRoom().updatePlayerRoomList();
        test.addWeapon(w3);//Add a weapon

        try
        {
            w3.checkBasicMode();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }
        try
        {
            w3.basicMode(enemy2,null,null,false,false);
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }


        w3.setPlayer(test);
        try
        {
            w3.basicMode(enemy2,null,null,false,false);
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }
        w3.setLoaded(true);
        boolean[] avaiableMethod = w3.checkAvaliableMode();
        assertFalse(avaiableMethod[0]);
        try
        {
            w3.checkBasicMode();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        //tutto disponibile
        test.setSquare(g1.getArena().getSquare(1,1));
        enemy.setSquare(s3);
        enemy2.setSquare(s5);
        enemy3.setSquare(s4);

        s.addPlayer(test);
        s3.addPlayer(enemy);
        s5.addPlayer(enemy2);
        s4.addPlayer(enemy3);

        s.getRoom().updatePlayerRoomList();
        s2.getRoom().updatePlayerRoomList();
        s3.getRoom().updatePlayerRoomList();
        test.setAmmoBlue(2);
        test.setAmmoYellow(2);
        test.setAmmoRed(2);
        assertTrue(w3.isLoaded());
        boolean[] avaiableMethod1 = w3.checkAvaliableMode();
        assertEquals(avaiableMethod1[0],true);


        List<Player> list1 = w3.checkBasicMode();
        List<Player> list2 = w3.checkChainReaction();
        List<Player> list3 = w3.checkHighVoltage();
        assertTrue(list1.contains(enemy));
        assertTrue(list2.contains(enemy2));
        assertTrue(list3.contains(enemy3));

        w3.basicMode(getPl(list1,enemy),getPl(list2,enemy2),getPl(list3,enemy3),true,true);
        assertEquals(enemy.getNumberOfDamagePoint(),2);
        assertEquals(enemy2.getNumberOfDamagePoint(),1);
        assertEquals(enemy2.getNumberOfDamagePoint(),2);
        assertFalse(w3.isLoaded());

    }

    @Test
    public void testMachineGun()
    {
        g1.setAllPlayer(enemy3);
        g1.setAllPlayer(enemy2);
        g1.setAllPlayer(enemy);
        g1.setAllPlayer(test);

        test.setSquare(s);
        enemy.setSquare(s4);
        enemy2.setSquare(s4);
        enemy3.setSquare(s4);
        s.addPlayer(test);
        s4.addPlayer(enemy);
        s4.addPlayer(enemy2);
        s4.addPlayer(enemy3);
        s.getRoom().updatePlayerRoomList();
        s4.getRoom().updatePlayerRoomList();
        test.addWeapon(w6);//Add a weapon
        try
        {
            w6.checkBasicMode();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println("player is null");
        }

        w6.setPlayer(test);
        w6.setLoaded(true);
        boolean[] avaiableMethod = w6.checkAvaliableMode();
        assertFalse(avaiableMethod[0]);
        assertFalse(avaiableMethod[1]);
        assertFalse(avaiableMethod[2]);
        try
        {
            w6.checkBasicMode();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        try
        {
            w6.checkFocusShotcMode();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }
        try
        {
            w6.checkTurretTripodeMode();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }


        //tutto disponibile

        enemy.setSquare(s2);
        enemy2.setSquare(s3);
        enemy3.setSquare(s2);
        s.addPlayer(test);
        s2.addPlayer(enemy);
        s3.addPlayer(enemy2);
        s2.addPlayer(enemy3);
        s.getRoom().updatePlayerRoomList();
        s2.getRoom().updatePlayerRoomList();
        s3.getRoom().updatePlayerRoomList();
        test.setAmmoYellow(2);
        test.setAmmoBlue(2);
        boolean[] avaiableMethod1 = w6.checkAvaliableMode();
        assertEquals(avaiableMethod1[0],true);
        assertEquals(avaiableMethod1[1],true);
        assertEquals(avaiableMethod1[2],true);

        List<Player> list1 = w6.checkBasicMode();
        List<Player> list2 = w6.checkFocusShotcMode();
        List<Player> list3 = w6.checkTurretTripodeMode();
        assertTrue(list1.contains(enemy));
        assertTrue(list2.contains(enemy));
        assertTrue(list3.contains(enemy));
        assertTrue(list1.contains(enemy2));
        assertTrue(list2.contains(enemy2));
        assertTrue(list3.contains(enemy2));
        assertTrue(list1.contains(enemy3));
        assertTrue(list2.contains(enemy3));
        assertTrue(list3.contains(enemy3));





        w6.basicMode(getPl(list1,enemy),getPl(list1,enemy2),getPl(list3,enemy3),getPl(list1,enemy2),true,true,true);


        assertEquals(enemy.getNumberOfDamagePoint(),2);
        assertEquals(enemy2.getNumberOfDamagePoint(),2);
        assertEquals(enemy3.getNumberOfDamagePoint(),1);
        assertFalse(w6.isLoaded());

        //solo basic disponibile
        test.setAmmoYellow(0);
        test.setAmmoBlue(0);
        w6.setLoaded(true);
        boolean[] avaiableMethod2 = w6.checkAvaliableMode();
        assertEquals(avaiableMethod2[0],true);
        assertEquals(avaiableMethod2[1],false);
        assertEquals(avaiableMethod2[2],false);
       list3 = w6.checkBasicMode();

        assertTrue(list3.contains(enemy));
        assertTrue(list3.contains(enemy2));
        assertTrue(list3.contains(enemy3));


        try
        {
            w6.basicMode(getPl(list1,enemy),getPl(list1,enemy2),null,null,true,true,true);
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }
        w6.setLoaded(true);

        w6.basicMode(getPl(list1,enemy2),getPl(list1,enemy),null,null,false,false,false);

        assertFalse(w6.isLoaded());
        assertEquals(enemy2.getNumberOfDamagePoint(),3);
        assertEquals(enemy.getNumberOfDamagePoint(),3);

        test.setAmmoYellow(2);
        test.setAmmoBlue(2);
        w6.setLoaded(true);
        w6.basicMode(getPl(list1,enemy2),getPl(list1,enemy),null,getPl(list1,enemy2),false,true,true);

        test.setAmmoYellow(2);
        test.setAmmoBlue(2);
        w6.setLoaded(true);
        try
        {
            w6.basicMode(getPl(list1,enemy),getPl(list1,enemy2),getPl(list1,enemy),null,true,true,true);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e);
        }

        test.setAmmoYellow(2);
        test.setAmmoBlue(2);
        w6.setLoaded(true);
        try
        {
            w6.basicMode(getPl(list1,enemy),getPl(list1,enemy),null,null,true,false,true);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e);
        }

        test.setAmmoYellow(2);
        test.setAmmoBlue(2);
        w6.setLoaded(true);
        try
        {
            w6.basicMode(getPl(list1,enemy2),getPl(list1,enemy),null,getPl(list1,enemy3),true,true,true);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e);
        }
        test.setAmmoYellow(2);
        test.setAmmoBlue(2);
        w6.setLoaded(true);
        try
        {
            w6.basicMode(getPl(list1,enemy),getPl(list1,enemy2),null,null,true,true,false);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e);
        }
>>>>>>> Stashed changes
    }
}

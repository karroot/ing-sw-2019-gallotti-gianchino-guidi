
 import it.polimi.deib.se2018.adrenalina.Model.Player;

import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.EletroSchyte;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.LockRifle;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.*;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import java.util.*;

 import org.junit.After;

import org.junit.Before;
import org.junit.Test;


import java.util.LinkedList;
import java.util.List;


import static org.junit.Assert.*;

public class TestBlueWeapons {
    //here we declare attribute for the tests
    private Player test;
    private Player enemy;


    private Player enemy3;

    private Player enemy2;

    private GameBoard g1= new GameBoard(null,null,1,10,null);





    private ColorId getPl(List<ColorId> pl,Player pg) throws  IllegalArgumentException // this method is used to check if the player is in the list and return the color of the inserted player
    {
        for(ColorId i : pl)
        {
            if (i.equals(pg.getColor()))
            {
                return pg.getColor();
            }

        }
        throw new IllegalArgumentException("player not in list");
    }



    //here we declare the weapons
    private EletroSchyte  w0 = new EletroSchyte(Color.BLUE,0,true);

    private LockRifle w1 = new LockRifle(Color.BLUE,1,true);
    private TractorBeam w2 = new TractorBeam(Color.BLUE,2,true);
    private THOR w3 = new THOR(Color.BLUE,3,true);
    private Whisper w4 = new Whisper(Color.BLUE,4,true);
    private PlasmaGun w5 = new PlasmaGun(Color.BLUE,5,true);
    private MachineGun w6 = new MachineGun(Color.BLUE,6,true);

    @Before
    public void setup()     //here we setup the weapons and the players
    {





        test = new Player(ColorId.YELLOW,"Claudio","Stringa di prova",false); // is always the attacker
        enemy = new Player(ColorId.GREY,"Carlo","Stringa di prova",false); // are always attacked
        enemy2 = new Player(ColorId.BLUE,"Marco","Stringa di prova",false);


        enemy3 = new Player(ColorId.GREEN,"Alice","Stringa di prova",false);

        w0.setName("EletroSchyte");
        w1.setName("LockRifle");
        w2.setName("TractorBeam");
        w3.setName("THOR");
        w4.setName("Whisper");
        w5.setName("PlasmaGun");
        w6.setName("MachineGun");

    }


    @After
    public void reset() // this method is used to reset the damage point
    {
        test.setNumberOfDamagePoint(0);
        enemy.setNumberOfDamagePoint(0);
        enemy2.setNumberOfDamagePoint(0);
        enemy3.setNumberOfDamagePoint(0);
    }


    @Test
    public void testInsertWeapon() // this method is used to insert the weapon
    {
        //Check if player has'nt weapons
        assertTrue(test.getWeaponCardList().isEmpty());

        test.addWeapon(w0);//Add a weapon

        //Check if player has the weapons
        assertTrue(!test.getWeaponCardList().isEmpty() && test.getWeaponCardList().contains(w0));
    }
    @Test
    public void testChangeWeapon() // this method is used to check the change of weapon
    {
        test.addWeapon(w0);//Add a weapon

        test.changeWeapon(w2,"EletroSchyte");

        //Check if player has the new weapon and he hasn't more the old too
        assertTrue(!test.getWeaponCardList().isEmpty() && test.getWeaponCardList().contains(w2) && !test.getWeaponCardList().contains(w0));

    }

    // this method is used to check if the weapon throw error if not correctly setup with player and
    //  to check if the weapon death Scythe in basic and reaper mode
    @Test
    public void testDeathSchytle() throws SquareNotInGameBoard {

    //here we set players in gameboard and spawn them 
        g1.setAllPlayer(enemy);
        g1.setAllPlayer(test);

        test.setSquare(g1.getArena().getSquare(1,1));
        enemy.setSquare(g1.getArena().getSquare(2,1));

       // here we move the player
 MethodsWeapons.moveTarget(test,1,1);
       // here we move the player
 MethodsWeapons.moveTarget(enemy,2,1);
        test.addWeapon(w0);//Add a weapon
//first we check if weapon fail if is not set to any player
        try
        {
            w0.checkBasicMode(); // this method return the target for basic mode
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println("player is null");
        }

        w0.setPlayer(test);
        w0.setLoaded(true); // here we load the weapon
        boolean[] availableMethod = w0.checkAvailableMode(); // it return the available modality to use the weapon
        assertFalse(availableMethod[0]);
        assertFalse(availableMethod[1]);
        try
        {
            w0.checkBasicMode(); // this method return the target for basic mode
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println("modalità base non utilizzabile");
        }

        try
        {
            w0.checkReaper(); // this method return the target for reaper mode
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println("modalità avanzata non utilizzabile");
        }

        //all mode available


       // here we move the player
 MethodsWeapons.moveTarget(enemy,1,1);
        test.setAmmoBlue(2);
        boolean[] availableMethod1 = w0.checkAvailableMode(); // it return the available modality to use the weapon
        assertEquals(availableMethod1[0],true);
        assertEquals(availableMethod1[1],true);

        List<ColorId> list1 = w0.checkBasicMode(); // this method return the target for basic mode
        List<ColorId> list2 = w0.checkReaper();// this method return the target for reaper mode
        assertTrue(list1.contains(enemy.getColor()));
        assertTrue(list2.contains(enemy.getColor()));
        //here we use the basic mode
        w0.basicMode(list1);
        assertEquals(enemy.getNumberOfDamagePoint(),1);
        assertFalse(w0.isLoaded());
        w0.setLoaded(true); // here we load the weapon
        //here we use the reaper mode
        w0.reaper(list2);
        assertEquals(enemy.getNumberOfDamagePoint(),3);

        //only basic available

        w0.setLoaded(true); // here we load the weapon
        boolean[] availableMethod2 = w0.checkAvailableMode(); // it return the available modality to use the weapon
        assertEquals(availableMethod2[0],true);
        assertEquals(availableMethod2[1],false);

        List<ColorId> list3 = w0.checkBasicMode(); // this method return the target for basic mode

        assertTrue(list3.contains(enemy.getColor()));
        w0.basicMode(list3);
        assertEquals(enemy.getNumberOfDamagePoint(),4);
        assertFalse(w0.isLoaded());
        w0.setLoaded(true); // here we load the weapon
        //here we see if the weapon fail to use reaper mode if the mode is not available due to lack of ammo
        try
        {
            w0.reaper(list3);
        }
        catch (IllegalStateException e)
        {
            System.out.println("modalità avanzata non utilizzabile");
        }




    }

    // this method is used to check if the weapon throw error if not correctly setup with player and
    //  to check if the weapon Lock Rifle in basic and SecondLock mode
    @Test
    public void testLockRifle() throws SquareNotInGameBoard {
        //here we set players in gameboard and spawn them 
        g1.setAllPlayer(enemy2);
        g1.setAllPlayer(enemy);
        g1.setAllPlayer(test);
        test.setSquare(g1.getArena().getSquare(1,1));

        enemy.setSquare(g1.getArena().getSquare(2,2));
        enemy2.setSquare(g1.getArena().getSquare(2,2));


       // here we move the player
 MethodsWeapons.moveTarget(test,1,1);
       // here we move the player
 MethodsWeapons.moveTarget(enemy,2,2);
       // here we move the player
 MethodsWeapons.moveTarget(enemy2,2,2);
        test.addWeapon(w1);//Add a weapon
        //first we check if weapon fail if is not set to any player
        try {
            w1.checkBasicMode(); // this method return the target for basic mode
            fail();
        } catch (IllegalStateException e) {
            System.out.println("player is null");
        }

        w1.setPlayer(test);
        w1.setLoaded(true); // here we load the weapon


//here we check that weapon mode are not available if weapon is not loaded
        boolean[] availableMethod = w1.checkAvailableMode(); // it return the available modality to use the weapon
        assertFalse(availableMethod[0]);
        assertFalse(availableMethod[1]);
        try {
            w1.checkBasicMode(); // this method return the target for basic mode
            fail();
        } catch (IllegalStateException e) {
            System.out.println("modalità base non utilizzabile");
        }

        try {
            w1.checkSecondLock(); // this method return the target for second lock mode
            fail();
        } catch (IllegalStateException e) {
            System.out.println("modalità avanzata non utilizzabile");
        }

        //all mode available



        w1.setLoaded(true); // here we load the weapon
        test.setAmmoRed(2);


       // here we move the player
 MethodsWeapons.moveTarget(enemy,1,2);
       // here we move the player
 MethodsWeapons.moveTarget(enemy2,1,3);

        w1.setLoaded(true); // here we load the weapon
        test.setAmmoRed(2);
        test.setAmmoBlue(2);

        boolean[] availableMethod1 = w1.checkAvailableMode(); // it return the available modality to use the weapon
        assertEquals(availableMethod1[0], true);
        assertEquals(availableMethod1[1], true);

        List<ColorId> list1 = w1.checkBasicMode(); // this method return the target for basic mode
        List<ColorId> list2 = w1.checkSecondLock(); // this method return the target for second lock mode

        assertTrue(list1.contains(enemy.getColor()));
        assertTrue(list2.contains(enemy.getColor()));
        assertTrue(list1.contains(enemy2.getColor()));
        assertTrue(list2.contains(enemy2.getColor()));


//here we use basic mode
        w1.basicMode(getPl(list1, enemy), getPl(list2, enemy2), true);

        assertEquals(enemy.getNumberOfDamagePoint(), 2);
        assertEquals(enemy.checkMarker(test.getColor()), 1);
        assertEquals(enemy2.checkMarker(test.getColor()), 1);
        assertFalse(w1.isLoaded());

        //only basic mode available


        test.setAmmoBlue(0);
        test.setAmmoRed(0);
        test.setAmmoYellow(0);


        w1.setLoaded(true); // here we load the weapon
        boolean[] availableMethod2 = w1.checkAvailableMode(); // it return the available modality to use the weapon
        assertEquals(availableMethod2[0], true);
        assertEquals(availableMethod2[1], false);

        List<ColorId> list3 = w1.checkBasicMode(); // this method return the target for basic mode

        assertTrue(list3.contains(enemy.getColor()));

        w1.basicMode(getPl(list3, enemy), null, false);
        assertEquals(enemy.getNumberOfDamagePoint(), 5);
        assertFalse(w1.isLoaded());
        w1.setLoaded(true); // here we load the weapon
        try {
            w1.basicMode(list3.get(0), null, true);
        } catch (IllegalStateException e) {
            System.out.println("modalità avanzata non utilizzabile");
        }

            try {
                w1.basicMode(list3.get(0), null, true);
                fail();

            } catch (IllegalStateException e) {
                System.out.println("modalità avanzata non utilizzabile");
            }


            w1.basicMode(getPl(list1, enemy2), null, false);
            assertFalse(w1.isLoaded());
            assertEquals(enemy2.getNumberOfDamagePoint(), 3);
            assertEquals(enemy2.checkMarker(test.getColor()), 1);
        w1.setLoaded(true); // here we load the weapon
            w1.basicMode(getPl(list3, enemy2), null, false);
            assertFalse(w1.isLoaded());
            assertEquals(enemy2.getNumberOfDamagePoint(), 6);
            assertEquals(enemy2.checkMarker(test.getColor()), 1);
        }


    // this method is used to check if the weapon throw error if not correctly setup with player and
    //  to check if the weapon Whisper in basic mode
    @Test
    public void testWhisper() throws SquareNotInGameBoard {
        g1.setAllPlayer(enemy2);
        g1.setAllPlayer(enemy);
        g1.setAllPlayer(test);

        test.setSquare(g1.getArena().getSquare(1,1));
        enemy.setSquare(g1.getArena().getSquare(2,2));
        enemy2.setSquare(g1.getArena().getSquare(2,2));
       // here we move the player
 MethodsWeapons.moveTarget(test,1,1);
       // here we move the player
 MethodsWeapons.moveTarget(enemy,2,2);
       // here we move the player
 MethodsWeapons.moveTarget(enemy2,2,2);
        test.addWeapon(w4);//Add a weapon
//first we check if weapon fail if is not loaded
        try
        {
            w4.checkBasicMode(); // this method return the target for basic mode
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }
        try
        {
            w4.basicMode(enemy2.getColor());
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }


        w4.setPlayer(test);
        try
        {
            w4.basicMode(enemy2.getColor());
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }
        w4.setLoaded(true); // here we load the weapon
        boolean[] availableMethod = w4.checkAvailableMode(); // it return the available modality to use the weapon
        assertFalse(availableMethod[0]);
        try
        {
            w4.checkBasicMode(); // this method return the target for basic mode
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }
        //all mode available



       // here we move the player
 MethodsWeapons.moveTarget(enemy,1,3);
       // here we move the player
 MethodsWeapons.moveTarget(enemy2,1,3);
        assertTrue(w4.isLoaded());
        boolean[] availableMethod1 = w4.checkAvailableMode(); // it return the available modality to use the weapon
        assertEquals(availableMethod1[0],true);


        List<ColorId> list1 = w4.checkBasicMode(); // this method return the target for basic mode

        assertTrue(list1.contains(enemy2.getColor()));

        w4.basicMode(getPl(list1,enemy2));
        assertEquals(enemy.getNumberOfDamagePoint(),0);
        assertEquals(enemy2.getNumberOfDamagePoint(),3);
        assertEquals(enemy2.checkMarker(test.getColor()),1);
        assertFalse(w4.isLoaded());

    }


    // this method is used to check if the weapon throw error if not correctly setup with player and
    //  to check if the weapon Thor in basic ,ChainReaction and HighVoltage
    @Test
    public void testThor() throws SquareNotInGameBoard {
        g1.setAllPlayer(enemy2);
        g1.setAllPlayer(enemy);
        g1.setAllPlayer(test);
        g1.setAllPlayer(enemy3);


        test.setSquare(g1.getArena().getSquare(1,1));
        enemy.setSquare(g1.getArena().getSquare(2,2));
        enemy2.setSquare(g1.getArena().getSquare(2,2));
        enemy3.setSquare(g1.getArena().getSquare(2,2));

       // here we move the player
 MethodsWeapons.moveTarget(test,1,1);
       // here we move the player
 MethodsWeapons.moveTarget(enemy,2,2);
       // here we move the player
 MethodsWeapons.moveTarget(enemy2,2,2);
       // here we move the player
 MethodsWeapons.moveTarget(enemy3,2,2);

        test.addWeapon(w3);//Add a weapon
//first we check if weapon fail if is not set to any player
        try
        {
            w3.checkBasicMode(); // this method return the target for basic mode
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }
        try
        {
            w3.basicMode(enemy2.getColor(),null,null,false,false);
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }


        w3.setPlayer(test);
        try
        {
            w3.basicMode(enemy2.getColor(),null,null,false,false);
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }
        w3.setLoaded(true); // here we load the weapon
        boolean[] availableMethod = w3.checkAvailableMode(); // it return the available modality to use the weapon
        assertFalse(availableMethod[0]);
        try
        {
            w3.checkBasicMode(); // this method return the target for basic mode
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        //all mode available



       // here we move the player
 MethodsWeapons.moveTarget(enemy,1,3);
       // here we move the player
 MethodsWeapons.moveTarget(enemy2,2,3);
       // here we move the player
 MethodsWeapons.moveTarget(enemy3,2,2);
        test.setAmmoBlue(2);
        test.setAmmoYellow(2);
        test.setAmmoRed(2);
        assertTrue(w3.isLoaded());
        boolean[] availableMethod1 = w3.checkAvailableMode(); // it return the available modality to use the weapon
        assertEquals(availableMethod1[0],true);


        List<ColorId> list1 = w3.checkBasicMode(); // this method return the target for basic mode
        List<ColorId> list2 = w3.checkChainReaction(); // this method return the target for chain reaction mode , it return only players that can be seen by a player that you can see
        List<ColorId> list3 = w3.checkHighVoltage();// this method return the target for high voltage mode , it return only players that can be seen by players that can be seen by a player that you can see
        assertTrue(list1.contains(enemy.getColor()));
        assertTrue(list2.contains(enemy2.getColor()));
        assertTrue(list3.contains(enemy3.getColor()));



        try
        {
            w3.basicMode(getPl(list1,enemy),enemy.getColor(),getPl(list3,enemy3),true,true);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e);
        }

        try
        {
            w3.basicMode(getPl(list1,enemy),getPl(list2,enemy2),enemy.getColor(),true,true);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e);
        }

        try
        {
            w3.basicMode(getPl(list1,enemy),getPl(list2,enemy2),enemy2.getColor(),true,true);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e);
        }

        w3.basicMode(getPl(list1,enemy),getPl(list2,enemy2),getPl(list3,enemy3),true,true);
        assertEquals(enemy.getNumberOfDamagePoint(),2);
        assertEquals(enemy2.getNumberOfDamagePoint(),1);
        assertEquals(enemy3.getNumberOfDamagePoint(),2);
        assertFalse(w3.isLoaded());
        w3.setLoaded(true); // here we load the weapon

        test.setAmmoBlue(1);
        test.setAmmoYellow(1);
        test.setAmmoRed(1);

        try
        {
            w3.basicMode(getPl(list1,enemy),getPl(list2,enemy2),getPl(list3,enemy3),true,true);
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }
        assertEquals(enemy.getNumberOfDamagePoint(),2);
        assertEquals(enemy2.getNumberOfDamagePoint(),1);
        assertEquals(enemy3.getNumberOfDamagePoint(),2);
        test.setAmmoBlue(0);
        try
        {
            w3.basicMode(getPl(list1,enemy),getPl(list2,enemy2),getPl(list3,enemy3),true,true);
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

       // here we move the player
 MethodsWeapons.moveTarget(test,2,2);
       // here we move the player
 MethodsWeapons.moveTarget(enemy,2,3);
       // here we move the player
 MethodsWeapons.moveTarget(enemy2,1,3);
       // here we move the player
 MethodsWeapons.moveTarget(enemy3,1,2);
        test.setAmmoYellow(2);
        test.setAmmoBlue(2);
        w3.setLoaded(true); // here we load the weapon
        list1 = w3.checkBasicMode(); // this method return the target for basic mode
        list2 = w3.checkChainReaction(); // this method return the target for chain reaction mode , it return only players that can be seen by a player that you can see
        list3 = w3.checkHighVoltage();// this method return the target for high voltage mode , it return only players that can be seen by players that can be seen by a player that you can see

        assertTrue(list1.contains(enemy.getColor()));
        assertTrue(list2.contains(enemy2.getColor()));
        assertTrue(list3.contains(enemy3.getColor()));
        assertFalse(list1.contains(enemy2.getColor()));



    }
    // this method is used to check if the weapon throw error if not correctly setup with player and
    //  to check if the weapon Machine Gun in basic ,Focus Shot Mode and Turret Tripode Mode
    @Test
    public void testMachineGun() throws SquareNotInGameBoard {
        g1.setAllPlayer(enemy2);
        g1.setAllPlayer(enemy);
        g1.setAllPlayer(test);
        g1.setAllPlayer(enemy3);


        test.setSquare(g1.getArena().getSquare(1,1));
        enemy.setSquare(g1.getArena().getSquare(2,2));
        enemy2.setSquare(g1.getArena().getSquare(2,2));
        enemy3.setSquare(g1.getArena().getSquare(2,2));

       // here we move the player
 MethodsWeapons.moveTarget(test,1,1);
       // here we move the player
 MethodsWeapons.moveTarget(enemy,2,2);
       // here we move the player
 MethodsWeapons.moveTarget(enemy2,2,2);
       // here we move the player
 MethodsWeapons.moveTarget(enemy3,2,2);
        test.addWeapon(w6);//Add a weapon
        //first we check if weapon fail if is not set to any player
        try
        {
            w6.checkBasicMode(); // this method return the target for basic mode
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println("player is null");
        }

        w6.setPlayer(test);
        w6.setLoaded(true); // here we load the weapon
        boolean[] availableMethod = w6.checkAvailableMode(); // it return the available modality to use the weapon
        assertFalse(availableMethod[0]);
        assertFalse(availableMethod[1]);
        assertFalse(availableMethod[2]);
        try
        {
            w6.checkBasicMode(); // this method return the target for basic mode
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }

        try
        {
            w6.checkFocusShotMode();  // this method return the target for focus shot mode
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }
        try
        {
            w6.checkTurretTripodeMode();  // this method return the target for focus turret tripode mode
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }


        //all mode available


       // here we move the player
 MethodsWeapons.moveTarget(enemy,1,2);
       // here we move the player
 MethodsWeapons.moveTarget(enemy2,1,3);
       // here we move the player
 MethodsWeapons.moveTarget(enemy3,1,2);
        test.setAmmoYellow(2);
        test.setAmmoBlue(2);
        boolean[] availableMethod1 = w6.checkAvailableMode(); // it return the available modality to use the weapon
        assertEquals(availableMethod1[0],true);
        assertEquals(availableMethod1[1],true);
        assertEquals(availableMethod1[2],true);

        List<ColorId> list1 = w6.checkBasicMode(); // this method return the target for basic mode
        List<ColorId> list2 = w6.checkFocusShotMode();  // this method return the target for focus shot mode
        List<ColorId> list3 = w6.checkTurretTripodeMode(); // this method return the target for focus turret tripode mode
        assertTrue(list1.contains(enemy.getColor()));
        assertTrue(list2.contains(enemy.getColor()));
        assertTrue(list3.contains(enemy.getColor()));
        assertTrue(list1.contains(enemy2.getColor()));
        assertTrue(list2.contains(enemy2.getColor()));
        assertTrue(list3.contains(enemy2.getColor()));
        assertTrue(list1.contains(enemy3.getColor()));
        assertTrue(list2.contains(enemy3.getColor()));
        assertTrue(list3.contains(enemy3.getColor()));





        w6.basicMode(getPl(list1,enemy),getPl(list1,enemy2),getPl(list3,enemy2),getPl(list3,enemy3),getPl(list1,enemy2),true,true,true);


        assertEquals(enemy.getNumberOfDamagePoint(),1);
        assertEquals(enemy2.getNumberOfDamagePoint(),3);
        assertEquals(enemy3.getNumberOfDamagePoint(),1);
        assertFalse(w6.isLoaded());

        //only basic mode available
        test.setAmmoYellow(0);
        test.setAmmoBlue(0);
        w6.setLoaded(true); // here we load the weapon
        boolean[] availableMethod2 = w6.checkAvailableMode(); // it return the available modality to use the weapon
        assertEquals(availableMethod2[0],true);
        assertEquals(availableMethod2[1],false);
        assertEquals(availableMethod2[2],false);
       list3 = w6.checkBasicMode(); // this method return the target for basic mode

        assertTrue(list3.contains(enemy.getColor()));
        assertTrue(list3.contains(enemy2.getColor()));
        assertTrue(list3.contains(enemy3.getColor()));


        try
        {
            w6.basicMode(getPl(list1,enemy),getPl(list1,enemy2),null,null,null,true,true,true);
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }
        w6.setLoaded(true); // here we load the weapon

        w6.basicMode(getPl(list1,enemy2),getPl(list1,enemy),null,null,null,false,false,false);

        assertFalse(w6.isLoaded());
        assertEquals(enemy2.getNumberOfDamagePoint(),4);
        assertEquals(enemy.getNumberOfDamagePoint(),2);

        test.setAmmoYellow(2);
        test.setAmmoBlue(2);
        w6.setLoaded(true); // here we load the weapon
        w6.basicMode(getPl(list1,enemy2),getPl(list1,enemy),null,getPl(list1,enemy3),getPl(list1,enemy2),false,true,true);

        test.setAmmoYellow(2);
        test.setAmmoBlue(2);
        w6.setLoaded(true); // here we load the weapon
        try
        {
            w6.basicMode(getPl(list1,enemy),getPl(list1,enemy2),getPl(list1,enemy2),getPl(list1,enemy),null,true,true,true);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e);
        }

        test.setAmmoYellow(2);
        test.setAmmoBlue(2);
        w6.setLoaded(true); // here we load the weapon
        try
        {
            w6.basicMode(getPl(list1,enemy),getPl(list1,enemy),null,null,null,true,false,true);
            fail();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        test.setAmmoYellow(2);
        test.setAmmoBlue(2);
        w6.setLoaded(true); // here we load the weapon
        try
        {
            w6.basicMode(getPl(list1,enemy2),getPl(list1,enemy),null,null,getPl(list1,enemy3),true,true,true);
            fail();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        test.setAmmoYellow(2);
        test.setAmmoBlue(2);
        w6.setLoaded(true); // here we load the weapon
        try
        {
            w6.basicMode(getPl(list1,enemy),getPl(list1,enemy2),null,null,null,true,true,false);
            fail();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }



    }



    // this method is used to check if the weapon throw error if not correctly setup with player and
    //  to check if the weapon Plasma Gun in all his possible combination of use that are
    // first move ,shot and finally add damage
    // first shot , move and finally add damage
    // first shoot , add damage and finally move
    @Test
    public  void testPlasmaGun() throws SquareNotInGameBoard, IllegalAccessException {
            String[] orderEffect = new String[3];

            g1.setAllPlayer(enemy2);
            g1.setAllPlayer(enemy);
            g1.setAllPlayer(test);
            g1.setAllPlayer(enemy3);


            test.setSquare(g1.getArena().getSquare(1,1));
            enemy.setSquare(g1.getArena().getSquare(2,2));
            enemy2.setSquare(g1.getArena().getSquare(2,2));
            enemy3.setSquare(g1.getArena().getSquare(2,2));

           // here we move the player
 MethodsWeapons.moveTarget(test,1,1);
           // here we move the player
 MethodsWeapons.moveTarget(enemy,2,2);
           // here we move the player
 MethodsWeapons.moveTarget(enemy2,2,2);
           // here we move the player
 MethodsWeapons.moveTarget(enemy3,2,2);

            test.addWeapon(w5);//Add a weapon


            // spara, muoviti ,aggiungi danno
            orderEffect[0] = "basic";
            orderEffect[1] = "with phase glide";
            orderEffect[2] = "with charged shot";
//first we check if weapon fail if is not set to any player
            try
            {
                w5.basicMode(enemy.getColor(),orderEffect,1,1);
                fail();
            }
            catch (IllegalStateException | IllegalAccessException | IllegalArgumentException e)
            {
                System.out.println(e);
            }


            w5.setPlayer(test);
            try
            {
                w5.basicMode(enemy.getColor(),orderEffect,1,1);
                fail();
            }
            catch (IllegalStateException | IllegalAccessException | IllegalArgumentException e)
            {
                System.out.println(e);
            }


            w5.setLoaded(true); // here we load the weapon




           // here we move the player
 MethodsWeapons.moveTarget(test,1,3);
           // here we move the player
 MethodsWeapons.moveTarget(enemy,4,1);


            List<String> reachableSquare = new LinkedList<>();
            Map<String,List<ColorId>> reachableBasicPlayer = new HashMap<>();
            Map<String,List<ColorId>> reachableBeforeMovePlayer = new HashMap<>();



            reachableSquare= w5.checkPhaseGlide(); // this method return the reachable squares



    //prima ti muovi , raggiungi il player e gli spari




           // here we move the player
 MethodsWeapons.moveTarget(enemy,1,3);
           // here we move the player
 MethodsWeapons.moveTarget(test,1,1);


            test.setAmmoBlue(3);
            reachableBasicPlayer = w5.checkAllTarget(); // this method return a map of squares and player that you can see by moving into that square

            reachableSquare= w5.checkPhaseGlide(); // this method return the reachable squares
            reachableBeforeMovePlayer = w5.checkAllTarget();  // this method return a map of squares and player that you can see by moving into that square

            assertFalse(reachableBasicPlayer.get(test.getSquare().getGameBoard().getArena().getSquare(1,2).toStringCoordinates()).contains(enemy3.getColor()));
            w5.basicMode(getPl(reachableBasicPlayer.get(test.getSquare().getGameBoard().getArena().getSquare(1,2).toStringCoordinates()),enemy),orderEffect,1,2);

            assertFalse(w5.isLoaded());
            assertEquals(enemy.getNumberOfDamagePoint(),3);





           // here we move the player
 MethodsWeapons.moveTarget(test,1,1);
           // here we move the player
 MethodsWeapons.moveTarget(enemy,2,3);



            // muoviti, spara ,aggiungi danno
            orderEffect[1] = "basic";
            orderEffect[0] = "with phase glide";
            orderEffect[2] = "with charged shot";
            test.setAmmoBlue(3);
            w5.setLoaded(true); // here we load the weapon

            reachableSquare= w5.checkPhaseGlide(); // this method return the reachable squares
            reachableBeforeMovePlayer = w5.checkAllTarget();  // this method return a map of squares and player that you can see by moving into that square
            assertTrue(reachableSquare.contains(g1.getArena().getSquare(1,3).toStringCoordinates()));
            List<ColorId> templ = reachableBeforeMovePlayer.get(g1.getArena().getSquare(1,3).toStringCoordinates());
         assertTrue(templ.contains(enemy.getColor()));
assertTrue(enemy.getNumberOfDamagePoint()==3);
//getPl(reachableBasicPlayer.get(test.getSquare().getGameBoard().getArena().getSquare(1,3).toStringCoordinates()),enemy)
            w5.basicMode(enemy.getColor(),orderEffect,1,3);

            assertEquals(enemy.getNumberOfDamagePoint(),6);
            assertTrue(test.getSquare().equals(test.getSquare().getGameBoard().getArena().getSquare(1,3)));

         // prova a muoverti e a sparare a un nemico che non  puoi raggiungere da quella posizione ( ma che comunque potevi raggiungere)




           // here we move the player
 MethodsWeapons.moveTarget(test,1,1);
           // here we move the player
 MethodsWeapons.moveTarget(enemy,2,2);
           // here we move the player
 MethodsWeapons.moveTarget(enemy2,1,3);

            // muoviti, spara ,aggiungi danno
            orderEffect[0] = "with phase glide";
            orderEffect[1] = "basic";
            orderEffect[2] = "with charged shot";

            test.setAmmoBlue(3);
            w5.setLoaded(true); // here we load the weapon
            assertTrue(enemy.getSquare().equals(g1.getArena().getSquare(2,2)));

            reachableBasicPlayer = w5.checkAllTarget();  // this method return a map of squares and player that you can see by moving into that square
            reachableBeforeMovePlayer = w5.checkAllTarget();  // this method return a map of squares and player that you can see by moving into that square


            Square squareTest= new SpawnPoint(2,2,g1,null,null);
            String testString = squareTest.toStringCoordinates();
          assertTrue(reachableBeforeMovePlayer.containsKey(testString));
          assertTrue(reachableBeforeMovePlayer.get(testString).contains(enemy.getColor()));


            w5.basicMode(getPl(reachableBasicPlayer.get(test.getSquare().getGameBoard().getArena().getSquare(1,2).toStringCoordinates()),enemy2),orderEffect,1,2);
            assertEquals(enemy.getNumberOfDamagePoint(),6);
            assertEquals(enemy2.getNumberOfDamagePoint(),3);
    assertTrue(test.getSquare().equals(g1.getArena().getSquare(1,2)));

            test.setAmmoBlue(3);

            try
            {
                reachableBeforeMovePlayer = w5.checkAllTarget();  // this method return a map of squares and player that you can see by moving into that square
                fail();
            }
            catch(IllegalStateException e)
            {
                System.out.println(e);
            }
            try
            {
                reachableSquare= w5.checkPhaseGlide(); // this method return the reachable squares
                fail();
            }
            catch(IllegalStateException e)
            {
                System.out.println(e);
            }
            w5.setLoaded(true); // here we load the weapon
            reachableBasicPlayer = w5.checkAllTarget();  // this method return a map of squares and player that you can see by moving into that square

            reachableBeforeMovePlayer = w5.checkAllTarget();  // this method return a map of squares and player that you can see by moving into that square
            test.setAmmoBlue(0);
            try
            {
                w5.basicMode(enemy.getColor(),orderEffect,2,1);

            }
            catch(IllegalStateException e)
            {
                System.out.println(e);
            }
        }

    // this method is used to check if the weapon throw error if not correctly setup with player and
    //  to check if the weapon Tractator Beam in basic and Punisher mode
    @Test
    public void testTractatorBeam() throws SquareNotInGameBoard {
        g1.setAllPlayer(enemy2);
        g1.setAllPlayer(enemy);
        g1.setAllPlayer(test);
        g1.setAllPlayer(enemy3);


        test.setSquare(g1.getArena().getSquare(1,1));
        enemy.setSquare(g1.getArena().getSquare(2,2));
        enemy2.setSquare(g1.getArena().getSquare(2,2));
        enemy3.setSquare(g1.getArena().getSquare(2,2));

       // here we move the player
 MethodsWeapons.moveTarget(test,1,1);
       // here we move the player
 MethodsWeapons.moveTarget(enemy,2,2);
       // here we move the player
 MethodsWeapons.moveTarget(enemy2,2,2);
       // here we move the player
 MethodsWeapons.moveTarget(enemy3,2,2);
        test.addWeapon(w2);//Add a weapon
        //first we check if weapon fail if is not set to any player
        try
        {
            w2.checkMoveBasicMode(); // this method return the target for basic mode as a map of player's color and string of where you can move them
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println("player is null");
        }

        w2.setPlayer(test);
        w2.setLoaded(true); // here we load the weapon
        boolean[] availableMethod = w2.checkAvailableMode(); // it return the available modality to use the weapon
        assertTrue(availableMethod[0]);
        assertTrue(availableMethod[1]);


        //all mode available



       // here we move the player
 MethodsWeapons.moveTarget(enemy,1,3);
       // here we move the player
 MethodsWeapons.moveTarget(enemy2,2,3);
       // here we move the player
 MethodsWeapons.moveTarget(enemy3,2,2);
        test.setAmmoBlue(3);
        test.setAmmoYellow(2);
        test.setAmmoRed(2);
        assertTrue(w2.isLoaded());
        boolean[] availableMethod1 = w2.checkAvailableMode(); // it return the available modality to use the weapon
        assertEquals(availableMethod1[0],true);


        Map<ColorId,List<String>> list1 = w2.checkMoveBasicMode();  // this method return the target for basic mode as a map of player's color and string of where you can move them
        List<ColorId> list2 = w2.checkPunisherMode();  // this method return the target for punisher mode

        assertFalse(list1.containsKey(test.getColor()));
        assertTrue(list1.containsKey(enemy.getColor()));
        assertTrue(list1.containsKey(enemy2.getColor()));
        assertTrue(list1.containsKey(enemy3.getColor()));

        assertTrue(list2.contains(enemy3.getColor()));
        assertTrue(list2.contains(enemy.getColor()));
        assertFalse(list2.contains(enemy2.getColor()));

        assertEquals(enemy2.getNumberOfDamagePoint(),0);
        w2.basicMode(enemy2.getColor(),1,3);
        assertEquals(enemy2.getNumberOfDamagePoint(),1);

        w2.setLoaded(true); // here we load the weapon

        w2.punisherMode(getPl(list2,enemy3));
        assertEquals(enemy3.getNumberOfDamagePoint(),3);

        w2.setLoaded(true); // here we load the weapon
        test.setAmmoBlue(0);
        test.setAmmoYellow(0);
        test.setAmmoRed(0);
      try{  w2.punisherMode(getPl(list2,enemy3));}
      catch (IllegalStateException e)
      {
          System.out.println(e);
      }

        //all mode available test 2

       // here we move the player
 MethodsWeapons.moveTarget(enemy,1,2);
       // here we move the player
 MethodsWeapons.moveTarget(enemy2,1,1);
       // here we move the player
 MethodsWeapons.moveTarget(test,3,3);
        assertEquals(enemy.getNumberOfDamagePoint(),0);
        assertEquals(enemy2.getNumberOfDamagePoint(),1);
        w2.setLoaded(true); // here we load the weapon
        test.setAmmoBlue(3);
        test.setAmmoRed(3);
        test.setAmmoYellow(3);
        list1 = w2.checkMoveBasicMode();  // this method return the target for basic mode as a map of player's color and string of where you can move them
       try{
           list2 = w2.checkPunisherMode(); // this method return the target for punisher mode
           fail();
       }
       catch (IllegalStateException e)
       {
           System.out.println(e);
       }
        boolean[] availableMethods = w2.checkAvailableMode(); // it return the available modality to use the weapon
        assertTrue(availableMethods[0]);
        assertFalse(availableMethods[1]);



        assertTrue(list1.containsKey(enemy.getColor()));
        assertTrue(list1.containsKey(enemy2.getColor()));
        w2.basicMode(enemy.getColor(),1,3);
        assertEquals(enemy.getNumberOfDamagePoint(),1);
        assertTrue(enemy.getSquare().equals(enemy.getSquare().getGameBoard().getArena().getSquare(1,3)));

        w2.setLoaded(true); // here we load the weapon
        w2.basicMode(enemy2.getColor(),2,2);

        assertEquals(enemy2.getNumberOfDamagePoint(),2);
        assertTrue(enemy2.getSquare().equals(enemy2.getSquare().getGameBoard().getArena().getSquare(2,2)));


        //all mode available test 3

       // here we move the player
 MethodsWeapons.moveTarget(enemy,1,2);
       // here we move the player
 MethodsWeapons.moveTarget(enemy2,1,1);
       // here we move the player
 MethodsWeapons.moveTarget(test,2,3);
        assertEquals(enemy.getNumberOfDamagePoint(),1);
        assertEquals(enemy2.getNumberOfDamagePoint(),2);
        w2.setLoaded(true); // here we load the weapon
        list1 = w2.checkMoveBasicMode(); // this method return the target for basic mode as a map of player's color and string of where you can move them
        list2 = w2.checkPunisherMode(); // this method return the target for punisher mode
        boolean[] availableMethodss = w2.checkAvailableMode(); // it return the available modality to use the weapon
        assertTrue(availableMethodss[0]);
        assertTrue(availableMethodss[1]);



        assertTrue(list1.containsKey(enemy.getColor()));
        assertTrue(list1.containsKey(enemy2.getColor()));
        assertTrue(list2.contains(enemy.getColor()));
        w2.basicMode(enemy.getColor(),1,3);
        assertEquals(enemy.getNumberOfDamagePoint(),2);
        assertTrue(enemy.getSquare().equals(enemy.getSquare().getGameBoard().getArena().getSquare(1,3)));

        w2.setLoaded(true); // here we load the weapon
        w2.basicMode(enemy2.getColor(),2,2);

        assertEquals(enemy2.getNumberOfDamagePoint(),3);
        assertTrue(enemy2.getSquare().equals(enemy2.getSquare().getGameBoard().getArena().getSquare(2,2)));


    }
}

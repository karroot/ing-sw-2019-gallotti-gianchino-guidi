
 import it.polimi.deib.se2018.adrenalina.Model.Player;

import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.EletroSchyte;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.LockRifle;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.*;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import java.util.*;
import java.util.stream.Collectors;
import org.junit.After;

import org.junit.Before;
import org.junit.Test;


import java.util.LinkedList;
import java.util.List;


import static org.junit.Assert.*;

public class TestBlueWeapons {
    private Player test;
    private Player enemy;


    private Player enemy3;

    private Player enemy2;

    private GameBoard g1= new GameBoard(null,null,1,10,null);





    private ColorId getPl(List<ColorId> pl,Player pg) throws  IllegalArgumentException
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





        test = new Player(ColorId.YELLOW,"Claudio","Stringa di prova",false);
        enemy = new Player(ColorId.GREY,"Carlo","Stringa di prova",false);
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
    public void reset()
    {
        test.setNumberOfDamagePoint(0);
        enemy.setNumberOfDamagePoint(0);
        enemy2.setNumberOfDamagePoint(0);
        enemy3.setNumberOfDamagePoint(0);
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
    public void testDeathSchytle() throws SquareNotInGameBoard {


        g1.setAllPlayer(enemy);
        g1.setAllPlayer(test);

        test.setSquare(g1.getArena().getSquare(1,1));
        enemy.setSquare(g1.getArena().getSquare(2,1));

        MethodsWeapons.moveTarget(test,1,1);
        MethodsWeapons.moveTarget(enemy,2,1);
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
        boolean[] availableMethod = w0.checkAvailableMode();
        assertFalse(availableMethod[0]);
        assertFalse(availableMethod[1]);
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


        MethodsWeapons.moveTarget(enemy,1,1);
        test.setAmmoBlue(2);
        boolean[] availableMethod1 = w0.checkAvailableMode();
        assertEquals(availableMethod1[0],true);
        assertEquals(availableMethod1[1],true);

        List<ColorId> list1 = w0.checkBasicMode();
        List<ColorId> list2 = w0.checkReaper();
        assertTrue(list1.contains(enemy.getColor()));
        assertTrue(list2.contains(enemy.getColor()));
        w0.basicMode(list1);
        assertEquals(enemy.getNumberOfDamagePoint(),1);
        assertFalse(w0.isLoaded());
        w0.setLoaded(true);
        w0.reaper(list2);
        assertEquals(enemy.getNumberOfDamagePoint(),3);

        //solo basic disponibil

        w0.setLoaded(true);
        boolean[] availableMethod2 = w0.checkAvailableMode();
        assertEquals(availableMethod2[0],true);
        assertEquals(availableMethod2[1],false);

        List<ColorId> list3 = w0.checkBasicMode();

        assertTrue(list3.contains(enemy.getColor()));
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
    public void testLockRifle() throws SquareNotInGameBoard {
        g1.setAllPlayer(enemy2);
        g1.setAllPlayer(enemy);
        g1.setAllPlayer(test);
        test.setSquare(g1.getArena().getSquare(1,1));

        enemy.setSquare(g1.getArena().getSquare(2,2));
        enemy2.setSquare(g1.getArena().getSquare(2,2));


        MethodsWeapons.moveTarget(test,1,1);
        MethodsWeapons.moveTarget(enemy,2,2);
        MethodsWeapons.moveTarget(enemy2,2,2);
        test.addWeapon(w1);//Add a weapon
        try {
            w1.checkBasicMode();
            fail();
        } catch (IllegalStateException e) {
            System.out.println("player is null");
        }

        w1.setPlayer(test);
        w1.setLoaded(true);



        boolean[] availableMethod = w1.checkAvailableMode();
        assertFalse(availableMethod[0]);
        assertFalse(availableMethod[1]);
        try {
            w1.checkBasicMode();
            fail();
        } catch (IllegalStateException e) {
            System.out.println("modalità base non utilizzabile");
        }

        try {
            w1.checkSecondLock();
            fail();
        } catch (IllegalStateException e) {
            System.out.println("modalità avanzata non utilizzabile");
        }

        //tutto disponibile



        w1.setLoaded(true);
        test.setAmmoRed(2);


        MethodsWeapons.moveTarget(enemy,1,2);
        MethodsWeapons.moveTarget(enemy2,1,3);

        w1.setLoaded(true);
        test.setAmmoRed(2);
        test.setAmmoBlue(2);

        boolean[] availableMethod1 = w1.checkAvailableMode();
        assertEquals(availableMethod1[0], true);
        assertEquals(availableMethod1[1], true);

        List<ColorId> list1 = w1.checkBasicMode();
        List<ColorId> list2 = w1.checkSecondLock();

        assertTrue(list1.contains(enemy.getColor()));
        assertTrue(list2.contains(enemy.getColor()));
        assertTrue(list1.contains(enemy2.getColor()));
        assertTrue(list2.contains(enemy2.getColor()));



        w1.basicMode(getPl(list1, enemy), getPl(list2, enemy2), true);

        assertEquals(enemy.getNumberOfDamagePoint(), 2);
        assertEquals(enemy.checkMarker(test.getColor()), 1);
        assertEquals(enemy2.checkMarker(test.getColor()), 1);
        assertFalse(w1.isLoaded());

        //solo basic disponibile


        test.setAmmoBlue(0);
        test.setAmmoRed(0);
        test.setAmmoYellow(0);


        w1.setLoaded(true);
        boolean[] availableMethod2 = w1.checkAvailableMode();
        assertEquals(availableMethod2[0], true);
        assertEquals(availableMethod2[1], false);

        List<ColorId> list3 = w1.checkBasicMode();

        assertTrue(list3.contains(enemy.getColor()));

        w1.basicMode(getPl(list3, enemy), null, false);
        assertEquals(enemy.getNumberOfDamagePoint(), 5);
        assertFalse(w1.isLoaded());
        w1.setLoaded(true);
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
        w1.setLoaded(true);
            w1.basicMode(getPl(list3, enemy2), null, false);
            assertFalse(w1.isLoaded());
            assertEquals(enemy2.getNumberOfDamagePoint(), 6);
            assertEquals(enemy2.checkMarker(test.getColor()), 1);
        }


    @Test
    public void testWhisper() throws SquareNotInGameBoard {
        g1.setAllPlayer(enemy2);
        g1.setAllPlayer(enemy);
        g1.setAllPlayer(test);

        test.setSquare(g1.getArena().getSquare(1,1));
        enemy.setSquare(g1.getArena().getSquare(2,2));
        enemy2.setSquare(g1.getArena().getSquare(2,2));
        MethodsWeapons.moveTarget(test,1,1);
        MethodsWeapons.moveTarget(enemy,2,2);
        MethodsWeapons.moveTarget(enemy2,2,2);
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
        w4.setLoaded(true);
        boolean[] availableMethod = w4.checkAvailableMode();
        assertFalse(availableMethod[0]);
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



        MethodsWeapons.moveTarget(enemy,1,3);
        MethodsWeapons.moveTarget(enemy2,1,3);
        assertTrue(w4.isLoaded());
        boolean[] availableMethod1 = w4.checkAvailableMode();
        assertEquals(availableMethod1[0],true);


        List<ColorId> list1 = w4.checkBasicMode();

        assertTrue(list1.contains(enemy2.getColor()));

        w4.basicMode(getPl(list1,enemy2));
        assertEquals(enemy.getNumberOfDamagePoint(),0);
        assertEquals(enemy2.getNumberOfDamagePoint(),3);
        assertEquals(enemy2.checkMarker(test.getColor()),1);
        assertFalse(w4.isLoaded());

    }


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

        MethodsWeapons.moveTarget(test,1,1);
        MethodsWeapons.moveTarget(enemy,2,2);
        MethodsWeapons.moveTarget(enemy2,2,2);
        MethodsWeapons.moveTarget(enemy3,2,2);

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
        w3.setLoaded(true);
        boolean[] availableMethod = w3.checkAvailableMode();
        assertFalse(availableMethod[0]);
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



        MethodsWeapons.moveTarget(enemy,1,3);
        MethodsWeapons.moveTarget(enemy2,2,3);
        MethodsWeapons.moveTarget(enemy3,2,2);
        test.setAmmoBlue(2);
        test.setAmmoYellow(2);
        test.setAmmoRed(2);
        assertTrue(w3.isLoaded());
        boolean[] availableMethod1 = w3.checkAvailableMode();
        assertEquals(availableMethod1[0],true);


        List<ColorId> list1 = w3.checkBasicMode();
        List<ColorId> list2 = w3.checkChainReaction();
        List<ColorId> list3 = w3.checkHighVoltage();
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
        w3.setLoaded(true);

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

    }

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

        MethodsWeapons.moveTarget(test,1,1);
        MethodsWeapons.moveTarget(enemy,2,2);
        MethodsWeapons.moveTarget(enemy2,2,2);
        MethodsWeapons.moveTarget(enemy3,2,2);
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
        boolean[] availableMethod = w6.checkAvailableMode();
        assertFalse(availableMethod[0]);
        assertFalse(availableMethod[1]);
        assertFalse(availableMethod[2]);
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


        MethodsWeapons.moveTarget(enemy,1,2);
        MethodsWeapons.moveTarget(enemy2,1,3);
        MethodsWeapons.moveTarget(enemy3,1,2);
        test.setAmmoYellow(2);
        test.setAmmoBlue(2);
        boolean[] availableMethod1 = w6.checkAvailableMode();
        assertEquals(availableMethod1[0],true);
        assertEquals(availableMethod1[1],true);
        assertEquals(availableMethod1[2],true);

        List<ColorId> list1 = w6.checkBasicMode();
        List<ColorId> list2 = w6.checkFocusShotcMode();
        List<ColorId> list3 = w6.checkTurretTripodeMode();
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

        //solo basic disponibile
        test.setAmmoYellow(0);
        test.setAmmoBlue(0);
        w6.setLoaded(true);
        boolean[] availableMethod2 = w6.checkAvailableMode();
        assertEquals(availableMethod2[0],true);
        assertEquals(availableMethod2[1],false);
        assertEquals(availableMethod2[2],false);
       list3 = w6.checkBasicMode();

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
        w6.setLoaded(true);

        w6.basicMode(getPl(list1,enemy2),getPl(list1,enemy),null,null,null,false,false,false);

        assertFalse(w6.isLoaded());
        assertEquals(enemy2.getNumberOfDamagePoint(),4);
        assertEquals(enemy.getNumberOfDamagePoint(),2);

        test.setAmmoYellow(2);
        test.setAmmoBlue(2);
        w6.setLoaded(true);
        w6.basicMode(getPl(list1,enemy2),getPl(list1,enemy),null,getPl(list1,enemy3),getPl(list1,enemy2),false,true,true);

        test.setAmmoYellow(2);
        test.setAmmoBlue(2);
        w6.setLoaded(true);
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
        w6.setLoaded(true);
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
        w6.setLoaded(true);
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
        w6.setLoaded(true);
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

            MethodsWeapons.moveTarget(test,1,1);
            MethodsWeapons.moveTarget(enemy,2,2);
            MethodsWeapons.moveTarget(enemy2,2,2);
            MethodsWeapons.moveTarget(enemy3,2,2);

            test.addWeapon(w5);//Add a weapon


            // spara, muoviti ,aggiungi danno
            orderEffect[0] = "basic";
            orderEffect[1] = "with phase glide";
            orderEffect[2] = "with charged shot";

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


            w5.setLoaded(true);




            MethodsWeapons.moveTarget(test,1,3);
            MethodsWeapons.moveTarget(enemy,4,1);


            List<String> reachableSquare = new LinkedList<>();
            Map<String,List<ColorId>> reachableBasicPlayer = new HashMap<>();
            Map<String,List<ColorId>> reachableBeforeMovePlayer = new HashMap<>();



            reachableSquare= w5.checkPhaseGlide();



    //prima ti muovi , raggiungi il player e gli spari




            MethodsWeapons.moveTarget(enemy,1,3);
            MethodsWeapons.moveTarget(test,1,1);


            test.setAmmoBlue(3);
            reachableBasicPlayer = w5.checkAllTarget();

            reachableSquare= w5.checkPhaseGlide();
            reachableBeforeMovePlayer = w5.checkAllTarget();

            assertFalse(reachableBasicPlayer.get(test.getSquare().getGameBoard().getArena().getSquare(1,2).toStringCoordinates()).contains(enemy3.getColor()));
            w5.basicMode(getPl(reachableBasicPlayer.get(test.getSquare().getGameBoard().getArena().getSquare(1,2).toStringCoordinates()),enemy),orderEffect,1,2);

            assertFalse(w5.isLoaded());
            assertEquals(enemy.getNumberOfDamagePoint(),3);





            MethodsWeapons.moveTarget(test,1,1);
            MethodsWeapons.moveTarget(enemy,2,3);



            // muoviti, spara ,aggiungi danno
            orderEffect[1] = "basic";
            orderEffect[0] = "with phase glide";
            orderEffect[2] = "with charged shot";
            test.setAmmoBlue(3);
            w5.setLoaded(true);

            reachableSquare= w5.checkPhaseGlide();
            reachableBeforeMovePlayer = w5.checkAllTarget();
            assertTrue(reachableSquare.contains(g1.getArena().getSquare(1,3).toStringCoordinates()));
            List<ColorId> templ = reachableBeforeMovePlayer.get(g1.getArena().getSquare(1,3).toStringCoordinates());
         assertTrue(templ.contains(enemy.getColor()));
assertTrue(enemy.getNumberOfDamagePoint()==3);
//getPl(reachableBasicPlayer.get(test.getSquare().getGameBoard().getArena().getSquare(1,3).toStringCoordinates()),enemy)
            w5.basicMode(enemy.getColor(),orderEffect,1,3);

            assertEquals(enemy.getNumberOfDamagePoint(),6);
            assertTrue(test.getSquare().equals(test.getSquare().getGameBoard().getArena().getSquare(1,3)));

         // prova a muoverti e a sparare a un nemico che non  puoi raggiungere da quella posizione ( ma che comunque potevi raggiungere)




            MethodsWeapons.moveTarget(test,1,1);
            MethodsWeapons.moveTarget(enemy,2,2);
            MethodsWeapons.moveTarget(enemy2,1,3);

            // muoviti, spara ,aggiungi danno
            orderEffect[0] = "with phase glide";
            orderEffect[1] = "basic";
            orderEffect[2] = "with charged shot";

            test.setAmmoBlue(3);
            w5.setLoaded(true);
            assertTrue(enemy.getSquare().equals(g1.getArena().getSquare(2,2)));

            reachableBasicPlayer = w5.checkAllTarget();
            reachableBeforeMovePlayer = w5.checkAllTarget();


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
                reachableBeforeMovePlayer = w5.checkAllTarget();
                fail();
            }
            catch(IllegalStateException e)
            {
                System.out.println(e);
            }
            try
            {
                reachableSquare= w5.checkPhaseGlide();
                fail();
            }
            catch(IllegalStateException e)
            {
                System.out.println(e);
            }
            w5.setLoaded(true);
            reachableBasicPlayer = w5.checkAllTarget();

            reachableBeforeMovePlayer = w5.checkAllTarget();
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

        MethodsWeapons.moveTarget(test,1,1);
        MethodsWeapons.moveTarget(enemy,2,2);
        MethodsWeapons.moveTarget(enemy2,2,2);
        MethodsWeapons.moveTarget(enemy3,2,2);
        test.addWeapon(w2);//Add a weapon
        try
        {
            w2.checkMoveBasicMode();
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println("player is null");
        }

        w2.setPlayer(test);
        w2.setLoaded(true);
        boolean[] availableMethod = w2.checkAvailableMode();
        assertTrue(availableMethod[0]);
        assertTrue(availableMethod[1]);


        //tutto disponibile



        MethodsWeapons.moveTarget(enemy,1,3);
        MethodsWeapons.moveTarget(enemy2,2,3);
        MethodsWeapons.moveTarget(enemy3,2,2);
        test.setAmmoBlue(3);
        test.setAmmoYellow(2);
        test.setAmmoRed(2);
        assertTrue(w2.isLoaded());
        boolean[] availableMethod1 = w2.checkAvailableMode();
        assertEquals(availableMethod1[0],true);


        Map<ColorId,List<String>> list1 = w2.checkMoveBasicMode();
        List<ColorId> list2 = w2.checkPunisherMode();

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

        w2.setLoaded(true);

        w2.punisherMode(getPl(list2,enemy3));
        assertEquals(enemy3.getNumberOfDamagePoint(),3);

        w2.setLoaded(true);
        test.setAmmoBlue(0);
        test.setAmmoYellow(0);
        test.setAmmoRed(0);
      try{  w2.punisherMode(getPl(list2,enemy3));}
      catch (IllegalStateException e)
      {
          System.out.println(e);
      }

        //tutto disponibile test 2

        MethodsWeapons.moveTarget(enemy,1,2);
        MethodsWeapons.moveTarget(enemy2,1,1);
        MethodsWeapons.moveTarget(test,3,3);
        assertEquals(enemy.getNumberOfDamagePoint(),0);
        assertEquals(enemy2.getNumberOfDamagePoint(),1);
        w2.setLoaded(true);
        test.setAmmoBlue(3);
        test.setAmmoRed(3);
        test.setAmmoYellow(3);
        list1 = w2.checkMoveBasicMode();
       try{
           list2 = w2.checkPunisherMode();
           fail();
       }
       catch (IllegalStateException e)
       {
           System.out.println(e);
       }
        boolean[] availableMethods = w2.checkAvailableMode();
        assertTrue(availableMethods[0]);
        assertFalse(availableMethods[1]);



        assertTrue(list1.containsKey(enemy.getColor()));
        assertTrue(list1.containsKey(enemy2.getColor()));
        w2.basicMode(enemy.getColor(),1,3);
        assertEquals(enemy.getNumberOfDamagePoint(),1);
        assertTrue(enemy.getSquare().equals(enemy.getSquare().getGameBoard().getArena().getSquare(1,3)));

        w2.setLoaded(true);
        w2.basicMode(enemy2.getColor(),2,2);

        assertEquals(enemy2.getNumberOfDamagePoint(),2);
        assertTrue(enemy2.getSquare().equals(enemy2.getSquare().getGameBoard().getArena().getSquare(2,2)));


        //tutto disponibile test 3

        MethodsWeapons.moveTarget(enemy,1,2);
        MethodsWeapons.moveTarget(enemy2,1,1);
        MethodsWeapons.moveTarget(test,2,3);
        assertEquals(enemy.getNumberOfDamagePoint(),1);
        assertEquals(enemy2.getNumberOfDamagePoint(),2);
        w2.setLoaded(true);
        list1 = w2.checkMoveBasicMode();
        list2 = w2.checkPunisherMode();
        boolean[] availableMethodss = w2.checkAvailableMode();
        assertTrue(availableMethodss[0]);
        assertTrue(availableMethodss[1]);



        assertTrue(list1.containsKey(enemy.getColor()));
        assertTrue(list1.containsKey(enemy2.getColor()));
        assertTrue(list2.contains(enemy.getColor()));
        w2.basicMode(enemy.getColor(),1,3);
        assertEquals(enemy.getNumberOfDamagePoint(),2);
        assertTrue(enemy.getSquare().equals(enemy.getSquare().getGameBoard().getArena().getSquare(1,3)));

        w2.setLoaded(true);
        w2.basicMode(enemy2.getColor(),2,2);

        assertEquals(enemy2.getNumberOfDamagePoint(),3);
        assertTrue(enemy2.getSquare().equals(enemy2.getSquare().getGameBoard().getArena().getSquare(2,2)));


    }
}

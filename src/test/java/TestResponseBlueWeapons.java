import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.GameBoard;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.communication_message.*;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestResponseBlueWeapons {

    //here we spawn the player and add them in the enemy list
    //here we declare attribute for the tests
    private Player test;
    private Player enemy;
    private Player enemy2;
    private Player enemy3;

    private GameBoard g1= new GameBoard(null,null,1,10,null);





    @Before
    public void setup()  //here we setup the players
    {

    //here we spawn the player and add them in the enemy list
        test = new Player(ColorId.YELLOW,"Claudio","Stringa di prova",false);
        enemy = new Player(ColorId.GREY,"Carlo","Stringa di prova",false);
        enemy2 = new Player(ColorId.BLUE,"Marco","Stringa di prova",false);
        enemy3 = new Player(ColorId.GREEN,"Alice","Stringa di prova",false);
    }

    //here we test the response of the weapon electro schyte 
    @Test
    public void testElectroSchyte() throws SquareNotInGameBoard {

    //here we spawn the player and add them in the enemy list


        g1.setAllPlayer(enemy2);
        g1.setAllPlayer(enemy);
        g1.setAllPlayer(test);

        test.setSquare(g1.getArena().getSquare(1,1));
        enemy.setSquare(g1.getArena().getSquare(2,1));
        enemy2.setSquare(g1.getArena().getSquare(2,1));

        MethodsWeapons.moveTarget(test,1,1);
        MethodsWeapons.moveTarget(enemy,2,1);
        MethodsWeapons.moveTarget(enemy2,2,1);

        List<ColorId> enemyList = new LinkedList<>();
        enemyList.add(enemy.getColor());
        enemyList.add(enemy2.getColor());

    //here we use the methods with the given enemy list to get the response

        ResponseElectroSchyte respbasic = new ResponseElectroSchyte(enemyList,true);


        assertEquals(respbasic.getTargetBasicMode(),enemyList);


        ResponseElectroSchyte resp = new ResponseElectroSchyte(enemyList,true);


        assertEquals(resp.getTargetsAlternativeMode(),enemyList);
        assertEquals(resp.isMode(),true);


    }
    //here we test the response of the weapon plasma gun 
    @Test
    public void testPlasma() throws SquareNotInGameBoard {

    //here we spawn the player and add them in the enemy list

        String[] orderEffect = new String[3];
        // spara, muoviti ,aggiungi danno
        orderEffect[0] = "basic";
        orderEffect[1] = "with phase glide";
        orderEffect[2] = "with charged shot";
        g1.setAllPlayer(enemy2);
        g1.setAllPlayer(enemy);
        g1.setAllPlayer(test);

        test.setSquare(g1.getArena().getSquare(1,1));
        enemy.setSquare(g1.getArena().getSquare(2,1));
        enemy2.setSquare(g1.getArena().getSquare(2,1));

        MethodsWeapons.moveTarget(test,1,1);
        MethodsWeapons.moveTarget(enemy,2,1);
        MethodsWeapons.moveTarget(enemy2,2,1);
        //here we use the methods with the given enemy list to get the response

        ResponsePlasmaGun resp = new ResponsePlasmaGun(enemy.getColor(),2,1,orderEffect);

        assertEquals(resp.getX(),2);
        assertEquals(resp.getY(),1);
        assertEquals(resp.getTargetBasicEffect(),enemy.getColor());

        assertEquals(resp.getOrderEffect()[0],orderEffect[0]);
        assertEquals(resp.getOrderEffect()[1],orderEffect[1]);
        assertEquals(resp.getOrderEffect()[2],orderEffect[2]);
    }

    //here we test the response of the weapon lock rifle
    @Test
    public void testLockRifle() throws SquareNotInGameBoard {

    //here we spawn the player and add them in the enemy list


        g1.setAllPlayer(enemy2);
        g1.setAllPlayer(enemy);
        g1.setAllPlayer(test);

        test.setSquare(g1.getArena().getSquare(1,1));
        enemy.setSquare(g1.getArena().getSquare(2,1));
        enemy2.setSquare(g1.getArena().getSquare(2,1));

        MethodsWeapons.moveTarget(test,1,1);
        MethodsWeapons.moveTarget(enemy,2,1);
        MethodsWeapons.moveTarget(enemy2,2,1);

        List<ColorId> enemyList = new LinkedList<>();
        enemyList.add(enemy.getColor());
        enemyList.add(enemy2.getColor());
        //here we use the methods with the given enemy list to get the response

        ResponseLockRifle respbasic = new ResponseLockRifle(enemy.getColor(),enemy2.getColor());


        assertEquals(respbasic.getTargetBasicMode(),enemy.getColor());




        assertEquals(respbasic.getTargetsAdditionalMode(),enemy2.getColor());


        ResponseLockRifle resp = new ResponseLockRifle(enemy2.getColor());
        assertEquals(resp.getTargetBasicMode(),enemy2.getColor());
    }
    //here we test the response of the weapon tractator beam
    @Test
    public void testTractatorBeam() throws SquareNotInGameBoard {

    //here we spawn the player and add them in the enemy list


        g1.setAllPlayer(enemy2);
        g1.setAllPlayer(enemy);
        g1.setAllPlayer(test);

        test.setSquare(g1.getArena().getSquare(1,1));
        enemy.setSquare(g1.getArena().getSquare(2,1));
        enemy2.setSquare(g1.getArena().getSquare(2,1));

        MethodsWeapons.moveTarget(test,1,1);
        MethodsWeapons.moveTarget(enemy,2,1);
        MethodsWeapons.moveTarget(enemy2,2,1);

        //here we use the methods with the given enemy list to get the response

        ResponseTractatorBeam respbasic = new ResponseTractatorBeam(enemy.getColor(),2,1);


        assertEquals(respbasic.getTargetBasicMode(),enemy.getColor());
        assertEquals(respbasic.getX(),2);
        assertEquals(respbasic.getY(),1);

        ResponseTractatorBeam resp = new  ResponseTractatorBeam(enemy2.getColor());


        assertEquals(resp.getTargetAlternativeMode(),enemy2.getColor());
    }
    //here we test the response of the weapon whisper
    @Test
    public void testWhisper() throws SquareNotInGameBoard {

    //here we spawn the player and add them in the enemy list


 
        g1.setAllPlayer(enemy);
        g1.setAllPlayer(test);

        test.setSquare(g1.getArena().getSquare(1,1));
        enemy.setSquare(g1.getArena().getSquare(2,1));


        MethodsWeapons.moveTarget(test,1,1);
        MethodsWeapons.moveTarget(enemy,2,1);


        List<ColorId> enemyList = new LinkedList<>();
        enemyList.add(enemy.getColor());
        enemyList.add(enemy2.getColor());

        //here we use the methods with the given enemy list to get the response
        ResponseWhisper respbasic = new ResponseWhisper(enemy.getColor());


        assertEquals(respbasic.getTargetBasicMode(),enemy.getColor());


    }
    //here we test the response of the weapon machine gun
    @Test
    public void testMachinGun() throws SquareNotInGameBoard {

    //here we spawn the player and add them in the enemy list

        g1.setAllPlayer(enemy3);
        g1.setAllPlayer(enemy2);
        g1.setAllPlayer(enemy);
        g1.setAllPlayer(test);

        test.setSquare(g1.getArena().getSquare(1,1));
        enemy.setSquare(g1.getArena().getSquare(2,1));
        enemy2.setSquare(g1.getArena().getSquare(2,1));
        enemy3.setSquare(g1.getArena().getSquare(2,1));

        MethodsWeapons.moveTarget(test,1,1);
        MethodsWeapons.moveTarget(enemy,2,1);
        MethodsWeapons.moveTarget(enemy2,2,1);
        MethodsWeapons.moveTarget(enemy3,2,1);

        //here we use the methods with the given enemy list to get the response

        ResponseMachineGun respbasic = new ResponseMachineGun(enemy.getColor(),null);


        assertEquals(respbasic.getTargetBasicMode(),enemy.getColor());


        ResponseMachineGun respbasi = new ResponseMachineGun(enemy.getColor(),enemy2.getColor(),enemy.getColor());



        assertEquals(respbasi.getTargetBasicMode(),enemy.getColor());
        assertEquals(respbasi.getTargetBasicModeSecond(),enemy2.getColor());
        assertEquals(respbasi.getTargetAdditionalMode(),enemy.getColor());

        ResponseMachineGun respbas = new ResponseMachineGun(enemy.getColor(),enemy2.getColor(),enemy.getColor(),enemy3.getColor(),true);



        assertEquals(respbas.getTargetBasicMode(),enemy.getColor());
        assertEquals(respbas.getTargetBasicModeSecond(),enemy2.getColor());
        assertEquals(respbas.getTargetSecondAdditionalMode(),enemy.getColor());
        assertEquals(respbas.getTargetSecondAdditionalModeSecond(),enemy3.getColor());

        ResponseMachineGun respba = new ResponseMachineGun(enemy.getColor(),enemy2.getColor(),enemy.getColor(),enemy3.getColor(),enemy2.getColor(),true);



        assertEquals(respba.getTargetBasicMode(),enemy.getColor());
        assertEquals(respba.getTargetBasicModeSecond(),enemy2.getColor());
        assertEquals(respba.getTargetAdditionalMode(),enemy.getColor());
        assertEquals(respba.getTargetSecondAdditionalMode(),enemy3.getColor());
        assertEquals(respba.getTargetSecondAdditionalModeSecond(),enemy2.getColor());





    }
    //here we test the response of the weapon thor
    @Test
    public void testThor() throws SquareNotInGameBoard {

    //here we spawn the player and add them in the enemy list



        g1.setAllPlayer(enemy3);
        g1.setAllPlayer(enemy2);
        g1.setAllPlayer(enemy);
        g1.setAllPlayer(test);

        test.setSquare(g1.getArena().getSquare(1,1));
        enemy.setSquare(g1.getArena().getSquare(2,1));
        enemy2.setSquare(g1.getArena().getSquare(2,1));
        enemy3.setSquare(g1.getArena().getSquare(2,1));

        MethodsWeapons.moveTarget(test,1,1);
        MethodsWeapons.moveTarget(enemy,2,1);
        MethodsWeapons.moveTarget(enemy2,2,1);
        MethodsWeapons.moveTarget(enemy3,2,1);

        //here we use the methods with the given enemy list to get the response
        ResponseTHOR respbasic = new ResponseTHOR(enemy.getColor());


        assertEquals(respbasic.getTargetBasicMode(),enemy.getColor());




        ResponseTHOR resp = new ResponseTHOR(enemy.getColor(),enemy2.getColor());
        assertEquals(resp.getTargetBasicMode(),enemy.getColor());
        assertEquals(resp.getTargetAdditionalMode(),enemy2.getColor());


        ResponseTHOR res = new ResponseTHOR(enemy.getColor(),enemy2.getColor(),enemy3.getColor());
        assertEquals(resp.getTargetBasicMode(),enemy.getColor());
        assertEquals(resp.getTargetAdditionalMode(),enemy2.getColor());
        assertEquals(res.getTargetSecondAdditionalMode(),enemy3.getColor());

        assertEquals(res.isMode(),false);
        assertEquals(res.isSecondMode(),true);
    }


}

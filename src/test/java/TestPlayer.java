import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import it.polimi.deib.se2018.adrenalina.Model.graph.*;

import java.util.Stack;

//Note
//Bisognerebbe testare anche il metodo playerthatsee quando avrò metodi per aggiungere player alle room e allo square


//Test the evolution of player's state correctly
//And the possible action that he can undergo
public class TestPlayer
{

    Player test;
    SpawnPoint sq = new SpawnPoint(1,1,null,ColorRoom.RED,null);

    @Before
    public void setup()
    {
        test = new Player(ColorId.YELLOW,"Claudio","Stringa di prova",false);
    }

    @Test
    public void testCreation() //Tests if the object player is created correctly
    {
        assertEquals(1,test.getAmmoBlue());
        assertEquals(1,test.getAmmoRed());
        assertEquals(1,test.getAmmoYellow());
        assertEquals(0,test.getDeathsCounter());
        assertEquals("Stringa di prova",test.getAction_hero_comment());
        assertEquals(ColorId.YELLOW,test.getColor());
        assertEquals("Claudio",test.getName());
        assertEquals(0,test.getNumberOfDamagePoint());
        assertEquals(0,test.getScore());
        assertFalse(test.isFirst());
        assertNull(test.getSquare());
        assertFalse(test.isFrenzy());
        assertTrue(test.getWeaponCardList().isEmpty());
    }

    @Test
    public void testOnlyDamage() //Tests if doing damage at the player, the damage point after there are in the board
    {

        ColorId[] expOut = new ColorId[12]; //Expected output

        expOut[0] = ColorId.PURPLE;
        expOut[1] = ColorId.PURPLE;
        expOut[2] = ColorId.BLUE;
        expOut[3] = ColorId.BLUE;
        expOut[4] = ColorId.GREY;
        expOut[5] = ColorId.GREY;
        expOut[6] = ColorId.GREY;
        expOut[7] = ColorId.PURPLE;
        expOut[8] = ColorId.BLUE;
        expOut[9] = ColorId.BLUE;
        expOut[10] = ColorId.GREY;

        test.doDamage(ColorId.PURPLE);
        test.doDamage(ColorId.PURPLE);
        test.doDamage(ColorId.BLUE);
        test.doDamage(ColorId.BLUE);
        test.doDamage(ColorId.GREY);
        test.doDamage(ColorId.GREY);

        assertFalse(test.isDead());

        test.doDamage(ColorId.GREY);
        test.doDamage(ColorId.PURPLE);
        test.doDamage(ColorId.BLUE);
        test.doDamage(ColorId.BLUE);

        assertFalse(test.isDead());

        test.doDamage(ColorId.GREY);

        assertTrue(test.isDead()); //Check if the player is dead

        assertFalse(test.isOverKill());//Check that the player is not overkilled

        ColorId[] effOut = test.getDamageCounter(); //Expected output

        for (int i = 0; i<11;i++) //Check if the player's board has all damage points
            assertTrue("Valori diversi in posizione " + i +" " + effOut[i] + " "+ expOut[i],effOut[i].equals(expOut[i]));

    }

    @Test
    public void testDamageAndMarks()//Test if the damage and the mark being handled correctly
    {

        ColorId[] expOut = new ColorId[12]; //Expected output

        expOut[0] = ColorId.PURPLE;
        expOut[1] = ColorId.GREY;
        expOut[2] = ColorId.GREY;
        expOut[3] = ColorId.PURPLE;
        expOut[4] = ColorId.PURPLE;
        expOut[5] = ColorId.PURPLE;
        expOut[6] = ColorId.PURPLE;
        expOut[7] = ColorId.BLUE;
        expOut[8] = ColorId.BLUE;
        expOut[9] = ColorId.BLUE;
        expOut[10] = ColorId.PURPLE;
        expOut[11] = ColorId.PURPLE;

        assertEquals(0,test.checkMarker(ColorId.PURPLE));
        assertEquals(0,test.checkMarker(ColorId.GREY));
        assertEquals(0,test.checkMarker(ColorId.BLUE));

        test.doDamage(ColorId.PURPLE); //Purple do 1 damage and 2 marks
        test.addMark(ColorId.PURPLE);
        test.addMark(ColorId.PURPLE);

        assertEquals(2, test.checkMarker(ColorId.PURPLE));

        test.doDamage(ColorId.GREY);//Grey do 2 damages and 1 mark
        test.doDamage(ColorId.GREY);
        test.addMark(ColorId.GREY);

        assertEquals(1,test.checkMarker(ColorId.GREY));

        test.doDamage(ColorId.PURPLE);//Purple do 1 damage
        test.useMark(ColorId.PURPLE);//Mark being used
        test.useMark(ColorId.PURPLE);

        assertEquals(0, test.checkMarker(ColorId.PURPLE));

        test.doDamage(ColorId.PURPLE);//Purple do 1 damage
        test.addMark(ColorId.PURPLE);
        test.addMark(ColorId.PURPLE); //PURPLE do 4 marks and 1 damage
        test.addMark(ColorId.PURPLE);
        test.addMark(ColorId.PURPLE);

        assertEquals(3, test.checkMarker(ColorId.PURPLE));//One mark is lost


        test.doDamage(ColorId.BLUE);//BLUE do 3 damages and 0 marks
        test.doDamage(ColorId.BLUE);
        test.doDamage(ColorId.BLUE);

        test.doDamage(ColorId.PURPLE); //Purple do the 11° damage point and two marks

        assertEquals(11,test.getNumberOfDamagePoint());

        test.useMark(ColorId.PURPLE);//1 mark do the 12° DAMAGE POINTS
        test.useMark(ColorId.PURPLE);//the other two are lost
        test.useMark(ColorId.PURPLE);

        assertEquals(12, test.getNumberOfDamagePoint());
        assertEquals(0,test.checkMarker(ColorId.PURPLE));
        assertTrue(test.isDead() && test.isOverKill()); //Player is dead with 12 damage points

        test.addMark(ColorId.PURPLE);
        test.addMark(ColorId.PURPLE);

        assertEquals(2,test.checkMarker(ColorId.PURPLE));
        assertEquals(1,test.checkMarker(ColorId.GREY));

        assertTrue(test.isOverKill());//Check if the player is overkilled

        ColorId[] effOut = test.getDamageCounter(); //Expected output

        for (int i = 0; i<11;i++) //Check if the player's board has all damage points
            assertTrue("Valori diversi in posizione " + i +" " + effOut[i] + " "+ expOut[i],effOut[i].equals(expOut[i]));

    }

    @Test
    public void testRespawn()//Check if the respawn change the state of player
    {

        for (int i=0;i<12;i++) //Let the player die
            test.doDamage(ColorId.BLUE);

        assertTrue(test.isOverKill() && test.isDead());

        test.respawn(sq);

        assertTrue(!test.isOverKill());
        assertTrue(!test.isDead());
        assertEquals(sq, test.getSquare());
        assertEquals(1,test.getDeathsCounter());

        try
        {
            test.respawn(sq);
            fail();
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
        }


    }

    @Test
    public void checkScores() //Checks if with the deaths of player the scores decrease
    {
        int[] expOut = new int[6];
        expOut[0] = 8;
        expOut[1] = 6;
        expOut[2] = 4;
        expOut[3] = 2;
        expOut[4] = 1;
        expOut[5] = 1;

        int[] effOut = test.getScorePoint();

        for (int i =0;i<6;i++)
            assertTrue(effOut[i]== expOut[i]);

        for (int i=0;i<12;i++) //Let the player die
            test.doDamage(ColorId.BLUE);

        test.respawn(sq);

        expOut = new int[6];
        expOut[0] = 6;
        expOut[1] = 4;
        expOut[2] = 2;
        expOut[3] = 1;
        expOut[4] = 1;
        expOut[5] = 1;

        effOut = test.getScorePoint();

        for (int i =0;i<6;i++)
            assertTrue(effOut[i] == expOut[i]);

        for (int i=0;i<12;i++) //Let the player die
            test.doDamage(ColorId.BLUE);

        test.respawn(sq);

        expOut = new int[6];
        expOut[0] = 4;
        expOut[1] = 2;
        expOut[2] = 1;
        expOut[3] = 1;
        expOut[4] = 1;
        expOut[5] = 1;

        effOut = test.getScorePoint();

        for (int i =0;i<6;i++)
            assertTrue(effOut[i]== expOut[i]);

        for (int i=0;i<12;i++) //Let the player die
            test.doDamage(ColorId.BLUE);

        test.respawn(sq);

        expOut = new int[6];
        expOut[0] = 2;
        expOut[1] = 1;
        expOut[2] = 1;
        expOut[3] = 1;
        expOut[4] = 1;
        expOut[5] = 1;

        effOut = test.getScorePoint();

        for (int i =0;i<6;i++)
            assertTrue(effOut[i]== expOut[i]);

        for (int i=0;i<12;i++) //Let the player die
            test.doDamage(ColorId.BLUE);

        test.respawn(sq);

        expOut = new int[6];
        expOut[0] = 1;
        expOut[1] = 1;
        expOut[2] = 1;
        expOut[3] = 1;
        expOut[4] = 1;
        expOut[5] = 1;

        effOut = test.getScorePoint();

        for (int i =0;i<6;i++)
            assertTrue(effOut[i]== expOut[i]);
    }


}

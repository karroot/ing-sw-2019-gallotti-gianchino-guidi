import it.polimi.deib.se2018.adrenalina.Model.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Map;

public class TestCalculateScore
{
    private Player test;

    //Create a player for the calculate of the score
    @Before
    public void setUp()
    {
        test = new Player(ColorId.YELLOW,"Claudio","Stringa di prova",false);
    }

    /*
        Calculate of the score in case of three players that hit the player "Claudio" and there isn't Overkill
     */
    @Test
    public void test3playerNoOver()
    {
        test.doDamage(ColorId.PURPLE);
        test.doDamage(ColorId.PURPLE);
        test.doDamage(ColorId.BLUE);
        test.doDamage(ColorId.BLUE);
        test.doDamage(ColorId.GREY);
        test.doDamage(ColorId.GREY);
        test.doDamage(ColorId.GREY);
        test.doDamage(ColorId.PURPLE);
        test.doDamage(ColorId.BLUE);
        test.doDamage(ColorId.BLUE);
        test.doDamage(ColorId.GREY);

        final Map<ColorId, Integer> scores = test.calculateScoreForEachPlayer();

        assertEquals(8,scores.get(ColorId.BLUE).intValue());
        assertEquals(5,scores.get(ColorId.PURPLE).intValue());
        assertEquals(6,scores.get(ColorId.GREY).intValue());
    }

    /*
      Calculate of the score in case of three players that hit the player "Claudio" and there is the Overkill
    */
    @Test
    public void test3playerOver()
    {
        test.doDamage(ColorId.PURPLE); //Purple do 1 damage and 2 marks
        test.addMark(ColorId.PURPLE);
        test.addMark(ColorId.PURPLE);
        test.doDamage(ColorId.GREY);//Grey do 2 damages and 1 mark
        test.doDamage(ColorId.GREY);
        test.addMark(ColorId.GREY);
        test.doDamage(ColorId.PURPLE);//Purple do 1 damage
        test.useMark(ColorId.PURPLE);//Mark being used
        test.useMark(ColorId.PURPLE);
        test.doDamage(ColorId.PURPLE);//Purple do 1 damage
        test.addMark(ColorId.PURPLE);
        test.addMark(ColorId.PURPLE); //PURPLE do 4 marks and 1 damage
        test.addMark(ColorId.PURPLE);
        test.addMark(ColorId.PURPLE);
        test.doDamage(ColorId.BLUE);//BLUE do 3 damages and 0 marks
        test.doDamage(ColorId.BLUE);
        test.doDamage(ColorId.BLUE);
        test.doDamage(ColorId.PURPLE); //Purple do the 11° damage point and two marks
        test.useMark(ColorId.PURPLE);//1 mark do the 12° DAMAGE POINTS
        test.useMark(ColorId.PURPLE);//the other two are lost
        test.useMark(ColorId.PURPLE);
        test.addMark(ColorId.PURPLE);
        test.addMark(ColorId.PURPLE);

       final Map<ColorId, Integer> scores = test.calculateScoreForEachPlayer();

        assertEquals(6,scores.get(ColorId.BLUE).intValue());
        assertEquals(9,scores.get(ColorId.PURPLE).intValue());
        assertEquals(4,scores.get(ColorId.GREY).intValue());
    }
    /*
          Calculate of the score in case of five players that hit the player "Claudio" and there is no parity
       */
    @Test
    public void test5playerNoParity()
    {
        test.doDamage(ColorId.PURPLE);
        test.doDamage(ColorId.PURPLE);

        test.doDamage(ColorId.GREY);
        test.doDamage(ColorId.GREY);
        test.doDamage(ColorId.GREY);

        test.doDamage(ColorId.BLUE);
        test.doDamage(ColorId.BLUE);
        test.doDamage(ColorId.BLUE);
        test.doDamage(ColorId.BLUE);

        test.doDamage(ColorId.YELLOW);

        test.doDamage(ColorId.GREEN);

        Map<ColorId, Integer> scores = test.calculateScoreForEachPlayer();


        assertEquals(6,scores.get(ColorId.GREY).intValue());
        assertEquals(8,scores.get(ColorId.BLUE).intValue());
        assertEquals(5,scores.get(ColorId.PURPLE).intValue());
        assertEquals(2,scores.get(ColorId.YELLOW).intValue());
        assertEquals(1,scores.get(ColorId.GREEN).intValue());
    }

    /*
      Calculate of the score in case of five players that hit the player "Claudio" and there is some parity
   */
    @Test
    public void test5playerParity()
    {
        test.doDamage(ColorId.PURPLE);
        test.doDamage(ColorId.PURPLE);

        test.doDamage(ColorId.GREY);
        test.doDamage(ColorId.GREY);
        test.doDamage(ColorId.GREY);
        test.doDamage(ColorId.GREY);

        test.doDamage(ColorId.BLUE);
        test.doDamage(ColorId.BLUE);
        test.doDamage(ColorId.BLUE);
        test.doDamage(ColorId.BLUE);

        test.doDamage(ColorId.YELLOW);

        test.doDamage(ColorId.GREEN);

        Map<ColorId, Integer> scores = test.calculateScoreForEachPlayer();


        assertEquals(8,scores.get(ColorId.GREY).intValue());
        assertEquals(6,scores.get(ColorId.BLUE).intValue());
        assertEquals(5,scores.get(ColorId.PURPLE).intValue());
        assertEquals(2,scores.get(ColorId.YELLOW).intValue());
        assertEquals(1,scores.get(ColorId.GREEN).intValue());
    }

    /*
      Calculate of the score in case of four players that hit the player "Claudio" and all are in parity
   */
    @Test
    public void test4playerAllParity()
    {
        test.doDamage(ColorId.PURPLE);
        test.doDamage(ColorId.GREY);
        test.doDamage(ColorId.BLUE);
        test.doDamage(ColorId.YELLOW);
        test.doDamage(ColorId.PURPLE);
        test.doDamage(ColorId.GREY);
        test.doDamage(ColorId.BLUE);
        test.doDamage(ColorId.YELLOW);
        test.doDamage(ColorId.PURPLE);
        test.doDamage(ColorId.GREY);
        test.doDamage(ColorId.BLUE);
        test.doDamage(ColorId.YELLOW);


        Map<ColorId, Integer> scores = test.calculateScoreForEachPlayer();


        assertEquals(6,scores.get(ColorId.GREY).intValue());
        assertEquals(4,scores.get(ColorId.BLUE).intValue());
        assertEquals(9,scores.get(ColorId.PURPLE).intValue());
        assertEquals(2,scores.get(ColorId.YELLOW).intValue());

    }
}

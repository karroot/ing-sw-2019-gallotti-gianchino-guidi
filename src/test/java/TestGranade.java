import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.GameBoard;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseTagbackGranade;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGranade
{

    private Player test;
    private Player enemy;

    private GameBoard g1= new GameBoard(null,null,1,10,null);





    @Before
    public void setup()
    {
        //here we setup the players
        test = new Player(ColorId.YELLOW,"Claudio","Stringa di prova",false);
        enemy = new Player(ColorId.GREY,"Carlo","Stringa di prova",false);
    }


    @Test
    public void testGranade() throws SquareNotInGameBoard {
        //here we spawn the players
        g1.setAllPlayer(enemy);
        g1.setAllPlayer(test);

        test.setSquare(g1.getArena().getSquare(1,1));
        enemy.setSquare(g1.getArena().getSquare(2,1));


        MethodsWeapons.moveTarget(test,1,1);
        MethodsWeapons.moveTarget(enemy,2,1);

        // here we use the method to get the response for the use of tag back granade
        ResponseTagbackGranade response = new ResponseTagbackGranade(enemy.getColor());


        assertEquals(response.getTargetBasicMode(),enemy.getColor());

    }


}

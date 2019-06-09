import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.GameBoard;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.EletroSchyte;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.LockRifle;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.TractorBeam;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseRunAround;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseShootPeople;
import it.polimi.deib.se2018.adrenalina.communication_message.ResponseTargettingScope;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestPlayerActions
{
    private Player test;
    private GameBoard g1= new GameBoard(null,null,1,10,null,null);


    private EletroSchyte w0 = new EletroSchyte(Color.BLUE,0,true);

    private LockRifle w1 = new LockRifle(Color.BLUE,1,true);
    private TractorBeam w2 = new TractorBeam(Color.BLUE,2,true);


    @Before
    public void setup()
    {
        w0.setName("EletroSchyte");
        w1.setName("LockRifle");
        w2.setName("TractorBeam");
        test = new Player(ColorId.YELLOW,"Claudio","Stringa di prova",false);
    }
/*
    @Test
    public void testLookForShoot() throws SquareNotInGameBoard {

        g1.setAllPlayer(test);

        test.setSquare(g1.getArena().getSquare(1,1));



        MethodsWeapons.moveTarget(test,1,1);


        ResponseShootPeople response = new ResponseShootPeople(w0.getName());
assertTrue(response.getTargetBasicMode().equals(w0.getName()));

    }
*/
    @Test
    public void testRunAround() throws SquareNotInGameBoard {

        g1.setAllPlayer(test);

        test.setSquare(g1.getArena().getSquare(1,1));



        MethodsWeapons.moveTarget(test,1,1);


        ResponseRunAround response = new ResponseRunAround(1,3);

        assertEquals(response.getX(),1);
        assertEquals(response.getY(),3);
    }


}

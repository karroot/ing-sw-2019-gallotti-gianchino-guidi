/* import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.NegativeCounter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import it.polimi.deib.se2018.adrenalina.Model.GameBoard.*;


import java.util.Set;


//Test with the SkullCounter
public class TestSkullCounter
{

    private GameBoard test;
    private  Stack<WeaponCard> weaponCardStack;
    private Stack<PowerUpCard> powerUpCardStack;
    private List<Room> roomList;
    private Graph arena;
    private int skullCounter;
    private Stack<AmmoTiles> ammoTilesStack;

    @Before
    public void setUp()
    {
        test = new GameBoard(weaponCardStack,powerUpCardStack,roomList,arena,skullCounter,ammoTilesStack);



    }
    @After
    public void tearDown()
    {
        expectedOut.clear();
    }

    @Test
    public void InizializeSkull()
    {



        try
        {
            test.setSkullCounter(-3); //Test for launching exception
            fail();
        }
        catch (NegativeCounter e)
        {
            System.out.println("fine");
        }




    }




}
*/
import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.graph.Graph;
import it.polimi.deib.se2018.adrenalina.Model.power_up_cards.PowerUpCard;
import it.polimi.deib.se2018.adrenalina.Model.weapon_cards.WeaponCard;
import java.util.Stack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;



import java.util.Set;


//Test with the SkullCounter




public class TestGameboard
{

    private GameBoard test;
    private  Stack<WeaponCard> weaponCardStack;
    private Stack<PowerUpCard> powerUpCardStack;
    private int arena=1;
    private int skullCounter;
    private Stack<AmmoTiles> ammoTilesStack;

    @Before
    public void setUp()
    {
        test = new GameBoard(weaponCardStack,powerUpCardStack,arena,skullCounter,ammoTilesStack);



    }
    @After

    @Test
    public void InizializeSkull()
    {




    }




}

import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.graph.Graph;
import it.polimi.deib.se2018.adrenalina.Model.power_up_cards.PowerUpCard;
import it.polimi.deib.se2018.adrenalina.Model.power_up_cards.TargettingScope;
import it.polimi.deib.se2018.adrenalina.Model.power_up_cards.Teleporter;
import it.polimi.deib.se2018.adrenalina.Model.weapon_cards.Flamethrower;
import it.polimi.deib.se2018.adrenalina.Model.weapon_cards.Furnace;
import it.polimi.deib.se2018.adrenalina.Model.weapon_cards.WeaponCard;
import java.util.Stack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;



import java.util.Set;


//Test with the SkullCounter




public class TestGameboard {

    private GameBoard test;

    private int arena = 1;
    private int skullCounter;
    private Stack<AmmoTiles> ammoTilesStack;
    private ColorId Red;
    private Stack<PowerUpCard> powerUpCardStack = new Stack<>();
    private Teleporter teleport = new Teleporter(Color.RED,1);
    private TargettingScope scope= new TargettingScope(Color.BLUE,2);
    private Stack<WeaponCard> weaponCardStack = new Stack<>();
    private Flamethrower flamethrower = new Flamethrower(Color.RED, 2, true);
    private Furnace furnace = new Furnace(Color.RED, 3, true);



    @Before
    public void setUp() {



        powerUpCardStack.add(teleport);
        powerUpCardStack.add(scope);
        weaponCardStack.add(furnace);
        weaponCardStack.add(flamethrower);


        test = new GameBoard(weaponCardStack, powerUpCardStack, arena, skullCounter, ammoTilesStack);
        test.setPowerUpCardStack(powerUpCardStack);
        test.setWeaponCardStack(weaponCardStack);

    }

    @After

    @Test
    public void InizializeSkull() {
        try {
            test.setSkullCounter(-3);
        } catch (IllegalArgumentException e) {
            System.out.println("ok1");
        }
    }
    @Test
    public void InizializeKillShotTrack() {
        try {
            test.setKillShotTrack(Red, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("ok2");
        }
    }
    @Test
    public void InizializePowerUpStack() {
        try
        {
            for (int i=0; i < 3 ; i++)
            {
                test.drawPowerUpCard();
            }

        } catch (NullPointerException e) {
            System.out.println("ok3");
        }
    }
@Test
    public void InizializeWeaponStack() {
        try
        {
            for (int i=0; i < 3 ; i++)
            {
                test.drawWeaponCard();
            }

        } catch (NullPointerException e) {
            System.out.println("ok4");
        }
    }
}
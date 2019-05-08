import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.PowerUpCard;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.TargettingScope;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.Teleporter;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Flamethrower;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Furnace;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


//Test with the SkullCounter




public class TestGameboard {

    private GameBoard test;

    private int arena = 1;
    private int skullCounter;
    private Stack<AmmoTiles> ammoTilesStack;


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


    @Test
    public void InizializeSkull() {


            test.setSkullCounter(5);

            assertNotEquals(test.getSkullCounter(),2);
        assertEquals(test.getSkullCounter(),5);
        try
        {
            test.setSkullCounter(-3);
            fail();
        } catch (IllegalArgumentException e) {
            System.out.println("SkullTestOK");
        }

    }
    @Test
    public void InizializeKillShotTrack() {

        test.setKillShotTrack(ColorId.YELLOW, 2);
        test.setKillShotTrack(ColorId.GREY, 1);
        assertEquals(test.getKillShotPlayer(test.getKillShotTrack(0)),ColorId.YELLOW );
        assertEquals(test.getKillShotPointCounter(test.getKillShotTrack(0)),2 );

        assertEquals(test.getKillShotPlayer(test.getKillShotTrack(1)),ColorId.GREY );
        assertEquals(test.getKillShotPointCounter(test.getKillShotTrack(1)),1 );

        assertNotEquals(test.getKillShotPlayer(test.getKillShotTrack(1)),ColorId.PURPLE );
        assertNotEquals(test.getKillShotPointCounter(test.getKillShotTrack(0)),1 );

        try {
            test.setKillShotTrack(ColorId.GREEN, 0);
            fail();
        } catch (IllegalArgumentException e) {
            System.out.println("KillShotTrackOK");
        }

    }
    @Test
    public void InizializePowerUpStack() {

            for (int i=0; i < 1 ; i++)
            {
                test.drawPowerUpCard();
            }
        assertFalse(test.getPowerUpCardStack().isEmpty());
            test.drawPowerUpCard();
        assertTrue(test.getPowerUpCardStack().isEmpty());
        try
        {   test.drawPowerUpCard();
            fail();
        } catch (NullPointerException e) {
            System.out.println("PowerUpStackOK");
        }
    }
@Test
    public void InizializeWeaponStack() {

            for (int i=0; i < 1 ; i++)
            {
                test.drawWeaponCard();
            }
            assertFalse(test.getWeaponCardStack().isEmpty());
            test.drawWeaponCard();
            assertTrue(test.getWeaponCardStack().isEmpty());
            try
            { test.drawWeaponCard();
            fail();
        } catch (NullPointerException e) {
            System.out.println("WeaponStackOK");
        }
    }
}
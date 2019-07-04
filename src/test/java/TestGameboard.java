import it.polimi.deib.se2018.adrenalina.Model.AmmoTiles;
import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.GameBoard;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.PowerUpCard;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.TargettingScope;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.Teleporter;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Flamethrower;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Furnace;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.RocketLauncher;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

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
        //here we setup the elements of the gameboard


        powerUpCardStack.add(teleport);
        powerUpCardStack.add(scope);
        weaponCardStack.add(furnace);
        weaponCardStack.add(flamethrower);


        test = new GameBoard(weaponCardStack, powerUpCardStack, arena, skullCounter, ammoTilesStack);
        test.setPowerUpCardStack(powerUpCardStack);
        test.setWeaponCardStack(weaponCardStack);

    }


    @Test
    public void InizializeSkull() { //here we setup the elements of the skull counter


            test.setSkullCounter(5);

            assertNotEquals(test.getSkullCounter(),2);
        assertEquals(test.getSkullCounter(),5);
        try
        {
            test.setSkullCounter(-3);
            fail();
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }

    }
    @Test
    public void InizializeKillShotTrack() { //here we setup the elements of the kill shot track

        test.setKillShotTrack(ColorId.YELLOW, 2); // this method add a track for player yellow with 2 point counter
        test.setKillShotTrack(ColorId.GREY, 1);
        assertEquals(test.getKillShotPlayer(test.getKillShotTrack(0)),ColorId.YELLOW );
        assertEquals(test.getKillShotPointCounter(test.getKillShotTrack(0)),2 );

        assertEquals(test.getKillShotPlayer(test.getKillShotTrack(1)),ColorId.GREY );
        assertEquals(test.getKillShotPointCounter(test.getKillShotTrack(1)),1 );

        assertNotEquals(test.getKillShotPlayer(test.getKillShotTrack(1)),ColorId.PURPLE );
        assertNotEquals(test.getKillShotPointCounter(test.getKillShotTrack(0)),1 );

        try {
            test.setKillShotTrack(ColorId.GREEN, 0); // here we try to add a track  with 0 point counter and must throw an exception
            fail();
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }

    }

@Test
    public void InizializeWeaponStack() { //here we setup the weapon stack

            for (int i=0; i < 1 ; i++)
            {
                test.drawWeaponCard();
            }
            assertFalse(test.getWeaponCardStack().isEmpty());
            test.drawWeaponCard();
            assertTrue(test.getWeaponCardStack().isEmpty());
            try
            { test.drawWeaponCard(); // if we try to draw a card when the weapon stack is empty it must throw an exception
            fail();
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }

    @Test
    public void weaponCardTest ()
    {
        //here we control if weapon card is created in a proper way
        WeaponCard weaponCard = new RocketLauncher(Color.RED,211, true);

        assertEquals("Lanciarazzi", weaponCard.getName());
        assertEquals(211, weaponCard.getWeaponID());
        assertEquals(2, weaponCard.getRedAmmoCost());
        assertEquals(0, weaponCard.getBlueAmmoCost());
        assertEquals(0, weaponCard.getYellowAmmoCost());
        assertEquals(Color.RED, weaponCard.getColor());
    }
}
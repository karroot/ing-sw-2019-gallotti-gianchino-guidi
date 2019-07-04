import it.polimi.deib.se2018.adrenalina.Controller.Controller;
import it.polimi.deib.se2018.adrenalina.Controller.Setup;
import it.polimi.deib.se2018.adrenalina.Model.AmmoTiles;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.PowerUpCard;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.assertEquals;

public class SetupTest {

    Controller controller;
    Setup setup = new Setup(controller);

    @Before
    public void setUp() throws Exception
    {

    }

    @Test
    public void spawn() {
    }

    @Test
    public void resetPlayer() {
    }

    @Test
    public void initialize() {
    }

    @Test
    public void createWeaponCardStack()
    {
        Stack<WeaponCard> weaponCardStack = new Stack<>();

        weaponCardStack = setup.createWeaponCardStack();

        assertEquals(21, weaponCardStack.size());
    }

    @Test
    public void createPowerUpStack()
    {
        Stack<PowerUpCard> powerUpCardStack = new Stack<>();

        powerUpCardStack = setup.createPowerUpStack();

        assertEquals(24, powerUpCardStack.size());
    }

    @Test
    public void createAmmoTilesStack()
    {
        Stack<AmmoTiles> ammoTilesStack = new Stack<>();

        ammoTilesStack = setup.createAmmoTilesStack();

        assertEquals(36, ammoTilesStack.size());

    }

    @Test
    public void createPlayers()
    {

    }

    @Test
    public void replenishBoard() {
    }
}
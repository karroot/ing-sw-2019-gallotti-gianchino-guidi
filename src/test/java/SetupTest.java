import it.polimi.deib.se2018.adrenalina.Controller.Controller;
import it.polimi.deib.se2018.adrenalina.Controller.Setup;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

public class SetupTest {

    Controller controller;
    Setup setup = new Setup(controller);

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
    public void createPowerUpStack() {
    }

    @Test
    public void createAmmoTilesStack() {
    }

    @Test
    public void createPlayers() {
    }

    @Test
    public void replenishBoard() {
    }
}
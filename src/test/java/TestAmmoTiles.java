import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.PowerUpCard;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.Teleporter;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class is a case test for the AmmoTile.
 *
 * @author giovanni
 */

public class TestAmmoTiles
{
    JustAmmo justAmmo1;
    JustAmmo justAmmo2;
    JustAmmo justAmmo3;

    PowerAndAmmo powerAndAmmo1;
    PowerAndAmmo powerAndAmmo2;
    PowerAndAmmo powerAndAmmo3;

    PowerUpCard teleporter;

    Player player;


    //da testare: creazione +  public void useAmmoTilesCards(Player player)  in entrambi i casi


    @Before
    public void setUp()
    {
        justAmmo1 = new JustAmmo(1, Color.RED, Color.YELLOW);
        justAmmo2 = new JustAmmo(2, Color.BLUE, Color.YELLOW);
        justAmmo3 = new JustAmmo(3, Color.RED, Color.BLUE);

        teleporter = new Teleporter(Color.BLUE, 1);

        powerAndAmmo1 = new PowerAndAmmo(4, Color.RED, Color.YELLOW);
        powerAndAmmo2 = new PowerAndAmmo(5, Color.BLUE, Color.YELLOW);
        powerAndAmmo3 = new PowerAndAmmo(6, Color.RED, Color.BLUE);

        player = new Player(ColorId.GREY, null, null , true);

        player.setAmmoBlue(0);
        player.setAmmoYellow(0);
        player.setAmmoRed(0);


    }



    @Test
    public void testUseJustAmmo ()
    {
        justAmmo1.useAmmoTilesCards(player);

        assertEquals(1, player.getAmmoRed());
        assertEquals(2, player.getAmmoYellow());

        justAmmo2.useAmmoTilesCards(player);

        assertEquals(1, player.getAmmoBlue());
        assertEquals(2, player.getAmmoYellow());

        justAmmo3.useAmmoTilesCards(player);

        assertEquals(2, player.getAmmoYellow());
        assertEquals(3, player.getAmmoBlue());
        assertEquals(2, player.getAmmoRed());

    }

    /*
    @Test
    public void testUsePowerAndAmmo ()
    {
        powerAndAmmo1.useAmmoTilesCards(player);

        assertEquals(teleporter, player.getPowerupCardList().get(0));
        assertEquals(1, player.getAmmoRed());
        assertEquals(1, player.getAmmoYellow());

        powerAndAmmo2.useAmmoTilesCards(player);
        powerAndAmmo3.useAmmoTilesCards(player);
        powerAndAmmo1.useAmmoTilesCards(player);

        assertEquals(3, player.getPowerupCardList().size());
        assertEquals(3, player.getAmmoYellow());
        assertEquals(3, player.getAmmoRed());
        assertEquals(2, player.getAmmoBlue());
    }
*/
}


import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.GameBoard;
import it.polimi.deib.se2018.adrenalina.Model.Player;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.Teleporter;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Sledgehammer;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.ZX2;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.UpdateModel;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Stack;

public class TestUpdateModel
{
    GameBoard board = new GameBoard(new Stack<>(),new Stack<>(),4,5,new Stack<>());
    Player player1;
    Player player2;
    Player player3;
    ZX2 zx2 = new ZX2(Color.YELLOW,1,true);
    Sledgehammer sledgehammer = new Sledgehammer(Color.YELLOW,2,true);
    Teleporter tele1 = new Teleporter(Color.YELLOW,1);
    Teleporter tele2 = new Teleporter(Color.YELLOW,2);

    @Before
    public void setUp() throws Exception
    {
        player1 = new Player(ColorId.YELLOW,"a","a",true);
        player2 = new Player(ColorId.BLUE,"b","b",false);
        player3 = new Player(ColorId.GREY,"c","c",false);

        player1.addWeapon(zx2);
        player1.addWeapon(sledgehammer);
        player1.addPowerUp(tele1);
        player1.addPowerUp(tele2);

        board.setAllPlayer(player1);
        board.setAllPlayer(player2);
        board.setAllPlayer(player3);
    }


    /*
        Test if the creation of the copy Immutable of the model works and all the data of the gameboard are taken correctly
     */
    @Test
    public void CreateObjectUpdateModel()
    {
        UpdateModel updateModel = new UpdateModel(board);

        assertEquals(board.getCode(),updateModel.getDataOfBoard().getCode());
        assertEquals(board.getSkullCounter(),updateModel.getDataOfBoard().getSkullCounter());
        assertEquals(board.getKillShotTrack().hashCode(),updateModel.getDataOfBoard().getKillShotTrack().hashCode());

        assertEquals(player1.getColor(),updateModel.getDataOfAllPlayer().get(0).getColor());
        assertEquals(player1.getName(),updateModel.getDataOfAllPlayer().get(0).getName());
        assertEquals(player1.getAmmoBlue(),updateModel.getDataOfAllPlayer().get(0).getAmmoBlue());
        assertEquals(player1.getAmmoYellow(),updateModel.getDataOfAllPlayer().get(0).getAmmoYellow());
        assertEquals(player1.getAmmoRed(),updateModel.getDataOfAllPlayer().get(0).getAmmoRed());

        assertEquals(player1.getWeaponCardList().get(0).getName(),updateModel.getDataOfAllPlayer().get(0).getWeaponCardList().get(0).getName());
        assertEquals(player1.getWeaponCardList().get(1).getName(),updateModel.getDataOfAllPlayer().get(0).getWeaponCardList().get(1).getName());

        assertEquals(player1.getPowerupCardList().get(0).getName(),updateModel.getDataOfAllPlayer().get(0).getPowerupCardList().get(0).getName());
        assertEquals(player1.getPowerupCardList().get(1).getName(),updateModel.getDataOfAllPlayer().get(0).getPowerupCardList().get(1).getName());




        assertEquals(player2.getColor(),updateModel.getDataOfAllPlayer().get(1).getColor());
        assertEquals(player3.getColor(),updateModel.getDataOfAllPlayer().get(2).getColor());
    }
}

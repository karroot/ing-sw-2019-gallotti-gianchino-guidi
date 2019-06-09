import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.PowerUpCard;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.Teleporter;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

public class TeleporterTest {

    private GameBoard board;
    private Player player;
    private PowerUpCard upCard;
    private Square destinationTeleport;
    
    @Before
    public void setUp() throws Exception 
    {
        board = new GameBoard(new Stack<>(),new Stack<>(),4,8,new Stack<>());
        player = new Player(ColorId.BLUE,"marco","a",true);
        upCard = new Teleporter(Color.YELLOW,1);
        player.setSquare(board.getArena().getSquare(1,1));
        destinationTeleport = board.getArena().getSquare(2,3);

    }


    @Test
    public void usePowerUpTest() throws SquareNotInGameBoard
    {
        //The player receive the powerUp

        player.addPowerUp(upCard);//The card is taken by the player
        upCard.setPlayer(player);

        assertEquals(player,upCard.getPlayer());//The card belongs at the player now

        Teleporter c = (Teleporter) player.usePowerUp(0);

        c.usePowerUp(2,3); //Player use the powerUp card

        //Check the power up cards of the player
        assertEquals(0,player.getPowerupCardList().size());
        //Check the square
        assertEquals(destinationTeleport, player.getSquare());
        assertTrue(destinationTeleport.getPlayerList().contains(player));
        assertFalse(board.getArena().getSquare(1,1).getPlayerList().contains(player));



        //check Room
        assertTrue(destinationTeleport.getRoom().getPlayerRoomList().contains(player));
        assertFalse(board.getArena().getSquare(1,1).getRoom().getPlayerRoomList().contains(player));


    }
}
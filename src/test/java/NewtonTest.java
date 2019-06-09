import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.Newton;
import it.polimi.deib.se2018.adrenalina.Model.card.power_up_cards.PowerUpCard;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class NewtonTest {

    private GameBoard board;
    private Player player;
    private Player playerTargetted;
    private PowerUpCard upCard;
    private Square destinationTeleport;

    @Before
    public void setUp() throws Exception
    {
        board = new GameBoard(new Stack<>(),new Stack<>(),4,8,new Stack<>());
        player = new Player(ColorId.BLUE,"marco","a",true);
        playerTargetted = new Player(ColorId.YELLOW,"alice","a",true);
        upCard = new Newton(Color.YELLOW,1);

        player.setSquare(board.getArena().getSquare(1,1));
        player.getSquare().addPlayer(player);
        player.getSquare().getRoom().updatePlayerRoomList();
        player.addPowerUp(upCard);

        playerTargetted.setSquare(board.getArena().getSquare(3,1));
        playerTargetted.getSquare().addPlayer(playerTargetted);
        playerTargetted.getSquare().getRoom().updatePlayerRoomList();

        board.setAllPlayer(player);
        board.setAllPlayer(playerTargetted);

        upCard.setPlayer(player);
        destinationTeleport = board.getArena().getSquare(3,3);
    }

    @Test
    public void usePowerUp1() throws SquareNotInGameBoard
    {
        Set<Square> effout = new HashSet<>();
        effout.add(board.getArena().getSquare(1,1));
        effout.add(board.getArena().getSquare(2,1));
        effout.add(board.getArena().getSquare(4,1));
        effout.add(board.getArena().getSquare(3,2));
        effout.add(board.getArena().getSquare(3,3));

        Newton c = (Newton) upCard;

        Map<ColorId, List<String>> out = c.checkMoveTarget(board);

        System.out.println(out);

        assertTrue(out.get(ColorId.YELLOW).containsAll(effout.stream().map(Square::toStringCoordinates).collect(Collectors.toSet())) && out.get(ColorId.YELLOW).size() == effout.size());
    }

    @Test
    public void usePowerUp2() throws SquareNotInGameBoard
    {
        playerTargetted.setSquare(board.getArena().getSquare(2,3));
        playerTargetted.getSquare().addPlayer(playerTargetted);
        playerTargetted.getSquare().getRoom().updatePlayerRoomList();

        Set<Square> effout = new HashSet<>();
        effout.add(board.getArena().getSquare(1,3));
        effout.add(board.getArena().getSquare(3,3));
        effout.add(board.getArena().getSquare(4,3));
        effout.add(board.getArena().getSquare(2,2));
        effout.add(board.getArena().getSquare(2,1));

        Newton c = (Newton) upCard;

        Map<ColorId, List<String>> out = c.checkMoveTarget(board);

        System.out.println(out);

        assertTrue(out.get(ColorId.YELLOW).containsAll(effout.stream().map(Square::toStringCoordinates).collect(Collectors.toSet())) && out.get(ColorId.YELLOW).size() == effout.size());
    }

    /*
    @Test
    public void checkMoveTarget() throws SquareNotInGameBoard
    {
        Newton c = (Newton) player.usePowerUp(0);

        c.usePowerUp(playerTargetted,3,3);

        //Check the power up cards of the player
        assertEquals(0,player.getPowerupCardList().size());
        //Check the square
        assertEquals(destinationTeleport, playerTargetted.getSquare());
        assertTrue(destinationTeleport.getPlayerList().contains(playerTargetted));
        assertFalse(board.getArena().getSquare(3,1).getPlayerList().contains(player));



        //check Room
        assertTrue(destinationTeleport.getRoom().getPlayerRoomList().contains(playerTargetted));
        assertFalse(board.getArena().getSquare(3,1).getRoom().getPlayerRoomList().contains(player));
    }
    */
}
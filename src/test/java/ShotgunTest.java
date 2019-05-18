import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.MethodsWeapons;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.ZX2;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

public class ShotgunTest {

    GameBoard board = new GameBoard(new Stack<>(),new Stack<>(),4,8,new Stack<>());
    Player p1 = new Player(ColorId.YELLOW,"caso","ciao",true);;
    Player p2 = new Player(ColorId.GREY,"caso","ciao",false);;
    Player p3 = new Player(ColorId.GREEN,"caso","ciao",false);;
    Player p4 = new Player(ColorId.PURPLE,"caso","ciao",false);;
    Player p5 = new Player(ColorId.BLUE,"caso","ciao",false);
    ZX2 zx2 = new ZX2(Color.BLUE,0,true);
    Square start;

    @Before
    public void setUp() throws Exception
    {
        start = board.getArena().getSquare(1,1);
        p1.setSquare(start);
        p2.setSquare(start);
        p3.setSquare(start);
        p4.setSquare(start);
        p5.setSquare(start);


        MethodsWeapons.moveTarget(p1,3,3);
        MethodsWeapons.moveTarget(p2,2,1);
        MethodsWeapons.moveTarget(p3,1,2);
        MethodsWeapons.moveTarget(p4,2,3);
        MethodsWeapons.moveTarget(p5,4,1);
    }

    @Test
    public void checkAvaliableMode()
    {

    }

    @Test
    public void checkBasicMode() {
    }

    @Test
    public void checkMoveBasicMode() {
    }

    @Test
    public void basicMode() {
    }

    @Test
    public void checkInLongBarrelMode() {
    }

    @Test
    public void inLongBarrelMode() {
    }
}
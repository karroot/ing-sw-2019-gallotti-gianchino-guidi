import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.communication_message.*;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.*;


public class TestResponseMessageYellowWeapons {

    List<ColorId> listOfPlayer = new LinkedList<>();
    ColorId player1;
    ColorId player2;

    @Before
    public void setUp() throws Exception
    {
        player1 = ColorId.BLUE;
        player2 = ColorId.GREEN;
        listOfPlayer.add(player1);
        listOfPlayer.add(player2);
    }

    @Test
    public void testResponseMessageZX2()
    {
        ResponseZX2 temp = new ResponseZX2(player1);

        assertFalse(temp.isMode());
        assertEquals(player1,temp.getTargetBasicMode());
        assertEquals(null,temp.getTargetsAlternativeMode());

        temp = new ResponseZX2(listOfPlayer);

        assertTrue(temp.isMode());
        assertEquals(null,temp.getTargetBasicMode());
        assertEquals(listOfPlayer,temp.getTargetsAlternativeMode());
    }

    @Test
    public void testResponseMessageShotgun()
    {
        ResponseShotgun temp = new ResponseShotgun(player1,true,true,1,2);

        assertEquals(player1,temp.getTarget());
        assertTrue(temp.isMode());
        assertTrue(temp.isMove());
        assertEquals(1,temp.getX());
        assertEquals(2,temp.getY());
    }

    @Test
    public void testResponseMessagePowerGlove()
    {
        ResponsePowerGlove temp = new ResponsePowerGlove(player1);

        assertFalse(temp.isMode());
        assertEquals(player1,temp.getTargetBasicMode());
        assertEquals(null,temp.getTargetsAlternativeMode());

        temp = new ResponsePowerGlove(listOfPlayer);

        assertTrue(temp.isMode());
        assertEquals(null,temp.getTargetBasicMode());
        assertEquals(listOfPlayer,temp.getTargetsAlternativeMode());
    }

    @Test
    public void testResponseMessageRailgun()
    {
        ResponseRailgun temp = new ResponseRailgun(player1);

        assertFalse(temp.isMode());
        assertEquals(player1,temp.getTarget1());
        assertEquals(null,temp.getTarget2());

        temp = new ResponseRailgun(player1,player2);

        assertTrue(temp.isMode());
        assertEquals(player1,temp.getTarget1());
        assertEquals(player2,temp.getTarget2());
    }

    @Test
    public void testResponseMessageShockwave()
    {
        ResponseShockwave temp = new ResponseShockwave(listOfPlayer);

        assertEquals(listOfPlayer,temp.getTargetsBasicMode());
        assertFalse(temp.isMode());

        temp = new ResponseShockwave();

        assertEquals(null,temp.getTargetsBasicMode());
        assertTrue(temp.isMode());
    }

    @Test
    public void testResponseMessageCyberblade()
    {
        String[] ordEff = new String[1];

        ResponseCyberblade temp = new ResponseCyberblade(player1,player2,1,2,ordEff);

        assertEquals(player1,temp.getTargetBasicEffect());
        assertEquals(player2,temp.getTargetForSliceEffect());
        assertEquals(1,temp.getX());
        assertEquals(2,temp.getY());
        assertTrue(ordEff.equals(temp.getOrderEffect()));
    }

    @Test
    public void testResponseMessageSledgehammer()
    {
        ResponseSledgehammer temp = new ResponseSledgehammer(player1);

        assertEquals(player1,temp.getTarget());
        assertEquals(0,temp.getX());
        assertEquals(0,temp.getY());
        assertEquals(false,temp.isMode());
        assertEquals(false,temp.isMove());

        temp = new ResponseSledgehammer(player1,1,2,true);

        assertEquals(player1,temp.getTarget());
        assertEquals(1,temp.getX());
        assertEquals(2,temp.getY());
        assertEquals(true,temp.isMode());
        assertEquals(true,temp.isMove());

    }

    @Test
    public void testResponseMessageTeleporter()
    {
        ResponseTeleporter temp = new ResponseTeleporter(1,2);

        assertEquals(1,temp.getX());
        assertEquals(2,temp.getY());
    }

    @Test
    public void testResponseMessageNewton()
    {
        ResponseNewton temp = new ResponseNewton(1,2,player1);

        assertEquals(1,temp.getX());
        assertEquals(2,temp.getY());
        assertEquals(player1,temp.getTarget());
    }
}

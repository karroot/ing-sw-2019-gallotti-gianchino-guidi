import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Cyberblade;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Flamethrower;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.Furnace;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.WeaponCard;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class is a case test for the SpawnPoint.
 *
 * @author giovanni
 */


public class TestSpawnPoint
{
    SpawnPoint spawnPointTest;
    GameBoard gameboard = new GameBoard(null,null,1,0,null);
    private Cyberblade cyberblade = new Cyberblade(Color.RED, 1, true);
    private Flamethrower flamethrower = new Flamethrower(Color.RED, 2, true);
    private Furnace furnace = new Furnace(Color.RED, 3, true);
    private Stack<WeaponCard> weaponCardStack = new Stack<>();
    private List<WeaponCard> weaponCardList;
    @Before
    public void setup ()
    {
        weaponCardList = new LinkedList<>();

        SideType[] sideType = new SideType[4];
        sideType[0] = SideType.OPEN;
        sideType[1] = SideType.WALL;
        sideType[2] = SideType.DOOR;
        sideType[3] = SideType.LIMIT;

        spawnPointTest = new SpawnPoint(1,2, gameboard, ColorRoom.RED,sideType);

    }

    @Test
    public void testCreation ()
    {
        List<Room> roomList = new LinkedList<>();
        roomList = gameboard.getRoomList();
        Room room;
        Square square = null;
        int x = 1;
        int y = 2;

        for (Room roomIterate:gameboard.getRoomList())
        {
            for (Square squareIterate: roomIterate.getSquareList())
            {
                if (squareIterate.getX() == x && squareIterate.getY() == y)
                {
                    square=squareIterate;
                }

            }
        }

        assertEquals(square.getX(), spawnPointTest.getX());
        assertEquals(square.getY(), spawnPointTest.getY());
        assertEquals(square.getColor(), spawnPointTest.getColor());
        assertEquals(square.getSide(), spawnPointTest.getSide());
    }

    @Test
    public void testDrawWeapon ()
    {
        //carta tolta + carta restituita effettivamente quella

        WeaponCard weaponCard;


        weaponCardList.add(cyberblade);
        weaponCardList.add(flamethrower);
        weaponCardList.add(furnace);


        spawnPointTest.setWeaponCardList(weaponCardList);

        weaponCard = spawnPointTest.drawWeapon(cyberblade);

        assertEquals(false, spawnPointTest.getWeaponCardList().contains(cyberblade));
        assertEquals(weaponCard, cyberblade);

    }


    @Test
    public void testSwapWeapon ()
    {

        WeaponCard weaponCard;
        weaponCardList.add(flamethrower);
        weaponCardList.add(furnace);

        spawnPointTest.setWeaponCardList(weaponCardList);




        assertEquals(false, spawnPointTest.getWeaponCardList().contains(cyberblade));
        assertTrue(spawnPointTest.getWeaponCardList().contains(flamethrower));

        weaponCard = spawnPointTest.swapWeapon(flamethrower,cyberblade);

        assertEquals(weaponCard, flamethrower);
        assertEquals(false, spawnPointTest.getWeaponCardList().contains(flamethrower));
        assertTrue(spawnPointTest.getWeaponCardList().contains(cyberblade));

    }

    @Test
    public void testAddNewWeapon ()
    {
        weaponCardStack.add(cyberblade);
        weaponCardStack.add(flamethrower);

        gameboard.setWeaponCardStack(weaponCardStack);

        WeaponCard weaponCard1;

        weaponCard1 = gameboard.getWeaponCardStack().peek();

        spawnPointTest.addNewWeapon();

        assertTrue(spawnPointTest.getWeaponCardList().contains(weaponCard1));

    }

}

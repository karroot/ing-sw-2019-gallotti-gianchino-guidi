import it.polimi.deib.se2018.adrenalina.Model.*;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import it.polimi.deib.se2018.adrenalina.Model.graph.*;

import java.util.LinkedHashSet;
import java.util.Set;


//Test with the gameBoard that it is in the second and third page of the manual
public class TestGraphGameboard1
{

    private Graph test;
    private Square n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11;
    private Set<Square> expectedOut;

    @Before
    public void setUp()
    {
        test = new Graph();//Create an empty graph

        //Create all square
        n1 = new AmmoPoint(1,1,null, ColorRoom.WHITE ,null);
        n2 = new AmmoPoint(2,1,null, ColorRoom.WHITE,null);
        n3 = new AmmoPoint(3,1,null, ColorRoom.WHITE,null);
        n4 = new SpawnPoint(4,1,null, ColorRoom.YELLOW,null);
        n5 = new SpawnPoint(1,2,null, ColorRoom.RED,null);
        n6 = new AmmoPoint(2,2,null, ColorRoom.PURPLE,null);
        n7 = new AmmoPoint(3,2,null, ColorRoom.PURPLE,null);
        n8 = new AmmoPoint(4,2,null, ColorRoom.YELLOW,null);
        n9 = new AmmoPoint(1,3,null, ColorRoom.RED,null);
        n10 = new AmmoPoint(2,3,null, ColorRoom.BLUE,null);
        n11 = new SpawnPoint(3,3,null, ColorRoom.BLUE,null);

        //Create a set of square empty
        expectedOut = new LinkedHashSet<>();

        //Tutta questa roba verrà fatta dal factory


    }
    @After
    public void tearDown()
    {
        expectedOut.clear();
    }

    @Test
    public void createGraph()
    {
        test.addNode(n1);
        test.addNode(n2);
        test.addNode(n3);
        test.addNode(n4);
        test.addNode(n5);
        test.addNode(n6);
        test.addNode(n7);
        test.addNode(n8);
        test.addNode(n9);
        test.addNode(n10);
        test.addNode(n11);

        test.addEdge(n1,n5,SideType.DOOR);
        test.addEdge(n1,n2,SideType.OPEN);

        test.addEdge(n2,n3,SideType.OPEN);
        test.addEdge(n2,n6,SideType.DOOR);

        test.addEdge(n3,n4,SideType.DOOR);
        test.addEdge(n3,n7,SideType.WALL);

        test.addEdge(n4,n8,SideType.OPEN);

        test.addEdge(n5,n6,SideType.WALL);
        test.addEdge(n5,n9,SideType.OPEN);

        test.addEdge(n6,n10,SideType.DOOR);
        test.addEdge(n6,n7,SideType.OPEN);

        test.addEdge(n7,n11,SideType.DOOR);
        test.addEdge(n7,n8,SideType.DOOR);

        test.addEdge(n9,n10,SideType.DOOR);

        test.addEdge(n10,n11,SideType.OPEN);

        //Tutta questa roba verrà fatta dalla factory

        try
        {
            test.getSquare(5,6); //Test for launching exception
            fail();
        }
        catch (SquareNotInGameBoard e)
        {
            System.out.println("ok");
        }

        Set<Square> t = test.squareReachableNoWall(5,6,3); //Test for coordinates wrong in method squareReachableNoWall

        assertTrue(t.isEmpty()); //t must be empty


    }

    @Test
    public void squareReachableL2()
    {
        test.addNode(n1);
        test.addNode(n2);
        test.addNode(n3);
        test.addNode(n4);
        test.addNode(n5);
        test.addNode(n6);
        test.addNode(n7);
        test.addNode(n8);
        test.addNode(n9);
        test.addNode(n10);
        test.addNode(n11);

        test.addEdge(n1,n5,SideType.DOOR);
        test.addEdge(n1,n2,SideType.OPEN);

        test.addEdge(n2,n3,SideType.OPEN);
        test.addEdge(n2,n6,SideType.DOOR);

        test.addEdge(n3,n4,SideType.DOOR);
        test.addEdge(n3,n7,SideType.WALL);

        test.addEdge(n4,n8,SideType.OPEN);

        test.addEdge(n5,n6,SideType.WALL);
        test.addEdge(n5,n9,SideType.OPEN);

        test.addEdge(n6,n10,SideType.DOOR);
        test.addEdge(n6,n7,SideType.OPEN);

        test.addEdge(n7,n11,SideType.DOOR);
        test.addEdge(n7,n8,SideType.DOOR);

        test.addEdge(n9,n10,SideType.DOOR);

        test.addEdge(n10,n11,SideType.OPEN);

        expectedOut.add(n1);
        expectedOut.add(n2);
        expectedOut.add(n3);
        expectedOut.add(n5);
        expectedOut.add(n6);
        expectedOut.add(n9);

        Set<Square> d = test.squareReachableNoWall(1,1,2);
        System.out.println(d);
        assertEquals(d.hashCode(),expectedOut.hashCode());
    }


    @Test
    public void squareReachableL3()
    {
        test.addNode(n1);
        test.addNode(n2);
        test.addNode(n3);
        test.addNode(n4);
        test.addNode(n5);
        test.addNode(n6);
        test.addNode(n7);
        test.addNode(n8);
        test.addNode(n9);
        test.addNode(n10);
        test.addNode(n11);

        test.addEdge(n1,n5,SideType.DOOR);
        test.addEdge(n1,n2,SideType.OPEN);

        test.addEdge(n2,n3,SideType.OPEN);
        test.addEdge(n2,n6,SideType.DOOR);

        test.addEdge(n3,n4,SideType.DOOR);
        test.addEdge(n3,n7,SideType.WALL);

        test.addEdge(n4,n8,SideType.OPEN);

        test.addEdge(n5,n6,SideType.WALL);
        test.addEdge(n5,n9,SideType.OPEN);

        test.addEdge(n6,n10,SideType.DOOR);
        test.addEdge(n6,n7,SideType.OPEN);

        test.addEdge(n7,n11,SideType.DOOR);
        test.addEdge(n7,n8,SideType.DOOR);

        test.addEdge(n9,n10,SideType.DOOR);

        test.addEdge(n10,n11,SideType.OPEN);

        expectedOut.add(n1);
        expectedOut.add(n2);
        expectedOut.add(n3);
        expectedOut.add(n4);
        expectedOut.add(n5);
        expectedOut.add(n6);
        expectedOut.add(n7);
        expectedOut.add(n8);
        expectedOut.add(n9);
        expectedOut.add(n10);
        expectedOut.add(n11);


            Set<Square> d = test.squareReachableNoWall(2,2,3);
            System.out.println(d);
            assertEquals(d.hashCode(),expectedOut.hashCode());



    }

    @Test
    public void squareReachableL3_1()
    {
        test.addNode(n1);
        test.addNode(n2);
        test.addNode(n3);
        test.addNode(n4);
        test.addNode(n5);
        test.addNode(n6);
        test.addNode(n7);
        test.addNode(n8);
        test.addNode(n9);
        test.addNode(n10);
        test.addNode(n11);

        test.addEdge(n1,n5,SideType.DOOR);
        test.addEdge(n1,n2,SideType.OPEN);

        test.addEdge(n2,n3,SideType.OPEN);
        test.addEdge(n2,n6,SideType.DOOR);

        test.addEdge(n3,n4,SideType.DOOR);
        test.addEdge(n3,n7,SideType.WALL);

        test.addEdge(n4,n8,SideType.OPEN);

        test.addEdge(n5,n6,SideType.WALL);
        test.addEdge(n5,n9,SideType.OPEN);

        test.addEdge(n6,n10,SideType.DOOR);
        test.addEdge(n6,n7,SideType.OPEN);

        test.addEdge(n7,n11,SideType.DOOR);
        test.addEdge(n7,n8,SideType.DOOR);

        test.addEdge(n9,n10,SideType.DOOR);

        test.addEdge(n10,n11,SideType.OPEN);


        expectedOut.add(n2);
        expectedOut.add(n3);
        expectedOut.add(n4);
        expectedOut.add(n6);
        expectedOut.add(n7);
        expectedOut.add(n8);
        expectedOut.add(n10);
        expectedOut.add(n11);


        Set<Square> d = test.squareReachableNoWall(4,2,3);
        System.out.println(d);
        assertEquals(d.hashCode(),expectedOut.hashCode());

        expectedOut.clear();


        expectedOut.add(n7);

        expectedOut.add(n10);
        expectedOut.add(n11);


        d = test.squareReachableNoWall(3,3,1);
        System.out.println(d);
        assertEquals(d.hashCode(),expectedOut.hashCode());

    }



}

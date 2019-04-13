import it.polimi.deib.se2018.adrenalina.Model.AmmoPoint;
import it.polimi.deib.se2018.adrenalina.Model.ColorRoom;
import it.polimi.deib.se2018.adrenalina.Model.SpawnPoint;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.Model.graph.Graph;
import it.polimi.deib.se2018.adrenalina.Model.graph.TypeSide;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.*;

//Test with the first game board that it is in the second page of the manual
public class TestGraphGameBoard2
{
    private Graph test;
    private Square n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12;
    private Set<Square> expectedOut;

    @Before
    public void setUp()
    {
        test = new Graph();//Create an empty graph

        //Create all square
        n2 = new AmmoPoint(2,1,null, ColorRoom.WHITE,null);
        n3 = new AmmoPoint(3,1,null, ColorRoom.WHITE,null);
        n4 = new SpawnPoint(4,1,null, ColorRoom.YELLOW,null);
        n5 = new SpawnPoint(1,2,null, ColorRoom.RED,null);
        n6 = new AmmoPoint(2,2,null, ColorRoom.RED,null);
        n7 = new AmmoPoint(3,2,null, ColorRoom.RED,null);
        n8 = new AmmoPoint(4,2,null, ColorRoom.YELLOW,null);
        n9 = new AmmoPoint(1,3,null, ColorRoom.BLUE,null);
        n10 = new AmmoPoint(2,3,null, ColorRoom.BLUE,null);
        n11 = new SpawnPoint(3,3,null, ColorRoom.BLUE,null);

        //Create a set of square empty
        expectedOut = new LinkedHashSet<>();



    }
    @After
    public void tearDown()
    {
        expectedOut.clear();
    }

    @Test
    public void createGraph()
    {
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


        test.addEdge(n2,n3,TypeSide.FREE);
        test.addEdge(n2,n6,TypeSide.PORT);

        test.addEdge(n3,n4,TypeSide.PORT);
        test.addEdge(n3,n7,TypeSide.WALL);

        test.addEdge(n4,n8,TypeSide.FREE);

        test.addEdge(n5,n6,TypeSide.FREE);
        test.addEdge(n5,n9,TypeSide.PORT);

        test.addEdge(n6,n10,TypeSide.WALL);
        test.addEdge(n6,n7,TypeSide.FREE);

        test.addEdge(n7,n11,TypeSide.PORT);
        test.addEdge(n7,n8,TypeSide.PORT);

        test.addEdge(n9,n10,TypeSide.FREE);


        test.addEdge(n10,n11,TypeSide.FREE);


    }
}

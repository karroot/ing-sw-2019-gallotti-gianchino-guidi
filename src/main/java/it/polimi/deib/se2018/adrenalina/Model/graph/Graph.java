package it.polimi.deib.se2018.adrenalina.Model.graph;

import it.polimi.deib.se2018.adrenalina.Model.SideType;
import it.polimi.deib.se2018.adrenalina.Model.Square;
import it.polimi.deib.se2018.adrenalina.Model.graph.exceptions.SquareNotInGameBoard;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Cysko7927
 * Implements a graph of Square
 */
public class Graph
{
    private Map<Square, List<Edge>> adjacencyList; //adjacency list of the graph

    /**
     *It initializes a graph with an adjacency List already done before
     * @param adjacencyList Is the adjacency list inherited from another graph
     */
    public Graph(Map<Square, List<Edge>> adjacencyList)
    {
        this.adjacencyList = adjacencyList;
    }

    /**
     *It initializes an empty graph
     */
    public Graph()
    {
        this.adjacencyList = new HashMap<>();

    }

    /**
     * It add a node in the graph if it is not present
     * @param newNode New node to add in the graph
     */
    public void addNode(Square newNode)
    {
        adjacencyList.putIfAbsent(newNode,new ArrayList<>()); //Insert node if it is absent in the adjacency list
    }



    /**
     * It add an edge bidirectional from node1 to node2
     *(Requires) the node must be into the graph
     * @param node1 a square to connect to node2
     * @param node2 a square to connect to node1
     * @param flag Represent if between two square there is a wall,
     */
    public void addEdge(Square node1, Square node2, SideType flag)
    {
        adjacencyList.get(node1).add(new Edge(flag, node2)); //Add the edge for node1 in the adjacencyList
        adjacencyList.get(node2).add(new Edge(flag, node1));//Add the edge for node2 in the adjacencyList
    }

    /**
     * Returns the square with coordinate x and y if is present else it executes an exception
     * It do the translation from coordinates to reference of square
     * @param x Coordinate x of the square to find
     * @param y Coordinate y of square to find
     * @return Square with coordinate x and y
     * @exception SquareNotInGameBoard is Executed if the square with coordinate x and y is not in the graph
     */
    public Square getSquare(int x,int y) throws SquareNotInGameBoard
    {
        Set<Square> temp = adjacencyList.keySet(); //obtain the set of all squares
        Square squareFounded = null;

        for (Square x1:temp) //Search in all squares
        {
            if (x1.getX() == x && x1.getY() == y)//If the coordinates match save the square
                squareFounded = x1;
        }

        if (squareFounded == null) //If square hasn't been founded signals it with the exception
            throw new SquareNotInGameBoard("Square with x ="+ x +" y = " + y + " isn't in the game board");

        return squareFounded;

    }



    /**
     *Returns a set of all square that can be reached starting from the square with
     * coordinates x and y through a door or not but no through a wall that are to certain distance
     * If the coordinate are wrong it returns an empty set
     * It do a BFS modified
     * @param x coordinate x  of starting square
     * @param y coordinate y of starting square
     * @param distance limit distance
     * @exception IllegalArgumentException if distance is greater of 5
     * @return Set of square that can be reached
     */
    public Set<Square> squareReachableNoWall(int x, int y, int distance)throws IllegalArgumentException
    {
        //N.B this algorithm works because each square has at least a link of type free or port

        if(distance > 5)
            throw new IllegalArgumentException("Distanza non ammisibile per le dimensioni del'arena");

        Square start = null;

        try
        {
            start = getSquare(x,y); //obtain starting square
        }
        catch (SquareNotInGameBoard e) //If square is not in the graph
        {
            System.err.println(e.toString()); //Signal that x and y are wrong
            return  new HashSet<>(); //Return an empty Set
        }

        Set<Square> squares = new HashSet<>(); //Create a set empty


        List<Edge> queue = new LinkedList<>();//List of support

        //#####################à

        int k = 1;
        squares.add(start); //Add starting square to the set

        List<Edge> edgeList = this.adjacencyList.get(start); //obtain all edge that start from starting square

        while(k <= distance) //while you don't arrive to distance indicated
        {
            for (Edge temp: edgeList) //Search in list of edge of a square
            {
                if (temp.getFlag() == SideType.OPEN || temp.getFlag() == SideType.DOOR) //If flag is ok
                {
                    squares.add(temp.getNextNode()); //Add next square to the set
                    queue.addAll(this.adjacencyList.get(temp.getNextNode()));//Add all edge that starts from next square the to queue

                }
            }

            edgeList.clear();//Remove old set of edge
            edgeList.addAll(queue);//Add the new edge for the next level
            queue.clear();//Remove edge from list of support


            k++; //go to see the square to distance k + 1
        }

        return squares;

    }

    /**
     * Return all squares that are at north of a square in the arena passed like variable
     * @param square starting square
     * @return Set of all squares that are at north before of the limit arena
     */
    public Set<Square> getAllSquareAtNorth(Square square)
    {
        int x = square.getX(); //obtain coordinates of starting square
        int y = square.getY();
        Square squareTemp = square;
        Set<Square> result = new HashSet<>(); //Create an empty set of squares

        int i = 1;

        result.add(square);

        //while you don't encounter the limit of the arena
        while (!squareTemp.getSide()[0].equals(SideType.LIMIT))
        {
            try
            {
                squareTemp = getSquare(x ,y + i); //Take a square at north
            }
            catch (SquareNotInGameBoard e) //if the square doesn't exist return the set of square founded
            {
                return result;
            }
            result.add(squareTemp);//If the square exists add to the set of square researched
            i++;//Go more at north
        }

        return result;//At the end it returns all square founded

    }

    /**
     * Return all squares that are at East of a square in the arena passed like variable
     * @param square starting square
     * @return Set of all squares that are at East before the limit arena
     */
    public Set<Square> getAllSquareAtEast(Square square)
    {
        int x = square.getX(); //obtain coordinates of starting square
        int y = square.getY();
        Square squareTemp = square;
        Set<Square> result = new HashSet<>(); //Create an empty set of squares

        int i = 1;


        //while you don't encounter the limit of the arena
        while (!squareTemp.getSide()[1].equals(SideType.LIMIT))
        {
            try
            {
                squareTemp = getSquare(x + i ,y); //Take a square at east
            }
            catch (SquareNotInGameBoard e) //if the square doesn't exist return the set of square founded
            {
                return result;
            }
            result.add(squareTemp);//If the square exists add to the set of square researched
            i++;//Go more at East
        }

        return result;//At the end it returns all square founded

    }

    /**
     * Return all squares that are at south of a square in the arena passed like variable
     * @param square starting square
     * @return Set of all squares that are at south before the limit arena
     */
    public Set<Square> getAllSquareAtSouth(Square square)
    {
        int x = square.getX(); //obtain coordinates of starting square
        int y = square.getY();
        Square squareTemp = square;
        Set<Square> result = new HashSet<>(); //Create an empty set of squares

        int i = 1;

        result.add(square);

        //while you don't encounter the limit of the arena
        while (!squareTemp.getSide()[2].equals(SideType.LIMIT))
        {
            try
            {
                squareTemp = getSquare(x ,y - i); //Take a square at south
            }
            catch (SquareNotInGameBoard e) //if the square doesn't exist return the set of square founded
            {
                return result;
            }
            result.add(squareTemp);//If the square exists add to the set of square researched
            i++;//Go more at south
        }

        return result;//At the end it returns all square founded

    }

    /**
     * Return all squares that are at west of a square in the arena passed like variable
     * @param square starting square
     * @return Set of all squares that are at west before the limit arena
     */
    public Set<Square> getAllSquareAtWest(Square square)
    {
        int x = square.getX(); //obtain coordinates of starting square
        int y = square.getY();
        Square squareTemp = square;
        Set<Square> result = new HashSet<>(); //Create an empty set of squares

        int i = 1;

        result.add(square);

        //while you don't encounter the limit of the arena
        while (!squareTemp.getSide()[3].equals(SideType.LIMIT))
        {
            try
            {
                squareTemp = getSquare(x - i ,y); //Take a square at west
            }
            catch (SquareNotInGameBoard e) //if the square doesn't exist return the set of square founded
            {
                return result;
            }
            result.add(squareTemp);//If the square exists add to the set of square researched
            i++;//Go more at west
        }

        return result;//At the end it returns all square founded

    }

    //Implements the abstract function
    @Override
    public String toString() {
        return "Graph{" +
                "adjacencyList=" + adjacencyList +
                '}';
    }
}

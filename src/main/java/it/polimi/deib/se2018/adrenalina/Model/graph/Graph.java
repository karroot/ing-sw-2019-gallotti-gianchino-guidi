package it.polimi.deib.se2018.adrenalina.Model.graph;

import it.polimi.deib.se2018.adrenalina.Model.Square;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Cysko7927
 * Implements a graph of Square
 * Class immutable
 */
public class Graph
{
    private Map<Square, List<Edge>> adjacencyList; //adjacency list of the graph

    /**
     *
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
    public void addEdge(Square node1, Square node2, TypeSide flag)
    {
        adjacencyList.get(node1).add(new Edge(flag, node2)); //Add the edge for node1 in the adjacencyList
        adjacencyList.get(node2).add(new Edge(flag, node1));//Add the edge for node2 in the adjacencyList
    }

    /**
     * Returns the square with coordinate x and y if is present else it returns null
     * It do the translation from coordinates to reference of square
     * @param x Coordinate x of the square to find
     * @param y Coordinate y of square to find
     * @return Square with coordinate x and y
     */
    public Square getSquare(int x,int y)
    {
        Set<Square> temp = adjacencyList.keySet(); //obtain the set of all squares
        Square squareFounded = null;

        for (Square x1:temp) //Search in all squares
        {
            if (x1.getX() == x && x1.getY() == y)//If the coordinates match save the square
                squareFounded = x1;
        }

        return squareFounded;

    }


    //Inserire l'operazione inversa di traduzione dal puntatore alle cordinate



    /**
     *Returns a set of all square that can be reached starting from the square with
     * coordinates x and y through a door or not but no through a wall that are to certain distance
     * It do a BFS modified
     * @param x coordinate x  of starting square
     * @param y coordinate y of starting square
     * @param distance limit distance
     * @return Set of square that can be reached
     */
    public Set<Square> squareReachableNoWall(int x, int y, int distance)
    {
        //N.B this algorithm works because each square has at least a link of type free or port

        Square start = getSquare(x,y); //obtain starting square
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
                if (temp.getFlag() == TypeSide.FREE || temp.getFlag() == TypeSide.PORT) //If flag is ok
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

    //Implements the abstract function
    @Override
    public String toString() {
        return "Graph{" +
                "adjacencyList=" + adjacencyList +
                '}';
    }
}

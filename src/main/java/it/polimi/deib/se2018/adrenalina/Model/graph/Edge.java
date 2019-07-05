package it.polimi.deib.se2018.adrenalina.Model.graph;

import it.polimi.deib.se2018.adrenalina.Model.SideType;
import it.polimi.deib.se2018.adrenalina.Model.Square;

/**
 * Implements the edge of the graph
 * Immutable Class
 * @author Cysko7927
 */
public class Edge
{
    private SideType flag; //Flag to indicate if in the side there is a wall, port or is free

    private Square nextNode;//It is the square of the game board associated to edge

    public Edge(SideType flag, Square nextNode)
    {
        this.flag = flag;
        this.nextNode = nextNode;
    }

    /**
     *
     * @return Flag to indicate if in the side there is a wall, port or is free
     */
    public SideType getFlag() {
        return flag;
    }

    /**
     *
     * @return It is the square of the game board associated to edge
     */
    public Square getNextNode() {
        return nextNode;
    }

    //AF

    @Override
    public String toString() {
        return "Edge{" +
                "flag=" + flag +
                ", nextNode=" + nextNode +
                '}';
    }
}

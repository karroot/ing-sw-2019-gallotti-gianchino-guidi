package it.polimi.deib.se2018.adrenalina.Model.graph;

import it.polimi.deib.se2018.adrenalina.Model.Square;

/**
 * @author Cysko7927
 * Implements the edge of the graph
 * Immutable Class
 */
public class Edge
{
    private TypeSide flag; //Flag to indicate if in the side there is a wall, port or is free

    private Square nextNode;//It is the square of the game board associated to edge

    public Edge(TypeSide flag, Square nextNode)
    {
        this.flag = flag;
        this.nextNode = nextNode;
    }

    /**
     *
     * @return Flag to indicate if in the side there is a wall, port or is free
     */
    public TypeSide getFlag() {
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

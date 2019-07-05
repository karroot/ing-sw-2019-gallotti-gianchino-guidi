package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;

/**
 * @author gioguidi
 */

public class ResponseRocketLauncher extends ResponseInput
{
    private ColorId targetPlayerBasicMode;
    private String targetSquareCoordinatesAsStringPlayerToMove;
    private String targetSquareCoordinatesAsStringTargetToMove;
    private boolean withFragWarhead;

    ResponseRocketLauncher (ColorId targetBasicEffect, String targetSquareCoordinatesAsStringPlayerToMove, String targetSquareCoordinatesAsStringTargetToMove, boolean withFragWarhead)
    {
        this.targetPlayerBasicMode = targetBasicEffect;
        this.targetSquareCoordinatesAsStringPlayerToMove = targetSquareCoordinatesAsStringPlayerToMove;
        this.targetSquareCoordinatesAsStringTargetToMove = targetSquareCoordinatesAsStringTargetToMove;
        this.withFragWarhead = withFragWarhead;
    }

    public ColorId getTargetPlayerBasicMode()
    {
        return targetPlayerBasicMode;
    }

    public String getTargetSquareCoordinatesAsStringPlayerToMove()
    {
        return targetSquareCoordinatesAsStringPlayerToMove;
    }

    public String getTargetSquareCoordinatesAsStringTargetToMove()
    {
        return targetSquareCoordinatesAsStringTargetToMove;
    }


    public boolean isWithFragWarhead()
    {
        return withFragWarhead;
    }
}

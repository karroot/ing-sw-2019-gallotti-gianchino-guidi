package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;

/**
 * This class implements the response for this weapon.
 *
 * @author gioguidi
 */

public class ResponseRocketLauncher extends ResponseInput
{
    private ColorId targetPlayerBasicMode;
    private String targetSquareCoordinatesAsStringPlayerToMove;
    private String targetSquareCoordinatesAsStringTargetToMove;
    private boolean withFragWarhead;

    /**
     * This method will set the targets in the response tp use the weapon.
     *
     */
    ResponseRocketLauncher (ColorId targetBasicEffect, String targetSquareCoordinatesAsStringPlayerToMove, String targetSquareCoordinatesAsStringTargetToMove, boolean withFragWarhead)
    {
        this.targetPlayerBasicMode = targetBasicEffect;
        this.targetSquareCoordinatesAsStringPlayerToMove = targetSquareCoordinatesAsStringPlayerToMove;
        this.targetSquareCoordinatesAsStringTargetToMove = targetSquareCoordinatesAsStringTargetToMove;
        this.withFragWarhead = withFragWarhead;
    }

    /**
     * This is a public getter for the attribute targetPlayerBasicMode
     *
     * @return the attribute targetPlayerBasicMode
     */
    public ColorId getTargetPlayerBasicMode()
    {
        return targetPlayerBasicMode;
    }

    /**
     * This is a public getter for the attribute targetSquareCoordinatesAsStringPlayerToMove
     *
     * @return the attribute targetSquareCoordinatesAsStringPlayerToMove
     */
    public String getTargetSquareCoordinatesAsStringPlayerToMove()
    {
        return targetSquareCoordinatesAsStringPlayerToMove;
    }

    /**
     * This is a public getter for the attribute targetSquareCoordinatesAsStringTargetToMove
     *
     * @return the attribute targetSquareCoordinatesAsStringTargetToMove
     */
    public String getTargetSquareCoordinatesAsStringTargetToMove()
    {
        return targetSquareCoordinatesAsStringTargetToMove;
    }


    /**
     * This is a public getter for the attribute withFragWarhead
     *
     * @return the attribute withFragWarhead
     */
    public boolean isWithFragWarhead()
    {
        return withFragWarhead;
    }

}

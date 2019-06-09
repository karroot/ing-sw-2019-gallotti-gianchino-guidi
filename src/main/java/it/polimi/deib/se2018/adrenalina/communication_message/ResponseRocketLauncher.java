package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;

/**
 * @author giovanni
 */

public class ResponseRocketLauncher extends ResponseInput
{
    private ColorId targetPlayerBasicMode;
    private String targetSquareCoordinatesAsStringPlayerToMove;
    private String targetSquareCoordinatesAsStringTargetToMove;
    private String[] orderEffect;

    public ResponseRocketLauncher (ColorId targetBasicEffect, String targetSquareCoordinatesAsStringPlayerToMove, String targetSquareCoordinatesAsStringTargetToMove, String[] orderEffect) {
        this.targetPlayerBasicMode = targetBasicEffect;
        this.targetSquareCoordinatesAsStringPlayerToMove = targetSquareCoordinatesAsStringPlayerToMove;
        this.targetSquareCoordinatesAsStringTargetToMove = targetSquareCoordinatesAsStringTargetToMove;
        this.orderEffect = orderEffect;
    }

    public ColorId getTargetPlayerBasicMode() {
        return targetPlayerBasicMode;
    }

    public String getTargetSquareCoordinatesAsStringPlayerToMove() {
        return targetSquareCoordinatesAsStringPlayerToMove;
    }

    public String getTargetSquareCoordinatesAsStringTargetToMove() {
        return targetSquareCoordinatesAsStringTargetToMove;
    }

    public String[] getOrderEffect() {
        return orderEffect;
    }
}

package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

public class ResponsePlasmaGun  extends ResponseInput
{
    private ColorId targetBasicEffect = null;//Target chosen for the basic effect
    private ColorId targetForchargedEffect = null;//Target chosen for the charged and dice effect
    private int x =0;//Coordinates for the square chosen by user in the shadowstep effect
    private int y = 0;
    private String[] orderEffect;//array that represent the order of the effect chosen by user

    /**
     * Create the response message for PlasmaGun
     * @param targetBasicEffect Target chosen for the basic effect
     * @param targetForchargedEffect Target chosen for the charged and dice effect
     * @param x Coordinate x for the square chosen by user in the shadowstep effect
     * @param y Coordinate y for the square chosen by user in the shadowstep effect
     * @param orderEffect array that represent the order of the effect chosen by user
     */
    public ResponsePlasmaGun(ColorId targetBasicEffect, ColorId targetForchargedEffect, int x, int y, String[] orderEffect) {
        this.targetBasicEffect = targetBasicEffect;
        this.targetForchargedEffect = targetForchargedEffect;
        this.x = x;
        this.y = y;
        this.orderEffect = orderEffect;
    }

    /**
     * @return get the target for PlasmaGun chosen for the basic effect
     */
    public ColorId getTargetBasicEffect()
    {
        return targetBasicEffect;
    }

    /**
     * @return get the target for PlasmaGun chosen for the charged effect
     */
    public ColorId getTargetForchargedEffect()
    {
        return targetForchargedEffect;
    }

    /**
     * @return get the coordinate x chosen by user for shadowstep effect
     */
    public int getX()
    {
        return x;
    }

    /**
     * @return get the coordinate y chosen by user for shadowstep effect
     */
    public int getY()
    {
        return y;
    }

    /**
     * @return get the array that represent the order of the effect chosen by user
     */
    public String[] getOrderEffect()
    {
        return orderEffect;
    }
}
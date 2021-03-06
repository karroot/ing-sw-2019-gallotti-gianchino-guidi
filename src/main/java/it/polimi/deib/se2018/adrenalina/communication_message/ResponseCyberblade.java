package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

/**
 * @author Cysko7927
 */
public class ResponseCyberblade extends ResponseInput
{
    private ColorId targetBasicEffect = null;//Target chosen for the basic effect
    private ColorId targetForSliceEffect = null;//Target chosen for the slice and dice effect
    private int x =0;//Coordinates for the square chosen by user in the shadowstep effect
    private int y = 0;
    private String[] orderEffect;//array that represent the order of the effect chosen by user

    /**
     * Create the response message for Cyberblade
     * @param targetBasicEffect Target chosen for the basic effect
     * @param targetForSliceEffect Target chosen for the slice and dice effect
     * @param x Coordinate x for the square chosen by user in the shadowstep effect
     * @param y Coordinate y for the square chosen by user in the shadowstep effect
     * @param orderEffect array that represent the order of the effect chosen by user
     */
    public ResponseCyberblade(ColorId targetBasicEffect, ColorId targetForSliceEffect, int x, int y, String[] orderEffect) {
        this.targetBasicEffect = targetBasicEffect;
        this.targetForSliceEffect = targetForSliceEffect;
        this.x = x;
        this.y = y;
        this.orderEffect = orderEffect;
    }

    /**
     * @return get the target for Cyberblade chosen for the basic effect
     */
    public ColorId getTargetBasicEffect()
    {
        return targetBasicEffect;
    }

    /**
     * @return get the target for Cyberblade chosen for the Slice effect
     */
    public ColorId getTargetForSliceEffect()
    {
        return targetForSliceEffect;
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

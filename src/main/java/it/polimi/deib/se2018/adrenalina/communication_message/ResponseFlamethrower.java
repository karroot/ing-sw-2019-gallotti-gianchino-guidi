package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.CardinalDirection;

/**
 * This class implements the response for this weapon.
 *
 * @author gioguidi
 */

public class ResponseFlamethrower extends  ResponseInput
{
    private ColorId targetBasicMode1;
    private ColorId targetBasicMode2;
    private ColorId targetBarbecueMode;
    private CardinalDirection targetDirectionBarbecueMode;
    boolean mode;

    /**
     * This method will set the targets in the response to use the basic mode of the weapon.
     *
     */
    ResponseFlamethrower(ColorId targetBasicMode1, ColorId targetBasicMode2)
    {
            this.targetBasicMode1 = targetBasicMode1;
            this.targetBasicMode2 = targetBasicMode2;
            mode = false;

    }

    /**
     * This method will set the targets in the response to use the alternative mode of the weapon.
     *
     */
    ResponseFlamethrower(CardinalDirection targetDirectionBarbecueMode, ColorId targetBarbecueMode)
    {
        this.targetDirectionBarbecueMode = targetDirectionBarbecueMode;
        this.targetBarbecueMode = targetBarbecueMode;
        mode = true;
    }

    /**
     * This is a public getter for the attribute getTargetBasicMode1
     *
     * @return the attribute getTargetBasicMode1
     */
    public ColorId getTargetBasicMode1()
    {
        return targetBasicMode1;
    }

    /**
     * This is a public getter for the attribute targetBasicMode2
     *
     * @return the attribute targetBasicMode2
     */
    public ColorId getTargetBasicMode2()
    {
        return targetBasicMode2;
    }

    /**
     * This is a public getter for the attribute targetBarbecueMode
     *
     * @return the attribute targetBarbecueMode
     */
    public ColorId getTargetBarbecueMode()
    {
        return targetBarbecueMode;
    }

    /**
     * This is a public getter for the attribute targetDirectionBarbecueMode
     *
     * @return the attribute targetDirectionBarbecueMode
     */
    public CardinalDirection getTargetDirectionBarbecueMode()
    {
        return targetDirectionBarbecueMode;
    }

    /**
     * This is a public getter for the attribute mode
     *
     * @return the attribute mode
     */
    public boolean isMode()
    {
        return mode;
    }

}

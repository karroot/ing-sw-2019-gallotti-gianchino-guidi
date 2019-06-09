package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.card.weapon_cards.CardinalDirection;

public class ResponseFlamethrower extends  ResponseInput
{
    private ColorId targetBasicMode1;
    private ColorId targetBasicMode2;
    private ColorId targetBarbecueMode;
    private CardinalDirection targetDirectionBarbecueMode;
    boolean mode;

    public ResponseFlamethrower ( ColorId targetBasicMode1, ColorId targetBasicMode2)
    {
            this.targetBasicMode1 = targetBasicMode1;
            this.targetBasicMode2 = targetBasicMode2;
            mode = false;

    }

    public ResponseFlamethrower (CardinalDirection targetDirectionBarbecueMode, ColorId targetBarbecueMode)
    {
        this.targetDirectionBarbecueMode = targetDirectionBarbecueMode;
        this.targetBarbecueMode = targetBarbecueMode;
        mode = true;
    }

    public ColorId getTargetBasicMode1() {
        return targetBasicMode1;
    }

    public ColorId getTargetBasicMode2() {
        return targetBasicMode2;
    }

    public ColorId getTargetBarbecueMode() {
        return targetBarbecueMode;
    }

    public CardinalDirection getTargetDirectionBarbecueMode() {
        return targetDirectionBarbecueMode;
    }

    public boolean isMode() {
        return mode;
    }
}

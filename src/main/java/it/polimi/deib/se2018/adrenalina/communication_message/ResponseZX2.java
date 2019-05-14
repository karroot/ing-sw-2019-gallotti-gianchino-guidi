package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.Model.Player;

import java.util.LinkedList;
import java.util.List;

public class ResponseZX2 extends ResponseInput
{

    private ColorId targetBasicMode;
    private List<ColorId> targetsAlternativeMode;
    boolean mode;

    public ResponseZX2(ColorId targetBasicMode)
    {
        this.targetBasicMode = targetBasicMode;
        mode = false;
    }

    public ResponseZX2(List<ColorId> targetsAlternativeMode)
    {
        this.targetsAlternativeMode = targetsAlternativeMode;
        mode = true;
    }

    public ColorId getTargetBasicMode()
    {
        return targetBasicMode;
    }

    public List<ColorId> getTargetsAlternativeMode() {
        return targetsAlternativeMode;
    }

    public boolean isMode() {
        return mode;
    }
}

package it.polimi.deib.se2018.adrenalina.communication_message;


import it.polimi.deib.se2018.adrenalina.Model.ColorId;

import java.util.HashMap;
import java.util.Map;

/**
 * @author giovanni
 */
public class ResponseHellion extends ResponseInput
{
    private HashMap<String, ColorId> targetBasicMode = new HashMap<>();
    private HashMap<String, ColorId> targetAlternativeMode = new HashMap<>();
    boolean mode;

    public ResponseHellion (HashMap<String, ColorId> targetBasicMode, HashMap<String, ColorId> targetAlternativeMode)
    {
        if (targetBasicMode != null)
        {
            this.targetBasicMode = targetBasicMode;
            mode = false;
        }

        if (targetAlternativeMode != null)
        {
            this.targetAlternativeMode = targetAlternativeMode;
            mode = true;
        }

    }

    public HashMap<String, ColorId> getTargetBasicMode() {
        return targetBasicMode;
    }

    public HashMap<String, ColorId> getTargetAlternativeMode() {
        return targetAlternativeMode;
    }

    public boolean isMode() {
        return mode;
    }
}

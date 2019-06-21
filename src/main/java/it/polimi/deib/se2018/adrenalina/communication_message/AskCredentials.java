package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

public class AskCredentials extends MessageNet
{

    ColorId colorId;

    public AskCredentials(ColorId colorId)
    {
        this.colorId = colorId;
    }

    public ColorId getColorId() {
        return colorId;
    }
}

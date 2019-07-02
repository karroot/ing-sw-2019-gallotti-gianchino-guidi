package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;


  /**
   * This class being used by Server to warn the view that the virtual view Wants the name and the hero effect of a player
   * The class contains also the  color that the player will use during the match
  * @author Cysko7927
   */

public class AskCredentials extends MessageNet
{

    ColorId colorId;

    /**
     * Create the message AskCredentials to send a player
     * @param colorId color that will be used by the player that to receive this message
     */
    public AskCredentials(ColorId colorId)
    {
        this.colorId = colorId;
    }

    /**
     * Getter for the colorId
     * @return color inserted in the constructor
     */
    public ColorId getColorId() {
        return colorId;
    }
}

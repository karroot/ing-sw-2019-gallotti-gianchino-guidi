package it.polimi.deib.se2018.adrenalina.communication_message.message_asking_controller;

import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;

/**
 * This class represents a request message for the controller to respawn a player using a power up
 * chosen by user
 * @author Cysko7927
 */
public class AskUsePowerUpRespawn extends ResponseInput
{
    private int cardChoice; //integer that represents the index of powerUp chosen by user

    /**
     * Create the message to send at the controller
     * @param cardChoice integer that represents the index of powerUp chosen by user
     */
    public AskUsePowerUpRespawn(int cardChoice)
    {
        this.cardChoice = cardChoice;
    }

    /**
     * Return the integer that represents the index of powerUp chosen by user
     * @return integer that represents the index of powerUp chosen by user
     */
    public int getCardChoice()
    {
        return cardChoice;
    }
}

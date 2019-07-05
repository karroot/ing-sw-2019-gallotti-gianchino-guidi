package it.polimi.deib.se2018.adrenalina.communication_message.message_asking_controller;

import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;


/**
 * This class represents a message of AFK
 * It being used by class {@link it.polimi.deib.se2018.adrenalina.View.TimerAFK}
 * to say at the server that a player didn't do an input for some seconds
 * The server after will skip the turn at the player
 * @author Cysko7927
 */
public class Afk extends ResponseInput {
}

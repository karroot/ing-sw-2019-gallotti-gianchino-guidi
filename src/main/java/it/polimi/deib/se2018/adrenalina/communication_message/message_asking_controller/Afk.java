package it.polimi.deib.se2018.adrenalina.communication_message.message_asking_controller;

import it.polimi.deib.se2018.adrenalina.communication_message.ResponseInput;


/**
 * @author Cysko7927
 * This class represents a message of AFK
 * It being used by class {@link it.polimi.deib.se2018.adrenalina.View.TimerAFK}
 * to say at the server that a player didn't do an input for some seconds
 * The server after will skip the turn at the player
 */
public class Afk extends ResponseInput {
}

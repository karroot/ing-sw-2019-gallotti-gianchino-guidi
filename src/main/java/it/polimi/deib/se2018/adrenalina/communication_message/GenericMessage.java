package it.polimi.deib.se2018.adrenalina.communication_message;

/**
 * This class represents a generic message sent by controller containing a message that the client will print
 * on the terminal
 */
public class GenericMessage extends MessageNet
{
    String text;

    /**
     * Create the generic message
     * @param text text to print on CLI or GUI of the user
     */
    public GenericMessage(String text)
    {
        this.text = text;
    }

    /**
     * Getter for the string to print
     * @return string to print
     */
    public String getText()
    {
        return text;
    }
}

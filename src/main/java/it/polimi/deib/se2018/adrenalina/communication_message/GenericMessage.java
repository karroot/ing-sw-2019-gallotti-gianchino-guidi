package it.polimi.deib.se2018.adrenalina.communication_message;

/**
 * This class represents a generic message that the client will print
 * on the terminal
 */
public class GenericMessage extends MessageNet
{
    String text;

    public GenericMessage(String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return text;
    }
}

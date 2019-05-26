package it.polimi.deib.se2018.adrenalina.communication_message;

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

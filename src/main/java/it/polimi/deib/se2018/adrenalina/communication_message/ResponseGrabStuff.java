package it.polimi.deib.se2018.adrenalina.communication_message;

public class ResponseGrabStuff extends ResponseInput
{
    private String targetSquareToGo;//Target for basic mode

    public ResponseGrabStuff(String targetSquareToGo)
    {
        this.targetSquareToGo = targetSquareToGo;

    }

    public String getTargetSquareToGo()
    {
        return targetSquareToGo;
    }

}

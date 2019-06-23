package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.Color;
import it.polimi.deib.se2018.adrenalina.Model.ColorId;

public class ResponseShootPeopleTerminator extends ResponseInput {
    ColorId target;
    public ResponseShootPeopleTerminator(ColorId target) {
        this.target=target;
    }

    public ColorId getTarget() {
        return target;
    }
}

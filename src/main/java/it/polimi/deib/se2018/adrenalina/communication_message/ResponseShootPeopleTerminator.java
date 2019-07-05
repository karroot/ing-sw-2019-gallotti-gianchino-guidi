package it.polimi.deib.se2018.adrenalina.communication_message;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;

public class ResponseShootPeopleTerminator extends ResponseInput {
    ColorId target;

    /**
     *  target for the terminator
     * @param target for the terminator
     */
    public ResponseShootPeopleTerminator(ColorId target) {
        this.target=target;
    }

    /**
     *  get target for the terminator
     * @return the chosen target for the terminator
     */
    public ColorId getTarget() {
        return target;
    }
}

package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.communication_message.update_model.PlayerImmutable;

public interface Terminal
{
    public void showBoard();
    public void showMenu();
    public void showFinalScore(String messageWithFinalScore);
    public void showMessage(String message);
    public void showError(String message);
    public void showPlayerBoard(PlayerImmutable player);







}

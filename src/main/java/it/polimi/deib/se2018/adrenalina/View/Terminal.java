package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.communication_message.update_model.PlayerImmutable;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.UpdateModel;

public interface Terminal
{
    public void showBoard();
    public void showMenu();
    public void showFinalScore(String messageWithFinalScore);
    public void showMessage(String message);
    public void showError(String message);
    public void showPlayerBoard(PlayerImmutable player);
    public UpdateModel getData();
    public void setData(UpdateModel data);
    public void showAction();
    public int selectAction();
    public int inputInt(int min,int max);
    public boolean askReloading();
    public boolean askPowerUPTeleOrNew();
    public void showPowerUp();
    public int selectPowerUp();







}

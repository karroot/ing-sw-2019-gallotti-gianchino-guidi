package it.polimi.deib.se2018.adrenalina.View;

import it.polimi.deib.se2018.adrenalina.Model.ColorId;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.PlayerImmutable;
import it.polimi.deib.se2018.adrenalina.communication_message.update_model.UpdateModel;

import java.util.List;

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
    public boolean askReloading();
    public boolean askPowerUPTeleOrNew();
    public void showPowerUp();
    public int selectPowerUp();
    public boolean askPowerUPTarget();
    public boolean askPowerUPForReload();
    public void setColorOfPlayer(ColorId color);

    //Method to handle the input
    int inputInt(int min,int max);
    void addOptionInput(String text);
    void addTextInput(String text);
    int inputInt(List<Integer> acceptedInt);







}

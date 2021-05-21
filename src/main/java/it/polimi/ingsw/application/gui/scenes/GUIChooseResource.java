package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.actions.data.ChooseResourceActionData;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.util.JSONUtility;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIChooseResource implements Initializable {

    public ImageView coin;
    public ImageView servant;
    public ImageView shield;
    public ImageView stone;

    private Resources res = new Resources();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void sendResource(Resources res){
        ChooseResourceActionData chooseResourceActionData = new ChooseResourceActionData();
        chooseResourceActionData.setPlayer(GameApplication.getInstance().getUserNickname());
        chooseResourceActionData.setRes(res);

        ActionPacket actionPacket = new ActionPacket(Action.CHOOSE_RESOURCE, JSONUtility.toJson(chooseResourceActionData, ChooseResourceActionData.class));
        GameApplication.getInstance().getGameController().getGameControllerIOHandler().notifyAction(actionPacket);
    }



    @FXML
    public void coinClick(){
        res.add(ResourceType.COINS, 1);
        sendResource(res);
    }

    @FXML
    public void servantClick(){
        res.add(ResourceType.COINS, 1);
        sendResource(res);
    }

    @FXML
    public void shieldClick(){
        res.add(ResourceType.COINS, 1);
        sendResource(res);
    }

    @FXML
    public void stoneClick(){
        res.add(ResourceType.COINS, 1);
        sendResource(res);
    }
}

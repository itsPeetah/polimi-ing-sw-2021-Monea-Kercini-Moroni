package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.actions.data.ChooseResourceActionData;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.util.JSONUtility;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class GUIChooseResource {

    public ImageView coin;
    public ImageView servant;
    public ImageView shield;
    public ImageView stone;

    private Resources res = new Resources();

    private void sendResource(Resources res){
        ChooseResourceActionData chooseResourceActionData = new ChooseResourceActionData();
        chooseResourceActionData.setPlayer(GameApplication.getInstance().getUserNickname());
        chooseResourceActionData.setRes(res);

        ActionPacket actionPacket = new ActionPacket(Action.CHOOSE_RESOURCE, JSONUtility.toJson(chooseResourceActionData, ChooseResourceActionData.class));
        GameApplication.getInstance().getGameController().getGameControllerIOHandler().notifyAction(actionPacket);

        Stage s = (Stage) coin.getScene().getWindow();
        s.close();
    }



    @FXML
    public void coinClick(){
        res.add(ResourceType.COINS, 1);
        sendResource(res);
    }

    @FXML
    public void servantClick(){
        res.add(ResourceType.SERVANTS, 1);
        sendResource(res);
    }

    @FXML
    public void shieldClick(){
        res.add(ResourceType.SHIELDS, 1);
        sendResource(res);
    }

    @FXML
    public void stoneClick(){
        res.add(ResourceType.STONES, 1);
        sendResource(res);
    }
}

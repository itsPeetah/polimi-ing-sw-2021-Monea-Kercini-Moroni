package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.controller.model.actions.Action;
import it.polimi.ingsw.controller.model.actions.ActionPacket;
import it.polimi.ingsw.controller.model.actions.data.ChooseResourceActionData;
import it.polimi.ingsw.controller.model.messages.Message;
import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.util.JSONUtility;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIChooseResource implements Initializable {
    private final static Message DEFAULT_MESSAGE = Message.CHOOSE_RESOURCE;

    public ImageView coin;
    public ImageView servant;
    public ImageView shield;
    public ImageView stone;

    @FXML
    private static Label messageLabel;

    private final Resources res = new Resources();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMessage(DEFAULT_MESSAGE);
    }

    public static void setMessage(Message message) {
        Platform.runLater(() -> messageLabel.setText(message.toString()));
    }

    private void sendResource(Resources res){
        ChooseResourceActionData chooseResourceActionData = new ChooseResourceActionData();
        chooseResourceActionData.setPlayer(GameApplication.getInstance().getUserNickname());
        chooseResourceActionData.setRes(res);

        ActionPacket actionPacket = new ActionPacket(Action.CHOOSE_RESOURCE, JSONUtility.toJson(chooseResourceActionData, ChooseResourceActionData.class));
        GameApplication.getInstance().getGameController().getGameControllerIOHandler().notifyAction(actionPacket);

        Stage s = (Stage) coin.getScene().getWindow();
        s.close();
        setMessage(DEFAULT_MESSAGE);
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

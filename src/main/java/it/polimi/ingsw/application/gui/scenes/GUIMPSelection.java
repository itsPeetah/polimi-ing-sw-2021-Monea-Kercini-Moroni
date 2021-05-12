package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationState;
import it.polimi.ingsw.application.gui.GUIStage;
import it.polimi.ingsw.application.gui.MaestriRinascimentoGUI;
import it.polimi.ingsw.network.common.NetworkPacket;
import it.polimi.ingsw.network.common.NetworkPacketType;
import it.polimi.ingsw.network.common.sysmsg.GameLobbyMessage;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Optional;

public class GUIMPSelection {
    /**
     * Scene constructor with customized size.
     */
    public static Scene getScene(int length, int height) {
        // When no connection
        while(!GameApplication.getInstance().isOnNetwork()) {
            Alert connectionAlert = new Alert(Alert.AlertType.CONFIRMATION);
            connectionAlert.setTitle("Connection is down");
            connectionAlert.setContentText("You are not connected to the server.\nTo start a MP game, you need to be connected.\nDo you want to retry to open a connection?");
            connectionAlert.setHeaderText(null);

            Optional<ButtonType> result = connectionAlert.showAndWait();
            if(result.get() == ButtonType.OK) {
                GameApplication.getInstance().connect("localhost", 42069);
                if (GameApplication.getInstance().isOnNetwork()) GUIStage.setScene(GUIMPSelection.getScene());
                else {
                    Alert connectionErrorAlert = new Alert(Alert.AlertType.ERROR);
                    connectionErrorAlert.setTitle("Connection error");
                    connectionErrorAlert.setHeaderText(null);
                    connectionErrorAlert.setContentText("Ooops,\nit is not possible to open a connection with the server!\nTry again.");
                    connectionErrorAlert.showAndWait();
                }
            } else return GUIGameModeSelection.getScene();
        }

        //With connection

        // Create grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Title
        Text gridTitle = new Text("Room selection");
        gridTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(gridTitle, 0, 0, 2, 1);

        // Text fields
        Label nickname = new Label("Nickname:");
        grid.add(nickname, 0, 1);
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);
        Label room = new Label("Room:");
        grid.add(room, 0, 2);
        TextField roomTextField = new TextField();
        grid.add(roomTextField, 1, 2);

        // Buttons
        Button joinRoomBtn = new Button("Join room");
        Button createRoomBtn = new Button("Create room");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(joinRoomBtn);
        hbBtn.getChildren().add(createRoomBtn);
        grid.add(hbBtn, 1, 4);

        // Buttons actions
        joinRoomBtn.setOnAction(actionEvent -> performSelection(userTextField.getText(), roomTextField.getText(), GameLobbyMessage.JOIN_ROOM));

        createRoomBtn.setOnAction(actionEvent -> performSelection(userTextField.getText(), roomTextField.getText(), GameLobbyMessage.CREATE_ROOM));

        // Create scene
        return new Scene(grid, length, height);
    }

    /**
     * Scene constructor with default size.
     */
    public static Scene getScene() {
        return getScene(MaestriRinascimentoGUI.LENGTH, MaestriRinascimentoGUI.HEIGHT);
    }

    private static void performSelection(String username, String room, GameLobbyMessage gameLobbyMessage) {
        new Thread(() -> {
            GameApplication.getInstance().setUserNickname(username);
            GameApplication.getInstance().setRoomName(room);
            GameApplication.getInstance().out("Processing request, please wait.");
            String messageContent = gameLobbyMessage.addBody(room + " " + username);
            NetworkPacket np = new NetworkPacket(NetworkPacketType.SYSTEM, messageContent);
            GameApplication.getInstance().setApplicationState(GameApplicationState.CONNECTING_TO_ROOM);
            GameApplication.getInstance().sendNetworkPacket(np);
            while (GameApplication.getInstance().getApplicationState() == GameApplicationState.CONNECTING_TO_ROOM) {}
            GameApplicationState newState = GameApplication.getInstance().getApplicationState();
            if (newState == GameApplicationState.PREGAME) {
                Platform.runLater(() -> GUIStage.setScene(GUIPreGame.getScene()));
            }}).start();
    }
}

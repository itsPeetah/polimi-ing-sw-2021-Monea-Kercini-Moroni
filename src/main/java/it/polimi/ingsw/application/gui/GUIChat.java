package it.polimi.ingsw.application.gui;

import it.polimi.ingsw.application.common.GameApplication;
import it.polimi.ingsw.application.common.GameApplicationIOHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class GUIChat {
    public static ObservableList<String> observableChatList = FXCollections.observableArrayList();
    private static final AtomicReference<Stage> openedChat = new AtomicReference<>(null);

    public static void bindChat(ListView<String> chatListView) {
        Platform.runLater(() -> {
            System.out.println("GUIChat.bindChat");
            observableChatList.clear();
            chatListView.setItems(observableChatList);
            observableChatList.addAll(Arrays.asList("Welcome to the chat, " + GameApplication.getInstance().getUserNickname() + "!", "Insert your messages in the field below and press enter to send them.", "You can write /whisper <nickname> <message> to send a private message to another player."));
        });
    }

    public static void sendMessage(String message) {
        if(message != null && message.length() > 0) {
            String[] splitMessage = message.split(" ");
            if(splitMessage[0].equals("/whisper")) {
                try {
                    System.out.println("GUIChat.sendMessage: found whisper");
                    String to = splitMessage[1];
                    if(to.equals(GameApplication.getInstance().getUserNickname())) {
                        observableChatList.add("You can't whisper to yourself!");
                        return;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    for(int i = 2; i < splitMessage.length; i++) {
                        stringBuilder.append(splitMessage[i]);
                        if(i < splitMessage.length - 1) stringBuilder.append(" ");
                    }
                    String content = stringBuilder.toString();
                    GameApplicationIOHandler.getInstance().pushWhisperMessage(content, to);
                    System.out.println("GUIChat.sendMessage: sent whisper");
                } catch(IndexOutOfBoundsException e) {
                    observableChatList.addAll(Arrays.asList("The format of your whisper is not correct!", "The correct format is \"/whisper <nickname> <message>\"."));
                }
            }
            else {
                GameApplicationIOHandler.getInstance().pushChatMessage(message);
            }
        }
    }

    public static void notifyMessage(String from, String body) {
        Platform.runLater(() -> observableChatList.add(from + ": " + body));
    }

    public static void notifyWhisper(String from, String body) {
        Platform.runLater(() -> observableChatList.add(from + " whispered: " + body));
    }

    public static Stage getChatStage() {
        return openedChat.get();
    }

    public static void setChatStage(Stage newStage) {
        Stage oldStage = openedChat.get();
        if(!openedChat.compareAndSet(oldStage, newStage)) {
            setChatStage(newStage);
        } else { // If successful
            if(oldStage != null) Platform.runLater(oldStage::close);
        }
    }
}

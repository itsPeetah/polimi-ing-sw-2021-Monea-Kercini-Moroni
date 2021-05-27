package it.polimi.ingsw.application.gui.scenes;

import it.polimi.ingsw.application.common.GameApplication;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class GUILoading implements Initializable {
    public static int MAXIMUM_TIME = Integer.MAX_VALUE;
    public static double ROTATION_PER_SECOND = 1;
    public ImageView loadingFlask;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Setup animation
        RotateTransition flaskAnimation = new RotateTransition(Duration.seconds(MAXIMUM_TIME), loadingFlask);
        flaskAnimation.setByAngle(MAXIMUM_TIME * ROTATION_PER_SECOND * 360);
        flaskAnimation.setInterpolator(Interpolator.LINEAR);
        flaskAnimation.setCycleCount(Animation.INDEFINITE);

        // Start animation
        flaskAnimation.play();
        loadingFlask.setVisible(true);
    }
}

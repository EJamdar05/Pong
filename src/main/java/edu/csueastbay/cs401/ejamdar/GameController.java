package edu.csueastbay.cs401.ejamdar;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.PongApplication;
import edu.csueastbay.cs401.pong.Puckable;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    public static final int FIELD_WIDTH = 1300;
    public static final int FIELD_HEIGHT = 860;
    public static final int VICTORY_SCORE = 10;
    private PongApplication pongApp;
    private JamdarPong game;
    private Timeline timeline;
    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayer2;
    private MediaPlayer mediaPlayer3;


    private Stage stage;
    private Scene scene;
    @FXML
    AnchorPane fieldPane;
    @FXML
    Label playerOneScore;
    @FXML
    Label playerTwoScore;
    @FXML
    private Button muteButton;
    @FXML
    private Button exitButton;
    private boolean isPlaying = true;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            backgroundMusic(1);
            game = new JamdarPong(VICTORY_SCORE, FIELD_WIDTH, FIELD_HEIGHT);

            Platform.runLater(() -> fieldPane.requestFocus());
            addGameElementsToField();
            setUpTimeline();

        }catch(Exception e){
            System.out.println(e);
        }

    }

    private void addGameElementsToField() {
        ArrayList<Puckable> pucks = game.getPucks();
        pucks.forEach((puck) -> {
            fieldPane.getChildren().add((Node) puck);
        });

        ArrayList<Collidable> objects = game.getObjects();
        objects.forEach((object)-> {
            fieldPane.getChildren().add((Node) object);
        });

    }

    public void backgroundMusic(int num){
        if (num == 1) {
            String path = "edu/csueastbay/cs401/ejamdar/menu.wav";
            Media media = new Media(getClass().getResource("/edu/csueastbay/cs401/ejamdar/menu.wav").toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        }else if (num == 2){
            Media media = new Media(getClass().getResource("/edu/csueastbay/cs401/ejamdar/point.wav").toExternalForm());
            mediaPlayer2 = new MediaPlayer(media);
            mediaPlayer2.play();
        }else if (num == 3){
            Media media = new Media(getClass().getResource("/edu/csueastbay/cs401/ejamdar/point.wav").toExternalForm());
            mediaPlayer3 = new MediaPlayer(media);
            mediaPlayer3.play();
        }
    }

    @FXML
    public void keyPressed(KeyEvent event) {
        System.out.println("Pressed: " + event.getCode());
        game.keyPressed(event.getCode());
    }

    @FXML
    public void keyReleased(KeyEvent event) {
        game.keyReleased(event.getCode());
        System.out.println("Released: " + event.getCode());
    }

    private void setUpTimeline() {

        timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                game.move();
                playerOneScore.setText(Integer.toString(game.getPlayerScore(1)));
                playerTwoScore.setText(Integer.toString(game.getPlayerScore(2)));
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    @FXML
    void mute(ActionEvent event) {
        if(isPlaying) {
            mediaPlayer.pause();
            mediaPlayer2.pause();
            mediaPlayer3.pause();
            isPlaying = false;
        }else if (!isPlaying){
            mediaPlayer.play();
            mediaPlayer2.play();
            mediaPlayer3.play();
           isPlaying = true;
        }
    }

    @FXML
    void exit(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
        mediaPlayer.stop();
        mediaPlayer2.stop();
        mediaPlayer3.stop();

    }






}

package edu.csueastbay.cs401.ejamdar;
import edu.csueastbay.cs401.pong.*;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.Random;


/**
 * JamdarPong will bring out the features for the Jamdar version of Pong.
 *
 * @author Eshaq Jamdar
 * @version 1.0
 * @see JamdarPong
 */
public class JamdarPong extends Game {
    /**
     * The array buffer into which the elements of the ArrayList are stored.
     * The capacity of the ArrayList is the length of this array buffer.
     */
    private double fieldHeight;
    private double fieldWidth;
    private double ballSpeed = 10.50;
    public MediaPlayer mediaPlayerSfx;
    public MediaPlayer mediaPlayerSfx2;
    private Stage stage;
    private Scene scene;
    public boolean paused = false;
    public Paddle playerOne;
    public Paddle playerTwo;
    private GameController game = new GameController();
    /**
     * The array buffer into which the elements of the ArrayList are stored.
     * The capacity of the ArrayList is the length of this array buffer.
     */
    public JamdarPong(int victoryScore, double fieldWidth, double fieldHeight) {

        super(victoryScore);
        System.out.println("In jamdar");
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        setVictoryScore(20);
        Random rand = new Random();
        int max = 5;
        int min = 1;

        int numBalls = rand.nextInt(max - min) + min;

        min = 10;
        max = 25;
        int width = rand.nextInt(max - min) + min;

        min = 25;
        max = 100;
        int height = rand.nextInt(max - min) + min;


        for(int i = 1 ; i <= numBalls ; i++){
            addBall();
        }

        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();

        float r2 = rand.nextFloat();
        float g2 = rand.nextFloat();
        float b2 = rand.nextFloat();

        Wall top = new Wall("Top Wall", 0,0, this.fieldWidth, 10);
        top.setFill(Color.WHITE);
        addObject(top);

        Wall bottom = new Wall("Bottom Wall", 0, this.fieldHeight -10, this.fieldWidth, 10 );
        bottom.setFill(Color.WHITE);
        addObject(bottom);

        Goal left = new Goal("Player 1 Goal", this.fieldWidth -10, 10, 10, this.fieldHeight - 20);
        left.setFill(Color.RED);
        addObject(left);

        Goal right = new Goal("Player 2 Goal", 0, 10, 10, this.fieldHeight - 20);
        right.setFill(Color.BLUE);
        addObject(right);

        playerOne = new Paddle(
                "Player 1 Paddle",
                50,
                (this.fieldHeight/2) - 50,
                width,
                height,
                10,
                this.fieldHeight - 10);
        playerOne.setFill(Color.color(r,g,b));
        addPlayerPaddle(1, playerOne);

        playerTwo = new Paddle(
                "Player 2 Paddle",
                this.fieldWidth - 50,
                (this.fieldHeight/2) - 50,
                width,
                height,
                10,
                this.fieldHeight - 10);
        playerTwo.setFill(Color.color(r2,g2,b2));
        addPlayerPaddle(2, playerTwo);

    }

    /**
     * collisionHandler will handle the collisions of the puck
     * @param puck
     * @param collision
     */
    @Override
    public void collisionHandler(Puckable puck, Collision collision)  {
        if(!paused) {
            switch (collision.getType()) {
                case "Wall":
                    puck.setDirection(0 - puck.getDirection());
                    break;
                case "Goal":
                    if (collision.getObjectID() == "Player 1 Goal") {
                        playSound(1);
                        addPointsToPlayer(1, 1);
                        puck.reset();
                    } else if (collision.getObjectID() == "Player 2 Goal") {
                        playSound(1);
                        addPointsToPlayer(2, 1);
                        puck.reset();
                    }
                    break;
                case "Paddle":
                    playSound(2);
                    double puckCenter = ((Puck) puck).getCenterY();
                    double angle;
                    if (collision.getObjectID() == "Player 1 Paddle") {
                        angle = mapRange(collision.getTop(), collision.getBottom(), -45, 45, puckCenter);
                    } else {
                        angle = mapRange(collision.getTop(), collision.getBottom(), 225, 135, puckCenter);
                    }
                    puck.setDirection(angle);

            }
        }
    }
    /**
     * The array buffer into which the elements of the ArrayList are stored.
     * The capacity of the ArrayList is the length of this array buffer.
     */
    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }

    /**
     * This method takes in an int representing the sound effect to be
     * played and will use GameController's backgroundMusic method to play
     * the sound.
     * <p>
     * This method is ran any time the puck collides with a wall or any paddle.
     * Sound effect 1 plays the point sound when a point is obtained by either player.
     * Sound effect 2 will play the sound anytime the puck hits any colidable object.
     *
     * @param sound an int representing the sfx to be played
     * @return nothing
     * @see JamdarPong
     */
    private void playSound(int sound)  {
        if(sound == 1) {
            game.backgroundMusic(2);
        }else if (sound == 2){
            game.backgroundMusic(3);

        }
    }
    /**
     * The array buffer into which the elements of the ArrayList are stored.
     * The capacity of the ArrayList is the length of this array buffer.
     */
    public MediaPlayer getMediaPlayerSfx() {
        return mediaPlayerSfx;
    }
    /**
     * The array buffer into which the elements of the ArrayList are stored.
     * The capacity of the ArrayList is the length of this array buffer.
     */
    public void addBall(){
        Puck puck = new Puck(this.fieldWidth, this.fieldHeight);
        puck.setID("JamdarPuck");
        addPuck(puck);
    }
    /**
     * The array buffer into which the elements of the ArrayList are stored.
     * The capacity of the ArrayList is the length of this array buffer.
     */
    @Override
    public void keyPressed(KeyCode code) {
        switch (code) {
            case W:
                playerOne.moveUp();
                break;
            case S:
                playerOne.moveDown();
                break;
            case A:
                playerOne.moveLeft();
                break;
            case D:
                playerOne.moveRight();
                break;
            case I:
                playerTwo.moveUp();
                break;
            case K:
                playerTwo.moveDown();
                break;
            case J:
                playerTwo.moveLeft();
                break;
            case L:
                playerTwo.moveRight();
                break;
        }
    }
    /**
     * The array buffer into which the elements of the ArrayList are stored.
     * The capacity of the ArrayList is the length of this array buffer.
     */
    @Override
    public void move() {
        playerOne.move2();
        playerTwo.move2();
        for (Puckable puck : getPucks()) {
            checkCollision(puck);
            puck.move();
        }
    }
    /**
     * The array buffer into which the elements of the ArrayList are stored.
     * The capacity of the ArrayList is the length of this array buffer.
     */
    //close will end the mediaPlayer instances
    public void close(){
        mediaPlayerSfx2.stop();
        mediaPlayerSfx.stop();
    }

}

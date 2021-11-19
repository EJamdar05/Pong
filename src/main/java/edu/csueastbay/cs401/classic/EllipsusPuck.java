package edu.csueastbay.cs401.classic;

import edu.csueastbay.cs401.pong.Puckable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import java.util.Random;

public class EllipsusPuck extends Ellipse implements Puckable {

    public static final double STARTING_SPEED = 5.0;
    public static final int STARTING_RADIUS_X = 20;
    public static final int STARTING_RADIUS_Y = 10;
    private final double fieldWidth;
    private final double fieldHeight;
    private String id;
    private Double speed;
    private Double direction;
    private Integer direction_lock_counter;

    public EllipsusPuck(double fieldWidth, double fieldHeight) {
        super();
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        reset();
    }

    @Override
    public void reset() {
        Random random = new Random();
        setCenterX(fieldWidth / 2);
        setCenterY(fieldHeight / 2);
        setRadiusX(STARTING_RADIUS_X);
        setRadiusY(STARTING_RADIUS_Y);
        setFill(Color.GREEN);

        this.direction_lock_counter = 0;

        speed = STARTING_SPEED;
        if (random.nextInt(2) == 0) {
            direction = (random.nextDouble() * 90) - 45;
        } else {
            direction = (random.nextDouble() * 90) + 115;
        }
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public double getDirection() {
        return direction;
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void setDirection(double angle) {
        if (this.direction_lock_counter < 1) {
            this.direction = angle;
            this.direction_lock_counter = 10;
        }
    }



    @Override
    public void move() {
        double deltaX = speed * Math.cos(Math.toRadians(direction));
        double deltaY = speed * Math.sin(Math.toRadians(direction));
        setCenterX(getCenterX() + deltaX);
        setCenterY(getCenterY() + deltaY);
        setRotate(getRotate() + 10);
        this.direction_lock_counter--;
    }

}

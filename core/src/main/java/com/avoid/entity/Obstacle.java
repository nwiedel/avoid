package com.avoid.entity;

import com.avoid.config.GameConfig;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

public class Obstacle extends GameObjectBase {

    private static final float BOUNDS_RADIUS = 0.3f;
    public static final float SIZE = 2 * BOUNDS_RADIUS;

    private float ySpeed = GameConfig.MEDIUM_OBSTACLE_SPEED;
    private boolean hit;

    public Obstacle(){
        super(BOUNDS_RADIUS);
    }

    public void update(){
        setY(getY() - ySpeed);
    }

    public float getWidth() {
        return SIZE;
    }

    public boolean isPlayerColliding(Player player){
        Circle playerBounds = player.getBounds();
        boolean overlaps = Intersector.overlaps(playerBounds, getBounds());
        hit = overlaps;
        return overlaps;
    }

    public void setYSpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    public boolean isNotHit(){
        return !hit;
    }
}

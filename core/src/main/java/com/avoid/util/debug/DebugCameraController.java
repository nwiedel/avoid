package com.avoid.util.debug;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

public class DebugCameraController {

    // -- Konstanten --
    private static final Logger log = new Logger(DebugCameraController.class.getSimpleName(), Logger.DEBUG);



    // -- Attribute --
    private Vector2 position = new Vector2();
    private Vector2 startPosition = new Vector2();
    private float zoom = 1.0f;

    // -- Konstruktor --
    public DebugCameraController() {
    }

    // -- öffentliche Methoden --
    public void setStartPosition(float x, float y){
        startPosition.set(x, y);
        position.set(x, y);
    }

    public void applyTo(OrthographicCamera camera){
        camera.position.set(position, 0);
        camera.zoom = zoom;
        camera.update();
    }

    public void handleDebugInput(float delta){
        if(Gdx.app.getType() != Application.ApplicationType.Desktop){
            return;
        }

        float moveSpeed = DEFAULT_MOVE_SPEED * delta;
        float zoomSpeed = DEFAULT_ZOOM_SPEED * delta;

        // Kamera Bewegung
        if (Gdx.input.isKeyPressed(DEFAULT_LEFT_KEY)){
            moveLeft(moveSpeed);
        }
        else if (Gdx.input.isKeyPressed(DEFAULT_RIGHT_KEY)){
            moveRight(moveSpeed);
        }
        else if (Gdx.input.isKeyPressed(DEFAULT_UP_KEY)){
            moveUp(moveSpeed);
        }
        else if (Gdx.input.isKeyPressed(DEFAULT_DOWN_KEY)){
            moveDown(moveSpeed);
        }

        // Zoom Kontrolle
        if (Gdx.input.isKeyPressed(DEFAULT_ZOOM_IN_KEY)){
            zoomIn(zoomSpeed);
        } else if(Gdx.input.isKeyPressed(DEFAULT_ZOOM_OUT_KEY)){
            zoomOut(zoomSpeed);
        }

        // Reset Kamera
        if(Gdx.input.isKeyPressed(DEFAULT_RESET_KEY)){
            reset();
        }

        // Log Kontrolle
        if (Gdx.input.isKeyPressed(DEFAULT_LOG_KEY)){
            logDebug();
        }
    }

    // -- private Methoden --
    private void setPosition(float x, float y){
        position.set(x, y);
    }

    private void setZoom(float value) {
        zoom = MathUtils.clamp(value, DEFAULT_MAX_ZOOM_IN, DEFAULT_MAX_ZOOM_OUT);

    }
    private void moveCamera(float xSpeed, float ySpeed){
        setPosition(position.x + xSpeed, position.y + ySpeed);
    }

    private void moveLeft(float speed){
        moveCamera(-speed, 0);
    }
    private void moveRight(float speed){
        moveCamera(speed, 0);
    }
    private void moveUp(float speed){
        moveCamera(0, speed);
    }
    private void moveDown(float speed){
        moveCamera(0, -speed);
    }

    private void zoomIn(float zoomSpeed){
        setZoom(zoom - zoomSpeed);
    }
    private void zoomOut(float zoomSpeed){
        setZoom(zoom + zoomSpeed);
    }

    private void reset(){
        position.set(startPosition);
        setZoom(1.0f);
    }
    private void logDebug(){
        log.debug("position = " + position + " zoom = " + zoom);
    }
}

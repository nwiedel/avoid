package com.avoid.screen;

import com.avoid.config.GameConfig;
import com.avoid.entity.Obstacle;
import com.avoid.entity.Player;
import com.avoid.util.Utilities;
import com.avoid.util.ViewportUtils;
import com.avoid.util.debug.DebugCameraController;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {

    private static final Logger log = new Logger(GameScreen.class.getSimpleName(), Logger.DEBUG);

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private Player player;
    private Array<Obstacle> obstacles = new Array<>();
    private float obstacleTimer;

    private DebugCameraController debugCameraController;

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        player = new Player();

        float startPlayerX = GameConfig.WORLD_WIDTH / 2;
        float startPlayerY = 1;

        player.setPosition(startPlayerX, startPlayerY);

        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);
    }

    @Override
    public void render(float delta) {
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);

        // update world
        update(delta);

        ScreenUtils.clear(Color.BLACK);

        renderDebug();
    }

    private void update(float delta){
        updatePlayer();
        updateObstacles(delta);
    }

    private void updatePlayer(){
        //log.debug("playerX = " + player.getX() + " playerY = " + player.getY());
        player.update();
        blockPlayerFromLeavingWorld();
    }

    private void blockPlayerFromLeavingWorld(){
        float playerX = MathUtils.clamp(player.getX(),
            player.getWidth() / 2,
            GameConfig.WORLD_WIDTH - player.getWidth() / 2);

        player.setPosition(playerX, player.getY());
    }

    private void updateObstacles(float delta){
        for (Obstacle obstacle : obstacles){
            obstacle.update();
        }
        createNewObstacle(delta);
    }

    private void createNewObstacle(float delta){
        obstacleTimer += delta;

        if (obstacleTimer >= GameConfig.OBSTACLE_SPAWN_TIME){
            float min = 0f;
            float max = GameConfig.WORLD_WIDTH;
            float obstacleX = MathUtils.random(min, max);
            float obstacleY = GameConfig.WORLD_HEIGHT;

            Obstacle obstacle = new Obstacle();
            obstacle.setPosition(obstacleX, obstacleY);
            obstacles.add(obstacle);
            obstacleTimer = 0f;
        }
    }

    private void renderDebug(){

        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();

        renderer.end();

        ViewportUtils.drawGrid(viewport, renderer);
    }

    private void drawDebug(){
        player.drawDebug(renderer);
        for (Obstacle obstacle : obstacles){
            obstacle.drawDebug(renderer);
        }
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        ViewportUtils.debugPixelsPerUnit(viewport);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }
}

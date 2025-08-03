package com.avoid.screen;

import com.avoid.assets.AssetDescriptors;
import com.avoid.assets.AssetPaths;
import com.avoid.config.GameConfig;
import com.avoid.entity.Background;
import com.avoid.entity.Obstacle;
import com.avoid.entity.Player;
import com.avoid.util.ViewportUtils;
import com.avoid.util.debug.DebugCameraController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameRenderer implements Disposable {

    // -------------------- Attribute --------------------
    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private OrthographicCamera hudCamera;
    private Viewport hudViewport;

    private SpriteBatch batch;
    private BitmapFont font;
    private final GlyphLayout layout = new GlyphLayout();

    private DebugCameraController debugCameraController;

    private final GameController controller;
    private final AssetManager assetManager;

    private Texture playerTexture;
    private Texture obstacleTexture;
    private Texture backgroundTexture;

    // -------------------- Konstruktoren --------------------
    public GameRenderer(AssetManager assetManager, GameController controller){
        this.assetManager = assetManager;
        this.controller = controller;
        init();
    }

    // -------------------- init() --------------------
    private void init(){
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        hudCamera = new OrthographicCamera();
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, hudCamera);
        batch = new SpriteBatch();
        font = assetManager.get(AssetDescriptors.FONT);

        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);

        playerTexture = assetManager.get(AssetDescriptors.PLAYER);
        obstacleTexture = assetManager.get(AssetDescriptors.OBSTACLE);
        backgroundTexture = assetManager.get(AssetDescriptors.BACKGROUND);
    }

    // -------------------- öffentliche Methoden --------------------
    public void render(float delta){
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);

        // Touch input
        if(Gdx.input.isTouched() && !controller.isGameOver()){
            Vector2 screenTouch = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            Vector2 worldTouch = viewport.unproject(new Vector2(screenTouch));

            System.out.println("screenTouch = " + screenTouch);
            System.out.println("worldTouch = " + worldTouch);

            Player player = controller.getPlayer();
            worldTouch.x = MathUtils.clamp(worldTouch.x,
                0,
                GameConfig.WORLD_WIDTH - player.getWidth());
            player.setX(worldTouch.x);
        }

        ScreenUtils.clear(Color.BLACK);

        renderGamePlay();

        renderUI();

        renderDebug();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
        ViewportUtils.debugPixelsPerUnit(viewport);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        batch.dispose();
    }

    // -------------------- private Methoden --------------------
    private void renderGamePlay(){
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        Background background = controller.getBackground();
        batch.draw(backgroundTexture,
            background.getX(), background.getY(),
            background.getWidth(), background.getHeight());

        Player player = controller.getPlayer();
        batch.draw(playerTexture,
            player.getX(), player.getY(),
            player.getWidth(), player.getHeight());

        for (Obstacle obstacle : controller.getObstacles()){
            batch.draw(obstacleTexture,
                obstacle.getX(), obstacle.getY(),
                obstacle.getWidth(), obstacle.getHeight());
        }

        batch.end();
    }

    private void renderUI(){
        hudViewport.apply();
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();

        String livesText = "LIVES: " + controller.getLives();
        layout.setText(font, livesText);
        font.draw(batch, livesText,
            20,
            GameConfig.HUD_HEIGHT - layout.height);

        String scoreText = "SCORE: " + controller.getDisplayScore();
        layout.setText(font, scoreText);
        font.draw(batch, scoreText,
            GameConfig.HUD_WIDTH - layout.width - 20,
            GameConfig.HUD_HEIGHT - layout.height);

        batch.end();
    }

    private void renderDebug(){
        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();

        renderer.end();

        ViewportUtils.drawGrid(viewport, renderer);
    }

    private void drawDebug(){
        controller.getPlayer().drawDebug(renderer);
        for (Obstacle obstacle : controller.getObstacles()){
            obstacle.drawDebug(renderer);
        }
    }
}

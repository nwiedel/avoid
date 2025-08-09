package com.avoid.screen.loading;

import com.avoid.AvoidGame;
import com.avoid.assets.AssetDescriptors;
import com.avoid.config.GameConfig;
import com.avoid.screen.game.GameScreen;
import com.avoid.util.Utilities;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoadingScreen extends ScreenAdapter {

    // -------------------- Konstanten --------------------
    public static final float PROGRESS_BAR_WIDTH = GameConfig.HUD_WIDTH / 2f;
    public static final float PROGRESS_BAR_HEIGHT = 60f;

    // -------------------- Attribute --------------------
    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private float progress;
    private float waitTime = 0.75f;
    private boolean changeScreen;

    private final AvoidGame game;
    private final AssetManager assetManager;

    // -------------------- Konstruktor --------------------

    public LoadingScreen(AvoidGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    // -------------------- öffentliche Methoden --------------------
    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        assetManager.load(AssetDescriptors.FONT);
        assetManager.load(AssetDescriptors.GAME_PLAY);
        assetManager.load(AssetDescriptors.UI);
    }

    @Override
    public void render(float delta) {
        update(delta);

        ScreenUtils.clear(Utilities.CORNFLOWER_BLUE);
        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        draw();

        renderer.end();

        if (changeScreen){
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }


    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    // -------------------- private Methoden --------------------
    private void update(float delta){
        
        progress = assetManager.getProgress();

        // assetManager.update() ergibt true, wenn alles geladen ist!
        if (assetManager.update()){
            waitTime -= delta;
            if (waitTime <= 0){
                changeScreen = true;
            }
        }
    }

    private void draw(){
        float progressBarX = (GameConfig.HUD_WIDTH - PROGRESS_BAR_WIDTH) / 2f;
        float progressBarY = (GameConfig.HUD_HEIGHT - PROGRESS_BAR_HEIGHT) / 2f;

        renderer.rect(progressBarX, progressBarY,
            progress * PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT);
    }

    private void waitMillis(long millis){
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

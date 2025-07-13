package com.avoid.screen;

import com.avoid.AvoidGame;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

public class GameScreen implements Screen {

    private final AvoidGame game;

    private AssetManager assetManager;

    private GameController controller;
    private GameRenderer renderer;

    public GameScreen(AvoidGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        controller = new GameController();
        renderer = new GameRenderer(controller);
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        renderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
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

    @Override
    public void dispose() {
        renderer.dispose();
    }
}

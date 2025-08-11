package com.avoid.screen.menu;

import com.avoid.AvoidGame;
import com.avoid.assets.AssetDescriptors;
import com.avoid.assets.RegionNames;
import com.avoid.config.GameConfig;
import com.avoid.util.Utilities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HighScoreScreen extends ScreenAdapter {

    private static final Logger log = new Logger(HighScoreScreen.class.getSimpleName(), Logger.DEBUG);

    private final AvoidGame game;
    private AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;

    public HighScoreScreen(AvoidGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);

        initUI();
    }

    private void initUI(){
        Table table = new Table();

        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        TextureAtlas uiAtlas = assetManager.get(AssetDescriptors.UI);
        BitmapFont font= assetManager.get(AssetDescriptors.FONT);

        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
        TextureRegion panelRegion = uiAtlas.findRegion(RegionNames.PANEL);

        TextureRegion backRegion = uiAtlas.findRegion(RegionNames.BACK);
        TextureRegion backPressedRegion = uiAtlas.findRegion(RegionNames.BACK_PRESSED);

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        // Hintergrund
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        // Highscoretext
        Label highScoreText = new Label("HIGHSCORE", labelStyle);

        // Highscorelabel
        Label highScoreLabel = new Label("100", labelStyle);

        // back button
        ImageButton backButton = new ImageButton(
            new TextureRegionDrawable(backRegion),
            new TextureRegionDrawable(backPressedRegion)
        );
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                back();
            }
        });

        // setup table
        Table contenTable = new Table();
        contenTable.defaults().pad(20);
        contenTable.setBackground(new TextureRegionDrawable(panelRegion));

        contenTable.add(highScoreText).row();
        contenTable.add(highScoreLabel).row();
        contenTable.add(backButton);

        contenTable.center();

        table.add(contenTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Utilities.CORNFLOWER_BLUE);

        stage.act();
        stage.draw();
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
        stage.dispose();
    }


    private void back(){
        log.debug("BACK!");
        game.setScreen(new MenuScreen(game));
    }

    private static ImageButton createButton(TextureAtlas atlas, String upRegionName, String downRegionName){
        TextureRegion upRegion = atlas.findRegion(upRegionName);
        TextureRegion downRegion = atlas.findRegion(downRegionName);

        return new ImageButton(
            new TextureRegionDrawable(upRegion),
            new TextureRegionDrawable(downRegion)
        );
    }
}

package com.avoid.screen.menu;

import com.avoid.AvoidGame;
import com.avoid.assets.AssetDescriptors;
import com.avoid.assets.RegionNames;
import com.avoid.common.GameManager;
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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HighScoreScreen extends MenuScreenBase {

    private static final Logger log = new Logger(HighScoreScreen.class.getSimpleName(), Logger.DEBUG);

    public HighScoreScreen(AvoidGame game) {
        super(game);
    }

    @Override
    protected Actor createUI(){
        Table table = new Table();

        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        Skin uiSkin = assetManager.get(AssetDescriptors.UI_SKIN);

        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);

        // Hintergrund
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        // Highscoretext
        Label highScoreText = new Label("HIGHSCORE", uiSkin);

        // Highscorelabel
        String highScoreString = GameManager.INSTANCE.getHighScoreString();
        Label highScoreLabel = new Label(highScoreString, uiSkin);

        // back button
        TextButton backButton = new TextButton("BACK", uiSkin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                back();
            }
        });

        // setup table
        Table contenTable = new Table(uiSkin);
        contenTable.defaults().pad(20);
        contenTable.setBackground(RegionNames.PANEL);

        contenTable.add(highScoreText).row();
        contenTable.add(highScoreLabel).row();
        contenTable.add(backButton);

        contenTable.center();

        table.add(contenTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
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

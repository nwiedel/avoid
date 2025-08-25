package com.avoid.screen.menu;

import com.avoid.AvoidGame;
import com.avoid.assets.AssetDescriptors;
import com.avoid.assets.RegionNames;
import com.avoid.common.GameManager;
import com.avoid.config.DifficultyLevel;
import com.avoid.config.GameConfig;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;

public class OptionsScreen extends MenuScreenBase {

    private static final Logger log = new Logger(OptionsScreen.class.getName(), Logger.DEBUG);

    private ButtonGroup<CheckBox> checkBoxGroup;
    private CheckBox easy;
    private CheckBox medium;
    private CheckBox hard;

    public OptionsScreen(AvoidGame game) {
        super(game);
    }

    @Override
    protected Actor createUI(){
        Table table = new Table();
        table.defaults().pad(15);

        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);


        return table;
    }

    private static ImageButton createButton(TextureAtlas atlas, String regionName){
        TextureRegion region = atlas.findRegion(regionName);
        return new ImageButton(new TextureRegionDrawable(region));
    }

    private void back(){
        log.debug("back!");
        game.setScreen(new MenuScreen(game));
    }
}

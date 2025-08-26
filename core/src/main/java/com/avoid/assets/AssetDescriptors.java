package com.avoid.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetDescriptors {

    public static final AssetDescriptor<BitmapFont> FONT =
        new AssetDescriptor<>(AssetPaths.UI_FONT, BitmapFont.class);

    public static final AssetDescriptor<TextureAtlas> GAME_PLAY =
        new AssetDescriptor<>(AssetPaths.GAME_PLAY, TextureAtlas.class);

    public static final AssetDescriptor<Skin> UI_SKIN =
        new AssetDescriptor<>(AssetPaths.UI_SKIN, Skin.class);

    public static final AssetDescriptor<Sound> HIT_SOUND =
    new AssetDescriptor<Sound>(AssetPaths.HIT, Sound.class);

    private AssetDescriptors(){}
}

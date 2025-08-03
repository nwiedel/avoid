package com.avoid.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class AssetDescriptors {

    public static final AssetDescriptor<BitmapFont> FONT =
        new AssetDescriptor<>(AssetPaths.UI_FONT, BitmapFont.class);

    public static final AssetDescriptor<Texture> BACKGROUND =
        new AssetDescriptor<>(AssetPaths.BACKGROUND, Texture.class);

    public static final AssetDescriptor<Texture> OBSTACLE =
        new AssetDescriptor<>(AssetPaths.OBSTACLE, Texture.class);

    public static final AssetDescriptor<Texture> PLAYER =
        new AssetDescriptor<>(AssetPaths.PLAYER, Texture.class);

    private AssetDescriptors(){}
}

package com.avoid.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class AssetDescriptors {

    private static final AssetDescriptor<BitmapFont> FONT =
        new AssetDescriptor<>(AssetPaths.UI_FONT, BitmapFont.class);

    private AssetDescriptors(){}
}

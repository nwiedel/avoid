package com.avoid.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ViewportUtils {

    private static final Logger log = new Logger(ViewportUtils.class.getSimpleName(), Logger.DEBUG);

    private static final int DEFAULT_CELL_SIZE = 1;

    public static void drawGrid(Viewport viewport, ShapeRenderer renderer){
        drawGrid(viewport, renderer, DEFAULT_CELL_SIZE);
    }

    public static void drawGrid(Viewport viewport, ShapeRenderer renderer, int cellSize) {
        // Parameter überprüfen
        if (viewport == null) {
            throw new IllegalArgumentException("dG: Viewport Parameter wird benötigt!");
        }
        if (renderer == null) {
            throw new IllegalArgumentException("dG: Renderer Parameter wird benötigt!");
        }
        if (cellSize < DEFAULT_CELL_SIZE) {
            cellSize = DEFAULT_CELL_SIZE;
        }

        // Farbe des Renderers abspeichern
        Color oldColor = new Color(renderer.getColor());

        int worldWidth = (int) viewport.getWorldWidth();
        int worldHeight = (int) viewport.getWorldHeight();
        int doubleWorldWidth = worldWidth * 2;
        int doubleWorldHeight = worldHeight * 2;

        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        renderer.setColor(Color.WHITE);
        // vertikale Linien
        int count = 0;
        for (int x = -doubleWorldWidth; x < doubleWorldWidth; x++){
            renderer.line(x,-doubleWorldHeight, x, doubleWorldHeight);
            count++;
        }
        // horizontale Linien
        for (int y = -doubleWorldHeight; y < doubleWorldHeight; y++){
            renderer.line(-doubleWorldWidth, y, doubleWorldWidth, y);
        }
        // nul-Achsen in rot zeichen
        renderer.setColor(Color.RED);
        renderer.line(0, -doubleWorldHeight, 0 , doubleWorldHeight);
        renderer.line(-doubleWorldWidth, 0, doubleWorldWidth, 0);
        // Welt Grenzen zeichnen
        renderer.setColor(Color.GREEN);
        renderer.line(0, worldHeight, worldWidth, worldHeight);
        renderer.line(worldWidth, 0, worldWidth, worldHeight);

        renderer.end();
        renderer.setColor(oldColor);
    }

    public static void debugPixelsPerUnit(Viewport viewport){
        if (viewport == null) {
            throw new IllegalArgumentException("dPPM: Viewport Parameter wird benötigt!");
        }
        float screenWidth = viewport.getScreenWidth();
        float screenHeight = viewport.getScreenHeight();
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        //PPU ist Pixels per world unit
        float xPPU = screenWidth / worldWidth;
        float yPPU = screenHeight / worldHeight;

        log.debug("x PPU = " + xPPU + ", yPPU = " + yPPU);
    }

    private ViewportUtils(){}
}

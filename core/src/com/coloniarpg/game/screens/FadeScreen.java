package com.coloniarpg.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;

public class FadeScreen extends ScreenAdapter {
    public enum FadeType { IN, OUT }

    public static class FadeInfo {
        public final FadeType type;
        public final Color color;
        public final Interpolation interpolation;
        public final float duration;

        public FadeInfo(FadeType type, Color color, Interpolation interpolation, float duration) {
            this.type = type;
            this.color = color;
            this.interpolation = interpolation;
            this.duration = duration;
        }
    }

  
    private final FadeInfo fadeInfo;
    private Screen currentScreen;
    private Screen nextScreen;
    private final Game game;
    private final ShapeRenderer shapeRenderer;
    private final Camera camera;
    private float elapsedTime;

    public FadeScreen(Game game, FadeInfo fadeInfo, Screen currentScreen, Screen nextScreen) {
        this.game = game;
        this.fadeInfo = fadeInfo;
        this.currentScreen = currentScreen;
        this.nextScreen = nextScreen;
        this.shapeRenderer = new ShapeRenderer();
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        initializeCamera();
    }

    private void initializeCamera() {
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f);
        camera.update();
    }

    private void renderFadeEffect() {
        enableBlend();

        float fadeProgress = Math.min(1f, elapsedTime / fadeInfo.duration);
        float opacity = fadeInfo.type == FadeType.OUT 
            ? fadeInfo.interpolation.apply(fadeProgress) 
            : 1f - fadeInfo.interpolation.apply(fadeProgress);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(fadeInfo.color.r, fadeInfo.color.g, fadeInfo.color.b, opacity);
        shapeRenderer.rect(0, 0, camera.viewportWidth, camera.viewportHeight);
        shapeRenderer.end();

        disableBlend();
    }

    private void enableBlend() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    private void disableBlend() {
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void show() {
        elapsedTime = 0f;
    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;
        handleScreenTransition();
        clearScreen();
        renderCurrentScreen(delta);
        renderFadeEffect();
    }
    private void handleScreenTransition() {
        if (elapsedTime >= fadeInfo.duration) {
            if (nextScreen != null && game.getScreen() != nextScreen) {
                // Verifica se já não estamos na próxima tela para evitar reset desnecessário
                game.setScreen(nextScreen);
                disposeCurrentScreen();
            } else if (nextScreen == null && game.getScreen() != currentScreen) {
                game.setScreen(currentScreen);
            }
        }
    }
    
    private void disposeCurrentScreen() {
        if (currentScreen != null) {
            currentScreen.dispose();
            currentScreen = null;
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
    }

    private void renderCurrentScreen(float delta) {
        if (currentScreen != null) {
            currentScreen.render(delta);
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void pause() {
        // Optionally handle pause behavior
    }

    @Override
    public void resume() {
        // Optionally handle resume behavior
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null); // Remove o InputProcessor atual
        // Optionally handle hide behavior
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}

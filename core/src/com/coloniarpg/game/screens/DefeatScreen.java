package com.coloniarpg.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.coloniarpg.game.AssetUtils;


public class DefeatScreen implements Screen {
    private Game game;
    private Stage stage;
    private BitmapFont font;

    public DefeatScreen(Game game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Fonte personalizada
        this.font = new BitmapFont();
        this.font.getData().setScale(2f); // Ajusta o tamanho da fonte para boa legibilidade
    }

    @Override
    public void show() {
        System.out.println("show() - InputProcessor configurado.");
        Gdx.input.setInputProcessor(stage);

        // Fundo (usando o fundo do menu)
        Image backgroundImage = new Image(new TextureRegionDrawable(AssetUtils.backgroundMenu));
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        // Texto de derrota (usando o asset correto para a derrota)
        Image defeatText = new Image(new TextureRegionDrawable(AssetUtils.perdeuText)); // Usando o asset correto para o texto de derrota
        defeatText.setPosition(
            stage.getWidth() / 2f - defeatText.getWidth() / 2f,
            stage.getHeight() - 150
        );
        defeatText.addAction(Actions.forever(Actions.sequence(
            Actions.fadeOut(1f),
            Actions.fadeIn(1f)
        )));
        stage.addActor(defeatText);

        // Texto de explicação (mensagem de derrota)
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        Label message = new Label("Que pena! Tente novamente!", labelStyle);
        message.setPosition(
            stage.getWidth() / 2f - message.getWidth() / 2f,
            stage.getHeight() / 2f - message.getHeight() / 2f
        );
        stage.addActor(message);

        // Botão "Voltar ao Menu"
        ImageButton backButton = createBackButton();
        backButton.setSize(200, 80); // Ajusta tamanho do botão
        backButton.setPosition(
            stage.getWidth() / 2f - backButton.getWidth() / 2f,
            50
        );
        stage.addActor(backButton);

        // Garante que o botão esteja na frente
        backButton.toFront();

        // Som de derrota
        AssetUtils.songWrong.play(); // Usando o som de derrota
    }

    private ImageButton createBackButton() {
        TextureRegionDrawable backDrawable = new TextureRegionDrawable(new TextureRegion(AssetUtils.playButton)); // Usando o asset do botão "Play"
        TextureRegionDrawable backHighlightDrawable = new TextureRegionDrawable(new TextureRegion(AssetUtils.playButtonHighlight));

        ImageButton backButton = new ImageButton(backDrawable);
        backButton.getStyle().imageOver = backHighlightDrawable;

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Botão 'Voltar ao Menu' clicado!");
                AssetUtils.songButton.play();
                game.setScreen(new MainMenuScreen(game)); // Redireciona para o menu principal
            }
        });

        return backButton;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Adiciona um log para garantir que o render está sendo chamado
        System.out.println("DefeatScreen: render chamado");

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize() - Atualizando o Viewport.");
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null); // Remove o InputProcessor ao ocultar a tela
        stage.clear(); // Limpa todos os atores do Stage
        System.out.println("hide() - Tela de derrota ocultada e recursos liberados.");
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        AssetUtils.songWrong.stop(); // Para o som de derrota quando a tela for descartada
    }
}

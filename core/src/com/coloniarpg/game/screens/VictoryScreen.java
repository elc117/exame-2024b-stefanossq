package com.coloniarpg.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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





public class VictoryScreen implements Screen {
    private Game game;
    private String itemGanho;
    private Stage stage;
    private BitmapFont font;

    public VictoryScreen(Game game, String itemGanho) {
        this.game = game;
        this.itemGanho = itemGanho;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Fonte personalizada
        this.font = new BitmapFont();
        this.font.getData().setScale(2f); // Ajusta tamanho da fonte para boa legibilidade
    }

    @Override
    public void show() {
        System.out.println("show() - InputProcessor configurado.");
        Gdx.input.setInputProcessor(stage);

        // Fundo
        Image backgroundImage = new Image(new TextureRegionDrawable(AssetUtils.backgroundMenu));
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        // Texto de vitória
        Image victoryText = new Image(new TextureRegionDrawable(AssetUtils.vitoriaText));
        victoryText.setPosition(
            stage.getWidth() / 2f - victoryText.getWidth() / 2f,
            stage.getHeight() - 150
        );
        victoryText.addAction(Actions.forever(Actions.sequence(
            Actions.fadeOut(1f),
            Actions.fadeIn(1f)
        )));
        stage.addActor(victoryText);

        // Ícone do item
        Image itemImage = new Image(new TextureRegionDrawable(getItemTexture(itemGanho)));
        itemImage.setSize(150, 150);
        itemImage.setPosition(
            stage.getWidth() / 2f - itemImage.getWidth() / 2f,
            stage.getHeight() / 2f - itemImage.getHeight() / 2f + 50
        );
        itemImage.addAction(Actions.forever(Actions.sequence(
            Actions.scaleTo(1.2f, 1.2f, 0.7f),
            Actions.scaleTo(1f, 1f, 0.7f)
        )));
        stage.addActor(itemImage);

        // Texto de explicação
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        Label message = new Label("Parabéns! Você ganhou:", labelStyle);
        message.setPosition(
            stage.getWidth() / 2f - message.getWidth() / 2f,
            itemImage.getY() + itemImage.getHeight() + 20
        );
        stage.addActor(message);

        // Botão "Play"
        ImageButton playButton = createPlayButton();
        playButton.setSize(200, 80); // Ajusta tamanho do botão
        playButton.setPosition(
            stage.getWidth() / 2f - playButton.getWidth() / 2f,
            50
        );
        stage.addActor(playButton);

        // Garante que o botão esteja na frente
        playButton.toFront();

        // Ativa modo de depuração para testes
        // stage.setDebugAll(true);

        // Som de vitória
        AssetUtils.songCorrect.play();
    }

    private ImageButton createPlayButton() {
        TextureRegionDrawable playDrawable = new TextureRegionDrawable(new TextureRegion(AssetUtils.playButton));
        TextureRegionDrawable playHighlightDrawable = new TextureRegionDrawable(new TextureRegion(AssetUtils.playButtonHighlight));

        ImageButton playButton = new ImageButton(playDrawable);
        playButton.getStyle().imageOver = playHighlightDrawable;

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Botão 'Play' clicado!");
                AssetUtils.songButton.play();
                game.setScreen(new MainMenuScreen(game)); // Redireciona para o menu principal
            }
        });

        // Adicione este log para verificar se o listener está sendo adicionado
        System.out.println("Listener de clique adicionado ao botão 'Play'");

        // Verifique a posição e dimensões do botão
        System.out.println("Dimensões do botão 'Play': " + playButton.getWidth() + "x" + playButton.getHeight());
        System.out.println("Posição do botão 'Play': (" + playButton.getX() + ", " + playButton.getY() + ")");

        return playButton;
    }

    private Texture getItemTexture(String itemGanho) {
        switch (itemGanho) {
            case "item1":
                return AssetUtils.item1;
            case "item2":
                return AssetUtils.item2;
            case "item3":
                return AssetUtils.item3;
            case "item4":
                return AssetUtils.item4;
            default:
                Gdx.app.error("VictoryScreen", "Item desconhecido: " + itemGanho);
                return new Texture(Gdx.files.internal("default_item.png")); // Caminho genérico
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Adicione um log para garantir que o render está sendo chamado
        System.out.println("VictoryScreen: render chamado");

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
        System.out.println("hide() - Tela de vitória ocultada e recursos liberados.");
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        AssetUtils.songCorrect.stop();
    }
}

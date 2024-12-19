package com.coloniarpg.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingsScreen implements Screen {
    private Stage stage;
    private SpriteBatch batch;
    private Texture background;
    private Game game;
    private BitmapFont font;

    public SettingsScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        background = new Texture("bg_mainmenu.jpg");
        font = new BitmapFont(); // Fonte padrão do LibGDX

        // Criação do botão de retorno no canto superior direito
        Texture returnButtonTexture = new Texture("ReturnButton.png");
        TextureRegionDrawable returnButtonDrawable = new TextureRegionDrawable(returnButtonTexture);
        ImageButton returnButton = new ImageButton(returnButtonDrawable);
        returnButton.setSize(100, 50); // Tamanho do botão
        returnButton.setPosition(Gdx.graphics.getWidth() - 110, Gdx.graphics.getHeight() - 60); // Canto superior direito
        returnButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenuScreen(game)); // Retorna ao menu principal
                return true;
            }
        });

        // Definindo as escalas de efeito para os botões
        float scaleGrow = 1.05f;
        float scaleNormal = 1f;

        // Criação dos botões dos itens
        Texture item1Texture = new Texture("item1.png");
        TextureRegionDrawable item1Drawable = new TextureRegionDrawable(item1Texture);
        ImageButton item1Button = new ImageButton(item1Drawable);
        item1Button.setSize(150, 150);
        item1Button.setPosition(100, 300);
        addButtonListeners(item1Button, scaleGrow, scaleNormal, () -> showItemDescription("POLESINE ORB: Protegido por um belo vale na tranquilidade do interior gaúcho, o município de São João do Polêsine.Essa relação com a natureza fez com que o orbe se mantivesse muito bem preservado.\""));

        Texture item2Texture = new Texture("item2.png");
        TextureRegionDrawable item2Drawable = new TextureRegionDrawable(item2Texture);
        ImageButton item2Button = new ImageButton(item2Drawable);
        item2Button.setSize(150, 150);
        item2Button.setPosition(300, 300);
        addButtonListeners(item2Button, scaleGrow, scaleNormal, () -> showItemDescription("P.G ORB: Encontrada no Rio Jacui, que forma a divisa natural do municipio e seus vizinhos. Este orbe sintetizou sua energia latente a partir do poder hidreletrico do Rio, tornando-se uma fonte de energia unica e poderosa."));

        Texture item3Texture = new Texture("item3.png");
        TextureRegionDrawable item3Drawable = new TextureRegionDrawable(item3Texture);
        ImageButton item3Button = new ImageButton(item3Drawable);
        item3Button.setSize(150, 150);
        item3Button.setPosition(500, 300);
        addButtonListeners(item3Button, scaleGrow, scaleNormal, () -> showItemDescription("BELGA ORB: Oculto em uma antiga biblioteca subterranea da Vila Belga, entre os volumes do 'Traite dArchitecture' de Louis Cloquet. A energia arquitetonica foi infundida no orbe, me sinto seguro."));

        Texture item4Texture = new Texture("item4.png");
        TextureRegionDrawable item4Drawable = new TextureRegionDrawable(item4Texture);
        ImageButton item4Button = new ImageButton(item4Drawable);
        item4Button.setSize(150, 150);
        item4Button.setPosition(700, 300);
        addButtonListeners(item4Button, scaleGrow, scaleNormal, () -> showItemDescription("Descrição do Item 4"));

        // Adiciona os botões e o botão de retorno ao stage
        stage.addActor(returnButton);
        stage.addActor(item1Button);
        stage.addActor(item2Button);
        stage.addActor(item3Button);
        stage.addActor(item4Button);
    }

    private void addButtonListeners(ImageButton button, float scaleGrow, float scaleNormal, Runnable onClick) {
        button.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                button.getImage().setScale(scaleGrow); // Aumenta o tamanho ao passar o mouse
                button.getImage().setOrigin(button.getImage().getWidth() / 2, button.getImage().getHeight() / 2); // Centraliza o aumento
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                button.getImage().setScale(scaleNormal); // Restaura o tamanho normal ao sair
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                onClick.run(); // Executa a ação do botão
                return true;
            }
        });
    }

  private void showItemDescription(String description) {
    // Remove qualquer descrição anterior
    for (int i = stage.getActors().size - 1; i >= 0; i--) {
        if (stage.getActors().get(i) instanceof Label) {
            stage.getActors().removeIndex(i);
        }
    }

    // Criação do Label para a descrição
    Label.LabelStyle labelStyle = new Label.LabelStyle();
    labelStyle.font = font; // Usa a fonte padrão
    Label label = new Label(description, labelStyle);

    // Ajusta o tamanho do Label e centraliza o texto
    label.setSize(500, 200); // Define um tamanho fixo para o Label
    label.setWrap(true); // Permite que o texto faça a quebra automática de linha
    label.setAlignment(Align.center); // Centraliza o texto

    // Centraliza a descrição na parte inferior
    label.setPosition(Gdx.graphics.getWidth() / 2 - label.getWidth() / 2, 100);
    
    stage.addActor(label);
}


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Preenche a tela com o fundo
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        background.dispose();
        font.dispose();
    }
}

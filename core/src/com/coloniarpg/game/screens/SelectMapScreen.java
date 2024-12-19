package com.coloniarpg.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.coloniarpg.game.AssetUtils;

public class SelectMapScreen implements Screen {
    private SpriteBatch batch;
    private Texture background;
    private Stage stage;
    private Game game;
    private FadeScreen fadeScreen;

    public SelectMapScreen(Game game) {
        this.game = game;
    }

    private void startScreenBattleTransition(Screen battleScreenInstance) {
        System.out.println("startScreenBattleTransition() - Iniciando transição de tela para a batalha...");
        FadeScreen.FadeInfo fadeOut = new FadeScreen.FadeInfo(FadeScreen.FadeType.OUT, Color.BLACK, Interpolation.smoother, 1f);
        fadeScreen = new FadeScreen(game, fadeOut, this, battleScreenInstance);
        game.setScreen(fadeScreen);
    }

    @Override
    public void show() {
        System.out.println("show() - Tela de seleção de mapas exibida.");
        AssetUtils.initAssets();
        batch = new SpriteBatch();
        background = AssetUtils.backgroundMenu;
        stage = new Stage(new ScreenViewport());

        // Configura tabela principal
        Table table = new Table();
        table.setFillParent(true); // Ocupa a tela inteira
        stage.addActor(table);

        // Adiciona texto "Selecionar Nível"
        TextureRegionDrawable selectLevelDrawable = new TextureRegionDrawable(new TextureRegion(AssetUtils.selectLevelText));
        Image textSelectLevel = new Image(selectLevelDrawable);
        table.top().padTop(20);
        table.add(textSelectLevel).colspan(4).center().padBottom(50);
        table.row(); // Próxima linha

        // Configura botões de níveis
        setupButtons(table);

        Gdx.input.setInputProcessor(stage);
        System.out.println("show() - Entrada configurada para a Stage.");
    }

    private void setupButtons(Table table) {
        TextureRegionDrawable[] buttonDrawables = {
            new TextureRegionDrawable(new TextureRegion(AssetUtils.levelButton1)),
            new TextureRegionDrawable(new TextureRegion(AssetUtils.levelButton2)),
            new TextureRegionDrawable(new TextureRegion(AssetUtils.levelButton3)),
            new TextureRegionDrawable(new TextureRegion(AssetUtils.levelButton4))
        };

        // Agora vamos criar as telas de batalha com a GenericBattleScreen
        Screen[] battleScreens = new Screen[] {
            new GenericBattleScreen(game, AssetUtils.backgroundBattle1, AssetUtils.avatar, AssetUtils.enemyDino, 1),
            new GenericBattleScreen(game, AssetUtils.backgroundBattle2, AssetUtils.avatar, AssetUtils.enemyDino, 2),
            new GenericBattleScreen(game, AssetUtils.backgroundBattle3, AssetUtils.avatar, AssetUtils.enemyDino, 3),
            new GenericBattleScreen(game, AssetUtils.backgroundBattle4, AssetUtils.avatar, AssetUtils.enemyDino, 4)
        };

        float buttonSize = Gdx.graphics.getWidth() * 0.2f; // Botões ocupam 20% da largura da tela

        for (int i = 0; i < buttonDrawables.length; i++) {
            final int index = i;
            ImageButton levelButton = createLevelButton(buttonDrawables[i], () -> {
                System.out.println("Botão de nível " + (index + 1) + " pressionado.");
                startScreenBattleTransition(battleScreens[index]);
            });
            table.add(levelButton).width(buttonSize).height(buttonSize).pad(10);
        }
    }

    private ImageButton createLevelButton(TextureRegionDrawable drawable, Runnable onClick) {
        ImageButton button = new ImageButton(drawable);
        addButtonListeners(button, 1.05f, 1f, onClick);
        return button;
    }

    private void addButtonListeners(ImageButton button, float scaleGrow, float scaleNormal, Runnable onClick) {
        button.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                button.getImage().setScale(scaleGrow);
                button.getImage().setOrigin(button.getImage().getWidth() / 2, button.getImage().getHeight() / 2);
                System.out.println("Mouse entrou no botão.");
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                button.getImage().setScale(scaleNormal);
                System.out.println("Mouse saiu do botão.");
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Botão pressionado.");
                AssetUtils.songButton.play();

                // Animação ao pressionar
                ((ImageButton) event.getListenerActor()).getImage().addAction(Actions.sequence(
                    Actions.scaleTo(0.9f, 0.9f, 0.1f, Interpolation.fade),
                    Actions.scaleTo(1f, 1f, 0.1f, Interpolation.fade)
                ));

                onClick.run();  // Executa a ação do botão
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Fundo responsivo
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
    public void hide() {
        Gdx.input.setInputProcessor(null); // Remove o InputProcessor atual
        System.out.println("hide() - Tela oculta.");
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        System.out.println("dispose() - Descartando recursos da tela de seleção.");
    }
}

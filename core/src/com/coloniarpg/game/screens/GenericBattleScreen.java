package com.coloniarpg.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.coloniarpg.game.AssetUtils;
import com.coloniarpg.game.utils.BattleState;


public class GenericBattleScreen implements Screen {
    private static final float FONT_SCALE = 3f;
    private static final float ATTACK_BUTTON_Y_OFFSET = 40;

    public static float windowWidth;
    public static float windowHeight;

    private SpriteBatch batch;
    private Texture background;
    private Stage stage;
    private Game game;
    private Texture avatarTexture;
    private Texture enemyTexture;
    private int questionScreenId;

    private ImageButton attackButton;

    private boolean isTransitioning = false; // Flag para controlar transições

    public GenericBattleScreen(Game game, Texture background, Texture avatarTexture, Texture enemyTexture, int questionScreenId) {
        this.game = game;
        this.background = background;
        this.avatarTexture = avatarTexture;
        this.enemyTexture = enemyTexture;
        this.questionScreenId = questionScreenId;
    }

    @Override
    public void show() {
        System.out.println("show() - Inicializando a tela da batalha...");
        AssetUtils.initAssets();

        if (batch == null) batch = new SpriteBatch();
        if (stage != null) stage.clear();
        else stage = new Stage(new ScreenViewport());

        windowWidth = Gdx.graphics.getWidth();
        windowHeight = Gdx.graphics.getHeight();

        BitmapFont font = new BitmapFont();
        font.getData().setScale(FONT_SCALE);
        Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);

        // Recriar os elementos visuais e funcionais da tela
        addCharacterElements(style);
        addAttackButton();
        checkBattleOutcome();

        Gdx.input.setInputProcessor(stage);
    }

    private void addCharacterElements(Label.LabelStyle style) {
        float avatarX = 0;
        float avatarY = 0;
        float enemyX = windowWidth - 440;
        float enemyY = windowHeight - 480;

        Image avatar = createImage(avatarTexture, avatarX, avatarY, 2f);
        Image enemy = createImage(enemyTexture, enemyX, enemyY);

        Group vidaAvatarGroup = createHealthGroup(AssetUtils.heart, style, Math.max(0, BattleState.vidaAvatar), avatarX + 40, avatarY);
        Group vidaInimigoGroup = createHealthGroup(AssetUtils.heart, style, Math.max(0, BattleState.vidaInimigo), enemyX + 80, enemyY + 20);

        stage.addActor(avatar);
        stage.addActor(enemy);
        stage.addActor(vidaAvatarGroup);
        stage.addActor(vidaInimigoGroup);
    }

    private void addAttackButton() {
        if (attackButton != null) {
            attackButton.remove(); // Remova o botão existente antes de recriar
        }

        float attackButtonX = windowWidth / 2 - AssetUtils.attackButton.getWidth() / 2;

        TextureRegionDrawable attackButtonDrawable = new TextureRegionDrawable(new TextureRegion(AssetUtils.attackButton));
        TextureRegionDrawable attackButtonHighlightDrawable = new TextureRegionDrawable(new TextureRegion(AssetUtils.attackButtonHighlight));
        attackButton = new ImageButton(attackButtonDrawable);
        attackButton.setPosition(attackButtonX, ATTACK_BUTTON_Y_OFFSET);
        attackButton.getStyle().imageOver = attackButtonHighlightDrawable;
        attackButton.getStyle().imageUp = attackButtonDrawable;

        attackButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AssetUtils.songButton.play();
                startQuestionScreenTransition();
                return true;
            }
        });

        stage.addActor(attackButton);
    }

    private void startQuestionScreenTransition() {
        if (!isTransitioning) {
            isTransitioning = true;
            System.out.println("Iniciando transição para tela de perguntas...");
            QuestionRepository questionRepository = new QuestionRepository();
            if (game != null) {
                game.setScreen(new QuestionScreen1(game, questionScreenId, questionRepository));
            } else {
                System.err.println("Erro: 'game' não está inicializado!");
            }
        }
    }

    private void startVictoryScreenTransition() {
        if (!isTransitioning) {
            isTransitioning = true;
            System.out.println("Iniciando transição para tela de vitória com fade...");
            
            // Defina a cor e o tipo do fade (IN ou OUT), a interpolação e a duração
            FadeScreen.FadeInfo fadeInfo = new FadeScreen.FadeInfo(
                FadeScreen.FadeType.OUT,  // Fade OUT para esconder a tela atual
                Color.BLACK,  // Cor do fade
                Interpolation.fade,  // Tipo de interpolação
                1f  // Duração do fade
            );

            // Criando o FadeScreen para transição suave
            FadeScreen fadeScreen = new FadeScreen(game, fadeInfo, this, new VictoryScreen(game, "item3"));
            
            // Iniciando a transição para a tela de vitória com fade
            game.setScreen(fadeScreen);
        }
    }

    private void startDefeatScreenTransition() {
        if (!isTransitioning) {
            isTransitioning = true;
            System.out.println("Iniciando transição para tela de derrota...");
         // Defina a cor e o tipo do fade (IN ou OUT), a interpolação e a duração
         FadeScreen.FadeInfo fadeInfo = new FadeScreen.FadeInfo(
            FadeScreen.FadeType.OUT,  // Fade OUT para esconder a tela atual
            Color.BLACK,  // Cor do fade
            Interpolation.fade,  // Tipo de interpolação
            1f  // Duração do fade
        );

        // Criando o FadeScreen para transição suave
        FadeScreen fadeScreen = new FadeScreen(game, fadeInfo, this, new DefeatScreen(game));
        
        // Iniciando a transição para a tela de vitória com fade
        game.setScreen(fadeScreen);
    }
}

    private void checkBattleOutcome() {
        if (BattleState.vidaAvatar <= 0) {
            resetBattle();
            startDefeatScreenTransition();
        } else if (BattleState.vidaInimigo <= 0) {
            resetBattle();
            startVictoryScreenTransition();
        }
    }

    private void resetBattle() {
        BattleState.vidaAvatar = 3;
        BattleState.vidaInimigo = 3;
        BattleState.indexPergunta = 0;
        if (stage != null) {
            stage.clear();
        }
    }

    private Image createImage(Texture texture, float x, float y, float scale) {
        Image image = new Image(texture);
        image.setPosition(x, y);
        image.setScale(scale);
        return image;
    }

    private Image createImage(Texture texture, float x, float y) {
        return createImage(texture, x, y, 1f);
    }

    private Group createHealthGroup(Texture heartTexture, Label.LabelStyle style, int health, float x, float y) {
        Group group = new Group();
        for (int i = 0; i < health; i++) {
            Image heart = new Image(heartTexture);
            heart.setPosition(x + i * 40, y);
            group.addActor(heart);
        }
        return group;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() {
        System.out.println("hide() - Tela sendo ocultada, liberando recursos...");
        Gdx.input.setInputProcessor(null);
        if (stage != null) {
            stage.clear();
        }
    }

    @Override
    public void dispose() {
        System.out.println("dispose() - Descartando os recursos...");
        if (stage != null) stage.dispose();
        if (batch != null) batch.dispose();
        if (background != null) background.dispose();
    }
}

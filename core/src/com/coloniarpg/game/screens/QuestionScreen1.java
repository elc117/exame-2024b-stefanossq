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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.coloniarpg.game.AssetUtils;
import com.coloniarpg.game.utils.BattleState;

public class QuestionScreen1 implements Screen {
    private final Game game;
    private final int battleScreen;
    private final QuestionRepository questionRepository;

    private Stage stage;
    private SpriteBatch batch;
    private Texture background;

    private String[] questions;
    private String[] answers;
    private String[] correctAnswers;

    public QuestionScreen1(Game game, int battleScreen, QuestionRepository questionRepository) {
        this.game = game;
        this.battleScreen = battleScreen;
        this.questionRepository = questionRepository;

        // Carregar perguntas e respostas para a batalha correspondente
        loadQuestionsForBattleScreen();
    }

    /**
     * Carrega as perguntas, respostas e as corretas para a battleScreen específica.
     */
    private void loadQuestionsForBattleScreen() {
        questions = questionRepository.getQuestions(battleScreen);
        answers = questionRepository.getAnswers(battleScreen);
        correctAnswers = questionRepository.getCorrectAnswers(battleScreen);

        if (questions == null || answers == null || correctAnswers == null) {
            throw new IllegalStateException("Erro ao carregar as perguntas e respostas do repositório!");
        }
    }

    @Override
    public void show() {
        // Inicializa os assets
        AssetUtils.initAssets();
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        background = AssetUtils.backgroundMenu;
        Gdx.input.setInputProcessor(stage); // Define o stage como o InputProcessor

        // Configura os elementos da interface do usuário (perguntas e botões)
        setupUI();
    }

    /**
     * Configura os elementos da interface do usuário (perguntas e botões).
     */
    private void setupUI() {
        float windowWidth = Gdx.graphics.getWidth();
        float windowHeight = Gdx.graphics.getHeight();

        BitmapFont font = new BitmapFont();
        font.getData().setScale(2);
        Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);

        // Criar o rótulo da pergunta
        int currentIndexPergunta = BattleState.indexPergunta % questions.length;
        Label questionLabel = new Label(questions[currentIndexPergunta], style);
        questionLabel.setPosition(
            windowWidth / 2f - questionLabel.getWidth() / 2f, 
            windowHeight - 100
        );
        stage.addActor(questionLabel);

        // Configurar botões de resposta
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(AssetUtils.answerButton));
        textButtonStyle.font = font;

        int buttonSpacing = 120;
        int initialButtonHeight = 300;

        for (int j = 0; j < 4; j++) {
            createAnswerButton(currentIndexPergunta, j, textButtonStyle, windowWidth, windowHeight - initialButtonHeight - (j * buttonSpacing));
        }
    }

    /**
     * Cria e adiciona um botão de resposta à tela.
     *
     * @param questionIndex Índice da pergunta atual.
     * @param buttonIndex Índice do botão/resposta.
     * @param style Estilo do botão.
     * @param x Posição horizontal do botão.
     * @param y Posição vertical do botão.
     */
    private void createAnswerButton(int questionIndex, int buttonIndex, TextButton.TextButtonStyle style, float x, float y) {
        final int answerIndex = questionIndex * 4 + buttonIndex;
        String answerText = answers[answerIndex];

        TextButton answerButton = new TextButton(answerText, style);
        answerButton.setPosition(x / 2f - AssetUtils.answerButton.getWidth() / 2f, y);

        answerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleAnswerSelection(answerIndex, questionIndex);
            }
        });

        stage.addActor(answerButton);
    }

    /**
     * Lida com a seleção da resposta, atualizando a vida do avatar ou do inimigo.
     */
    private void handleAnswerSelection(int answerIndex, int questionIndex) {
        if (answers[answerIndex].equals(correctAnswers[questionIndex])) {
            BattleState.vidaInimigo--;
            AssetUtils.songCorrect.play();
        } else {
            BattleState.vidaAvatar--;
            AssetUtils.songWrong.play();
        }

        // Atualizar o índice da pergunta
        BattleState.indexPergunta++;
        startScreenBattleTransition(battleScreen);
    }

    /**
     * Inicia a transição de volta para a tela de batalha.
     */
    private void startScreenBattleTransition(int battleScreen) {
        Texture battleBackground = getBattleBackground(battleScreen);
        Texture enemyTexture = getEnemyTexture(battleScreen);

        Screen nextBattleScreen = new GenericBattleScreen(
            game,
            battleBackground,
            AssetUtils.avatar,
            enemyTexture,
            battleScreen
        );

        FadeScreen.FadeInfo fadeOut = new FadeScreen.FadeInfo(FadeScreen.FadeType.OUT, Color.BLACK, Interpolation.smoother, 1f);
        Screen fadeScreen = new FadeScreen(game, fadeOut, this, nextBattleScreen);
        game.setScreen(fadeScreen);
    }

    /**
     * Obtém o fundo da batalha com base no índice da tela.
     */
    private Texture getBattleBackground(int screenIndex) {
        switch (screenIndex) {
            case 1:
                return AssetUtils.backgroundBattle1;
            case 2:
                return AssetUtils.backgroundBattle2;
            case 3:
                return AssetUtils.backgroundBattle3;
            default:
                throw new IllegalArgumentException("Índice de tela inválido!");
        }
    }

    /**
     * Obtém a textura do inimigo com base no índice da tela.
     */
    private Texture getEnemyTexture(int screenIndex) {
        switch (screenIndex) {
            case 1:
            case 2:
                return AssetUtils.enemyDino;
            case 3:
                return AssetUtils.enemyTigre;
            default:
                throw new IllegalArgumentException("Índice de tela inválido!");
        }
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
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}

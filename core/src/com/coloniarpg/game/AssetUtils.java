package com.coloniarpg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class AssetUtils {
    public static Texture playButton;
    public static Texture playButtonHighlight;
    public static Texture settingsButton;
    public static Texture settingsButtonHighlight;
    public static Texture title;
    public static Texture backgroundMenu;
    public static Texture itemButtonTexture;
    public static Texture itemButtonHighlightTexture;

    public static Texture item1; // Adicionado
    public static Texture item2; // Adicionado
    public static Texture item3;
    public static Texture item4;

    public static Texture selectLevelText;
    public static Texture levelButton1;
    public static Texture levelButton2;
    public static Texture levelButton3;
    public static Texture levelButton4; // Novo botão do nível 4

    public static Texture backgroundBattle1;
    public static Texture backgroundBattle2;
    public static Texture backgroundBattle3;
    public static Texture backgroundBattle4; // Novo fundo da batalha 4

    public static Texture avatar;
    public static Texture enemyDino;
    public static Texture enemyJacare;
    public static Texture enemyTigre;
    public static Texture enemyDino2;

    public static Texture answerButton;
    public static Texture attackButton;
    public static Texture attackButtonHighlight;
    public static Texture heart;

    public static Texture venceuText;
    public static Texture perdeuText;
    public static Texture ganhouItemText;
    public static Texture vitoriaText;

    // Novo asset do botão de retorno
    public static Texture returnButton;

    public static Sound songCorrect;
    public static Sound songWrong;
    public static Sound songButton;

    private AssetUtils() {
    }

    public static void initAssets() {
        loadAssets();
    }

    private static void loadAssets() {
        playButton = new Texture("play_button.png");
        playButtonHighlight = new Texture("play_button_highlighted.png");
        settingsButton = new Texture("settingsButton.png");
        settingsButtonHighlight = new Texture("settingsButtonHighlight.png");
        title = new Texture("tittle_game.png");
        backgroundMenu = new Texture("bg_mainmenu.jpg");
        itemButtonTexture = new Texture(Gdx.files.internal("item_button.png"));
        itemButtonHighlightTexture = new Texture(Gdx.files.internal("item_button_highlight.png"));

        item1 = new Texture("item1.png"); // Carregamento do item1
        item2 = new Texture("item2.png"); // Carregamento do item2
        item3 = new Texture("item3.png"); // Carregamento do item3
        item4 = new Texture("item4.png"); // Carregamento do item4

        selectLevelText = new Texture("select_level_text.png");
        levelButton1 = new Texture("level_button_1.png");
        levelButton2 = new Texture("level_button_2.png");
        levelButton3 = new Texture("level_button_3.png");
        levelButton4 = new Texture("level_button_4.png"); // Carregamento do botão do nível 4

        backgroundBattle1 = new Texture("bg_battle_1.png");
        backgroundBattle2 = new Texture("bg_battle_2.png");
        backgroundBattle3 = new Texture("bg_battle_3.png");
        backgroundBattle4 = new Texture("bg_battle_4.png"); // Carregamento do fundo da batalha 4

        avatar = new Texture("avatar.png");
        enemyDino = new Texture("enemy_dino.png");
        enemyJacare = new Texture("enemy_jacare.png");
        enemyTigre = new Texture("enemy_tigre.png");
        enemyDino2 = new Texture("enemy_dino2.png");

        answerButton = new Texture("answer_button.png");
        attackButton = new Texture("attack_button.png");
        attackButtonHighlight = new Texture("attack_button_highlighted.png");
        heart = new Texture("heart.png");

        venceuText = new Texture("text_venceu.png");
        perdeuText = new Texture("text_perdeu.png");
        ganhouItemText = new Texture("ganhouItemText.png");
        vitoriaText = new Texture("victoryText.png");

        // Carregamento do asset do botão de retorno
        returnButton = new Texture("ReturnButton.png");

        // Corrigido para o caminho correto dos arquivos de som
        songCorrect = Gdx.audio.newSound(Gdx.files.internal("song_correct.mp3"));
        songWrong = Gdx.audio.newSound(Gdx.files.internal("song_wrong.mp3"));
        songButton = Gdx.audio.newSound(Gdx.files.internal("song_button.mp3"));
    }

    public static void dispose() {
        playButton.dispose();
        playButtonHighlight.dispose();
        settingsButton.dispose();
        settingsButtonHighlight.dispose();
        title.dispose();
        backgroundMenu.dispose();
        itemButtonTexture.dispose();
        itemButtonHighlightTexture.dispose();

        item1.dispose(); // Dispose do item1
        item2.dispose(); // Dispose do item2
        item3.dispose(); // Dispose do item3
        item4.dispose(); // Dispose do item4

        selectLevelText.dispose();
        levelButton1.dispose();
        levelButton2.dispose();
        levelButton3.dispose();
        levelButton4.dispose(); // Dispose do botão do nível 4

        backgroundBattle1.dispose();
        backgroundBattle2.dispose();
        backgroundBattle3.dispose();
        backgroundBattle4.dispose(); // Dispose do fundo da batalha 4

        avatar.dispose();
        enemyDino.dispose();
        enemyJacare.dispose();
        enemyTigre.dispose();

        answerButton.dispose();
        attackButton.dispose();
        attackButtonHighlight.dispose();
        heart.dispose();

        venceuText.dispose();
        perdeuText.dispose();
        ganhouItemText.dispose();
        vitoriaText.dispose();

        // Dispose do novo asset do botão de retorno
        returnButton.dispose();

        songCorrect.dispose();
        songWrong.dispose();
        songButton.dispose();
    }
}

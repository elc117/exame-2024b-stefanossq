# Exame Final Paradigmas de Programação - ELC117
Nome: Stéfano Camargo Squarcieri
Curso: Sistemas de informação


O objetivo é entregar melhorias no jogo e/ou no código, garantindo que o projeto esteja refinado e funcional.

## Reorganização das Battlescreens

> No projeto original (https://github.com/elc117/game-2023b-coloniarpg) já tinha duas classes que compartilhavam de propriedades semelhantes mas eram separadas, na minha entrega eu criei mais duas, essas classes foram reduzidas pra GenericBattleScreen.java e BattleState.java :
---
 **GenericBattleScreen.java** : Gerência das telas e dos parâmetros das batalhas

 **BattleState.java**: Dados do estado da batalha 

Ta, a classe BattleState guarda a vida do jogador/inimigo e o indexpergunta é a posicao da pergunta do quiz, foi uma forma de isolar o estado da batalha. E a GenericBattleScreen da o render nos assets e botoes da tela e chama as tela de vitoria/derrota. Pra criar um novo nivel agora so parametrizar as texturas , antes tinha que criar uma classe nova e fica chamando assets e bem dizer toda battlescreen ficava sendo duplicada. 

### Tela Vitória/Derrota :

Antes, o projeto usava uma classe única chamada TextScreen, que exibia mensagens de vitória ou derrota, com base em boolean (dead). A motivo pra criar as screens Victory/Defeat foi a adição da recompensa na  tela de vitória que mostra o item ganho (um orb especifico), e uma tela de derrota. Da p considerar um refinamento visual tbm, ja que antes era mais simples a tela.

#### QuestionScreen1:
As perguntas e respostas ficavam tudo na mesma classe, entao separei em QuestionRepository as perguntas, alternativas e respostas. O metódo de transiçao da tela tava duplicado sendo que fazia a mesma coisa, onde o metodo startScreenBattleTransition unificou o startScreenBattleTransition1 e startScreenBattleTransition2. Tava meio que uma bola de neve se quisesse criar outra batalha tinha que copiar a classe inventar mais um startScreenBattle3Transition e com as questoes junto só essa classe tava gigante.

```java

// ANTES
private void startScreenBattle1Transition() {
    BattleScreen1 battleScreenInstance = new BattleScreen1(game);
    fadeScreen = new FadeScreen(game, fadeOut, this, battleScreenInstance);
    game.setScreen(fadeScreen);
}

private void startScreenBattle2Transition() {
    BattleScreen2 battleScreenInstance = new BattleScreen2(game);
    fadeScreen = new FadeScreen(game, fadeOut, this, battleScreenInstance);
    game.setScreen(fadeScreen);
}
```
Atualizado :
```java
private void startScreenBattleTransition(Screen nextScreen) {
    fadeScreen = new FadeScreen(game, fadeOut, this, nextScreen);
    game.setScreen(fadeScreen);
}
```

##### Mudanças, considerações visuais...
O sistema de HP, vida e tal era uma Label com número e gambiarrada com as coordenadas pra ficar no meio do asset do coração.
Se o layout mudasse o numero ficava estatico lá e fora que se por exemplo o hp fosse de 3 digitos ja ia zoar o jeito que ficava.

```java
Label vidaAvatarLabel = new Label("" + vidaAvatar, style);
Group vidaAvatarGroup = new Group();
vidaAvatarGroup.addActor(heart1);
vidaAvatarGroup.addActor(vidaAvatarLabel);
vidaAvatarLabel.setPosition(heart1.getWidth() / 2 - vidaAvatarLabel.getWidth() / 2, heart1.getHeight() / 2 - vidaAvatarLabel.getHeight() / 2);
vidaAvatarGroup.setPosition(avatarX + 40, avatarY);
```
**ISSO ERA SÓ PRA VIDA DO AVATAR**


**ATUALIZADO:**
```java
Group vidaAvatarGroup = createHealthGroup(AssetUtils.heart, style, Math.max(0, BattleState.vidaAvatar), avatarX + 40, avatarY);
Group vidaInimigoGroup = createHealthGroup(AssetUtils.heart, style, Math.max(0, BattleState.vidaInimigo), enemyX + 80, enemyY + 20);
```
**Método Auxiliar createHealthGroup:**
```java

private Group createHealthGroup(Texture heartTexture, Label.LabelStyle style, int health, float x, float y) {
    Group group = new Group();
    for (int i = 0; i < health; i++) {
        Image heart = new Image(heartTexture);
        heart.setPosition(x + i * 40, y);
        group.addActor(heart);
    }
    return group;
}
```
![Inserir um título](https://github.com/user-attachments/assets/2b6eaf96-9e49-4067-a6c0-065c8ff8f4eb)

###### LEVEL3 ASSET (VILA BELGA): 

![level_button_3](https://github.com/user-attachments/assets/042ee533-342a-4411-9882-c47ea8111634)

### CODE FIXES DA VERSÃO ORIGINAL (Mensões honrosas):
Gradle 1.7 já ta obsoleto, o próprio já ta avisando, o projeto todo ta com suporte a java 8 agora(Apesar de estarmos no 14 , eu acho).
Não sei se o projeto original foi entregue numa versão não definitiva mas já de ínicio ao tentar buildar desktop os assets de som nao estavam declarados apesar de existirem.
Dispose de elementos das questões por exemplo faziam com que multiplos cliques virassem bugs nas telas de batalha, fixed. 

### CONCLUSÃO:
Detalhamento das mudanças foi feito em cada tópico, mas concluindo fiz refatorações nas classes BattleScreens unificando pra GenericBattleScreen, telas de vitoria/derrota, mudança na logica do armazenamento das perguntas  e transicao de tela na QuestionScreen1 e algumas mudanças no visual do jogo. Segue o Diagrama de classes atualizado :
![Exame2024bUML](https://github.com/user-attachments/assets/7b46d048-69ef-4b3a-9f8b-48d0da440bc6)

### GAME DEMO:




https://github.com/user-attachments/assets/f72b1bcc-30db-4ba3-b9b4-e8edf956cfad

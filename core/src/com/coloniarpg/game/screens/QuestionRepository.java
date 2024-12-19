package com.coloniarpg.game.screens;

public class QuestionRepository {

    public String[] getQuestions(int battleScreen) {
        switch (battleScreen) {
            case 1:
                return new String[]{
                    "Qual é o nome do rio que corta a cidade de São João do Polêsine?",
                    "Em que ano São João do Polêsine foi emancipada?",
                    "Qual é a principal atração turística de São João do Polêsine?",
                    "Qual é o prato típico de São João do Polêsine?",
                    "Qual é a principal atividade econômica de São João do Polêsine?"
                };
            case 2:
                return new String[]{
                    "Em que ano Pinhal Grande foi emancipada?",
                    "Qual é a principal atividade econômica de Pinhal Grande?",
                    "Qual é a principal atração turística de Pinhal Grande?",
                    "Qual é o nome do santo padroeiro de Pinhal Grande?",
                    "Qual é o rio que forma a divisa natural de Pinhal Grande?"
                };
            case 3:
                return new String[]{
                    "Qual é o nome do engenheiro que projetou a Vila Belga ?",
                    "Em que ano a Vila Belga foi inaugurada?",
                    "Qual a ocupação dos primeiros moradores da Vila Belga?",
                    "Qual o nome da principal rua da Vila Belga?",
                    "O que acontece na Vila Belga nos segundos e terceiros domingos de cada mês?"
                };
            case 4:
                return new String[]{
                    "Qual planeta é conhecido como o 'Planeta Vermelho'?",
                    "Qual é o satélite natural da Terra?",
                    "Quantos planetas existem no nosso sistema solar?",
                    "Quem foi o primeiro homem a pisar na Lua?",
                    "Qual é o maior planeta do nosso sistema solar?"
                };
            default:
                return new String[]{};
        }
    }

    public String[] getAnswers(int battleScreen) {
        switch (battleScreen) {
            case 1:
                return new String[]{
                    "Rio Jacuí", "Rio Uruguai", "Rio Ibicuí", "Rio Paraná",
                    "1991", "1992", "1993", "1994",
                    "Igreja Matriz São João Batista", "Museu Municipal", "Parque Municipal", "Santuário Nossa Senhora de Fátima",
                    "Pizza", "Xis", "Galeto à moda gaúcha", "Carne de porco assada",
                    "Agricultura", "Pecuária", "Turismo", "Indústria"
                };
            case 2:
                return new String[]{
                    "1990", "1991", "1992", "1993",
                    "Agricultura", "Pecuária", "Turismo", "Indústria",
                    "Festa do trabalhador", "Festa do Colono", "Festa do Pinhão", "Festa do Peão",
                    "São José", "São João", "São Pedro", "São Paulo",
                    "Rio Ibicuí", "Rio Uruguai", "Rio Jacuí", "Rio Paraná"
                };
            case 3:
                return new String[]{
                    "Gustave Vauthier", "Gustavo Walter", "George Washignton", "Gustavo Lima",
                    "1800", "1900", "1905", "1999",
                    "Artesãos", "Ferroviários", "Agricultores", "Alfaiates",
                    "Dr.Bozano", "Dr.Wauthier", "Dr.House", "Dr.Vauthier",
                    "Brique da Vila Belga", "BelgaParty", "Café Belga", "Beyblade"
                };
            case 4:
                return new String[]{
                    "Marte", "Vênus", "Júpiter", "Saturno",
                    "Lua", "Sol", "Marte", "Júpiter",
                    "8", "9", "10", "11",
                    "Neil Armstrong", "Buzz Aldrin", "Yuri Gagarin", "John Glenn",
                    "Júpiter", "Saturno", "Urano", "Netuno"
                };
            default:
                return new String[]{};
        }
    }

    public String[] getCorrectAnswers(int battleScreen) {
        switch (battleScreen) {
            case 1:
                return new String[]{
                    "Rio Ibicuí", "1992", "Igreja Matriz São João Batista", "Galeto à moda gaúcha", "Agricultura"
                };
            case 2:
                return new String[]{
                    "1992", "Agricultura", "Festa do Pinhão", "São José", "Rio Jacuí"
                };
            case 3:
                return new String[]{
                    "Gustave Vauthier", "1905", "Ferroviários", "Dr.Wauthier", "Brique da Vila Belga"
                };
            case 4:
                return new String[]{
                    "Marte", "Lua", "8", "Neil Armstrong", "Júpiter"
                };
            default:
                return new String[]{};
        }
    }
}

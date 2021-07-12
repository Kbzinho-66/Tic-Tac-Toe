package com.example.jogodavelha

import androidx.core.content.ContextCompat

data class StatusJogo(private val main: MainActivity) {

    var jogadas: Int = 0
    var turno: Turno = Turno.ZERO
    var ganhador: Int = 0

    fun posicaoJogadorNoVetor(): Int {

        // Retorna a posição do jogador no vetor de jogadores usado em Jogo
        return when (turno) {
            Turno.JOGADOR_1 -> 0
            Turno.JOGADOR_2 -> 1
            Turno.VITORIA -> ganhador
            else -> -1
        }
    }

    fun trocaTurno() {

        /* Troca o turno e faz as mudanças gráficas devidas */
        when (turno) {

            Turno.JOGADOR_1 -> {
                turno = Turno.JOGADOR_2

                OperacoesEmViews.apply {
                    mudarTexto(main.getString(R.string.player_2))
                    mudarCorTexto(ContextCompat.getColor(main, R.color.Aurora2))
                    mudarCorBotaoJogador1(ContextCompat.getColor(main, R.color.PolarNight4))
                    mudarCorBotaoJogador2(ContextCompat.getColor(main, R.color.Aurora2))
                }

            }

            Turno.JOGADOR_2 -> {
                turno = Turno.JOGADOR_1

                OperacoesEmViews.apply {
                    mudarTexto(main.getString(R.string.player_1))
                    mudarCorTexto(ContextCompat.getColor(main, R.color.Cyan))
                    mudarCorBotaoJogador1(ContextCompat.getColor(main, R.color.Cyan))
                    mudarCorBotaoJogador2(ContextCompat.getColor(main, R.color.PolarNight4))
                }

            }

            else -> return

        }

        jogadas++

    }

    fun jogador1Comeca(jogo: Jogo) {

        /* Se o jogo ainda não tiver começado e ambos jogadores já tiverem foto,
        marca esse jogador para começar */
        if (turno == Turno.ZERO && jogo.jogadores[1].temFoto) {

            turno = Turno.JOGADOR_1

            OperacoesEmViews.apply {
                mudarTexto(main.getString(R.string.player_1))
                mudarCorTexto(ContextCompat.getColor(main, R.color.Cyan))
                mudarCorBotaoJogador1(ContextCompat.getColor(main, R.color.Cyan))
            }

        }

    }

    fun jogador2Comeca(jogo: Jogo) {

        /* Se o jogo ainda não tiver começado e ambos jogadores já tiverem foto,
        marca esse jogador para começar */
        if (turno == Turno.ZERO && jogo.jogadores[0].temFoto) {

            turno = Turno.JOGADOR_2

            OperacoesEmViews.apply {
                mudarTexto(main.getString(R.string.player_2))
                mudarCorTexto(ContextCompat.getColor(main, R.color.Aurora2))
                mudarCorBotaoJogador2(ContextCompat.getColor(main, R.color.Aurora2))
            }

        }
    }

    fun reiniciar() {

        jogadas = 0
        turno = Turno.ZERO

        OperacoesEmViews.apply {
            mudarTexto(main.getString(R.string.pick_first))
            mudarCorTexto(ContextCompat.getColor(main, R.color.Red))
            mudarCorBotaoJogador1(ContextCompat.getColor(main, R.color.PolarNight4))
            mudarCorBotaoJogador2(ContextCompat.getColor(main, R.color.PolarNight4))
        }
    }

}

enum class Turno {
    ZERO, JOGADOR_1, JOGADOR_2, VITORIA, EMPATE
}
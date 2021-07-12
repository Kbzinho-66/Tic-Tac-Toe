package com.example.jogodavelha

import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat

class Jogo(private val main: MainActivity) {

    var status = StatusJogo(main)
    var tabuleiro = Tabuleiro(main, status)
    var jogadores = arrayOf(Jogador(main.applicationContext), Jogador(main.applicationContext))

    private fun getJogadorAtual(): Jogador {
        return jogadores[status.posicaoJogadorNoVetor()]
    }

    fun realizaJogada(posicao: Int) {

        /*
        Recebe a posição escolhida por um jogador, tenta preencher a célula com
        a imagem adequada e verifica se essa jogada causou uma vitória ou um empate
         */

        when (status.turno) {

            Turno.ZERO -> OperacoesEmViews.mudarTexto(main.getString(R.string.pick_first))

            Turno.JOGADOR_1, Turno.JOGADOR_2 -> {

                if (status.jogadas < 9 && tabuleiro.celulaNaoTemFoto(posicao)) {
                    tabuleiro.preencheCelula(getJogadorAtual().uriImagem, posicao)

                    when {
                        tabuleiro.verificaVitoria() -> {
                            status.ganhador = status.posicaoJogadorNoVetor() + 1
                            status.turno = Turno.VITORIA
                        }
                        status.jogadas + 1 == 9 -> status.turno = Turno.EMPATE
                        else -> status.trocaTurno()
                    }
                }
            }

            Turno.VITORIA, Turno.EMPATE -> return

        }

    }

    fun testaVitoria() {

        /* Tratamento gráfico após vitória e empate */

        if (status.turno == Turno.VITORIA) {

            vitoria()

            Handler(Looper.getMainLooper()).postDelayed({
                tabuleiro.limparTabuleiro()
            }, 3000L)

        } else if (status.turno == Turno.EMPATE) {

            empate()

            Handler(Looper.getMainLooper()).postDelayed({
                tabuleiro.limparTabuleiro()
            }, 3000L)

        }
    }

    private fun vitoria() {

        val cor: Int = if (status.ganhador == 1) {
            R.color.Cyan
        } else {
            R.color.Aurora2
        }

        // TODO (Implementar uma tela de ganhador)
        OperacoesEmViews.apply {
            mudarTexto(main.getString(R.string.victory, status.ganhador))
            mudarCorTexto(ContextCompat.getColor(main, cor))
        }

    }

    private fun empate() {

        OperacoesEmViews.apply {
            mudarTexto(main.getString(R.string.draw))
            mudarCorTexto(ContextCompat.getColor(main, R.color.Red))
        }

    }

    fun clear() {

        /* Função de limpeza "nuclear", que apaga tudo */
        status.reiniciar()
        tabuleiro.limparTabuleiro()
        tabuleiro.limparJogadores()
        jogadores = arrayOf(Jogador(main.applicationContext), Jogador(main.applicationContext))
        OperacoesEmViews.mudarTexto(main.getString(R.string.begin))
    }
}
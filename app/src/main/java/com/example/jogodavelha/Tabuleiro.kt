package com.example.jogodavelha

import android.net.Uri
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.bumptech.glide.Glide

class Tabuleiro(private val main: MainActivity, private val status: StatusJogo) {

    val views: Array<ImageView> = arrayOf(
            main.findViewById(R.id.posicao_0), main.findViewById(R.id.posicao_1), main.findViewById(R.id.posicao_2),
            main.findViewById(R.id.posicao_3), main.findViewById(R.id.posicao_4), main.findViewById(R.id.posicao_5),
            main.findViewById(R.id.posicao_6), main.findViewById(R.id.posicao_7), main.findViewById(R.id.posicao_8)
    )


    private val fotoNaCelula: Array<FotoJogador> = arrayOf(
            FotoJogador.VAZIA, FotoJogador.VAZIA, FotoJogador.VAZIA,
            FotoJogador.VAZIA, FotoJogador.VAZIA, FotoJogador.VAZIA,
            FotoJogador.VAZIA, FotoJogador.VAZIA, FotoJogador.VAZIA
    )

    var ultimaJogada: Int = 0

    fun celulaNaoTemFoto(posicao: Int): Boolean {
        return fotoNaCelula[posicao] == FotoJogador.VAZIA
    }

    fun preencheCelula(uri: Uri?, posicao: Int) {

        uri?.let {
            Glide
                .with(main)
                .load(it)
                .into(views[posicao])
        }
        fotoNaCelula[posicao] = fotoJogadorAtual()
        ultimaJogada = posicao
    }

    private fun fotoJogadorAtual(): FotoJogador {

        // retorna o flag usado para identificar de qual jogador é uma foto
        return if (status.turno == Turno.JOGADOR_1) FotoJogador.UM else FotoJogador.DOIS
    }

    fun verificaVitoria(): Boolean {

        /*
         Agrupa os pares de células que podem garantir a vitória com base na ultima jogada
         ---------
         0 - 1 - 2
         3 - 4 - 5
         6 - 7 - 8
         ---------
        */

        return when (ultimaJogada) {
            0 -> verificaPares(arrayOf(Pair(1, 2), Pair(4, 8), Pair(3, 6)))
            1 -> verificaPares(arrayOf(Pair(0, 2), Pair(4, 7)))
            2 -> verificaPares(arrayOf(Pair(0, 1), Pair(4, 6), Pair(5, 8)))
            3 -> verificaPares(arrayOf(Pair(0, 6), Pair(4, 5)))
            4 -> verificaPares(arrayOf(Pair(0, 8), Pair(1, 7), Pair(2, 6), Pair(3, 5)))
            5 -> verificaPares(arrayOf(Pair(2, 8), Pair(3, 4)))
            6 -> verificaPares(arrayOf(Pair(0, 3), Pair(2, 4), Pair(7, 8)))
            7 -> verificaPares(arrayOf(Pair(1, 4), Pair(6, 8)))
            8 -> verificaPares(arrayOf(Pair(0, 4), Pair(2, 5), Pair(6, 7)))
            else -> false
        }

    }

    private fun verificaPares(pares: Array<Pair<Int, Int>>): Boolean {

        /* Verifica cada um dos pares de células e se encontrar um vencedor,
        chama a função que mostra as 3 células */

        val jogadorAtual = fotoJogadorAtual()
        for (par in pares) {
            if (jogadorAtual == fotoNaCelula[par.first] &&
                    fotoNaCelula[par.first] == fotoNaCelula[par.second]
            ) {
                mostrarJogadaVitoriosa(par)
                return true
            }
        }

        return false
    }

    private fun mostrarJogadaVitoriosa(jogada: Pair<Int, Int>) {

        val padding = 30

        when (status.turno) {

            Turno.JOGADOR_1 -> {

                views[jogada.first].apply {
                    setPadding(padding)
                    setBackgroundColor(ContextCompat.getColor(context, R.color.Cyan))
                }
                views[ultimaJogada].apply {
                    setPadding(padding)
                    setBackgroundColor(ContextCompat.getColor(context, R.color.Cyan))
                }
                views[jogada.second].apply {
                    setPadding(padding)
                    setBackgroundColor(ContextCompat.getColor(context, R.color.Cyan))
                }
            }

            Turno.JOGADOR_2 -> {

                views[jogada.first].apply {
                    setPadding(padding)
                    setBackgroundColor(ContextCompat.getColor(context, R.color.Aurora2))
                }
                views[ultimaJogada].apply {
                    setPadding(padding)
                    setBackgroundColor(ContextCompat.getColor(context, R.color.Aurora2))
                }
                views[jogada.second].apply {
                    setPadding(padding)
                    setBackgroundColor(ContextCompat.getColor(context, R.color.Aurora2))
                }
            }

            else -> return
        }
    }

    fun limparTabuleiro() {

        /* Tira a foto de todas as células e limpa o flag que identifica o jogador, mas mantém
        as fotos dos jogadores */

        for (pos in 0..8) {
            Glide.with(main).clear(views[pos])
            views[pos].apply {
                setPadding(0)
                setBackgroundColor(ContextCompat.getColor(context, R.color.PolarNight4))
            }
            fotoNaCelula[pos] = FotoJogador.VAZIA
        }

        status.reiniciar()
    }

    fun limparJogadores() {

        val botaoJogador1 = main.findViewById<ImageButton>(R.id.botaoJogador1)
        Glide.with(main).clear(botaoJogador1)
        val botaoJogador2 = main.findViewById<ImageButton>(R.id.botaoJogador2)
        Glide.with(main).clear(botaoJogador2)

    }

}

enum class FotoJogador {
    VAZIA, UM, DOIS
}
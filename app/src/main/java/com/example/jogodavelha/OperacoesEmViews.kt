package com.example.jogodavelha

import android.widget.ImageButton
import android.widget.TextView

class OperacoesEmViews {

    companion object {

        private lateinit var main: MainActivity

        fun setMain(m: MainActivity) {
            main = m
        }

        fun mudarTexto(string: String) {
            main.findViewById<TextView>(R.id.indicadorDeTurno).text = string
        }

        fun mudarCorTexto(cor: Int) {
            main.findViewById<TextView>(R.id.indicadorDeTurno).setTextColor(cor)
        }

        fun mudarCorBotaoJogador1(cor: Int) {
            main.findViewById<ImageButton>(R.id.botaoJogador1)
                    .setBackgroundColor(cor)
        }

        fun mudarCorBotaoJogador2(cor: Int) {
            main.findViewById<ImageButton>(R.id.botaoJogador2)
                    .setBackgroundColor(cor)
        }

    }
}
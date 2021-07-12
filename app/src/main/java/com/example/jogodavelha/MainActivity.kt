package com.example.jogodavelha

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val CODIGO_CAMERA = 1
    private val CODIGO_ESCRITA = 2

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        OperacoesEmViews.setMain(this)

        val jogo = Jogo(this)

        requisitaPermissoes()

        // region Inicialização Botões
        /* Por algum motivo, toda essa parte tem que ficar na função onCreate para funcionar */

        val atividadeTirarFotoJogador1 =
                registerForActivityResult(ActivityResultContracts.TakePicture()) { ok ->
                    if (ok) {

                        Glide
                            .with(this)
                            .load(jogo.jogadores[0].uriImagem)
                            .into(botaoJogador1)

                        botaoJogador1.apply {
                            setBackgroundColor(ContextCompat.getColor(context, R.color.Black))
                            foreground = null
                            rotation = 90F
                        }

                        jogo.jogadores[0].temFoto = true
                    }
                }

        botaoJogador1.setOnClickListener {
            if (jogo.jogadores[0].temFoto) {
                jogo.status.jogador1Comeca(jogo)
            } else {
                atividadeTirarFotoJogador1.launch(jogo.jogadores[0].uriImagem)
            }
        }

        val atividadeTirarFotoJogador2 =
                registerForActivityResult(ActivityResultContracts.TakePicture()) { ok ->
                    if (ok) {

                        Glide
                            .with(this)
                            .load(jogo.jogadores[1].uriImagem)
                            .into(botaoJogador2)

                        botaoJogador2.apply {
                            setBackgroundColor(ContextCompat.getColor(context, R.color.Black))
                            foreground = null
                            rotation = 90F
                        }

                        jogo.jogadores[1].temFoto = true
                    }
                }

        botaoJogador2.setOnClickListener {
            if (jogo.jogadores[1].temFoto) {
                jogo.status.jogador2Comeca(jogo)
            } else {
                atividadeTirarFotoJogador2.launch(jogo.jogadores[1].uriImagem)
            }
        }

        botaoClear.setOnClickListener { jogo.clear() }

        for (celula in jogo.tabuleiro.views) {

            celula.apply {
                rotation = 90F
                setOnClickListener { view ->
                    when (view) {

                        posicao_0 -> {
                            jogo.apply {
                                realizaJogada(0)
                                testaVitoria()
                            }
                        }
                        posicao_1 -> {
                            jogo.apply {
                                realizaJogada(1)
                                testaVitoria()
                            }
                        }
                        posicao_2 -> {
                            jogo.apply {
                                realizaJogada(2)
                                testaVitoria()
                            }
                        }
                        posicao_3 -> {
                            jogo.apply {
                                realizaJogada(3)
                                testaVitoria()
                            }
                        }
                        posicao_4 -> {
                            jogo.apply {
                                realizaJogada(4)
                                testaVitoria()
                            }
                        }
                        posicao_5 -> {
                            jogo.apply {
                                realizaJogada(5)
                                testaVitoria()
                            }
                        }
                        posicao_6 -> {
                            jogo.apply {
                                realizaJogada(6)
                                testaVitoria()
                            }
                        }
                        posicao_7 -> {
                            jogo.apply {
                                realizaJogada(7)
                                testaVitoria()
                            }
                        }
                        posicao_8 -> {
                            jogo.apply {
                                realizaJogada(8)
                                testaVitoria()
                            }
                        }
                    }
                }
            }

        }

        // endregion Inicialização Botões
    }

    // region Funções de Permissão

    // TODO(Quem sabe refatorar pra registerForActivityResult)
    private fun requisitaPermissoes() {
        verificaPermissoes(android.Manifest.permission.CAMERA, "Câmera", CODIGO_CAMERA)
        verificaPermissoes(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                "Escrita",
                CODIGO_ESCRITA
        )
    }

    private fun verificaPermissoes(permissao: String, nome: String, codigoPermissao: Int) {

        when {

            ContextCompat.checkSelfPermission(
                    applicationContext,
                    permissao
            ) == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(
                        applicationContext,
                        "Permissão de $nome garantida",
                        Toast.LENGTH_SHORT
                ).show()
            }

            shouldShowRequestPermissionRationale(permissao) -> mostraAlerta(
                    permissao,
                    nome,
                    codigoPermissao
            )

            else -> ActivityCompat.requestPermissions(this, arrayOf(permissao), codigoPermissao)

        }

    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        fun verificacao(nome: String) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                        applicationContext,
                        "Permissão de $nome recusada",
                        Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                        applicationContext,
                        "Permissão de $nome garantida",
                        Toast.LENGTH_SHORT
                ).show()
            }
        }

        when (requestCode) {
            CODIGO_CAMERA -> verificacao("Câmera")
            CODIGO_ESCRITA -> verificacao("Escrita")
        }
    }

    private fun mostraAlerta(permissao: String, nome: String, codigoPermissao: Int) {
        val builder = AlertDialog.Builder(this)

        builder.apply {
            setMessage("Você precisa conceder permissão de $nome para usar esse aplicativo.")
            setTitle("Permissão Necessária")
            setPositiveButton("OK") { alerta, qual ->
                ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(permissao),
                        codigoPermissao
                )
            }
        }

        val alerta = builder.create()
        alerta.show()
    }

    // endregion Funções de Permissão

}

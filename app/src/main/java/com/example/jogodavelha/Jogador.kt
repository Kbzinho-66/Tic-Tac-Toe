package com.example.jogodavelha

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class Jogador(contexto: Context) {

    var temFoto = false
    val uriImagem: Uri? by lazy { criaArquivo(contexto) }


    private fun criaArquivo(contexto: Context): Uri? {

        // TODO (Mudar isso aqui)
        val timestamp = SimpleDateFormat("yyyyMMdd_Hhmmss").format(Date())
        val pasta = contexto.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imagem = pasta?.let { it.path + File.separator + "${timestamp}.jpg" }
        val caminhoImagem: File? = imagem?.let { File(it) }

        return caminhoImagem?.let {
            FileProvider.getUriForFile(contexto.applicationContext,
                    contexto.applicationContext.packageName + ".provider", it)
        }
    }

}


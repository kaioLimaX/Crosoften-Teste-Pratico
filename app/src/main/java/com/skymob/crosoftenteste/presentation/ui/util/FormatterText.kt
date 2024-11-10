package com.skymob.crosoftenteste.presentation.ui.util

import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun setupClickableTextView(
    textView: TextView,
    fullText: String,
    linkText: String,
    onClick: () -> Unit // Função lambda para o clique
) {
    // Cria um SpannableString com o texto completo
    val spannableString = SpannableString(fullText)

    // Define o índice onde o linkText começa
    val startIndex = fullText.indexOf(linkText)
    val endIndex = startIndex + linkText.length

    // Adiciona um ClickableSpan
    spannableString.setSpan(object : ClickableSpan() {
        override fun onClick(widget: View) {
            // Chama a função lambda ao clicar no texto clicável
            onClick()
        }
    }, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    // Define o SpannableString no TextView
    textView.text = spannableString
    textView.movementMethod = LinkMovementMethod.getInstance() // Permite clicar no link
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(dateString: String): String {
    val formatter = DateTimeFormatter
        .ofPattern("dd/MM/yyyy HH:mm:ss")
        .withZone(ZoneId.of("America/Sao_Paulo"))

    // Converte a string para Instant
    val instant = Instant.parse(dateString)

    // Formata o Instant para o formato desejado
    return formatter.format(instant)
}
package com.skymob.crosoftenteste.presentation.ui.util

import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView

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
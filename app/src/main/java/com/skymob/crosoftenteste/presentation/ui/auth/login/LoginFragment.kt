package com.skymob.crosoftenteste.presentation.ui.auth.login

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.skymob.crosoftenteste.R
import com.skymob.crosoftenteste.databinding.FragmentLoginBinding
import com.skymob.crosoftenteste.presentation.ui.base.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    override val viewModel: LoginViewModel by viewModels()


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            goToMain()
        }
        //configurar o texto do botão "Registre-se"
        formatTextButtonRegister()

    }

    private fun formatTextButtonRegister() {
        val textRegister = binding.txtRegister.text.toString()
        setupClickableTextView(
            binding.txtRegister,
            textRegister,
            "Registre-se",
            onClick = {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        )
    }

    //cria um hiperlink e onclick para o texto
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

    private fun goToMain() {
        findNavController().navigate(
            R.id.action_loginFragment_to_mainFragment,
            null,
            NavOptions.Builder()
                .setPopUpTo(R.id.loginFragment, true)
                .build()
        )
    }
}
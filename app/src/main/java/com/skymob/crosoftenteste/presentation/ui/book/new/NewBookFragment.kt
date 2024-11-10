package com.skymob.crosoftenteste.presentation.ui.book.new

import android.Manifest
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.permissionx.guolindev.PermissionX
import com.skymob.crosoftenteste.data.remote.dto.book.BookRequest
import com.skymob.crosoftenteste.databinding.FragmentNewBookBinding
import com.skymob.crosoftenteste.presentation.ui.base.BaseFragment
import com.skymob.crosoftenteste.presentation.ui.state.ViewState
import com.skymob.crosoftenteste.util.AlertLoading
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewBookFragment : BaseFragment<FragmentNewBookBinding, NewBookViewModel>() {
    var selectedImageUri: Uri? = null
    override val viewModel: NewBookViewModel by viewModel()
    private val alertLoading by lazy {
        AlertLoading(requireContext())
    }

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            selectedImageUri = uri
            binding.imgBook.setImageURI(uri)
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewBookBinding = FragmentNewBookBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initOnClick()
        initObserver()
        checkPermissions()

    }

    private fun initObserver() {
        viewModel.state.observe(viewLifecycleOwner){
            when(it){
                is ViewState.Error -> {
                    alertLoading.close()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is ViewState.Loading -> {
                    alertLoading.show("Adicionando novo Livro")
                }
                is ViewState.Sucess -> {
                    alertLoading.close()
                    findNavController().navigateUp()
                    Toast.makeText(requireContext(), "Livro adicionado com sucesso", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initOnClick() {
        with(binding){
            btnSelectImage.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            btnRegisterNewBook.setOnClickListener {
                val title = edtTitle.text.toString()
                val author = edtAuthor.text.toString()
                val description = edtDescription.text.toString()

                if (selectedImageUri != null) {
                    if (title.isNotEmpty() && author.isNotEmpty() && description.isNotEmpty()) {
                        val bookRequest = BookRequest(
                            title = title,
                            author = author,
                            categoryId = 1,
                            summary = description
                        )
                        // Chama o ViewModel para processar o envio
                        viewModel.uploadImageAndAddBook(selectedImageUri!!, bookRequest)
                    } else {
                        Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Selecione uma imagem", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkPermissions() {
        val permissions = mutableListOf<String>()

        // Adiciona permissões com base na versão do Android
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            permissions.add(Manifest.permission.READ_MEDIA_IMAGES)
            permissions.add(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
        } else { // Android 12 ou inferior
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        // Solicita permissões usando PermissionX
        PermissionX.init(this)
            .permissions(permissions)
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(deniedList, "Você precisa permitir as permissões necessárias nas configurações manualmente", "OK", "Cancelar")
            }
            .request { allGranted, grantedList, deniedList ->
                if (!allGranted) {
                    Toast.makeText(requireContext(), "Permissão negada", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
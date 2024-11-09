package com.skymob.crosoftenteste.presentation.ui.book.new

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.permissionx.guolindev.PermissionX
import com.skymob.crosoftenteste.databinding.FragmentNewBookBinding
import com.skymob.crosoftenteste.presentation.ui.base.BaseFragment

class NewBookFragment : BaseFragment<FragmentNewBookBinding, NewBookViewModel>() {
    override val viewModel: NewBookViewModel by viewModels()


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewBookBinding = FragmentNewBookBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermissions()
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
                scope.showForwardToSettingsDialog(deniedList, "You need to allow necessary permissions in Settings manually", "OK", "Cancel")
            }
            .request { allGranted, grantedList, deniedList ->
                if (!allGranted) {
                    Toast.makeText(requireContext(), "Permissão negada", Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(requireContext(), "Permissão concedida", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
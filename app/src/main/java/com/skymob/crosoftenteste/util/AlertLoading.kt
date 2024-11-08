package com.skymob.crosoftenteste.util

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.skymob.crosoftenteste.R

class AlertLoading(
    private val context: Context
) {

    private val viewCarregamento = View.inflate(
        context, R.layout.layout_carregamento, null
    )
    private var alertDialog: AlertDialog? = null

    fun show( titulo: String ){
        val alertBuilder = AlertDialog.Builder( context )
            .setTitle( titulo )
            .setCancelable( false )
            .setView( viewCarregamento )

        alertDialog = alertBuilder.create()
        alertDialog?.show()
    }

    fun close(){
        alertDialog?.setOnDismissListener {
            val viewGroup = viewCarregamento.parent as ViewGroup
            viewGroup.removeView( viewCarregamento )
        }
        alertDialog?.dismiss()
    }

}
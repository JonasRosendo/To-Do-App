package com.jonasrosendo.to_doapp

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

object TaskUtils {

    fun allTextsFilled(title: String, description: String) =
        title.isNotEmpty() && description.isNotEmpty()

    fun showFeedbackMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun confirmItemRemoval(
        context: Context,
        title: String,
        message: String,
        onPositiveClick: () -> Unit,
        positiveButtonMessage: String = context.getString(
            R.string.yes
        ),
        negativeButtonMessage: String = context.getString(R.string.no)
    ) {
        AlertDialog.Builder(context)
            .setCancelable(false)
            .setTitle("'$title'")
            .setMessage(message)
            .setPositiveButton(positiveButtonMessage) { _, _ -> onPositiveClick() }
            .setNegativeButton(negativeButtonMessage) { dialog, _ -> dialog?.dismiss() }
            .show()
    }

    fun hideKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusView = activity.currentFocus
        inputMethodManager.hideSoftInputFromWindow(currentFocusView?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
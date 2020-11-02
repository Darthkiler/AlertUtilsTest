package com.datacomprojects.alertutilstest

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import darthkilersprojects.com.log.L
import javax.inject.Inject

@ActivityScoped
open class AlertUtils @Inject constructor(
        @ActivityContext protected val context: Context,
        private val lifecycle: Lifecycle
) : LifecycleObserver {

    private var simpleAlertDialog: AlertDialog? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun dismiss() {
        simpleAlertDialog?.dismiss()
    }

    protected fun createTextAlert(text: String, title: String? = null, positiveButton: Button? = null, negativeButton: Button? = null, cancelable: Boolean = true, dismissRunnable: (() -> Unit)? = null ) {
        createAlert(title, getTextView(text), positiveButton ?: Button("OK", null), negativeButton, cancelable, dismissRunnable)
    }

    /*
    *   Публичный метод для вывода любой простой ошибки
    * */
    fun customTextError(text: String, positiveText: String = "OK", runnable: (() -> Unit)? = null) {
        createInfoAlert(text, positiveText, runnable)
    }

    /*
    *   Метод для создания набора дефолтных алертов
    * */
    protected fun createInfoAlert(text: String, positiveText: String = "OK", runnable: (() -> Unit)? = null) {
        createTitleAlert(text, null, positiveText, runnable)
    }

    /*
    *   Метод для переименования
    * */
    fun renameAlert(oldName: String,/*TODO проверка*/ onSuccess: (String) -> Unit) {

        val view = View(context)

        createAlert("New name", view, Button("ok") { onSuccess("newName") }, Button("cancel", null))
    }

    /*
    *   Метод для создания алертов с title
    * */
    protected fun createTitleAlert(text: String, title: String?, positiveText: String = "OK", runnable: (() -> Unit)? = null) {
        createTextAlert(
                text = text,
                title = title,
                positiveButton = Button(positiveText, runnable)
        )
    }

    /*
    *   Метод для создания полных алертов с 2 кнопками
    * */
    protected fun createFullAlert(
            title: String?,
            text: String,
            positiveText: String = "OK",
            positiveRunnable: (() -> Unit)? = null,
            negativeText: String = "OK",
            negativeRunnable: (() -> Unit)? = null,
            cancelable: Boolean = false,
            dismissRunnable: (() -> Unit)? = null
    ) {
        createTextAlert(
                text, title,
                Button(positiveText, positiveRunnable),
                Button(negativeText, negativeRunnable),
                cancelable, dismissRunnable
        )
    }

    private fun getTextView(text: String): View = (View.inflate(context, R.layout.alert_textview, null) as TextView).apply { this.text = text }

    private fun createAlert(title: String?, body: View?, positiveButton: Button, negativeButton: Button?, cancelable: Boolean = true, dismissRunnable: (() -> Unit)? = null ) {
        simpleAlertDialog?.run {
            if (isShowing)
                dismiss()
        }

        simpleAlertDialog = AlertDialog.Builder(context).create()

        simpleAlertDialog?.run {

            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setView(
                    CustomAlertView(context).apply {
                        setTitle(title)
                        addView(body)
                        setPositive(positiveButton.text) {
                            positiveButton.runnable?.run { this() }
                            simpleAlertDialog?.dismiss()
                        }
                        setNegative(negativeButton?.text) {
                            negativeButton?.runnable?.run { this() }
                            simpleAlertDialog?.dismiss()
                        }
                    }
            )
            setCancelable(cancelable)
            setOnCancelListener {
                dismissRunnable?.run { this() }
            }
            show()
        }


    }

    data class Button(val text: String, val runnable: (() -> Unit)?)


    @Module
    @InstallIn(ActivityComponent::class)
    object AlertDialogModule {

        @ActivityScoped
        @Provides
        fun provideLifecycle(
                @ActivityContext context: Context
        ): Lifecycle = (context as FragmentActivity).lifecycle

    }
}
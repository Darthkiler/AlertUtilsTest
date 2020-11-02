package com.datacomprojects.alertutilstest

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible

class CustomAlertView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.alert_layout, this)
    }

    private val container = findViewById<FrameLayout>(R.id.alert_container)
    private val title = findViewById<AppCompatTextView>(R.id.alert_title)
    private val positiveTextView = findViewById<AppCompatTextView>(R.id.alert_positive)
    private val negativeTextView = findViewById<AppCompatTextView>(R.id.alert_negative)

    override fun addView(child: View?) {
        container.addView(child)
    }

    fun setTitle(title: String?) {
        this.title.run {
            text = title
            isVisible = title != null
        }
    }

    fun setPositive(text: String, runnable: () -> Unit) {
        this.positiveTextView.run {
            this.text = text
            setOnClickListener { runnable() }
        }
    }

    fun setNegative(text: String?, runnable: () -> Unit) {
        this.negativeTextView.run {
            this.text = text
            isVisible = text != null
            setOnClickListener { runnable() }
        }
    }
}
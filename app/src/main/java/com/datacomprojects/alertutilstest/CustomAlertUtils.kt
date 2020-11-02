package com.datacomprojects.alertutilstest

import android.content.Context
import android.view.View
import androidx.lifecycle.Lifecycle
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CustomAlertUtils @Inject constructor(@ActivityContext context: Context, lifecycle: Lifecycle) : AlertUtils(context, lifecycle) {


    fun noInternetError() {
        createTextAlert("NO internet")
    }



    fun deleteAlert() {

    }

    fun unsavedChanges() {

    }
}
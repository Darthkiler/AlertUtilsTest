package com.datacomprojects.alertutilstest

import android.content.Context
import dagger.hilt.android.qualifiers.ActivityContext
import darthkilersprojects.com.log.L
import java.util.logging.Handler
import javax.inject.Inject

class MyRandomClass @Inject constructor (@ActivityContext val context: Context) {

    @Inject
    lateinit var alertUtils: CustomAlertUtils

    fun qwe() {
        alertUtils.renameAlert("asd") {
            L.show(it)
        }
    }
}
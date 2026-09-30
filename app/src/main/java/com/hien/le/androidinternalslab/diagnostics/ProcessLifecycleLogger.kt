package com.hien.le.androidinternalslab.diagnostics

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class ProcessLifecycleLogger :
    DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {

        Log.d(
            TAG,
            """
            ==========================
            PROCESS -> FOREGROUND
            ==========================
            """.trimIndent()
        )
    }

    override fun onStop(owner: LifecycleOwner) {

        Log.d(
            TAG,
            """
            ==========================
            PROCESS -> BACKGROUND
            ==========================
            """.trimIndent()
        )
    }

    companion object {

        const val TAG =
            "ProcessLifecycle"
    }
}
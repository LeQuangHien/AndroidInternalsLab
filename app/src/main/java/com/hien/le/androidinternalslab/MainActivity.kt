package com.hien.le.androidinternalslab

import android.os.Bundle
import android.os.Process
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.ProcessLifecycleOwner
import com.hien.le.androidinternalslab.diagnostics.ProcessLifecycleLogger
import com.hien.le.androidinternalslab.process.ProcessInfoScreen

class MainActivity :
    ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        logProcessInformation()

        ProcessLifecycleOwner
            .get()
            .lifecycle
            .addObserver(
                ProcessLifecycleLogger()
            )

        setContent {

            MaterialTheme {

                ProcessInfoScreen()
            }
        }
    }

    private fun logProcessInformation() {

        Log.d(
            "ProcessLab",
            """
            ==============================
            APP PROCESS CREATED
            ==============================

            PID:
            ${Process.myPid()}

            UID:
            ${Process.myUid()}

            Process:
            ${applicationInfo.processName}

            Thread:
            ${Thread.currentThread().name}

            ==============================
            """.trimIndent()
        )
    }
}
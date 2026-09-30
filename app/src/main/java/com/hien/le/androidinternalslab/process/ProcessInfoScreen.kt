package com.hien.le.androidinternalslab.process

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun ProcessInfoScreen() {

    val context =
        LocalContext.current

    var processInfo by remember {
        mutableStateOf(
            ProcessInfoProvider.getProcessInfo(
                context
            )
        )
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(16.dp)
    ) {

        Text(
            text = "Android Process Lab",
            style =
                MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = {
                processInfo =
                    ProcessInfoProvider
                        .getProcessInfo(context)
            }
        ) {
            Text("Refresh")
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        SectionCard(
            title = "Linux Process"
        ) {

            InfoRow(
                label = "PID",
                value = processInfo.pid.toString()
            )

            InfoRow(
                label = "UID",
                value = processInfo.uid.toString()
            )

            InfoRow(
                label = "Process",
                value = processInfo.processName
            )

            InfoRow(
                label = "Package",
                value = processInfo.packageName
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        SectionCard(
            title = "Thread"
        ) {

            InfoRow(
                label = "Thread",
                value =
                    processInfo.currentThreadName
            )

            InfoRow(
                label = "Thread ID",
                value =
                    processInfo.currentThreadId
                        .toString()
            )

            InfoRow(
                label = "Main Thread",
                value =
                    processInfo.isMainThread
                        .toString()
            )

            InfoRow(
                label = "CPU cores",
                value =
                    processInfo.availableProcessors
                        .toString()
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        SectionCard(
            title = "Process Importance"
        ) {

            InfoRow(
                label = "Importance",
                value =
                    processInfo.processImportanceName
            )

            InfoRow(
                label = "Raw value",
                value =
                    processInfo.processImportance
                        .toString()
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        SectionCard(
            title = "Java / ART Memory"
        ) {

            InfoRow(
                label = "Heap used",
                value =
                    "${processInfo.javaHeapUsedMb} MB"
            )

            InfoRow(
                label = "Heap max",
                value =
                    "${processInfo.javaHeapMaxMb} MB"
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        SectionCard(
            title = "Native Memory"
        ) {

            InfoRow(
                label = "Native Heap",
                value =
                    "${processInfo.nativeHeapAllocatedMb} MB"
            )

            InfoRow(
                label = "PSS",
                value =
                    "${processInfo.totalPssMb} MB"
            )

            InfoRow(
                label = "Private Dirty",
                value =
                    "${processInfo.privateDirtyMb} MB"
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        SectionCard(
            title = "System Memory"
        ) {

            InfoRow(
                label = "Available",
                value =
                    "${processInfo.systemAvailableMemoryMb} MB"
            )

            InfoRow(
                label = "Total",
                value =
                    "${processInfo.systemTotalMemoryMb} MB"
            )

            InfoRow(
                label = "Low Memory",
                value =
                    processInfo.lowMemory
                        .toString()
            )
        }

        Spacer(
            modifier = Modifier.height(32.dp)
        )
    }
}

@Composable
private fun SectionCard(
    title: String,
    content: @Composable () -> Unit
) {

    Card(
        modifier =
            Modifier.fillMaxWidth()
    ) {

        Column(
            modifier =
                Modifier.padding(16.dp)
        ) {

            Text(
                text = title,
                style =
                    MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            content()
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String
) {

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 6.dp
                ),

        horizontalArrangement =
            Arrangement.SpaceBetween
    ) {

        Text(
            text = label,
            style =
                MaterialTheme.typography.bodyMedium
        )

        Text(
            text = value,
            style =
                MaterialTheme.typography.bodyMedium
        )
    }

    HorizontalDivider()
}
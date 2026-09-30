package com.hien.le.androidinternalslab.process

import android.app.ActivityManager
import android.content.Context
import android.os.Debug
import android.os.Looper
import android.os.Process

object ProcessInfoProvider {

    fun getProcessInfo(context: Context): ProcessInfo {

        val activityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        val pid = Process.myPid()
        val uid = Process.myUid()

        val processName =
            findProcessName(activityManager, pid)
                ?: "Unknown"

        val runtime = Runtime.getRuntime()

        val javaHeapUsed =
            runtime.totalMemory() - runtime.freeMemory()

        val javaHeapMax =
            runtime.maxMemory()

        val nativeHeapAllocated =
            Debug.getNativeHeapAllocatedSize()

        val memoryInfoArray =
            activityManager.getProcessMemoryInfo(intArrayOf(pid))

        val processMemoryInfo =
            memoryInfoArray.firstOrNull()

        val totalPss =
            processMemoryInfo?.totalPss ?: 0

        val privateDirty =
            processMemoryInfo?.totalPrivateDirty ?: 0

        val systemMemoryInfo =
            ActivityManager.MemoryInfo()

        activityManager.getMemoryInfo(systemMemoryInfo)

        val importance =
            findProcessImportance(
                activityManager = activityManager,
                pid = pid
            )

        return ProcessInfo(
            pid = pid,
            uid = uid,
            processName = processName,
            packageName = context.packageName,

            currentThreadName = Thread.currentThread().name,
            currentThreadId = Thread.currentThread().id,

            isMainThread =
                Looper.myLooper() == Looper.getMainLooper(),

            availableProcessors =
                Runtime.getRuntime().availableProcessors(),

            javaHeapUsedMb =
                bytesToMb(javaHeapUsed),

            javaHeapMaxMb =
                bytesToMb(javaHeapMax),

            nativeHeapAllocatedMb =
                bytesToMb(nativeHeapAllocated),

            totalPssMb =
                kbToMb(totalPss),

            privateDirtyMb =
                kbToMb(privateDirty),

            systemAvailableMemoryMb =
                bytesToMb(systemMemoryInfo.availMem),

            systemTotalMemoryMb =
                bytesToMb(systemMemoryInfo.totalMem),

            lowMemory =
                systemMemoryInfo.lowMemory,

            processImportance =
                importance,

            processImportanceName =
                importanceToString(importance)
        )
    }

    private fun findProcessName(
        activityManager: ActivityManager,
        pid: Int
    ): String? {

        return activityManager
            .runningAppProcesses
            ?.firstOrNull {
                it.pid == pid
            }
            ?.processName
    }

    private fun findProcessImportance(
        activityManager: ActivityManager,
        pid: Int
    ): Int {

        return activityManager
            .runningAppProcesses
            ?.firstOrNull {
                it.pid == pid
            }
            ?.importance
            ?: ActivityManager.RunningAppProcessInfo.IMPORTANCE_GONE
    }

    private fun importanceToString(
        importance: Int
    ): String {

        return when (importance) {

            ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND ->
                "FOREGROUND"

            ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND_SERVICE ->
                "FOREGROUND_SERVICE"

            ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE ->
                "VISIBLE"

            ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE ->
                "SERVICE"

            ActivityManager.RunningAppProcessInfo.IMPORTANCE_CACHED ->
                "CACHED"

            ActivityManager.RunningAppProcessInfo.IMPORTANCE_GONE ->
                "GONE"

            else ->
                "UNKNOWN ($importance)"
        }
    }

    private fun bytesToMb(
        bytes: Long
    ): Long {

        return bytes / 1024 / 1024
    }

    private fun kbToMb(
        kb: Int
    ): Int {

        return kb / 1024
    }
}
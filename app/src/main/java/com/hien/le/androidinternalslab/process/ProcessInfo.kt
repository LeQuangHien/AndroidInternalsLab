package com.hien.le.androidinternalslab.process

data class ProcessInfo(
    val pid: Int,
    val uid: Int,
    val processName: String,
    val packageName: String,

    val currentThreadName: String,
    val currentThreadId: Long,
    val isMainThread: Boolean,

    val availableProcessors: Int,

    val javaHeapUsedMb: Long,
    val javaHeapMaxMb: Long,

    val nativeHeapAllocatedMb: Long,

    val totalPssMb: Int,
    val privateDirtyMb: Int,

    val systemAvailableMemoryMb: Long,
    val systemTotalMemoryMb: Long,

    val lowMemory: Boolean,

    val processImportance: Int,
    val processImportanceName: String
)
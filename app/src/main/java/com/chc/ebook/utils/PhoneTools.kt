package com.chc.ebook.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager

/**
 * 监控手机电量
 */
class BatteryReceiver(private val onBatteryChanged: (Int) -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        onBatteryChanged(level)
    }
}

/**
 * 不退出应用 返回桌面
 */
fun backHomeScreen(context: Context) {
    val intent = Intent(Intent.ACTION_MAIN).apply {
        addCategory(Intent.CATEGORY_HOME)
    }
    context.startActivity(intent)
}

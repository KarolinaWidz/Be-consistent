package edu.karolinawidz.beconsistent.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class DateChangedReceiver : BroadcastReceiver() {
    lateinit var dateChangedAction: () -> Unit
    lateinit var actualizeIcons: () -> Unit
    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action == Intent.ACTION_DATE_CHANGED) {
            dateChangedAction()
            actualizeIcons()
        }
    }
}
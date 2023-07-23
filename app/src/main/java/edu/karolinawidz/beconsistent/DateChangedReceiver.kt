package edu.karolinawidz.beconsistent

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class DateChangedReceiver : BroadcastReceiver() {
    lateinit var dateChangedAction: () -> Unit
    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action == Intent.ACTION_DATE_CHANGED) {
            dateChangedAction()
        }
    }
}
package edu.karolinawidz.beconsistent.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class DateChangedReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "DateChangedReceiver"
    }

    lateinit var dateChangedAction: () -> Unit
    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action == Intent.ACTION_DATE_CHANGED) {
            dateChangedAction()
            Log.i(TAG, "Date changed broadcast received")
        }
    }
}
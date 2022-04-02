package com.mohamed.demo


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.widget.Toast


class BroadReceiver: BroadcastReceiver() {
  override fun onReceive(context: Context?, intent: Intent?) {

    val powerManager = context!!.getSystemService(Context.POWER_SERVICE) as PowerManager
    val wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp::MyWakelockTag1")
    wakeLock.acquire(10000)
    val id = intent!!.getIntExtra("id", -1)

    Toast.makeText(context, "id.toString()", Toast.LENGTH_LONG).show()

  }

}


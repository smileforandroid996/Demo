package com.mohamed.demo

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    private fun setAlarmForReminder(){

        val cal = Calendar.getInstance()

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent1 = Intent(this, BroadReceiver::class.java)
        intent1.putExtra("id", "id")

        val pendingIntent = PendingIntent.getBroadcast(this, 1012, intent1, PendingIntent.FLAG_UPDATE_CURRENT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val alarmClockInfo = AlarmManager.AlarmClockInfo( cal.timeInMillis + (2 * 60 * 1000), pendingIntent)
            alarmManager.setAlarmClock(alarmClockInfo, pendingIntent)
        }else{
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.timeInMillis  + (2 * 60 * 1000), pendingIntent)
        }

        Toast.makeText(this, "Done", Toast.LENGTH_LONG).show()
    }


    private fun showAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("Battery Optimization")
            .setMessage("You need to disable battery optimization, to achieve this click ok and select all apps and search for Boraq and click on it and select Don't Optimize Option or you can click cancel")
            .setPositiveButton(android.R.string.yes) { dialog, which ->
                dialog.dismiss()
                val intent = Intent()
                intent.action = Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS
                startActivity(intent)
            }
            .setNegativeButton(android.R.string.no) { dialog, which ->
                dialog.dismiss()
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    private fun checkBatteryOptimization() {
        val pm = getSystemService(POWER_SERVICE) as PowerManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                showAlertDialog()
            }else{
                setAlarmForReminder()
            }
        }
    }

    fun setAlarm(view: View) {checkBatteryOptimization()}

}
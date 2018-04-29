package com.technologies.zenlight.worklight_kotlin

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.Camera
import android.hardware.camera2.CameraManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.preference.PreferenceManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.security.Policy

class MainScreen : AppCompatActivity() {

    companion object {

        val REQUEST_CAMERA_PERMISSIONS = 1
        var timer: Long = (15 * 1) * 1000
        var isPermissions = false

    }

    val sdk = Build.VERSION.SDK_INT
    val marshMallow = Build.VERSION_CODES.M
    private lateinit var camera: Camera
    private lateinit var cameraManager: CameraManager
    private lateinit var cameraId: String
    private lateinit var countDownTimer: CountDownTimer
    private var isInitialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countDownTimer = object : CountDownTimer(5000, 1000) {

            override fun onTick(millisUntilFinished: Long) {

                if (!check_box_audio.isChecked)
                    countDownSound()
            }

            override fun onFinish() {

                finishAffinity()
            }
        }

        spinner_time.setSelection(getTimerPreferences(this))
        spinner_theme.setSelection(getThemePreferences(this))
        check_box_audio.isChecked = getAudioPreferences(this)

        setUpSpinners()

        //our hide button handler
        btn_hide.setOnClickListener({

            hideSound(this, spinner_theme.selectedItem.toString(), check_box_audio.isChecked)
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        })

        //handler for the audio layout
        layout_audio.setOnClickListener({

            check_box_audio.isChecked = !check_box_audio.isChecked
        })



        if (getTimerPreferences(this) == 0) {

            timer = (15 * 1) * 1000

        } else if (getTimerPreferences(this) == 1) {

            timer = (60 * 10) * 1000

        }

        showCameraFlash()

    }


    @SuppressLint("NewApi")
    fun showCameraFlash() {

        if (isRequestCameraPermissionsGranted()) {

            isPermissions = true

            if (sdk >= marshMallow) {
                cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
                cameraId = cameraManager.cameraIdList[0]
                cameraManager.setTorchMode(cameraId, true)
                openSound(this, spinner_theme.selectedItem.toString(), check_box_audio.isChecked)

            } else {

                camera = Camera.open()
                val parameters = camera.parameters
                openSound(this, spinner_theme.selectedItem.toString(), check_box_audio.isChecked)
                parameters.flashMode = Camera.Parameters.FLASH_MODE_TORCH
                camera.parameters = parameters
                camera.startPreview()
            }

        }
    }

    @SuppressLint("NewApi")
    fun hideCameraFlash() {

        if (sdk >= marshMallow) {

            if (isPermissions) {

                cameraManager.setTorchMode(cameraId, false)

            }

        } else {

            camera.stopPreview()
            camera.release()
        }
        closeSound(this, spinner_theme.selectedItem.toString(), check_box_audio.isChecked)

    }


    private fun setTimer() {

        countDownTimer.cancel()
        Handler().removeCallbacksAndMessages(this)

        Handler().postDelayed({
            countDownTimer.start()

        }, timer)
    }


    private fun setUpSpinners() {


        spinner_time.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                (spinner_time.selectedView as TextView).setTextColor(ContextCompat.getColor(applicationContext, R.color.white)) //<----

                if (isInitialized){

                    setTimer()

                } else {
                    isInitialized = true
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


        spinner_theme.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                (spinner_theme.selectedView as TextView).setTextColor(ContextCompat.getColor(applicationContext, R.color.white)) //<----

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun isRequestCameraPermissionsGranted(): Boolean {

        if (sdk >= marshMallow) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.CAMERA), //request specific permission from user
                        REQUEST_CAMERA_PERMISSIONS)

                return false

            } else {

                return true
            }

        } else {

            return true
        }
    }


    private fun countDownSound() {

        if (spinner_theme.selectedItem.toString() == DIGITAL) {

            val mediaPlayer = MediaPlayer.create(this, R.raw.countdown)
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer.start()

        } else {

            val mediaPlayer = MediaPlayer.create(this, R.raw.gun_release)
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer.start()
        }

    }


    override fun onResume() {
        super.onResume()

        if (spinner_theme.selectedItemPosition < 2) {

            setTimer()

        }
        showNotification(this)
    }

    override fun onPause() {

        super.onPause()

        //set timer preferences
        val timer = spinner_time.selectedItemPosition
        setTimerPreferences(this, timer)

        //set theme preferences
        val theme = spinner_theme.selectedItemPosition
        setThemePreferences(this, theme)

        //set audio preferences
        setAudioPreferences(this, check_box_audio.isChecked)

    }

    override fun onDestroy() {
        super.onDestroy()

        Handler().removeCallbacksAndMessages(this)

        countDownTimer.cancel()

        val mNotifyMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotifyMgr.cancelAll()

        hideCameraFlash()
    }
}

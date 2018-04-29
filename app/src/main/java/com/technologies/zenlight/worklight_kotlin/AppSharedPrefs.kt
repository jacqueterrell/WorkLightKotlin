package com.technologies.zenlight.worklight_kotlin

import android.content.Context
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*


val TIMER_PREF = "Timer"
val AUDIO_PREF = "Audio"
val THEME_PREF = "Theme"


fun setTimerPreferences(context: Context, positon : Int) {

    PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putInt(TIMER_PREF,positon)
            .apply()
}

fun getTimerPreferences(context: Context): Int {

    return PreferenceManager.getDefaultSharedPreferences(context)
            .getInt(TIMER_PREF,0)

}

fun setAudioPreferences(context: Context,isAudio : Boolean){

    PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putBoolean(AUDIO_PREF,isAudio)
            .apply()
}

fun getAudioPreferences(context: Context) : Boolean{

    return  PreferenceManager.getDefaultSharedPreferences(context)
            .getBoolean(AUDIO_PREF,false)

}

fun setThemePreferences(context: Context,positon: Int) {

    PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putInt(THEME_PREF,positon)
            .apply()
}

fun getThemePreferences(context: Context): Int {

    return PreferenceManager.getDefaultSharedPreferences(context)
            .getInt(THEME_PREF,0)

}

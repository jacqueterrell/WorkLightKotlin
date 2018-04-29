package com.technologies.zenlight.worklight_kotlin

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer



val DIGITAL = "Digital"


fun hideSound(context : Context, filter : String, isAudioEnabled : Boolean){


    if (!isAudioEnabled)
    if (filter.equals(DIGITAL)){

        val mediaPlayer = MediaPlayer.create(context,R.raw.hide_app)
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.start()

    } else{

        val mediaPlayer = MediaPlayer.create(context,R.raw.shells_fall)
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.start()
    }
}

fun openSound(context: Context,filter: String,isAudioEnabled : Boolean){

    if (!isAudioEnabled)
    if (filter.equals(DIGITAL)){

        val mediaPlayer = MediaPlayer.create(context,R.raw.open_app)
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.start()

    } else{

        val mediaPlayer = MediaPlayer.create(context,R.raw.shotgun)
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.start()
    }
}

fun closeSound(context: Context,filter: String,isAudioEnabled : Boolean){

    if (!isAudioEnabled)
    if (filter.equals(DIGITAL)){

        val mediaPlayer = MediaPlayer.create(context,R.raw.close_app)
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.start()

    } else{

        val mediaPlayer = MediaPlayer.create(context,R.raw.reload)
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.start()
    }
}


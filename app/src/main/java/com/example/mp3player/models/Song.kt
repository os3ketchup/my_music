package com.example.mp3player.models

import java.io.Serializable
import java.util.concurrent.TimeUnit

class Song:Serializable{
    var id:String?=null
    var title:String?=null
    var album:String?=null
    var artist:String?=null
    var duration: Long = 0
    var path:String?=null
    var artUri:String?=null

    constructor()
    constructor(
        id: String?,
        title: String?,
        album: String?,
        artist: String?,
        duration: Long,
        path: String?,
        artUri: String?
    ) {
        this.id = id
        this.title = title
        this.album = album
        this.artist = artist
        this.duration = duration
        this.path = path
        this.artUri = artUri
    }

    fun formatDuration(duration: Long):String{
        val minutes = TimeUnit.MINUTES.convert(duration,TimeUnit.MILLISECONDS)
        val seconds = (TimeUnit.SECONDS.convert(duration,TimeUnit.MILLISECONDS)-
                minutes*TimeUnit.SECONDS.convert(1,TimeUnit.MINUTES))
        return String.format("%02d:%02d",minutes,seconds)
    }

}
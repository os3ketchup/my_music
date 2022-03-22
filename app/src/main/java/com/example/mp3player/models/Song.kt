package com.example.mp3player.models

import java.io.Serializable

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


}
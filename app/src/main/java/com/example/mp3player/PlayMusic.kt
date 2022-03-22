package com.example.mp3player

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mp3player.databinding.FragmentAllMusicBinding
import com.example.mp3player.models.Song




import android.annotation.SuppressLint
import android.net.Uri

import android.provider.MediaStore


import androidx.navigation.fragment.findNavController
import com.example.mp3player.adapter.MusicAdapter
import com.example.mp3player.databinding.FragmentPlayMusicBinding

import java.io.File


class PlayMusic : Fragment() {
    lateinit var binding: FragmentPlayMusicBinding
    lateinit var musicAdapter: MusicAdapter
    lateinit var list:ArrayList<Song>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayMusicBinding.inflate(inflater, container, false)
        initializeLayout()
        return binding.root
    }

    private fun initializeLayout() {
        binding.apply {
            rvMusicList.setHasFixedSize(true)
            rvMusicList.setItemViewCacheSize(13)
            list = getAllAudio()
            musicAdapter = MusicAdapter(binding.root.context,list,object:MusicAdapter.RvClick{
                override fun itemClick(song: Song) {
                    findNavController().navigate(R.id.allMusic, Bundle().apply {
                        putSerializable("music",song)

                    })
                }


            })
            rvMusicList.adapter = musicAdapter

        }

    }

    @SuppressLint("Range")
    private fun getAllAudio(): ArrayList<Song>{
        val tempList = ArrayList<Song>()
        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
        val projection = arrayOf(MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATE_ADDED,MediaStore.Audio.Media.DATA,MediaStore.Audio.Media.ALBUM_ID)
        val cursor = requireActivity().contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            MediaStore.Audio.Media.DATE_ADDED + " DESC",
            null)
        if (cursor!=null)
            if (cursor.moveToFirst()){
                do {
                    val titleC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val idC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                    val albumC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    val artistC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val durationC = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    val albumIdC = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)).toString()
                    val uri = Uri.parse("content://media//external/audio/albumart")
                    val artUri = Uri.withAppendedPath(uri,albumIdC).toString()
                    val song = Song(idC,titleC,albumC,artistC,durationC,pathC,artUri)
                    val file = File(song.path)
                    if (file.exists())
                        tempList.add(song)
                }while (cursor.moveToNext())
                cursor.close()
            }

        return tempList
    }


}
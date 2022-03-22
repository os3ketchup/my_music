package com.example.mp3player

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mp3player.databinding.FragmentAllMusicBinding
import com.example.mp3player.models.Song

class AllMusic : Fragment() {
    private lateinit var binding: FragmentAllMusicBinding
    private lateinit var list: ArrayList<Song>
    var musicPosition = 0
    lateinit var song: Song
    private var mediaPlayer: MediaPlayer? = null
    var isPlayed = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        song = requireArguments().getSerializable("music") as Song

        binding.tvTitle.text = song.title
        binding.tvAuthor.text = song.artist

        println("pozitisiya $musicPosition")
        println("bu kooooooota" + song.path)
        list = ArrayList()
        if (mediaPlayer == null)
            mediaPlayer = MediaPlayer()
        mediaPlayer!!.reset()
        mediaPlayer!!.setDataSource(song.path)
        mediaPlayer!!.prepare()
        mediaPlayer!!.start()
        isPlayed = true
        binding.ivPausePlay.setImageResource(R.drawable.ic_pause)

        binding.ivPausePlay.setOnClickListener {
            if (isPlayed) pauseMusic() else playMusic()
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAllMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun playMusic() {
        binding.ivPausePlay.setImageResource(R.drawable.ic_pause)
        isPlayed = true
        mediaPlayer!!.start()
    }

    private fun pauseMusic() {
        binding.ivPausePlay.setImageResource(R.drawable.ic_play)
        isPlayed = false
        mediaPlayer!!.pause()
    }


}
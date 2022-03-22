package com.example.mp3player

import android.annotation.SuppressLint
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
    companion object{
        lateinit var musicListAM:ArrayList<Song>
    }



    var musicPosition = 0
    lateinit var song: Song
    private var mediaPlayer: MediaPlayer? = null
    var isPlayed = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeLayout()

        isPlayed = true
        binding.ivPausePlay.setImageResource(R.drawable.ic_pause)

        binding.ivPausePlay.setOnClickListener { if (isPlayed) pauseMusic() else playMusic()}
        binding.ivBack.setOnClickListener { prevNextSong(false) }
        binding.ivNext.setOnClickListener { prevNextSong(true) }
      /*  binding.seekBarMusic.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if(fromUser){
                    mediaPlayer!!.seekTo(progress)
                }
            }
            override fun onStartTrackingTouch(p0: SeekBar?):Unit {}
            override fun onStopTrackingTouch(p0: SeekBar?) :Unit{}
        })*/
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAllMusicBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("SetTextI18n")
    private fun setLayout(){
        binding.tvTitle.text = musicListAM[musicPosition].title
        binding.tvAuthor.text = musicListAM[musicPosition].artist

        binding.tvWhichOne.text =" ${musicPosition + 1}" + "/" + "${musicListAM.size}"
    }

    private fun createMediaPlayer(){
        try {
            if (mediaPlayer == null)
                mediaPlayer = MediaPlayer()
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(musicListAM[musicPosition].path )
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
        }catch (e:Exception){return}
    }
    private fun initializeLayout(){
        song = requireArguments().getSerializable("music") as Song
        musicPosition = requireArguments().getInt("position")


        println("pozitisiya $musicPosition")
        println("bu kooooooota" + song.path)
        musicListAM = ArrayList()
        musicListAM.addAll(PlayMusic.PlayListPM)
        setLayout()
        createMediaPlayer()
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

    private fun prevNextSong(increment:Boolean){
        if (increment){
            setSongPosition(increment = true)
            setLayout()
            createMediaPlayer()
        }else{
            setSongPosition(increment = false)
            setLayout()
            createMediaPlayer()
        }
    }

    private fun setSongPosition(increment: Boolean){
        if (increment){
            if (musicListAM.size -1 == musicPosition)
                musicPosition=0
            else ++musicPosition
        }else{
            if (0 == musicPosition)
                musicPosition= musicListAM.size-1
            else --musicPosition
        }
    }


}
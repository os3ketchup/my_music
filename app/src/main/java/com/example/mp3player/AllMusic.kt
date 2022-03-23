package com.example.mp3player

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mp3player.databinding.FragmentAllMusicBinding
import com.example.mp3player.models.Song


class AllMusic : Fragment() {
    private lateinit var binding: FragmentAllMusicBinding
    lateinit var runnable: Runnable

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
        binding.seekBarMusic.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if(fromUser){
                    mediaPlayer!!.seekTo(progress)
                }
            }
            override fun onStartTrackingTouch(p0: SeekBar?):Unit {}
            override fun onStopTrackingTouch(p0: SeekBar?) :Unit{}
        })
        binding.ivForward30.setOnClickListener {
            mediaPlayer?.seekTo(mediaPlayer?.currentPosition!!.plus(30000))
        }
        binding.ivBack30.setOnClickListener {
            mediaPlayer?.seekTo(mediaPlayer?.currentPosition!!.minus(30000))
        }
        binding.ivMenu.setOnClickListener {

            findNavController().popBackStack()
            mediaPlayer?.reset()
        }

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
            binding.seekBarMusic.progress = 0
            binding.seekBarMusic.max = mediaPlayer!!.duration

        }catch (e:Exception){return}
    }

    @SuppressLint("SetTextI18n")
    fun seekBarSetup(){
        runnable = Runnable {
            binding.seekBarMusic.progress = mediaPlayer!!.currentPosition
            binding.tvProgress.text = musicListAM[musicPosition].formatDuration((mediaPlayer!!.currentPosition.toLong())) + "/" + musicListAM[musicPosition].formatDuration((mediaPlayer!!.duration.toLong()))
            Handler(Looper.getMainLooper()).postDelayed(runnable,200)

        }
        Handler(Looper.getMainLooper()).postDelayed(runnable,0)
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
        seekBarSetup()
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
            seekBarSetup()
        }else{
            setSongPosition(increment = false)
            setLayout()
            createMediaPlayer()
            seekBarSetup()
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



    override fun onPause() {
        super.onPause()
        mediaPlayer!!.reset()
    }

}
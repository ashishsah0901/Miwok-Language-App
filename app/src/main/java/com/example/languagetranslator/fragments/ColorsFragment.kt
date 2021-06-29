package com.example.languagetranslator.fragments

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.languagetranslator.R
import com.example.languagetranslator.adapters.ColorsAdapter
import com.example.languagetranslator.adapters.ColorsAdapterListener
import com.example.languagetranslator.data.Color
import com.example.languagetranslator.databinding.ActivityColorBinding

class ColorsFragment: Fragment(), ColorsAdapterListener {
    private lateinit var binding: ActivityColorBinding
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var mAudioManager: AudioManager
    private lateinit var mFocusRequest: AudioFocusRequest
    private val mAudioFocusChangeListener = AudioManager.OnAudioFocusChangeListener {
        if(it==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || it==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
            mediaPlayer?.pause()
            mediaPlayer?.seekTo(0)
        }else if(it==AudioManager.AUDIOFOCUS_GAIN){
            mediaPlayer?.start()
        }else if(it==AudioManager.AUDIOFOCUS_LOSS){
            if(mediaPlayer!=null) {
                mediaPlayer!!.stop()
                mediaPlayer!!.release()
                mediaPlayer = null
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mAudioManager.abandonAudioFocusRequest(mFocusRequest)
                }
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.activity_color,container,false)
        mAudioManager = activity?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val colors = ArrayList<Color>()
        colors.add(Color("Red", "weṭeṭṭi", R.mipmap.color_red, R.raw.color_red))
        colors.add(Color("Green", "chokokki", R.mipmap.color_green, R.raw.color_green))
        colors.add(Color("Brown", "ṭakaakki", R.mipmap.color_brown, R.raw.color_brown))
        colors.add(Color("Gray", "ṭopoppi", R.mipmap.color_gray, R.raw.color_gray))
        colors.add(Color("Black", "kululli", R.mipmap.color_black, R.raw.color_black))
        colors.add(Color("White", "kelelli", R.mipmap.color_white, R.raw.color_white))
        colors.add(Color("Dusty Yellow", "ṭopiisә", R.mipmap.color_dusty_yellow, R.raw.color_dusty_yellow))
        colors.add(Color("Mustard Yellow", "chiwiiṭә", R.mipmap.color_mustard_yellow, R.raw.color_mustard_yellow))
        binding = ActivityColorBinding.inflate(inflater)
        val mAdapter = ColorsAdapter(requireContext(), colors, this)
        val recyclerView: RecyclerView = rootView.findViewById(R.id.colorsRV)
        recyclerView.adapter = mAdapter
        return rootView
    }

    override fun onItemClicked(id: Int) {
        if(mediaPlayer!=null) {
            mediaPlayer!!.release()
            mediaPlayer = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mAudioManager.abandonAudioFocusRequest(mFocusRequest)
            }else{
                mAudioManager.abandonAudioFocus(mAudioFocusChangeListener)
            }
        }

        val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setOnAudioFocusChangeListener(mAudioFocusChangeListener)
                .build()
            mAudioManager.requestAudioFocus(mFocusRequest)
        } else {
            mAudioManager.requestAudioFocus(mAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
        }
        if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

            mediaPlayer = MediaPlayer.create(requireActivity(), id)
        Log.d("ColorFragment","MediaPlayer create")
            mediaPlayer!!.start()
            mediaPlayer!!.setOnCompletionListener {
                mediaPlayer!!.release()
                mediaPlayer = null
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mAudioManager.abandonAudioFocusRequest(mFocusRequest)
                }else{
                    mAudioManager.abandonAudioFocus(mAudioFocusChangeListener)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if(mediaPlayer!=null) {
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            mediaPlayer = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mAudioManager.abandonAudioFocusRequest(mFocusRequest)
            }else{
                mAudioManager.abandonAudioFocus(mAudioFocusChangeListener)
            }
        }
    }
}
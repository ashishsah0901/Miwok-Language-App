package com.example.languagetranslator.fragments

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.languagetranslator.R
import com.example.languagetranslator.activity.MainActivity
import com.example.languagetranslator.adapters.PhrasesAdapter
import com.example.languagetranslator.adapters.PhrasesAdapterListener
import com.example.languagetranslator.data.Phrase
import com.example.languagetranslator.databinding.ActivityPhraseBinding

class PhrasesFragment: Fragment(), PhrasesAdapterListener {
    private lateinit var binding: ActivityPhraseBinding
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var mAudioManager: AudioManager
    private lateinit var mFocusRequest: AudioFocusRequest
    private val mAudioFocusChangeListener = AudioManager.OnAudioFocusChangeListener {
        if(it== AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || it== AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
            mediaPlayer?.pause()
            mediaPlayer?.seekTo(0)
        }else if(it== AudioManager.AUDIOFOCUS_GAIN){
            mediaPlayer?.start()
        }else if(it== AudioManager.AUDIOFOCUS_LOSS){
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
        val rootView = inflater.inflate(R.layout.activity_phrase,container,false)
        mAudioManager = activity?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val phrases = ArrayList<Phrase>()
        phrases.add(Phrase("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going))
        phrases.add(Phrase("What is your name?","tinnә oyaase'nә",R.raw.phrase_what_is_your_name))
        phrases.add(Phrase("My name is...","oyaaset...",R.raw.phrase_my_name_is))
        phrases.add(Phrase("How are you feeling?","michәksәs?",R.raw.phrase_how_are_you_feeling))
        phrases.add(Phrase("I am feeling good","kuchi achit",R.raw.phrase_im_feeling_good))
        phrases.add(Phrase("Are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming))
        phrases.add(Phrase("Yes, I'm coming","hәә’ әәnәm",R.raw.phrase_yes_im_coming))
        phrases.add(Phrase("I'm coming","әәnәm",R.raw.phrase_im_coming))
        phrases.add(Phrase("Let's go","yoowutis",R.raw.phrase_lets_go))
        phrases.add(Phrase("Come here","әnni'nem",R.raw.phrase_come_here))
        binding = ActivityPhraseBinding.inflate(inflater)
        val mAdapter = PhrasesAdapter(requireContext(),phrases,this)
        val recyclerView: RecyclerView = rootView.findViewById(R.id.phrasesRV)
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
            val mPlaybackAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build()
            mFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(mPlaybackAttributes)
                .setAcceptsDelayedFocusGain(true)
                .setWillPauseWhenDucked(true)
                .setOnAudioFocusChangeListener(mAudioFocusChangeListener)
                .build()
            mAudioManager.requestAudioFocus(mFocusRequest)
        } else {
            mAudioManager.requestAudioFocus(mAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
        }
        if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

            mediaPlayer = MediaPlayer.create(activity, id)
            mediaPlayer!!.start()
            mediaPlayer!!.setOnCompletionListener {
//                Toast.makeText(this, "I'm Done", Toast.LENGTH_SHORT).show()
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
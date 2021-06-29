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
import com.example.languagetranslator.adapters.FamilyAdapter
import com.example.languagetranslator.adapters.FamilyAdapterListener
import com.example.languagetranslator.data.Family
import com.example.languagetranslator.databinding.ActivityFamilyBinding

class FamilyFragment: Fragment(), FamilyAdapterListener {
    private lateinit var binding: ActivityFamilyBinding
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
        val rootView = inflater.inflate(R.layout.activity_family,container,false)
        mAudioManager = activity?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val family = ArrayList<Family>()
        family.add(Family("Father","әpә",R.mipmap.family_father,R.raw.family_father))
        family.add(Family("Mother","әṭa",R.mipmap.family_mother,R.raw.family_mother))
        family.add(Family("Son","angsi",R.mipmap.family_son,R.raw.family_son))
        family.add(Family("Daughter","tune",R.mipmap.family_daughter,R.raw.family_daughter))
        family.add(Family("Elder Brother","taachi",R.mipmap.family_older_brother,R.raw.family_older_brother))
        family.add(Family("Younger Brother","chalitti",R.mipmap.family_younger_brother,R.raw.family_younger_brother))
        family.add(Family("Elder Sister","teṭe",R.mipmap.family_older_sister,R.raw.family_older_sister))
        family.add(Family("Younger Sister","kolliti",R.mipmap.family_younger_sister,R.raw.family_younger_sister))
        family.add(Family("Grandmother","ama",R.mipmap.family_grandmother,R.raw.family_grandmother))
        family.add(Family("Grandfather","paapa",R.mipmap.family_grandfather,R.raw.family_grandfather))
        binding = ActivityFamilyBinding.inflate(inflater)
        val mAdapter = FamilyAdapter(requireContext(),family,this)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.familyRV)
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
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
import com.example.languagetranslator.adapters.NumberAdapterListener
import com.example.languagetranslator.adapters.NumbersAdapter
import com.example.languagetranslator.data.Number
import com.example.languagetranslator.databinding.ActivityNumberBinding

class NumbersFragment : Fragment(), NumberAdapterListener {
    private lateinit var binding: ActivityNumberBinding
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
        val rootView = inflater.inflate(R.layout.activity_number,container,false)
        mAudioManager = activity?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val numbers = ArrayList<Number>()
        numbers.add(Number("One","lutti", R.mipmap.number_one,R.raw.number_one))
        numbers.add(Number("Two","otiiko", R.mipmap.number_ten,R.raw.number_two))
        numbers.add(Number("Three","tolookosu", R.mipmap.number_three,R.raw.number_three))
        numbers.add(Number("Four","oyyisa", R.mipmap.number_four,R.raw.number_four))
        numbers.add(Number("Five","massokka", R.mipmap.number_five,R.raw.number_five))
        numbers.add(Number("Six","temmokka", R.mipmap.number_six,R.raw.number_six))
        numbers.add(Number("Seven","kenekaku", R.mipmap.number_seven,R.raw.number_seven))
        numbers.add(Number("Eight","kawinta", R.mipmap.number_eight,R.raw.number_eight))
        numbers.add(Number("Nine","wo’e", R.mipmap.number_nine,R.raw.number_nine))
        numbers.add(Number("Ten","na’aacha", R.mipmap.number_ten,R.raw.number_ten))
        binding = ActivityNumberBinding.inflate(inflater)
        val mAdapter = NumbersAdapter(requireContext(),numbers,this)
        val recyclerView: RecyclerView = rootView.findViewById(R.id.numbersRV)
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
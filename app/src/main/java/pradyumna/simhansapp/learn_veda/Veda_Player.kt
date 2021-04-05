package pradyumna.simhansapp.learn_veda

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pradyumna.simhansapp.R
import pradyumna.simhansapp.adaptersFolders.RvClickHandler
import pradyumna.simhansapp.viewModel.VedaDataViewModel
import java.util.*
import pradyumna.simhansapp.adapterFiles.PRvAdapter as PRvAdapter

class Veda_Player : AppCompatActivity(),RvClickHandler {

    lateinit var seekBar: SeekBar
    lateinit var play_Btn: Button
    lateinit var mediaPlayer: MediaPlayer
    lateinit var player_file_name:TextView
    var handler = Handler()
    private lateinit var pauseBtn: Button
    var runnable: Runnable? = null

    private lateinit var mRecyclerView: RecyclerView
    lateinit var mVedaDataViewModel: VedaDataViewModel
    private var pause: Boolean = false

    lateinit var items: Map<String, Any>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_veda__player)
        mRecyclerView = findViewById(R.id.PlayerRv)
        player_file_name=findViewById(R.id.player_file_name)
        seekBar = findViewById(R.id.seekBar)
        play_Btn = findViewById(R.id.play_button)
        pauseBtn = findViewById(R.id.pause_button)
        mVedaDataViewModel = ViewModelProvider(this).get(VedaDataViewModel::class.java)
        val adapter = PRvAdapter(this)
        mRecyclerView.setAdapter(adapter)
        mRecyclerView.setLayoutManager(LinearLayoutManager(applicationContext))
        val intent = intent
        val name = intent.getStringExtra("Name")
        Log.d("intent", "Extras: $name")
//        lifecycle.addObserver(mVedaDataViewModel)
        mVedaDataViewModel!!.getAllFileName(name).observe(this, { stringObjectMap ->
            if (stringObjectMap != null) {
                items = stringObjectMap.toMap()
                adapter.submitList(stringObjectMap.keys.toList())
            } else {
                Log.d("Set Send", "DATA NOT SEND TO ADAPTER")
            }
        })
        play_Btn.setOnClickListener(View.OnClickListener {
            if (mediaPlayer!!.isPlaying) {
                Toast.makeText(this,"Media already playing",Toast.LENGTH_SHORT).show()
            }
            else{
                mediaPlayer!!.start()
                pauseBtn.visibility = View.VISIBLE
                play_Btn.visibility = View.GONE
                updateSeekBar()
            }
        })

        pauseBtn.setOnClickListener(View.OnClickListener {
            if (mediaPlayer!!.isPlaying) {
                handler.removeCallbacks(updater)
                mediaPlayer!!.pause()
                pauseBtn.visibility = View.GONE
                play_Btn.visibility = View.VISIBLE
            }
        })

        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2) {
                    mediaPlayer.seekTo(p1 * 1000)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                TODO("Not yet implemented")
            }

        })
        seekBar.setMax(100)
    }

    override fun onItemClick(position: Int) {
        mediaPlayer = MediaPlayer()
        Log.d("Song Data", "URL: " + items[items.keys.elementAt(position)].toString())
        var url=items[items.keys.elementAt(position)].toString()
        mediaPlayer=MediaPlayer.create(this, Uri.parse(url))
        player_file_name.setText(items.keys.elementAt(position))
        mediaPlayer.start()
        pauseBtn.visibility = View.VISIBLE
        play_Btn.visibility = View.GONE
        updateSeekBar()
    }


    private val updater = Runnable {
        updateSeekBar()
    }

    private fun updateSeekBar() {
        if (mediaPlayer!!.isPlaying) {
            seekBar!!.progress = (mediaPlayer!!.currentPosition.toFloat() / mediaPlayer!!.duration * 100).toInt()
            handler.postDelayed(updater, 1000)
        }
    }

    private fun milliSecondToTimer(milliSeconds: Long): String {
        var timerString = ""
        val secondString: String
        val hours = (milliSeconds / (1000 * 60 * 60)).toInt()
        val minutes = (milliSeconds % (1000 * 60 * 60)).toInt() / (1000 * 60)
        val seconds = (milliSeconds % (1000 * 60 * 60) / (1000 * 60) / 1000).toInt()
        if (hours > 0) {
            timerString = "$hours:"
        }
        secondString = if (minutes < 10) {
            "0$seconds"
        } else {
            "" + seconds
        }
        timerString = "$timerString$minutes:$secondString"
        return timerString
    }
}



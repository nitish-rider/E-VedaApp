package pradyumna.simhansapp.learn_veda

import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_veda__player.*
import pradyumna.simhansapp.R
import pradyumna.simhansapp.adapterFiles.PRvAdapter
import pradyumna.simhansapp.adaptersFolders.RvClickHandler
import pradyumna.simhansapp.utils.LoadingDialog
import pradyumna.simhansapp.viewModel.VedaDataViewModel
import java.util.concurrent.TimeUnit

class Veda_Player : AppCompatActivity(), RvClickHandler {
    override fun onRestart() {
        super.onRestart()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
    }

    override fun onPause() {
        super.onPause()
        if(mediaPlayer.isPlaying){
            mediaPlayer.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }

    }

    //Variables
    private lateinit var seekBar: SeekBar
    private lateinit var play_Btn: Button
    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private lateinit var player_time_start: TextView
    private lateinit var player_time_end: TextView
    private lateinit var back_10_sec: Button
    private lateinit var forward_10_sec: Button
    private lateinit var player_file_name: TextView
    private lateinit var progressDialog: ProgressDialog
    private var handler = Handler()
    private lateinit var pauseBtn: Button
    private var runnable: Runnable? = null

    private lateinit var mRecyclerView: RecyclerView
    lateinit var mVedaDataViewModel: VedaDataViewModel
    private var pause: Boolean = false


    lateinit var items: Map<String, Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_veda__player)
        //Status bar Color
        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.orangeMain)
        }

        //Action Bar Object
        val actionBar = supportActionBar

        // Define ColorDrawable object and parse color
        val colorDrawable = ColorDrawable(Color.parseColor("#F1D548"))

        // Set BackgroundDrawable
        actionBar!!.setBackgroundDrawable(colorDrawable)


        supportActionBar?.title = "Veda Player"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        //Initializing values
        mRecyclerView = findViewById(R.id.PlayerRv)
        player_file_name = findViewById(R.id.player_file_name)
        seekBar = findViewById(R.id.seekBar)
        play_Btn = findViewById(R.id.play_button)
        pauseBtn = findViewById(R.id.pause_button)
        back_10_sec = findViewById(R.id.back10sec)
        forward_10_sec = findViewById(R.id.forward10sec)
        player_time_start = findViewById(R.id.player_time_start)
        player_time_end = findViewById(R.id.player_time_end)

        //View Model Initialize
        mVedaDataViewModel = ViewModelProvider(this).get(VedaDataViewModel::class.java)

        //Set adapter
        val adapter = PRvAdapter(this, this@Veda_Player)
        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager = LinearLayoutManager(applicationContext)


        val intent = intent
        val name = intent.getStringExtra("Name")
        Log.d("intent", "Extras: $name")

        mVedaDataViewModel.getAllFileName(name).observe(this, { stringObjectMap ->
            if (stringObjectMap != null) {
                val sortedMap = stringObjectMap.toSortedMap(compareBy<String> { it.length }.thenBy { it })
                items = sortedMap.toMap()
                adapter.submitList(sortedMap.keys.toList())
            } else {
                Log.d("Set Send", "DATA NOT SEND TO ADAPTER")
            }
        })
        play_Btn.setOnClickListener(View.OnClickListener {
            if (mediaPlayer.isPlaying) {
                Toast.makeText(this, "Media already playing", Toast.LENGTH_SHORT).show()
            } else {
                mediaPlayer.start()
                pauseBtn.visibility = View.VISIBLE
                play_Btn.visibility = View.GONE
                updateSeekBar()
            }
        })

        back_10_sec.setOnClickListener(View.OnClickListener {
            if (mediaPlayer.isPlaying) {
                val currrPosition = mediaPlayer.currentPosition
                if (currrPosition - 10000 > 0) {
                    mediaPlayer.seekTo(currrPosition - 10000)
                    player_time_start.text = milliSecondToTimer(mediaPlayer.currentPosition.toLong())
                } else {
                    mediaPlayer.stop()
                }
            }
        })

        forward_10_sec.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                val currrPosition = mediaPlayer.currentPosition
                if (currrPosition + 10000 < mediaPlayer.duration) {
                    mediaPlayer.seekTo(currrPosition + 10000)
                    player_time_start.text = milliSecondToTimer(mediaPlayer.currentPosition.toLong())

                } else {
                    mediaPlayer.stop()
                }
            }
        }

        pauseBtn.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                handler.removeCallbacks(updater)
                mediaPlayer.pause()
                pauseBtn.visibility = View.GONE
                play_Btn.visibility = View.VISIBLE
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2) {

                    mediaPlayer.seekTo(p1)
                }
                player_time_start.text = milliSecondToTimer(mediaPlayer.currentPosition.toLong())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        mediaPlayer.setOnCompletionListener {
            play_button.visibility = View.VISIBLE
            pauseBtn.visibility = View.GONE
            mediaPlayer.seekTo(0)
        }

    }

    override fun onItemClick(position: Int) {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.reset()
            mediaPlayer.reset()
        }
        val loading = LoadingDialog(this)
        loading.startLoading()
        val handler = Handler()
        handler.postDelayed({ loading.isDismiss() }, 3000)
        Log.d("Song Data", "URL: " + items[items.keys.elementAt(position)].toString())
        val url = items[items.keys.elementAt(position)].toString()
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()
            player_file_name.text = items.keys.elementAt(position)
            pauseBtn.visibility = View.VISIBLE
            play_Btn.visibility = View.GONE
            val finalTime = mediaPlayer.duration
            Log.d("Song Duration", "Duration: " + mediaPlayer.duration)
            player_time_start.text = milliSecondToTimer(0)
            Log.d("Song time", "time: " + String.format("%02d : %02d", TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong()), TimeUnit.MILLISECONDS.toSeconds(finalTime.toLong()) - TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong()) * 60))
            player_time_end.text = String.format("%02d : %02d", TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong()), TimeUnit.MILLISECONDS.toSeconds(finalTime.toLong()) - TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong()) * 60)
            seekBar.max = finalTime
            updateSeekBar()
        }
    }


    private val updater = Runnable {
        updateSeekBar()
        val timeElapsed = mediaPlayer.currentPosition
        player_time_start.text = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(timeElapsed.toLong()), TimeUnit.MILLISECONDS.toSeconds(timeElapsed.toLong()) - TimeUnit.MILLISECONDS.toMinutes(timeElapsed.toLong()) * 60)

    }


    private fun updateSeekBar() {
        if (mediaPlayer.isPlaying) {
            seekBar.progress = (mediaPlayer.currentPosition)
            handler.postDelayed(updater, 100)
        }
    }

    private fun milliSecondToTimer(duration: Long): String {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(duration), TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MILLISECONDS.toMinutes(duration) * 60)
    }
}

package pradyumna.simhansapp.sthotas

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pradyumna.simhansapp.R
import pradyumna.simhansapp.adapterFiles.PRvAdapter
import pradyumna.simhansapp.adaptersFolders.RvClickHandler
import pradyumna.simhansapp.viewModel.SthotasDataViewModel

class Sthotras_Player : AppCompatActivity(),RvClickHandler {

    override fun onBackPressed(){
        super.onBackPressed()
        if(mediaPlayer.isPlaying){
            mediaPlayer.stop()
        }

    }

    lateinit var seekBar: SeekBar
    lateinit var play_Btn: Button
    lateinit var mediaPlayer: MediaPlayer
    lateinit var player_file_name: TextView
    var handler = Handler()
    private lateinit var pauseBtn: Button
    var runnable: Runnable? = null

    private lateinit var mRecyclerView: RecyclerView
    lateinit var mSthotasDataViewModel: SthotasDataViewModel

    private var pause: Boolean = false


    lateinit var items: Map<String, Any>
    override fun onCreate(savedInstanceState: Bundle?) {

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

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sthotras__player)

        supportActionBar?.title = "Sthotra Player"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mRecyclerView = findViewById(R.id.PlayerRvSS)
        player_file_name=findViewById(R.id.player_file_nameSS)
        seekBar = findViewById(R.id.seekBarSS)
        play_Btn = findViewById(R.id.play_buttonSS)
        pauseBtn = findViewById(R.id.pause_buttonSS)

        mSthotasDataViewModel=ViewModelProvider(this).get(SthotasDataViewModel::class.java)

        val adapter = PRvAdapter(this)
        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager = LinearLayoutManager(applicationContext)

        val intent = intent
        val name = intent.getStringExtra("Name")
        Log.d("intent", "Extras: $name")

        mSthotasDataViewModel!!.getAllFileName(name).observe(this,{ stringObjectMap ->
            if (stringObjectMap != null) {
                items = stringObjectMap.toMap()
                adapter.submitList(stringObjectMap.keys.toList())
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

        pauseBtn.setOnClickListener(View.OnClickListener {
            if (mediaPlayer.isPlaying) {
                handler.removeCallbacks(updater)
                mediaPlayer.pause()
                pauseBtn.visibility = View.GONE
                play_Btn.visibility = View.VISIBLE
            }
        })

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2) {
                    mediaPlayer.seekTo(((p1/100.0) *mediaPlayer.duration).toInt())
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

    }

    override fun onItemClick(position: Int) {
        mediaPlayer = MediaPlayer()
        Log.d("Song Data", "URL: " + items[items.keys.elementAt(position)].toString())
        val url=items[items.keys.elementAt(position)].toString()
        mediaPlayer=MediaPlayer.create(this, Uri.parse(url))
        player_file_name.setText(items.keys.elementAt(position))
        mediaPlayer.start()
        pauseBtn.visibility = View.VISIBLE
        play_Btn.visibility = View.GONE
        updateSeekBar()
    }


    private val updater = Runnable {
        updateSeekBar()
        val currentDuration = mediaPlayer.currentPosition.toLong()
    }



    private fun updateSeekBar() {
        if (mediaPlayer.isPlaying) {
            seekBar.progress = (mediaPlayer.currentPosition.toFloat() / mediaPlayer.duration * 100).toInt()
            handler.postDelayed(updater, 1000)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop();
        mediaPlayer.release()
    }

}
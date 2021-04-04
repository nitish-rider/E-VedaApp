package pradyumna.simhansapp.learn_veda

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pradyumna.simhansapp.R
import pradyumna.simhansapp.viewModel.VedaDataViewModel
import java.util.*
import java.util.stream.Collectors
import pradyumna.simhansapp.adapterFiles.PRvAdapter as PRvAdapter

class Veda_Player : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    lateinit var mVedaDataViewModel: VedaDataViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_veda__player)
        mRecyclerView = findViewById(R.id.PlayerRv)
        mVedaDataViewModel = ViewModelProvider(this).get(VedaDataViewModel::class.java)
        val adapter = PRvAdapter()
        mRecyclerView.setAdapter(adapter)
        mRecyclerView.setLayoutManager(LinearLayoutManager(applicationContext))
        val intent = intent
        val name = intent.getStringExtra("Name")
        Log.d("intent", "Extras: $name")
//        lifecycle.addObserver(mVedaDataViewModel)
        mVedaDataViewModel!!.getAllFileName(name).observe(this, { stringObjectMap ->
            if (stringObjectMap != null) {
                adapter.submitList(stringObjectMap.keys.toList())
            } else {
                Log.d("Set Send", "DATA NOT SEND TO ADAPTER")
            }
        })
    }
}



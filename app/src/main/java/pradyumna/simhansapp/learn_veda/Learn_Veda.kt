package pradyumna.simhansapp.learn_veda

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pradyumna.simhansapp.R
import pradyumna.simhansapp.adaptersFolders.RvAdapter
import pradyumna.simhansapp.adaptersFolders.RvClickHandler
import pradyumna.simhansapp.viewModel.VedasFolderViewModel
import java.util.*

//import java.util.Observer;
class Learn_Veda : AppCompatActivity(), RvClickHandler {

    lateinit var recyclerView: RecyclerView
    lateinit var items: ArrayList<String?>
    lateinit var mVedasFolderViewModel: VedasFolderViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        //Status bar Color
        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.orangeMain   )
        }

        //Action Bar Object
        val actionBar = supportActionBar

        // Define ColorDrawable object and parse color
        val colorDrawable = ColorDrawable(Color.parseColor("#F1D548"))
        // Set BackgroundDrawable
        actionBar!!.setBackgroundDrawable(colorDrawable)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn__veda)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recyclerView = findViewById(R.id.lvrecyclerView)
        mVedasFolderViewModel = ViewModelProviders.of(this).get(VedasFolderViewModel::class.java)
        val adapter = RvAdapter(this)
        recyclerView.setAdapter(adapter)
        val layoutManager =LinearLayoutManager(applicationContext)
        recyclerView.setLayoutManager(layoutManager)
        if (mVedasFolderViewModel!!.allFolderName != null) {
            mVedasFolderViewModel!!.allFolderName.observe(this, { strings ->
                if (!strings.isEmpty()) {
                    items = strings
                    adapter.setItems(strings)
                } else {
                    Log.d("TAG", "OnNoData")
                }
            })
        }
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, Veda_Player::class.java)
        intent.putExtra("Name", items!![position])
        startActivity(intent)
    }
}
package pradyumna.simhansapp.prabandham

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pradyumna.simhansapp.R
import pradyumna.simhansapp.adaptersFolders.RvAdapter
import pradyumna.simhansapp.adaptersFolders.RvClickHandler
import pradyumna.simhansapp.viewModel.PrabandhamFolderViewModel
import java.util.*

class Prabandham : AppCompatActivity(), RvClickHandler {
    //Variables
    private var adapter = RvAdapter(this)
    private lateinit var recyclerView: RecyclerView
    lateinit var items: ArrayList<String?>
    lateinit var mPrabandhamDataViewModel: PrabandhamFolderViewModel
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
        setContentView(R.layout.activity_prabandham)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        //Set adapter
        recyclerView = findViewById(R.id.LprecyclerView)
        mPrabandhamDataViewModel = ViewModelProvider(this).get(PrabandhamFolderViewModel::class.java)
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        //Observing Live Data and providing to adapter
        if (mPrabandhamDataViewModel.allFolderName != null) {
            mPrabandhamDataViewModel.allFolderName.observe(this, { strings ->
                if (!strings.isEmpty()) {
                    strings.sort()
                    items = strings
                    adapter.submitList(strings)
                } else {
                    Log.d("TAG", "OnNoData")
                }
            })
        }
        //Search option filter data
        mPrabandhamDataViewModel.queryLiveData.observe(this, { s ->
            if (s != null) {
                val toList: List<String> = mPrabandhamDataViewModel.allFolderName.value?.filter { it.toLowerCase(Locale.ROOT).contains(s.toLowerCase(Locale.ROOT)) }?.toList()
                        ?: return@observe
                adapter.submitList(toList)
            }
        })
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this@Prabandham, Prabandham_Player::class.java)
        intent.putExtra("Name", items[position])
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        //Initialize Menu inflater
        val menuInflater: MenuInflater = menuInflater
        //Inflate Menu
        menuInflater.inflate(R.menu.menu_search, menu)
        //Initialize Menu Items
        val menuItem: MenuItem = menu?.findItem(R.id.search_view)!!

        //SearchView Variable
        val searchView: androidx.appcompat.widget.SearchView = menuItem.actionView as androidx.appcompat.widget.SearchView
        //Initialize Search View
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mPrabandhamDataViewModel.queryLiveData.postValue(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                mPrabandhamDataViewModel.queryLiveData.postValue(newText)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}
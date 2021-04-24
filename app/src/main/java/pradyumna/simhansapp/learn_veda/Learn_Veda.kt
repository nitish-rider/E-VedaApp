package pradyumna.simhansapp.learn_veda

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pradyumna.simhansapp.R
import pradyumna.simhansapp.adaptersFolders.RvAdapter
import pradyumna.simhansapp.adaptersFolders.RvClickHandler
import pradyumna.simhansapp.viewModel.VedasFolderViewModel
import java.util.*


//import java.util.Observer;
class Learn_Veda : AppCompatActivity(), RvClickHandler {
    //Variables
    private val adapter = RvAdapter(this)
    private lateinit var recyclerView: RecyclerView
    private lateinit var items: ArrayList<String?>
    lateinit var mVedasFolderViewModel: VedasFolderViewModel

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
        setContentView(R.layout.activity_learn__veda)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        recyclerView = findViewById(R.id.lvrecyclerView)
        mVedasFolderViewModel = ViewModelProvider(this).get(VedasFolderViewModel::class.java)

        //Set adapter
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager

        //Observing Live Data and providing to adapter
        if (mVedasFolderViewModel.allFolderName != null) {
            mVedasFolderViewModel.allFolderName.observe(this, { strings ->
                if (strings.isNotEmpty()) {
                    strings.sort()
                    items = strings
                    adapter.submitList(strings)
                } else {
                    Log.d("TAG", "OnNoData")
                }
            })
        }

        //Search option filter data
        mVedasFolderViewModel.queryLiveData.observe(this) { query: String? ->
            if (query != null) {
                val toList = mVedasFolderViewModel.allFolderName.value?.filter { it.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT)) }?.toList()
                        ?: return@observe
                adapter.submitList(toList)
            }
        }
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, Veda_Player::class.java)
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
                mVedasFolderViewModel.queryLiveData.postValue(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                mVedasFolderViewModel.queryLiveData.postValue(newText)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

}
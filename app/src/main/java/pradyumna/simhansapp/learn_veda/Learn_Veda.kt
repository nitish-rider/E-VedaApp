package pradyumna.simhansapp.learn_veda

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
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
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn__veda)
        recyclerView = findViewById(R.id.lvrecyclerView)
        mVedasFolderViewModel = ViewModelProviders.of(this).get(VedasFolderViewModel::class.java)
        val adapter = RvAdapter(this)
        recyclerView.setAdapter(adapter)
        val layoutManager = GridLayoutManager(this, 2)
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
        //        Log.d("onclick", "Data"+items.get(position));
//        FirebaseFileNames firebaseFileNames=new FirebaseFileNames();
//        LiveData<ArrayList<String>> fname= firebaseFileNames.getFileName("Learn Vedas",items.get(position));
//        Log.d("TAG", "Data"+items.get(position));
        intent.putExtra("Name", items!![position])
        startActivity(intent)
    }
}
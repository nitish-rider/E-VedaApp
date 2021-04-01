package pradyumna.simhansapp.learn_veda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
//import java.util.Observer;

import pradyumna.simhansapp.R;
import pradyumna.simhansapp.adaptersFolders.RvAdapter;
import pradyumna.simhansapp.viewModel.VedasFolderViewModel;

public class Learn_Veda extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> items;
    VedasFolderViewModel mVedasFolderViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn__veda);
        recyclerView=findViewById(R.id.lvrecyclerView);
        mVedasFolderViewModel= ViewModelProviders.of(this).get(VedasFolderViewModel.class);


        RvAdapter adapter= new RvAdapter();
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        if(mVedasFolderViewModel.getAllFolderName()!=null){
            mVedasFolderViewModel.getAllFolderName().observe(this, new Observer<ArrayList<String>>() {
                @Override
                public void onChanged(ArrayList<String> strings) {
                    if(!strings.isEmpty()){
                        adapter.setItems(strings);
                    }
                    else{
                        Log.d("TAG", "OnNoData");
                    }
                }
            });
        }
    }
}
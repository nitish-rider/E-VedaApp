package pradyumna.simhansapp.sthotas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import pradyumna.simhansapp.R;
import pradyumna.simhansapp.adaptersFolders.RvAdapter;
import pradyumna.simhansapp.viewModel.SthotasFolderViewModel;

public class Sthotras extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> items;
    SthotasFolderViewModel mSthotasFolderViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sthothram);

        recyclerView=findViewById(R.id.LsrecyclerView);
        mSthotasFolderViewModel= ViewModelProviders.of(this).get(SthotasFolderViewModel.class);

        RvAdapter adapter= new RvAdapter();
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        if(mSthotasFolderViewModel.getAllFolderName()!=null){
            mSthotasFolderViewModel.getAllFolderName().observe(this, new Observer<ArrayList<String>>() {
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
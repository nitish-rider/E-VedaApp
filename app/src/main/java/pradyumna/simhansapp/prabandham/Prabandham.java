package pradyumna.simhansapp.prabandham;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import pradyumna.simhansapp.R;
import pradyumna.simhansapp.adapters.RvAdapter;
import pradyumna.simhansapp.viewModel.PrabandhamFolderViewModel;

public class Prabandham extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> items;
    PrabandhamFolderViewModel mPrabandhamDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prabandham);

        recyclerView=findViewById(R.id.LprecyclerView);
        mPrabandhamDataViewModel= ViewModelProviders.of(this).get(PrabandhamFolderViewModel.class);

        RvAdapter adapter= new RvAdapter();
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        if(mPrabandhamDataViewModel.getAllFolderName()!=null){
            mPrabandhamDataViewModel.getAllFolderName().observe(this, new Observer<ArrayList<String>>() {
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
package pradyumna.simhansapp.prabandham;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import pradyumna.simhansapp.R;
import pradyumna.simhansapp.adaptersFolders.RvAdapter;
import pradyumna.simhansapp.adaptersFolders.RvClickHandler;
import pradyumna.simhansapp.learn_veda.Veda_Player;
import pradyumna.simhansapp.viewModel.PrabandhamFolderViewModel;

public class Prabandham extends AppCompatActivity implements RvClickHandler {
    RecyclerView recyclerView;
    ArrayList<String> items;
    PrabandhamFolderViewModel mPrabandhamDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prabandham);

        recyclerView=findViewById(R.id.LprecyclerView);
        mPrabandhamDataViewModel= ViewModelProviders.of(this).get(PrabandhamFolderViewModel.class);

        RvAdapter adapter= new RvAdapter(this);
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        if(mPrabandhamDataViewModel.getAllFolderName()!=null){
            mPrabandhamDataViewModel.getAllFolderName().observe(this, new Observer<ArrayList<String>>() {
                @Override
                public void onChanged(ArrayList<String> strings) {
                    if(!strings.isEmpty()){
                        items=strings;
                        adapter.setItems(strings);
                    }
                    else{
                        Log.d("TAG", "OnNoData");
                    }
                }
            });
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent=new Intent(Prabandham.this, Veda_Player.class);
        intent.putExtra("Name",items.get(position));
        startActivity(intent);
    }
}
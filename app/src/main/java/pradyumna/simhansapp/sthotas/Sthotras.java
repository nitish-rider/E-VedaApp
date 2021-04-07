package pradyumna.simhansapp.sthotas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import pradyumna.simhansapp.R;
import pradyumna.simhansapp.adaptersFolders.RvAdapter;
import pradyumna.simhansapp.adaptersFolders.RvClickHandler;
import pradyumna.simhansapp.learn_veda.Veda_Player;
import pradyumna.simhansapp.prabandham.Prabandham;
import pradyumna.simhansapp.viewModel.SthotasFolderViewModel;

public class Sthotras extends AppCompatActivity implements RvClickHandler {
    RecyclerView recyclerView;
    ArrayList<String> items;
    SthotasFolderViewModel mSthotasFolderViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Status bar Color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.orangeMain));
        }

        //Action Bar Object
        ActionBar actionBar=getSupportActionBar();

        // Define ColorDrawable object and parse color
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#F1D548"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sthothram);

        recyclerView=findViewById(R.id.LsrecyclerView);
        mSthotasFolderViewModel= ViewModelProviders.of(this).get(SthotasFolderViewModel.class);

        RvAdapter adapter= new RvAdapter(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(mSthotasFolderViewModel.getAllFolderName()!=null){
            mSthotasFolderViewModel.getAllFolderName().observe(this, new Observer<ArrayList<String>>() {
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
        Intent intent=new Intent(Sthotras.this, Veda_Player.class);
        intent.putExtra("Name",items.get(position));
        startActivity(intent);
    }
}
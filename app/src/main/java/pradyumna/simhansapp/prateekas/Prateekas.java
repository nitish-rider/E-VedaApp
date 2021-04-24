package pradyumna.simhansapp.prateekas;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

import pradyumna.simhansapp.R;
import pradyumna.simhansapp.adapterPdf.PrvAdapter;
import pradyumna.simhansapp.adaptersFolders.RvClickHandler;
import pradyumna.simhansapp.viewModel.PrateekasViewModel;

public class Prateekas extends AppCompatActivity implements RvClickHandler {


    //Variables
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    PrvAdapter adapter = new PrvAdapter(this);
    RecyclerView recyclerView;
    ArrayList<String> fileName = new ArrayList<>();
    PrateekasViewModel mPrateekasViewModel;
    Map<String, Object> DocData;
    MutableLiveData<Map<String, Object>> allUrl = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prateekas);

        //Status bar Color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.orangeMain));
        }

        //Action Bar Object
        ActionBar actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#F1D548"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);


        //initialize var
        recyclerView = findViewById(R.id.prateekasRV);
        mPrateekasViewModel = new ViewModelProvider(this).get(PrateekasViewModel.class);

        //Set adapter
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //Observing Live Data and providing to adapter
        if (mPrateekasViewModel.getAllFileName() != null) {
            mPrateekasViewModel.getAllFileName().observe(this, new Observer<ArrayList<String>>() {
                @Override
                public void onChanged(ArrayList<String> strings) {
                    if (!strings.isEmpty()) {
                        fileName = strings;
                        adapter.submitList(strings);
                    } else {
                        Log.d("TAG", "OnNoData");
                    }
                }
            });
        }

    }

    @Override
    public void onItemClick(int position) {
        DocumentReference docRef = db.collection("Pratheekas").document(fileName.get(position));
        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                DocData = documentSnapshot.getData();
                                Log.d("URLDATA", DocData.get(DocData.keySet().toArray()[0]).toString());
                                String googleDocsUrl = DocData.get(DocData.keySet().toArray()[0]).toString();
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(Uri.parse(googleDocsUrl), "text/html");
                                startActivity(intent);
                            } else {
                                Log.d("TAG", "No such document");
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });


    }


}

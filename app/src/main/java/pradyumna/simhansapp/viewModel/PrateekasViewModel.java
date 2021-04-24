package pradyumna.simhansapp.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PrateekasViewModel extends AndroidViewModel {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MutableLiveData<ArrayList<String>> allFileName;
    ArrayList<String> allfileName;

    public PrateekasViewModel(@NonNull Application application) {
        super(application);
        allFileName = new MutableLiveData<>();
        allfileName = new ArrayList<>();
        setAllFileName("Pratheekas");
    }

    public MutableLiveData<ArrayList<String>> getAllFileName() {
        return allFileName;
    }

    public void setAllFileName(String type) {
        db.collection(type)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshots : task.getResult()) {
                                if (documentSnapshots.getId() != null) {
                                    Log.d("TAG", "OnSuccess" + documentSnapshots.getId());
                                    allfileName.add(documentSnapshots.getId());
                                }
                            }
                            allFileName.postValue(allfileName);
                        } else {
                            Log.d("TAG", "OnNoData");

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "OnFaliure");
            }
        });
    }
}

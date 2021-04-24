package pradyumna.simhansapp.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FirebaseFolderNames {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MutableLiveData<ArrayList<String>> FolderName=new MutableLiveData<>();
    ArrayList<String> folderName=new ArrayList<>();

    public MutableLiveData<ArrayList<String>> getFolderName(String type) {
        db.collection(type)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.getId() != null) {
                                Log.d("TAG", "OnSuccess" + document.getId());
                                folderName.add(document.getId());
                            }
                        }
                        FolderName.postValue(folderName);
                    } else {
                        Log.d("TAG", "OnNoData");
                    }
                }).addOnFailureListener(e -> Log.d("TAG", "OnFaliure"));
        return FolderName;
    }
}

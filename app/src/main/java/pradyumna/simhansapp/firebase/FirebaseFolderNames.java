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
    MutableLiveData<ArrayList<String>> FolderName;
    ArrayList<String> folderName;

    public MutableLiveData<ArrayList<String>> getFolderName(String type) {
        FolderName = new MutableLiveData<ArrayList<String>>();
        folderName = new ArrayList<String>();
        db.collection(type)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getId() != null) {
                                    Log.d("TAG", "OnSuccess" + document.getId());
                                    folderName.add(document.getId());
                                }
                            }
                            FolderName.setValue(folderName);
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
        return FolderName;
    }
}

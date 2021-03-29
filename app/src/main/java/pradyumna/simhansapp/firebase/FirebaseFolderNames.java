package pradyumna.simhansapp.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FirebaseFolderNames {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Map<String, Object>> FolderName;

    ArrayList<Map<String, Object>> getFolderName(String type){
        db.collection(type)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                FolderName.add(document.getData());
                            }
                        } else {
                        }
                    }
                });
        return FolderName;
    }
}

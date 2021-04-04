package pradyumna.simhansapp.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class SthotasDataViewModel extends AndroidViewModel {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> DocData;
    MutableLiveData<Map<String, Object>> FileData;

    public SthotasDataViewModel(@NonNull Application application) {
        super(application);
        FileData=new MutableLiveData<>();
    }

    public MutableLiveData<Map<String, Object>> getAllFileName(String Doc) {
        getFileName("Learn Sthotras", Doc);
        return FileData;
    }

    public Map<String, Object> getDocData() {
        return DocData;
    }
    public void getFileName(String type, String doc) {
        DocumentReference docref = db.collection(type).document(doc);
        docref.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                DocData = document.getData();
                                FileData.postValue(DocData);
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

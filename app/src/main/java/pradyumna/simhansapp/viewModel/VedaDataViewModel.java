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
import java.util.Set;

public class VedaDataViewModel extends AndroidViewModel {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> DocData;
    MutableLiveData<Map<String, Object>> FileData;


//    FirebaseFileNames mFirebaseFileNames;
//    LiveData<Map<String, Object>> AllFileName;
//    LiveData<ArrayList<String>> AllFileLinks;
//    MutableLiveData<Set<String>> Allfilename;

    public VedaDataViewModel(@NonNull Application application) {
        super(application);
//        mFirebaseFileNames = new FirebaseFileNames();
        FileData=new MutableLiveData<>();
//        notifyAll();
//        Allfilename=new MutableLiveData<>();

    }

    public MutableLiveData<Map<String, Object>> getAllFileName(String Doc) {
        getFileName("Learn Vedas", Doc);
        return FileData;
    }

//    public LiveData<ArrayList<String>> getAllFileLinks(String Doc, String id) {
//
//        AllFileLinks = mFirebaseFileNames.getFileLink("Learn Vedas", Doc, id);
//        return AllFileLinks;
//    }

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
//                                Log.d("fbgetfile", "DocumentSnapshot data: " + document.getData());
//                                Log.d("Map Data", "MP DATA: "+ DocData.keySet());
//                                FileName=DocData.keySet();
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

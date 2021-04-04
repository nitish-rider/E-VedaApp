package pradyumna.simhansapp.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class FirebaseFileNames {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MutableLiveData<Map<String,Object>> Filename;
    MutableLiveData<ArrayList<String>> Filelink;
//    ArrayList<String> FileName;
    ArrayList<String> FileLink;
    Map<String,Object> DocData;
    Set<String> FileName;


        public MutableLiveData<Map<String,Object>> getFileName(String type, String doc){
        Filename=new MutableLiveData<>();
//            Log.d("T", "DocumentSnapshot data: " + doc);
        DocumentReference docref=db.collection(type).document(doc);
                docref.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                DocData=document.getData();
//                                Log.d("fbgetfile", "DocumentSnapshot data: " + document.getData());
//                                Log.d("Map Data", "MP DATA: "+ DocData.keySet());
//                                FileName=DocData.keySet();
                                Filename.postValue(DocData);
                            } else {
                                Log.d("TAG", "No such document");
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });
//                Filename.setValue(FileName);

        return  Filename;
    }

        public MutableLiveData<ArrayList<String>> getFileLink(String type, String doc, String id){
        FileLink=new ArrayList<>();
        Filelink=new MutableLiveData<>();
        DocumentReference docref=db.collection(type).document(doc);
        docref.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                FileLink.add(document.getString(id)); //Print the name
                            } else {
                                Log.d("TAG", "No such document");
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });
            Filelink.setValue(FileLink);
        return Filelink;
    }
}

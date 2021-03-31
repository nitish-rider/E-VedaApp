package pradyumna.simhansapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;

import pradyumna.simhansapp.firebase.FirebaseFolderNames;

public class PrabandhamFolderViewModel extends AndroidViewModel {
    FirebaseFolderNames mFirebaseFolderNames;
    LiveData<ArrayList<String>> AllFolderName;
    public PrabandhamFolderViewModel(@NonNull Application application) {
        super(application);
        mFirebaseFolderNames=new FirebaseFolderNames();
        AllFolderName=mFirebaseFolderNames. getFolderName("Learn Prabandham");
    }

    public LiveData<ArrayList<String>> getAllFolderName() {
        return AllFolderName;
    }
}

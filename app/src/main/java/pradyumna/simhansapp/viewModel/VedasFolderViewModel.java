package pradyumna.simhansapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.security.AllPermission;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pradyumna.simhansapp.firebase.FirebaseFolderNames;

public class VedasFolderViewModel extends AndroidViewModel {

    FirebaseFolderNames mFirebaseFolderNames;
    LiveData<ArrayList<String>> AllFolderName;

    public VedasFolderViewModel(@NonNull Application application) {
        super(application);
        mFirebaseFolderNames=new FirebaseFolderNames();
        AllFolderName=mFirebaseFolderNames. getFolderName("Learn Vedas");
    }

    public LiveData<ArrayList<String>> getAllFolderName() {
        return AllFolderName;
    }
}

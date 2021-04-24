package pradyumna.simhansapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import pradyumna.simhansapp.firebase.FirebaseFolderNames;

public class VedasFolderViewModel extends AndroidViewModel {
    //Variables
    private final FirebaseFolderNames mFirebaseFolderNames;
    private final LiveData<ArrayList<String>> AllFolderName;
    private final MutableLiveData<String> queryLiveData;

    public VedasFolderViewModel(@NonNull Application application) {
        super(application);
        mFirebaseFolderNames = new FirebaseFolderNames();
        AllFolderName = mFirebaseFolderNames.getFolderName("Learn Vedas");
        queryLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<String> getQueryLiveData() {
        return queryLiveData;
    }

    public LiveData<ArrayList<String>> getAllFolderName() {
        return AllFolderName;
    }
}

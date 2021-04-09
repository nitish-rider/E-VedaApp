package pradyumna.simhansapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.security.AllPermission;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pradyumna.simhansapp.firebase.FirebaseFolderNames;

public class VedasFolderViewModel extends AndroidViewModel {
    //Variables
    private FirebaseFolderNames mFirebaseFolderNames;
    private LiveData<ArrayList<String>> AllFolderName;
    private MutableLiveData<String> queryLiveData;

    public VedasFolderViewModel(@NonNull Application application) {
        super(application);
        mFirebaseFolderNames=new FirebaseFolderNames();
        AllFolderName=mFirebaseFolderNames. getFolderName("Learn Vedas");
        queryLiveData=new MutableLiveData<>();
    }

    public MutableLiveData<String> getQueryLiveData() {
        return queryLiveData;
    }

    public LiveData<ArrayList<String>> getAllFolderName() {
        return AllFolderName;
    }
}

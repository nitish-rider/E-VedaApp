package pradyumna.simhansapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import pradyumna.simhansapp.firebase.FirebaseFolderNames;

public class SthotasFolderViewModel extends AndroidViewModel {
    //Variables
    private FirebaseFolderNames mFirebaseFolderNames;
    private LiveData<ArrayList<String>> AllFolderName;
    private MutableLiveData<String> queryLiveData;

    public SthotasFolderViewModel(@NonNull Application application) {
        super(application);
        mFirebaseFolderNames=new FirebaseFolderNames();
        AllFolderName=mFirebaseFolderNames. getFolderName("Learn Sthotras");
        queryLiveData=new MutableLiveData<>();
    }

    public MutableLiveData<String> getQueryLiveData() {
        return queryLiveData;
    }

    public LiveData<ArrayList<String>> getAllFolderName() {
        return AllFolderName;
    }
}

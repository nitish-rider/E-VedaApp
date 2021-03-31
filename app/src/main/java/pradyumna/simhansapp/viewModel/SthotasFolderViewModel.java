package pradyumna.simhansapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;

import pradyumna.simhansapp.firebase.FirebaseFolderNames;

public class SthotasFolderViewModel extends AndroidViewModel {
    FirebaseFolderNames mFirebaseFolderNames;
    LiveData<ArrayList<String>> AllFolderName;
    public SthotasFolderViewModel(@NonNull Application application) {
        super(application);
        mFirebaseFolderNames=new FirebaseFolderNames();
        AllFolderName=mFirebaseFolderNames. getFolderName("Learn Sthotras");
    }

    public LiveData<ArrayList<String>> getAllFolderName() {
        return AllFolderName;
    }
}

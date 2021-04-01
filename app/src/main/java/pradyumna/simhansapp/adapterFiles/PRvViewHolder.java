package pradyumna.simhansapp.adapterFiles;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pradyumna.simhansapp.R;

public class PRvViewHolder extends RecyclerView.ViewHolder {

    TextView mTextView;

    public PRvViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextView=itemView.findViewById(R.id.FileNameRV);
    }
}

package pradyumna.simhansapp.learn_veda;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pradyumna.simhansapp.R;

public class LvViewHolher extends RecyclerView.ViewHolder {

    TextView mTextView;
    TextView mTextView2;

    public LvViewHolher(@NonNull View itemView) {
        super(itemView);
        mTextView= mTextView.findViewById(R.id.RvText);
        mTextView2=mTextView2.findViewById(R.id.RvText2);
    }
}

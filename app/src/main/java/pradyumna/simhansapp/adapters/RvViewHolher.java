package pradyumna.simhansapp.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pradyumna.simhansapp.R;

public class RvViewHolher extends RecyclerView.ViewHolder {

    TextView mTextView;

    public RvViewHolher(@NonNull View itemView) {
        super(itemView);
        mTextView= itemView.findViewById(R.id.RvText);
    }
}

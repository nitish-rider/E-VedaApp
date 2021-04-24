package pradyumna.simhansapp.adapterPdf;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import pradyumna.simhansapp.R;
import pradyumna.simhansapp.adaptersFolders.RvClickHandler;

public class PrvAdapter extends ListAdapter<String, PrvAdapter.PRvViewHolher> {

    RvClickHandler mRvClickHandler;

    public PrvAdapter(RvClickHandler rvClickHandler) {
        super(new DiffUtil.ItemCallback<String>() {
            @Override
            public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
                return oldItem.equals(newItem);
            }
        });
        mRvClickHandler = rvClickHandler;
    }

    @NonNull
    @Override
    public PRvViewHolher onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prateekas_item_view, parent, false);
        return (new PRvViewHolher(view));
    }

    @Override
    public void onBindViewHolder(@NonNull PRvViewHolher holder, int position) {
        String title1 = getItem(position);
        holder.mTextView.setText(title1);

    }

    public class PRvViewHolher extends RecyclerView.ViewHolder {

        TextView mTextView;

        public PRvViewHolher(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.FileNamePRV);
            itemView.setOnClickListener(view -> mRvClickHandler.onItemClick(getAdapterPosition()));
        }
    }
}

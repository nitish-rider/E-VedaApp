package pradyumna.simhansapp.adaptersFolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import pradyumna.simhansapp.R;

public class RvAdapter extends ListAdapter<String,RvAdapter.RvViewHolher> {

    RvClickHandler mRvClickHandler;




    public RvAdapter(RvClickHandler rvClickHandler) {
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
    public RvViewHolher onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.reccycle_view_item,parent,false);
        return (new RvViewHolher(view));
    }

    @Override
    public void onBindViewHolder(@NonNull RvViewHolher holder, int position) {
            String title1 = getItem(position);
            holder.mTextView.setText(title1);
    }


    public class RvViewHolher extends RecyclerView.ViewHolder {

        TextView mTextView;

        public RvViewHolher(@NonNull View itemView) {
            super(itemView);
            mTextView= itemView.findViewById(R.id.RvText);
            itemView.setOnClickListener(view -> mRvClickHandler.onItemClick(getAdapterPosition()));
        }
    }
}



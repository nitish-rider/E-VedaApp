package pradyumna.simhansapp.adaptersFolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pradyumna.simhansapp.R;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.RvViewHolher> {

    ArrayList<String> items;
    RvClickHandler mRvClickHandler;

    public RvAdapter(RvClickHandler rvClickHandler) {
        mRvClickHandler = rvClickHandler;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RvViewHolher onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.reccycle_view_item,parent,false);
        return (new RvViewHolher(view));
    }

    @Override
    public void onBindViewHolder(@NonNull RvViewHolher holder, int position) {
        if (items != null) {
            String title1 = items.get(position);
            holder.mTextView.setText(title1);
        } else {
            holder.mTextView.setText("NO WORD");
        }

    }

    @Override
    public int getItemCount() {
        if (items != null)
            return items.size();
        else return 0;
    }
    public class RvViewHolher extends RecyclerView.ViewHolder {

        TextView mTextView;

        public RvViewHolher(@NonNull View itemView) {
            super(itemView);
            mTextView= itemView.findViewById(R.id.RvText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRvClickHandler.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}



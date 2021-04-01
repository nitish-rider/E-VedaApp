package pradyumna.simhansapp.adaptersFolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pradyumna.simhansapp.R;

public class RvAdapter extends RecyclerView.Adapter<RvViewHolher> {

    ArrayList<String> items;

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
}

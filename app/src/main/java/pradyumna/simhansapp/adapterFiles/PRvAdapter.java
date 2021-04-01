package pradyumna.simhansapp.adapterFiles;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pradyumna.simhansapp.R;

public class PRvAdapter extends RecyclerView.Adapter<PRvViewHolder> {

    ArrayList<String> items;

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public PRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.player_recycler_view_item,parent,false);
        return (new PRvViewHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull PRvViewHolder holder, int position) {
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

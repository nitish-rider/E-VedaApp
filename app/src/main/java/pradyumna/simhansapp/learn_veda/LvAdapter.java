package pradyumna.simhansapp.learn_veda;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pradyumna.simhansapp.R;

public class LvAdapter extends RecyclerView.Adapter<LvViewHolher> {

    ArrayList<String> items;


    public LvAdapter(ArrayList<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public LvViewHolher onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.reccycle_view_item,parent,false);
        return (new LvViewHolher(view));
    }

    @Override
    public void onBindViewHolder(@NonNull LvViewHolher holder, int position) {
        String title1= items.get(position);
        String title2=items.get(position+1);
        holder.mTextView.setText(title1);
        holder.mTextView2.setText(title2);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

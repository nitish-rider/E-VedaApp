package pradyumna.simhansapp.adapterFiles;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Set;

import pradyumna.simhansapp.R;

public class PRvAdapter extends ListAdapter<String,PRvViewHolder> {


    public PRvAdapter() {
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
    }


    @NonNull
    @Override
    public PRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.player_recycler_view_item,parent,false);
        return (new PRvViewHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull PRvViewHolder holder, int position) {

        String item = getItem(position);
        holder.mTextView.setText(item);


//        if (items != null) {
//            Object[] obj;
//            obj= items.toArray();
//
//            String title1 =obj[position].toString();
//            Log.d("OnBinding", "set DATA"+obj[position].toString());
//            holder.mTextView.setText(title1);
//        } else {
//            holder.mTextView.setText("NO WORD");
//        }
    }

}

package pradyumna.simhansapp.adapterFiles;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import pradyumna.simhansapp.R;
import pradyumna.simhansapp.adaptersFolders.RvClickHandler;
import pradyumna.simhansapp.utils.LoadingDialog;

public class PRvAdapter extends ListAdapter<String,PRvAdapter.PRvViewHolder> {

    RvClickHandler mRvClickHandler;
    Activity callerActivity;

    public PRvAdapter(RvClickHandler rvClickHandler, Activity activity) {

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
        mRvClickHandler=rvClickHandler;
        callerActivity=activity;
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

    }
    public class PRvViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        public PRvViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView=itemView.findViewById(R.id.FileNameRV);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    LoadingDialog loadingDialog=new LoadingDialog(callerActivity);
//                    loadingDialog.startLoading();
//                    Thread thread=new Thread(){
//                        @Override
//                        public void run() {
//                            try {
//                                Thread.sleep(3000);
//                                loadingDialog.isDismiss();
//                                super.run();
//                            }
//                            catch (Exception e) {
//                                Log.e("tag", e.getMessage());
//                            }
//                        }
//                    };
//                    thread.start();
                    mRvClickHandler.onItemClick(getAdapterPosition());
                }
            });
        }
    }

}

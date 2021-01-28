package morahman.bd.com.islamicWazPro;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by user on 19/5/18.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> listItems;
    private Context context;

    public MyAdapter(List<String> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        v.setOnClickListener(MainActivity.myOnClickListner);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       // final String listItem = listItems.get(position);
        //holder.speakerName.setText(listItem.getSpeakerName());
        holder.speakerName.setText(listItems.get(position));


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView speakerName;
        public LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);

            speakerName =(TextView)itemView.findViewById(R.id.speakerName);
            linearLayout =(LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}

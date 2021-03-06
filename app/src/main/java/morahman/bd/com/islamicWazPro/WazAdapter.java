package morahman.bd.com.islamicWazPro;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by user on 21/5/18.
 */

public class WazAdapter extends RecyclerView.Adapter<WazAdapter.ViewHolder>  {

    private List<ListWaz> listItems;
    private Context context;

    public WazAdapter(List<ListWaz> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public WazAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_waz,parent,false);
        return new WazAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WazAdapter.ViewHolder holder, int position) {
        final ListWaz listItem = listItems.get(position);
        holder.speaker_name.setText(listItem.getName());
        holder.waz_title.setText(listItem.getTitle());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String URL =listItem.getUrlName(); // your URL here
                Intent intent = new Intent(MediaStore.INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH);
                //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(URL), "audio/*");
                v.getContext().startActivity(intent);

                //Toast.makeText(context,"URL Value:"+listItem.getUrlName(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView waz_title;
        public  TextView speaker_name;
        public LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);

            waz_title =(TextView)itemView.findViewById(R.id.was_title);
            speaker_name =(TextView)itemView.findViewById(R.id.speaker_name);
            linearLayout =(LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}

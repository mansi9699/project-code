package asquero.com.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Anmol on 10-Apr-18.
 */

public class LiveListAdapter extends RecyclerView.Adapter<LiveListAdapter.ViewHolder> {

    private List<LiveList>list;
    private Context context;

    public LiveListAdapter(List<LiveList> listLive, Live live) {
        this.list = listLive;
        this.context = live;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.live_list_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LiveList listItem = list.get(position);

        holder.contestCode.setText(""+listItem.getContestCode());
        holder.contestName.setText(listItem.getContestName());
        holder.endDate.setText(""+(listItem.getStartDate()));
        holder.startDate.setText(""+(listItem.getEndDate()));
        //holder.imageView.setImageResource(listItem.getImage());
        /*try {
            super(itemView);
            ImageView imagev = (ImageView) itemView.findViewById(R.id.imageView);
            Picasso.get().load("http://i.imgur.com/DvpvklR.png").placeholder(R.drawable.upcoming).error(R.drawable.ended).into(imagev);
        }
        catch (java.lang.RuntimeException e){
            //Toast.makeText(getApplicationContext(), "Picasso Failed", Toast.LENGTH_SHORT).show();
        }*/
        holder.aic.setText(listItem.getAIC());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView contestCode;
        public TextView contestName;
        public TextView endDate;
        public TextView startDate;
        // public ImageView imageView;
        public TextView aic;

        public ViewHolder(View itemView) {
            super(itemView);
            contestCode = (TextView)itemView.findViewById(R.id.contestCode);
            contestName = (TextView)itemView.findViewById(R.id.contestName);
            endDate = (TextView)itemView.findViewById(R.id.startDateNum);
            startDate = (TextView)itemView.findViewById(R.id.endDateNum);
            //ImageView imagev = (ImageView)itemView.findViewById(R.id.imageView);
            ImageView imagev = (ImageView) itemView.findViewById(R.id.imageView);
            try {
                Picasso.get().load("https://mikkegoes.com/wp-content/uploads/2017/02/css-code-laptop.jpeg").into(imagev);
            }
            catch (java.lang.RuntimeException e){
                //Toast.makeText(getApplicationContext(), "Picasso Failed", Toast.LENGTH_SHORT).show();
                Picasso.get().load("https://images.pexels.com/photos/577585/pexels-photo-577585.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260").placeholder(R.drawable.upcoming).error(R.drawable.ended).into(imagev);
            }
            aic = (TextView)itemView.findViewById(R.id.AICTextView);
        }
    }
}

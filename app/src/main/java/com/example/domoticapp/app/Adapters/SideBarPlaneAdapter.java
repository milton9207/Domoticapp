package com.example.domoticapp.app.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.domoticapp.app.R;

import java.util.List;

/**
 * Created by milton on 16/07/15.
 */
public class SideBarPlaneAdapter extends RecyclerView.Adapter<SideBarPlaneAdapter.MyViewHolder> {

    private SparseBooleanArray selectedItems;
    private int selectedItem = 0;
    private RecyclerView mRecyclerView;

    private List<Integer> data;
    private LayoutInflater inflater;
    private static SideBarClickListener listener;

    public interface SideBarClickListener
    {
        void onMenuItemClicked(int position);
    }

    public SideBarPlaneAdapter(Context context, List list, SideBarClickListener listener)
    {
        this.data = list;
        this.listener = listener;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.sidebarrow,parent,false);


        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        //get the current item selected (highlighted)
        holder.itemView.setSelected(selectedItem == position);

        Integer data = this.data.get(position);
        holder.imageView.setImageResource(data);


    }


    @Override
    public int getItemCount() {
        return data.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.recycler_imageview_sidebar);
//            imageView.setOnClickListener(this);

            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {

            // Redraw the old selection and the new
            notifyItemChanged(selectedItem);
            selectedItem = mRecyclerView.getChildPosition(v);
            notifyItemChanged(selectedItem);

            //get position and sent to the listener
            int itemPosition = getPosition();
            Log.d("sideBarAdapter","clicked " + itemPosition);
            listener.onMenuItemClicked(itemPosition);

        }
    }
}

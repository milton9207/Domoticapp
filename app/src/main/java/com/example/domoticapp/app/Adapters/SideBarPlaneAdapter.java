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

    private List<Integer> data;
    private LayoutInflater inflater;

    public SideBarPlaneAdapter(Context context, List list)
    {
        this.data = list;
        inflater = LayoutInflater.from(context);

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.sidebarrow,parent,false);


        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {



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
            itemView.setOnClickListener(this);



        }


        @Override
        public void onClick(View v) {

            Log.i("gg",getPosition() + "");
            int itemPosition = getPosition();


            switch (itemPosition)
            {
                case 0 :
                    itemView.setSelected(true);
//                    if(itemView.isSelected())
//                    {
//                        Log.i("gg","ff");
//                        itemView.setSelected(false);
//                    }
//                    else
//                    {
//                        Log.i("gg","ff22");
//
//                        itemView.setSelected(true);
//                    }
//                    break;
                    break;
                case 1 :
                    itemView.setSelected(true);
                    break;
            }

//
        }
    }
}

package com.example.domoticapp.app.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.domoticapp.app.Modules.LightModule.Light;
import com.example.domoticapp.app.R;

import java.util.List;

/**
 * Created by milton on 14/07/15.
 */
public class SimpleRecyclerAdapter extends RecyclerView.Adapter<SimpleRecyclerAdapter.MyViewHolder> {

    private List<String> data;
    private List<Light> dataLight;
    private LayoutInflater inflater;

    public SimpleRecyclerAdapter(Context context ,List<Light> list)
    {
        this.dataLight = list;
        inflater = LayoutInflater.from(context);

    }


    public void addItem(Light item)
    {
        dataLight.add(item);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.recyclerrow,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Light data = this.dataLight.get(position);

        holder.textView.setText(data.getId());

    }


    @Override
    public int getItemCount() {
        return dataLight.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.recycler_textView);


        }
    }
}

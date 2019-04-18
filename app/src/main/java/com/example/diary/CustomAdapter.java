package com.example.diary;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    ArrayList<MobileOs> mobileOsArrayList = new ArrayList<>();
    LayoutInflater layoutInflater;
    LinearLayout ln;
    Context context;
    DatabaseHelper db;

    public CustomAdapter(ArrayList<MobileOs> mobileOsArrayList, Context context) {
        this.mobileOsArrayList = mobileOsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.list_layout, viewGroup , false);
        ViewHolder vh = new ViewHolder(v);
        ln = (LinearLayout) v.findViewById(R.id.linear_Layout);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
            ln.setBackgroundColor(Integer.parseInt(mobileOsArrayList.get(i).getBackColor()));
            viewHolder.date.setText(mobileOsArrayList.get(i).getDate());
            viewHolder.time.setText(mobileOsArrayList.get(i).getTime());
            viewHolder.description.setText(mobileOsArrayList.get(i).getTitle());
            viewHolder.linearLayoutLayout.setTag(viewHolder);
            viewHolder.linearLayoutLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder holder = (ViewHolder)v.getTag();
                    int position = holder.getPosition();
                    String id = mobileOsArrayList.get(position).getIdName();
                    Intent intent = new Intent(context,Update.class);
                    intent.putExtra("id", id);
                    context.startActivity(intent);
                }
            });
            viewHolder.linearLayoutLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(context,viewHolder.description.getText().toString(), Toast.LENGTH_LONG).show();
                    return false;
                }
            });
    }

    public String getID(int position){
        String id = mobileOsArrayList.get(position).getIdName();
        return id;
    }
    @Override
    public int getItemCount() {
        return mobileOsArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView time , date , description, id;
        LinearLayout linearLayoutLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.idHolder);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            description = itemView.findViewById(R.id.description);
            linearLayoutLayout = itemView.findViewById(R.id.linear_Layout);
        }
    }
}
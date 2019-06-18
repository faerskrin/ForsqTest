package com.example.forsqtest.UI.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.forsqtest.Data.model.Item;
import com.example.forsqtest.R;

import java.util.List;

public class AdapterResponceInfo extends RecyclerView.Adapter<AdapterResponceInfo.GroupViewHolder> {

    private List<Item> mItem;
    private OnitemClick onitemClick;

    public void setItems(List<Item> items) {
        mItem = items;
        notifyDataSetChanged();
    }

    public void setOnitemClick(OnitemClick onitemClick) {
        this.onitemClick = onitemClick;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_recyc, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        holder.mName.setText(mItem.get(position).getVenue().getName());
        holder.mDistance.setText(mItem.get(position).getVenue().getLocation().getDistance()+ " meters");

        Glide.with(holder.mImage)
                .load(mItem.get(position).getVenue().getCategories().get(0).getIcon().getPrefix()+"100.png")
                .apply(RequestOptions
                .errorOf(R.drawable.ic_launcher_foreground)
                .placeholder(R.drawable.ic_launcher_foreground))
                .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }


    public class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mName = itemView.findViewById(R.id.rv_name);
        TextView mDistance = itemView.findViewById(R.id.rv_dist);
        ImageView mImage = itemView.findViewById(R.id.rv_img);

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onitemClick.start(mItem.get(getAdapterPosition()));
        }
    }

    interface OnitemClick{
        void start (Item item);
    }
}

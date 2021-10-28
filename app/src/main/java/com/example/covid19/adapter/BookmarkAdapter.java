package com.example.covid19.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19.R;
import com.example.covid19.room.DataCovid;
import com.example.covid19.room.DataCovidBookmark;
import com.squareup.picasso.Picasso;

public class BookmarkAdapter extends ListAdapter<DataCovidBookmark, BookmarkAdapter.ViewHolder> {

    private final DataCovidBookmarkClickableCallback callback;

    public BookmarkAdapter(@NonNull DataCovidBookmarkClickableCallback callback) {
        super(new AsyncDifferConfig.Builder<>(new DiffUtil.ItemCallback<DataCovidBookmark>() {
            @Override
            public boolean areItemsTheSame(@NonNull DataCovidBookmark oldItem, @NonNull DataCovidBookmark newItem) {
                return oldItem.country.equalsIgnoreCase(newItem.country);
            }
            @Override
            public boolean areContentsTheSame(@NonNull DataCovidBookmark oldItem, @NonNull DataCovidBookmark newItem) {
                return oldItem.country.equalsIgnoreCase(newItem.country);
            }
        }).build());
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.detail_covid_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNegara.setText(getItem(position).country);
        holder.tvBenua.setText(getItem(position).continent);
        String imgUrl = getItem(position).flag;
        Picasso.get().load(imgUrl).into(holder.imgBendera);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvNegara;
        TextView tvBenua;
        ImageView imgBendera;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNegara = itemView.findViewById(R.id.tvNegara);
            tvBenua = itemView.findViewById(R.id.tvBenua);
            imgBendera = itemView.findViewById(R.id.imgFlag);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAbsoluteAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                DataCovidBookmark dataCovid = getItem(position);
                callback.onClick(v, dataCovid);
            }
        }
    }
}


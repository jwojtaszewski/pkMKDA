package com.example.kuba.databasetest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterDatabase extends RecyclerView.Adapter<AdapterDatabase.ViewHolder> implements Filterable
{
    private ArrayList<Item> mItemList;
    private ArrayList<Item> mItemListFull;
    private OnItemClickListener mListener;

    public interface OnItemClickListener
    {
        void onItemClick(int position);
        void onAddClick(int position);
        //void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView1;
        public TextView mTextView2;
        //public ImageView mEatImage;
        public ImageView mEatButton;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener)
        {
        super(itemView);
        mTextView1 = itemView.findViewById(R.id.layout_item_database_text_top_id);
        mTextView2 = itemView.findViewById(R.id.layout_item_database_text_bottom_id);
        mEatButton = itemView.findViewById(R.id.layout_item_database_button);

        itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(listener != null)
                {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) //position must be valid
                    {
                        listener.onItemClick(position);
                    }
                }
            }
        });


        mEatButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(listener != null)
                {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION)
                    {
                        listener.onAddClick(position);
                    }
                }
            }});
        }
    }

    public AdapterDatabase(ArrayList<Item> itemList)
    {
        mItemList = itemList;
        mItemListFull = new ArrayList<>(itemList); //independent copy of the itemList
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_database, parent, false);
        ViewHolder vh = new ViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Item currentItem = mItemList.get(position);

        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
    }

    @Override
    public int getItemCount()
    {
        return mItemList.size();
    }

    private Filter exampleFilter = new Filter()
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Item> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0)
            {
                filteredList.addAll(mItemListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Item item: mItemListFull)
                {
                    if(item.getText1().toLowerCase().contains(filterPattern)) //startsWith instead of contains
                    {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            mItemList.clear();
            mItemList.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public Filter getFilter()
    {
        return exampleFilter;
    }

}

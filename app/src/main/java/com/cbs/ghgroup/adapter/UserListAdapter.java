package com.cbs.ghgroup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cbs.ghgroup.ItemClickListener;
import com.cbs.ghgroup.R;
import com.cbs.ghgroup.model.userlist.UsersDetail;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.myViewHolder> implements Filterable {
    Context context;
    private List<UsersDetail> usersDetails;
    private List<UsersDetail> usersFullList;
    ItemClickListener itemClickListener;

    public UserListAdapter(Context context, List<UsersDetail> usersDetails, ItemClickListener itemClickListener) {
        this.context = context;
        this.usersDetails = usersDetails;
        this.itemClickListener = itemClickListener;
        usersFullList = new ArrayList<>(usersDetails);
    }

    @NonNull
    @Override
    public UserListAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_list_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.myViewHolder holder, int position) {
        UsersDetail usersDetail = usersDetails.get(position);

        holder.tv_customer_name.setText(usersDetail.getUserName());
        holder.tv_customer_code.setText(usersDetail.getUserCode());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (itemClickListener != null) {
                    itemClickListener.onClick(usersDetail.getUserCode());
                   // itemClickListener.onClick(usersDetail.getUserName());

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return usersDetails.size();
    }

    @Override
    public Filter getFilter() {
        return userFilter;
    }

    public Filter userFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<UsersDetail> usersFilter = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                usersFilter.addAll(usersFullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (UsersDetail usersDetail : usersFullList) {
                    if (usersDetail.getUserCode().toLowerCase().contains(filterPattern)) {
                        usersFilter.add(usersDetail);
                    } else if (usersDetail.getUserName().toLowerCase().contains(filterPattern)){
                        usersFilter.add(usersDetail);
                    } else {

                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = usersFilter;
            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            usersDetails.clear();
            usersDetails.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView tv_customer_code, tv_customer_name;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_customer_code = itemView.findViewById(R.id.tv_customer_code);
            this.tv_customer_name = itemView.findViewById(R.id.tv_customer_name);
        }
    }
}

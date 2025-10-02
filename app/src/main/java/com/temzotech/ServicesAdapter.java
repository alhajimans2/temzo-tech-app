package com.temzotech;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Service> services;
    private final OnItemClickListener listener;
    private final ViewType viewType;

    public enum ViewType {
        SIMPLE, DETAILED
    }

    public interface OnItemClickListener {
        void onItemClick(Service service);
    }

    public ServicesAdapter(List<Service> services, OnItemClickListener listener, ViewType viewType) {
        this.services = services;
        this.listener = listener;
        this.viewType = viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ViewType.DETAILED.ordinal()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item_detailed, parent, false);
            return new ServiceViewHolderDetailed(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Service service = services.get(position);
        if (holder instanceof ServiceViewHolderDetailed) {
            ((ServiceViewHolderDetailed) holder).serviceName.setText(service.getName());
            ((ServiceViewHolderDetailed) holder).serviceIcon.setImageResource(service.getIcon());
            ((ServiceViewHolderDetailed) holder).serviceDescription.setText(service.getDescription());
        } else {
            ((ServiceViewHolder) holder).serviceTitle.setText(service.getName());
            ((ServiceViewHolder) holder).serviceIcon.setImageResource(service.getIcon());
        }
        holder.itemView.setOnClickListener(v -> listener.onItemClick(service));
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewType.ordinal();
    }

    public void filterList(List<Service> filteredList) {
        services = filteredList;
        notifyDataSetChanged();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        ImageView serviceIcon;
        TextView serviceTitle;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceIcon = itemView.findViewById(R.id.service_icon);
            serviceTitle = itemView.findViewById(R.id.service_name);
        }
    }

    public static class ServiceViewHolderDetailed extends RecyclerView.ViewHolder {
        ImageView serviceIcon;
        TextView serviceName;
        TextView serviceDescription;

        public ServiceViewHolderDetailed(@NonNull View itemView) {
            super(itemView);
            serviceIcon = itemView.findViewById(R.id.service_icon);
            serviceName = itemView.findViewById(R.id.service_name);
            serviceDescription = itemView.findViewById(R.id.service_description);
        }
    }
}

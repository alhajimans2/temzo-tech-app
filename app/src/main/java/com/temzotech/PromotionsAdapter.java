package com.temzotech;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PromotionsAdapter extends RecyclerView.Adapter<PromotionsAdapter.PromotionViewHolder> {

    private final List<Promotion> promotionList;
    private final OnItemClickListener listener;

    public PromotionsAdapter(List<Promotion> promotionList, OnItemClickListener listener) {
        this.promotionList = promotionList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PromotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.promotion_card_item, parent, false);
        return new PromotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromotionViewHolder holder, int position) {
        Promotion promotion = promotionList.get(position);
        holder.promotionImage.setImageResource(promotion.getImage());
        holder.promotionTitle.setText(promotion.getTitle());
        holder.promotionDescription.setText(promotion.getDescription());
        holder.itemView.setOnClickListener(v -> listener.onPromotionItemClick(promotion));
        holder.claimOfferButton.setOnClickListener(v -> listener.onPromotionItemClick(promotion));
    }

    @Override
    public int getItemCount() {
        return promotionList.size();
    }

    public interface OnItemClickListener {
        void onPromotionItemClick(Promotion promotion);
    }

    static class PromotionViewHolder extends RecyclerView.ViewHolder {
        ImageView promotionImage;
        TextView promotionTitle;
        TextView promotionDescription;
        Button claimOfferButton;

        public PromotionViewHolder(@NonNull View itemView) {
            super(itemView);
            promotionImage = itemView.findViewById(R.id.promotion_image);
            promotionTitle = itemView.findViewById(R.id.promotion_title);
            promotionDescription = itemView.findViewById(R.id.promotion_description);
            claimOfferButton = itemView.findViewById(R.id.claim_offer_button);
        }
    }
}

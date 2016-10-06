package com.github.myhealth;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.myhealth.api.Bill;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Chris on 5-10-2016.
 */

public class BillsAdapter extends RecyclerView.Adapter {

    List<Bill> mBills;
    Context mContext;

    public BillsAdapter(Context context, List<Bill> bills) {
        super();
        mContext = context;
        mBills = bills;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BillViewHolder billHolder = (BillViewHolder) holder;
        Bill bill = mBills.get(position);

        // usage for clicking on it to expand it
//        final boolean isExpanded = position==mExpandedPosition;
//        holder.details.setVisibility(isExpanded?View.VISIBLE:View.GONE);
//        holder.itemView.setActivated(isExpanded);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mExpandedPosition = isExpanded ? -1:position;
//                TransitionManager.beginDelayedTransition(recyclerView);
//                notifyDataSetChanged();
//            }
//        });


        billHolder.mId.setText("Bill id: " + bill.getId());
        billHolder.mStatus.setText("Status: " + bill.getStatus());
        
        String lineCodes = "-";
        String lineDescriptions = "-";
        String linePrices = "-";

        if (bill.getLines().size() != 0) {
            Log.d("building lines", "onBindViewHolder: size is different than 0");
            Iterator iterator = bill.getLines().iterator();
            Bill.Line line = ((Bill.Line) iterator.next());

            StringBuilder codeStringBuilder = new StringBuilder();
            StringBuilder descriptionStringBuilder = new StringBuilder();
            StringBuilder priceStringBuilder = new StringBuilder();

            codeStringBuilder.append(line.code);
            descriptionStringBuilder.append(line.description);
            priceStringBuilder.append(mContext.getString(R.string.profile_bill_eur) + line.price);

            Log.d("BindViewHolder", "onBindViewHolder: abt to iterate");
            while (iterator.hasNext()) {
                Log.d("BindViewHolder", "onBindViewHolder: an iteration");

                codeStringBuilder.append("\n");
                descriptionStringBuilder.append("\n");
                priceStringBuilder.append("\n");

                line = (Bill.Line) iterator.next();

                codeStringBuilder.append(line.code);
                descriptionStringBuilder.append(line.description);
                priceStringBuilder.append(mContext.getString(R.string.profile_bill_eur) + line.price);
            }

            lineCodes = codeStringBuilder.toString();
            lineDescriptions = descriptionStringBuilder.toString();
            linePrices = priceStringBuilder.toString();
        }

        Log.d("onBindViewHolder", "onBindViewHolder: setting texts now.");
        billHolder.mLineCode.setText(lineCodes);
        billHolder.mLineDescription.setText(lineDescriptions);
        billHolder.mLinePrice.setText(linePrices);


        //showInfo.getShow().getImage().getMedium() // url of image

    }

    @Override
    public int getItemCount() {
        Log.d("ITEMCOUNT", "getItemCount: " + mBills.size());
        return mBills.size();
    }

    @Override
    public BillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.card_bill, parent, false);

        return new BillViewHolder(itemView);
    }

    public static class BillViewHolder extends RecyclerView.ViewHolder {
        public TextView mId;
        public TextView mStatus;

        public TextView mLineCode;
        public TextView mLineDescription;
        public TextView mLinePrice;

        public BillViewHolder(View v) {
            super(v);
            mId =  (TextView) v.findViewById(R.id.bill_id);
            mStatus =  (TextView) v.findViewById(R.id.bill_status);

            mLineCode =  (TextView) v.findViewById(R.id.bill_line_code);
            mLineDescription =  (TextView) v.findViewById(R.id.bill_line_description);
            mLinePrice =  (TextView) v.findViewById(R.id.bill_line_price);
        }
    }
}


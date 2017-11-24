package com.example.bittukumar.treatwell.PendingAppointmentsRecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bittukumar.treatwell.R;

import java.util.Collections;
import java.util.List;


public class PRecycler_View_Adapter extends RecyclerView.Adapter<PView_Holder> {

    List<PData> list = Collections.emptyList();
    Context context;

    public PRecycler_View_Adapter(List<PData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public PView_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_appointment_layout, parent, false);
        PView_Holder holder = new PView_Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(PView_Holder holder, int position) {
        holder.doc_nameTV.setText(list.get(position).doc_name);
        holder.doc_specTV.setText(list.get(position).doc_spec);
        holder.hosp_nameTV.setText(list.get(position).hosp_name);
        holder.hosp_addrTV.setText(list.get(position).hosp_addr);
        holder.feeTV.setText(list.get(position).fee);
        holder.starttimeTV.setText(list.get(position).starttime);
        holder.endtimeTV.setText(list.get(position).endtime);
        holder.date_TV.setText(list.get(position).date);


//        TextView tv = new TextView(holder.ll.getContext());
//        tv.setText("bittu");
//        holder.ll.addView(tv);




//        animate(holder);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView
    public void insert(int position, PData data) {
        list.add(position, data);
        notifyItemInserted(position);

    }
    // Remove a RecyclerView item containing the SData object
    public void remove(int position) {
//        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }
    public void refresh(List<PData>data)
    {
        list = data;
        notifyDataSetChanged();
    }

//    public void animate(RecyclerView.ViewHolder viewHolder) {
//        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.anticipate_overshoot_interpolator);
//        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
//    }


}

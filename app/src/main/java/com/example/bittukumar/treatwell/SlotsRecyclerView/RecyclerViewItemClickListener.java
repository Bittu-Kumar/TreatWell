package com.example.bittukumar.treatwell.SlotsRecyclerView;

import android.view.View;

/**
 * Created by Valdio Veliu on 14/01/2017.
 */

public interface RecyclerViewItemClickListener {
    public void onClick(View view, int position);

    public void onLongClick(View view, int position);
}

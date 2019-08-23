package com.openclassrooms.realestatemanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;

import java.util.List;


/**
 * Created by Lionel JOFFRAY - on 12/05/2019.
 * <p>
 * History Adapter for the user History list (show user history)
 *
 */
public class SlideshowAdapter extends RecyclerView.Adapter<SlideshowAdapter.MyViewHolder> {

    private final Context mContext;
    public List mImageList;
    int mRed;
    int mGreen;


    public SlideshowAdapter(List imageList, Context context) {
        mImageList = imageList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View slideView = inflater.inflate(R.layout.slideshow_item, parent, false);
        return new MyViewHolder(slideView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
      //  updateWithFreshInfo(mImageList.get(position), holder, position);
    }

    /**
     * Update RecyclerView with restaurant list and date.
     */
  //  private void updateWithFreshInfo(final ImageList item, final MyViewHolder holder, int position) {

 //   }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }


    /**
     * View Holder .
     *
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mName;
        TextView mDate;

        MyViewHolder(View view) {
            super(view);

        }
    }
}
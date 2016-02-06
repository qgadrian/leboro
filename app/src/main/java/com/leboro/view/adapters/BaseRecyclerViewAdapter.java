package com.leboro.view.adapters;

import java.util.List;

import com.leboro.MainActivity;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseRecyclerViewAdapter<ELEMENT_TYPE, HOLDER_TYPE extends BaseViewHolder<ELEMENT_TYPE>>
        extends RecyclerView.Adapter<HOLDER_TYPE> {

    private List<ELEMENT_TYPE> elements;

    private int rowResource;

    private Class<HOLDER_TYPE> holderClazz;

    public BaseRecyclerViewAdapter(List<ELEMENT_TYPE> elements, int rowResource, Class<HOLDER_TYPE> holderClazz) {
        this.elements = elements;
        this.rowResource = rowResource;
        this.holderClazz = holderClazz;
    }

    @Override
    @SuppressWarnings("unchecked")
    public HOLDER_TYPE onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(rowResource, parent, false);

        try {
            return (HOLDER_TYPE) Class.forName(holderClazz.getName()).getConstructor(View.class).newInstance(itemView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e(MainActivity.DEBUG_APP, "Exception creating view holder");
        throw new RuntimeException("Exception creating view holder");
    }

    @Override
    public void onBindViewHolder(HOLDER_TYPE holder, int position) {
        ELEMENT_TYPE element = elements.get(position);
        holder.setHolderData(element);
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    public void updateDataAndNotify(List<ELEMENT_TYPE> elements) {
        this.elements = elements;
        notifyDataSetChanged();
    }
}
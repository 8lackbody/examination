package com.zht.examination.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.zht.examination.R;
import com.zht.examination.device.ReadTag;

import java.io.Serializable;
import java.util.List;

public class AllAdapter extends BaseAdapter {
    private Context mContext;
    private List<ReadTag> mList;
    private LayoutInflater layoutInflater;

    public AllAdapter(Context context, List<ReadTag> list) {
        mContext = context;
        mList = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mList.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewParent) {
        // TODO Auto-generated method stub
        ItemView iv = null;
        if (view == null) {
            iv = new ItemView();
            view = layoutInflater.inflate(R.layout.list_all, null);
            iv.tvId = (TextView) view.findViewById(R.id
                    .id_text);
            iv.tvEpc = (TextView) view.findViewById(R.id.epc_text);
            view.setTag(iv);
        } else {
            iv = (ItemView) view.getTag();
        }

        return view;
    }

    public class ItemView implements Serializable {
        TextView tvId;
        TextView tvEpc;
    }
}

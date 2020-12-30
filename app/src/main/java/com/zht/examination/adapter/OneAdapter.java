package com.zht.examination.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zht.examination.R;
import com.zht.examination.device.ReadTag;

import java.util.List;

public class OneAdapter extends BaseAdapter {
    private Context mContext;
    private List<ReadTag> mList;
    private LayoutInflater layoutInflater;

    public OneAdapter(Context context, List<ReadTag> list) {
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
        if(view == null){
            iv = new ItemView();
            view = layoutInflater.inflate(R.layout.list_one, null);
            iv.tvId = view.findViewById(R.id.id_text);
            iv.tvEpc = view.findViewById(R.id.epc_text);
            iv.tvTime = view.findViewById(R.id.times_text);
            view.setTag(iv);
        }else{
            iv = (ItemView)view.getTag();
        }

        return view;
    }

    public class ItemView{
        TextView tvId;
        TextView tvEpc;
        TextView tvTime;
    }
}

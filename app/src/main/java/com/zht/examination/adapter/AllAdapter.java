package com.zht.examination.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zht.examination.R;
import com.zht.examination.device.ReadTag;

import java.util.List;

public class AllAdapter extends RecyclerView.Adapter<AllAdapter.MyViewHolder> {

    private Context context;
    private List<ReadTag> data;
    private View inflater;

    //构造方法，传入数据
    public AllAdapter(Context context, List<ReadTag> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        inflater = LayoutInflater.from(context).inflate(R.layout.list_all, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(inflater);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textEpc.setText(position);
        holder.textId.setText(data.get(position).getEpcId());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setList(List<ReadTag> data) {
        this.data = data;
    }

    //内部类，绑定控件
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textEpc;
        TextView textId;

        public MyViewHolder(View itemView) {
            super(itemView);
            textEpc = itemView.findViewById(R.id.epc_text);
            textId = itemView.findViewById(R.id.id_text);
        }
    }
}

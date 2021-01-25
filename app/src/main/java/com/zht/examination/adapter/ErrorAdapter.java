package com.zht.examination.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zht.examination.R;
import com.zht.examination.device.ErrorData;

import java.util.List;

public class ErrorAdapter extends RecyclerView.Adapter<ErrorAdapter.MyViewHolder> {

    private Context context;
    private List<ErrorData> data;
    private View inflater;

    //构造方法，传入数据
    public ErrorAdapter(Context context, List<ErrorData> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ErrorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        inflater = LayoutInflater.from(context).inflate(R.layout.list_error, parent, false);
        ErrorAdapter.MyViewHolder myViewHolder = new ErrorAdapter.MyViewHolder(inflater);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textId.setText(position);
        holder.textEpc.setText(data.get(position).getEpc());
        holder.textError.setText(data.get(position).getErrorType());
    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public void setList(List<ErrorData> data) {
        this.data = data;
    }

    //内部类，绑定控件
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textId;
        TextView textEpc;
        TextView textError;

        public MyViewHolder(View itemView) {
            super(itemView);
            textEpc = itemView.findViewById(R.id.epc_text);
            textId = itemView.findViewById(R.id.id_text);
            textError = itemView.findViewById(R.id.type_text);
        }
    }
}

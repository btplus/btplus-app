package com.hackathon.btp.btplus.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hackathon.btp.btplus.R;
import com.hackathon.btp.btplus.model.Compromisso;

import java.util.List;

public class CompromissoCustomAdapter extends RecyclerView.Adapter<CompromissoCustomAdapter.MyViewHolder> {

    private Context mContext;
    private List<Compromisso> compromissoList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_titulo;
        TextView tv_data_vencimento;
        ImageView iv_icon;
        LinearLayout ll_line;

        public MyViewHolder(View view) {
            super(view);
            tv_titulo = (TextView) view.findViewById(R.id.tv_titulo);
            tv_data_vencimento = (TextView) view.findViewById(R.id.tv_data_vencimento);
            iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            ll_line = (LinearLayout) view.findViewById(R.id.ll_line);
        }
    }

    public CompromissoCustomAdapter(Context mContext, List<Compromisso> compromissoList) {
        this.mContext = mContext;
        this.compromissoList = compromissoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_compromisso, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Compromisso compromisso = compromissoList.get(position);
        holder.tv_titulo.setText("" + compromisso.getTitulo());
        holder.tv_data_vencimento.setText("" + compromisso.getDataVencimento());

        if(compromisso.getTipoCompromisso().equals("EXAME")) {
            holder.iv_icon.setImageResource(R.drawable.exame_icon);
        } else {
            holder.iv_icon.setImageResource(R.drawable.course_icon);
        }

        holder.ll_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return compromissoList.size();
    }
}
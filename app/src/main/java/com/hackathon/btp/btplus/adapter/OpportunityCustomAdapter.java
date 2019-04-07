package com.hackathon.btp.btplus.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hackathon.btp.btplus.R;
import com.hackathon.btp.btplus.model.Compromisso;
import com.hackathon.btp.btplus.model.Oportunidade;

import java.util.List;

public class OpportunityCustomAdapter extends RecyclerView.Adapter<OpportunityCustomAdapter.MyViewHolder> {

    private Context mContext;
    private List<Oportunidade> oportunidadeList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_vaga;
        TextView tv_percentage;
        TextView tv_curso1;
        TextView tv_curso2;
        TextView tv_faculdade;
        TextView tv_tempo_empresa;
        ImageView iv_icon;
        LinearLayout ll_line;

        public MyViewHolder(View view) {
            super(view);
            tv_vaga = (TextView) view.findViewById(R.id.tv_vaga);
            tv_percentage = (TextView) view.findViewById(R.id.tv_percentage);
            tv_curso1 = (TextView) view.findViewById(R.id.tv_curso1);
            tv_curso2 = (TextView) view.findViewById(R.id.tv_curso2);
            tv_faculdade = (TextView) view.findViewById(R.id.tv_faculdade);
            tv_tempo_empresa = (TextView) view.findViewById(R.id.tv_tempo_empresa);

            iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            ll_line = (LinearLayout) view.findViewById(R.id.ll_line);
        }
    }

    public OpportunityCustomAdapter(Context mContext, List<Oportunidade> oportunidades) {
        this.mContext = mContext;
        this.oportunidadeList = oportunidades;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_oportunidade, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Oportunidade oportunidade = oportunidadeList.get(position);
        holder.tv_vaga.setText("" + oportunidade.getVaga());
        holder.tv_percentage.setText("" + oportunidade.getPercentage());
        holder.tv_curso1.setText("" + oportunidade.getCurso1());
        holder.tv_curso2.setText("" + oportunidade.getCurso2());
        holder.tv_faculdade.setText("" + oportunidade.getFaculdade());
        holder.tv_tempo_empresa.setText("" + oportunidade.getTempoEmpresa());

        holder.ll_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return oportunidadeList.size();
    }
}

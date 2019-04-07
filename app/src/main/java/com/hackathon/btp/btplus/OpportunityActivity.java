package com.hackathon.btp.btplus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.hackathon.btp.btplus.adapter.CompromissoCustomAdapter;
import com.hackathon.btp.btplus.adapter.OpportunityCustomAdapter;
import com.hackathon.btp.btplus.model.Compromisso;
import com.hackathon.btp.btplus.model.Oportunidade;

import java.util.ArrayList;

public class OpportunityActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ArrayList<Oportunidade> listOportunidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opportunity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView = (RecyclerView) findViewById(R.id.listOportunidades);
        recyclerView.setLayoutManager(mLayoutManager);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);


        listOportunidade = new ArrayList<Oportunidade>();
        listOportunidade.add(new Oportunidade("Vaga de Operador 5", "50%", "Curso 1 - OK", "Curso 2 - NOK", "Faculdade - OK", "3 anos de empresa"));
        listOportunidade.add(new Oportunidade("Vaga de Operador 5 STS", "75%", "Curso 1 - OK", "Curso 2 - OK", "Faculdade - OK", "3 anos de empresa"));


        OpportunityCustomAdapter opportunityCustomAdapter;
        opportunityCustomAdapter = new OpportunityCustomAdapter(OpportunityActivity.this, listOportunidade);

        recyclerView.setAdapter(opportunityCustomAdapter);

    }
}

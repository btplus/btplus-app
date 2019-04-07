package com.hackathon.btp.btplus.model;

import java.util.ArrayList;

public class Payload {

    String text;
    Object context;
    String output;
    ArrayList<Compromisso> compromissos;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getContext() {
        return context;
    }

    public void setContext(Object context) {
        this.context = context;
    }


    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public ArrayList<Compromisso> getCompromissos() {
        return compromissos;
    }

    public void setCompromissos(ArrayList<Compromisso> compromissos) {
        this.compromissos = compromissos;
    }
}

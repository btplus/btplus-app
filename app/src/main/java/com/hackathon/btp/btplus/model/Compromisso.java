package com.hackathon.btp.btplus.model;

public class Compromisso {

    String _id;
    String _rev;
    String titulo;
    String status;
    String tipo;
    String tipoCompromisso;
    String dataVencimento;
    boolean realizado;

    public Compromisso(String titulo, String status, String tipo, String tipoCompromisso, String dataVencimento) {
        this.titulo = titulo;
        this.status = status;
        this.tipo = tipo;
        this.tipoCompromisso = tipoCompromisso;
        this.dataVencimento = dataVencimento;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipoCompromisso() {
        return tipoCompromisso;
    }

    public void setTipoCompromisso(String tipoCompromisso) {
        this.tipoCompromisso = tipoCompromisso;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }
}

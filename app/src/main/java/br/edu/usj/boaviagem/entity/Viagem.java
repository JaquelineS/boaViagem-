package br.edu.usj.boaviagem.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by jaqueline on 26/06/2017.
 */

public class Viagem {

    private int id, quantidadePessoas;
    private String destino, tipoViagem;
    private Date dataSaida, dataChegada;
    private BigDecimal orcamento;

    public Viagem() {
    }

    public Viagem(int id) {
        this();
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidadePessoas() {
        return quantidadePessoas;
    }

    public void setQuantidadePessoas(int quantidadePessoas) {
        this.quantidadePessoas = quantidadePessoas;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getTipoViagem() {
        return tipoViagem;
    }

    public void setTipoViagem(String tipoViagem) {
        this.tipoViagem = tipoViagem;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public Date getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(Date dataChegada) {
        this.dataChegada = dataChegada;
    }

    public BigDecimal getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(BigDecimal orcamento) {
        this.orcamento = orcamento;
    }
}

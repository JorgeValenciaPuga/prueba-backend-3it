package com.tresit.creacionusuarios.domain;

public class TelefonoResponseDTO {
    private String numero;
    private String codigoArea;
    private String codigoPais;

    public TelefonoResponseDTO(String numero, String codigoArea, String codigoPais) {
        this.numero = numero;
        this.codigoArea = codigoArea;
        this.codigoPais = codigoPais;
    }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getCodigoArea() { return codigoArea; }
    public void setCodigoArea(String codigoArea) { this.codigoArea = codigoArea; }

    public String getCodigoPais() { return codigoPais; }
    public void setCodigoPais(String codigoPais) { this.codigoPais = codigoPais; }
}


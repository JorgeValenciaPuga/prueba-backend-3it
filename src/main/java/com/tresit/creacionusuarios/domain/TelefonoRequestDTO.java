package com.tresit.creacionusuarios.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class TelefonoRequestDTO {

    @NotBlank(message = "El número de teléfono es obligatorio")
    @Pattern(regexp = "^\\d{7,15}$", message = "El número debe contener entre 7 y 15 dígitos")
    private String numero;

    @NotBlank(message = "El código de área es obligatorio")
    @Pattern(regexp = "^\\d+$", message = "El código de área solo debe contener números")
    private String codigoArea;

    @NotBlank(message = "El código de país es obligatorio")
    @Pattern(regexp = "^\\d+$", message = "El código de país solo debe contener números")
    private String codigoPais;

    // Getters y setters
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getCodigoArea() { return codigoArea; }
    public void setCodigoArea(String codigoArea) { this.codigoArea = codigoArea; }

    public String getCodigoPais() { return codigoPais; }
    public void setCodigoPais(String codigoPais) { this.codigoPais = codigoPais; }
}

package com.mycompany.restaurante.dto.response;

import com.mycompany.restaurante.model.Chef;

import java.util.Date;

public class ChefResponseDTO {

    private int id;
    private String nombre;

    private String turno;
    private String especialidad;
    private int anosExperiencia;

    public ChefResponseDTO() {}

    public static ChefResponseDTO from(Chef chef) {
        ChefResponseDTO dto = new ChefResponseDTO();

        dto.setId(chef.getId());
        dto.setNombre(chef.getNombre());

        dto.setTurno(chef.getTurno());
        dto.setEspecialidad(chef.getEspecialidad());
        dto.setAnosExperiencia(chef.getAnosExperiencia());

        return dto;
    }

    private void setAnosExperiencia(int anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
    }

    private void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    private void setTurno(String turno) {
        this.turno = turno;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTurno() {
        return turno;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public int getAnosExperiencia() {
        return anosExperiencia;
    }
}

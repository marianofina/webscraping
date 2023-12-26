package org.example.models;

import java.sql.Date;

public class Empresa {
    private String nombre;
    private String descripcion;
    private Integer empleos;
    private String rubro;
    private String link;
    private Date fecha;
    private Integer pais;
    private Integer estadoEmpresa;
    private Integer id_screenshot;

    public Empresa() {
    }

    public Empresa(String nombre,
                   String descripcion,
                   Integer empleos,
                   String rubro,
                   String link,
                   Date fecha,
                   Integer pais,
                   Integer estadoEmpresa,
                   Integer id_screenshot) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.empleos = empleos;
        this.rubro = rubro;
        this.link = link;
        this.fecha = fecha;
        this.pais = pais;
        this.estadoEmpresa = estadoEmpresa;
        this.id_screenshot = id_screenshot;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getEmpleos() {
        return empleos;
    }

    public void setEmpleos(Integer empleos) {
        this.empleos = empleos;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getPais() {
        return pais;
    }

    public void setPais(Integer pais) {
        this.pais = pais;
    }

    public Integer getEstadoEmpresa() {
        return estadoEmpresa;
    }

    public void setEstadoEmpresa(Integer estadoEmpresa) {
        this.estadoEmpresa = estadoEmpresa;
    }

    public Integer getId_screenshot() {
        return id_screenshot;
    }

    public void setId_screenshot(Integer id_screenshot) {
        this.id_screenshot = id_screenshot;
    }
}

package com.challengerlura.Busca.tu.Buk.model;



import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaFallecio;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Libros> libros;

    public Autor(){ }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaFallecio() {
        return fechaFallecio;
    }

    public void setFechaFallecio(Integer fechaFallecio) {
        this.fechaFallecio = fechaFallecio;
    }

    public List<Libros> getLibros() {
        return libros;
    }

    public void setLibros(List<Libros> libros) {
        this.libros = libros;
    }

    public Autor(DatosAutor autor){
        this.nombre =  autor.nombreAutor();
        this.fechaNacimiento = autor.fechaNacimiento();
        this.fechaFallecio = autor.fechaFallecio();
    }

    @Override
    public String toString() {
        return  "Nombre='" + nombre + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", fechaFallecio=" + fechaFallecio;

    }
}

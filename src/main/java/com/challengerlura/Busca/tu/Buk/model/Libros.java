package com.challengerlura.Busca.tu.Buk.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "libros")
public class Libros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long libroId;

    @Column(unique = true)
    private String titulo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "autor_id")
    private Autor autor;


    private String idioma;
    private String imagen;

    private Long descargas;

    public Libros(){ }

    public Libros(DatosLibro datosLibro){
        this.libroId = datosLibro.libroId();
        this.titulo = datosLibro.titulo();
        if(datosLibro.autor() != null && !datosLibro.autor().isEmpty()){
            this.autor = new Autor(datosLibro.autor().get(0));
        } else {
            this.autor = null;
        }
        this.idioma = idiNuevo(datosLibro.idioma());
        this.imagen = imgNueva(datosLibro.imagen());
        this.descargas = datosLibro.descargas();

    }

    private String imgNueva(Media imagens) {
        if (imagens == null || imagens.imagen().isEmpty()){
            return "No tiene Imagen";
        }
        return imagens.imagen();
    }

    private String idiNuevo(List<String> idiomas) {
        if (idiomas == null || idiomas.isEmpty()){
            return "Desconocido";
        }
        return idiomas.get(0);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLibroId() {
        return libroId;
    }

    public void setLibroId(Long libroId) {
        this.libroId = libroId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Long getDescargas() {
        return descargas;
    }

    public void setDescargas(Long descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return  "\nid=" + id +
                "\nlibroId=" + libroId +
                "\ntitulo='" + titulo + '\'' +
                "\nautor=" + autor +
                "\nidioma='" + idioma + '\'' +
                "\nimagen='" + imagen + '\'' +
                "\ndescargas=" + descargas;
    }
}

package com.pooespol.Publicacion;

import java.util.ArrayList;

import com.pooespol.Principales.*;
import com.pooespol.Tipos.EstadoArticulo;

/**
 * Clase que representa un artículo publicado en el Sistema de Gestion Articulos.
 **/
public class Articulo {
    private Autor autor;
    private int codigoArticulo;
    private String titulo;
    private String resumen;
    private String contenido;
    private String palabrasClave;
    private EstadoArticulo estado;
    private ArrayList<Revisor> revisores;
    private Editor editor;

    /**
     * Constructor de la clase Articulo.
     * @param autor          El autor del artículo.
     * @param codigoArticulo El código único del artículo.
     * @param titulo         El título del artículo.
     * @param resumen        El resumen del artículo.
     * @param contenido      El contenido del artículo.
     * @param palabrasClave  Las palabras clave del artículo.
     **/    
    public Articulo(Autor autor ,int codigoArticulo, String titulo, String resumen, String contenido, String palabrasClave) {
        this.autor=autor;
        this.codigoArticulo = codigoArticulo;
        this.titulo = titulo;
        this.resumen = resumen;
        this.contenido = contenido;
        this.palabrasClave = palabrasClave;
        this.revisores = new ArrayList<>();
        this.editor = null; // Inicialmente no tiene editor asignado
        this.estado=EstadoArticulo.SINREVISION;
    }
    
/**
     * Obtiene el autor del artículo.
     * @return El autor del artículo.
     **/
    public Autor getAutor() {
        return autor;
    }

    /**
     * Obtiene el código único del artículo.
     * @return El código único del artículo.
     **/
    public int getCodigoArticulo() {
        return codigoArticulo;
    }

     /**
     * Obtiene el título del artículo.
     * @return El título del artículo.
     **/
    public String getTitulo() {
        return titulo;
    }

     /**
     * Obtiene el resumen del artículo.
     * @return El resumen del artículo.
     **/
    public String getResumen() {
        return resumen;
    }

     /**
     * Obtiene el contenido del artículo.
     * @return El contenido del artículo.
     **/
    public String getContenido() {
        return contenido;
    }

    /**
     * Obtiene las palabras clave del artículo.
     * @return Las palabras clave del artículo.
     **/
    public String getPalabrasClave() {
        return palabrasClave;
    }

    /**
     * Obtiene el estado actual del artículo.
     * @return El estado del artículo.
     **/
    public EstadoArticulo getEstado() {
        return estado;
    }

    /**
     * Establece el estado del artículo.
     * @param estado El estado a establecer.
     **/
    public void setEstado(EstadoArticulo estado) {
        this.estado = estado;
    }
    
    /**
     * Agrega un revisor al artículo.
     * @param revisor El revisor a agregar.
     **/
    public void agregarRevisor(Revisor revisor) {
        revisores.add(revisor);
    }

    /**
     * Obtiene la lista de revisores asignados al artículo.
     * @return La lista de revisores del artículo.
     **/
    public ArrayList<Revisor> getRevisores() {
        return revisores;
    }

    /**
     * Establece el editor del artículo.
     * @param editor El editor a establecer.
     **/
    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    /**
     * Obtiene el editor del artículo.
     * @return El editor del artículo.
     **/
    public Editor getEditor() {
        return editor;
    }

    /**
     * Devuelve una representación en cadena del artículo.
     * @return Una cadena que describe el artículo con su título, autor, código, resumen, contenido, palabras clave y estado.
     **/    
    @Override
    public String toString() {
        return "El Articulo con " +
                "Titulo='" + titulo + '\'' +
                ", Autor:" + autor.getNombre()+" "+autor.getApellido() +
                ", codigo del Articulo=" + codigoArticulo +
                ", resumen='" + resumen + '\'' +
                ", contenido='" + contenido + '\'' +
                ", palabrasClave='" + palabrasClave + '\'' +
                ", estado=" + estado +
                " fue sometido existosamente";
    }
}


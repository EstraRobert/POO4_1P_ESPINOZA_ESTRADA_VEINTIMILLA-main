package com.pooespol.Publicacion;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.pooespol.Principales.Usuario;

/**
 * Clase que representa a un autor de artículos científicos dentro del Sistema de Gestion Articulos .
 **/
public class Autor extends Usuario {
    private int codigoID;
    private String institucion;
    private String campoInvestigacion;

    /**
     * Constructor de la clase Autor.
     * @param codigoID           El código identificador único del autor.
     * @param nombre             El nombre del autor.
     * @param apellido           El apellido del autor.
     * @param correo             El correo electrónico del autor.
     * @param institucion        La institución a la que pertenece el autor.
     * @param campoInvestigacion El campo de investigación del autor.
     **/
    public Autor(int codigoID, String nombre, String apellido, String correo, String institucion, String campoInvestigacion) {
        super(nombre,apellido,correo);
        this.codigoID = codigoID;
        this.institucion = institucion;
        this.campoInvestigacion = campoInvestigacion;
    }

    /**
     * Obtiene el código identificador único del autor.
     * @return El código identificador del autor.
     **/
    public int getCodigoID() {
        return codigoID;
    }
   
    /**
     * Obtiene la institución a la que pertenece el autor.
     * @return La institución del autor.
     **/
    public String getInstitucion() {
        return institucion;
    }

    /**
     * Obtiene el campo de investigación del autor.
     * @return El campo de investigación del autor.
     **/
    public String getCampoInvestigacion() {
        return campoInvestigacion;
    }

    /**
     * Devuelve una representación en cadena del autor, incluyendo su nombre, apellido, correo electrónico,
     * institución y campo de investigación.
     * @return Representación en cadena del autor.
     **/
    @Override
    public String toString() {
        return  super.toString()+
                ", institucion='" + institucion + '\'' +
                ", campoInvestigacion='" + campoInvestigacion + '\'' +
                ' ';
    }

    /**
     * Genera y registra un nuevo artículo con los datos proporcionados.
     * @param scanner   El scanner para la entrada de datos.
     * @param articulos La lista de artículos donde se agregará el nuevo artículo.
     * @param autor     El autor que está sometiendo el artículo.
     **/
    public void someterArticulo(Scanner scanner, ArrayList<Articulo> articulos,Autor autor) {
        // Registrar datos del artículo
        System.out.println("\nRegistro de datos del artículo:");
        System.out.print("Título: ");
        String tituloArticulo = scanner.nextLine();
        System.out.print("Resumen: ");
        String resumenArticulo = scanner.nextLine();
        System.out.print("Contenido: ");
        String contenidoArticulo = scanner.nextLine();
        System.out.print("Palabras clave: ");
        String palabrasClaveArticulo = scanner.nextLine();

        // Generar un código único para el artículo (simulación)
        Random r=new Random();
        int i= r.nextInt(50);
        int codigoArticulo = codigoID + 1*i; // Ejemplo simple de generación de código único

        Articulo articulo = new Articulo(autor,codigoArticulo, tituloArticulo, resumenArticulo, contenidoArticulo, palabrasClaveArticulo);
        articulos.add(articulo);
        System.out.println(articulo.toString());


    }

}

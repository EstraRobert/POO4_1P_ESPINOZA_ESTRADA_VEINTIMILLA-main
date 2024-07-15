package com.pooespol.Principales;

/**
 * Clase abstracta Usuario que representa a un usuario genérico en el sistema.
 **/
public abstract class Usuario {
    private String nombre;
    private String apellido;
    private String correo;

    /**
     * Constructor de la clase Usuario.
     * @param nombre   Nombre del usuario.
     * @param apellido Apellido del usuario.
     * @param correo   Correo electrónico del usuario.
     **/
    public Usuario(String nombre, String apellido, String correo ) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
    }
    
    /**
     * Obtiene el nombre del usuario.
     * @return El nombre del usuario.
     **/
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el apellido del usuario.
     * @return El apellido del usuario.
     **/
    public String getApellido() {
        return apellido;
    }

     /**
     * Obtiene el correo electrónico del usuario.
     * @return El correo electrónico del usuario.
     **/
    public String getCorreo() {
        return correo;
    }    

    /**
     * Método abstracto que representa la tarea asignada al usuario.
     * Cada subclase debe implementar su propia versión de esta tarea.
     **/
    public void tareaAsignada(){
        System.out.println("");
    }

    /**
     * Representación en cadena del objeto Usuario.
     * @return Una cadena que representa el objeto Usuario.
     **/
    @Override
    public String toString() {
        return  "Nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                ' ';
    }
}

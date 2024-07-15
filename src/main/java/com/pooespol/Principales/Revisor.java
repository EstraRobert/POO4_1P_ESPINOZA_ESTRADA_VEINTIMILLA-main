package com.pooespol.Principales;


import java.util.Scanner;

import com.pooespol.Main.Aplicacion;
import com.pooespol.Publicacion.Articulo;
import com.pooespol.Tipos.TipoDeRol;

/**
 * Clase Revisor que hereda de Usuario. Representa a un revisor en el Sistema de Gestion Articulos.
 */
public class Revisor extends Usuario {
    private String userAcceso;
    private String contrasena;
    private TipoDeRol tipoRol;
    private String especialidad;
    private int numeroArticulosRevisados;
    private Articulo articuloAsignado; // Nuevo atributo para almacenar el artículo asignado al revisor
    private boolean decision;
    private String comentarios; // Nuevo atributo para almacenar los comentarios del revisor
    private boolean decisionTomada;
    public static  Scanner sc = new Scanner(System.in);

    /**
     * Constructor de la clase Revisor.
     * 
     * @param nombre      Nombre del revisor.
     * @param apellido    Apellido del revisor.
     * @param correo      Correo electrónico del revisor.
     * @param userAcceso  Usuario para acceder al sistema.
     * @param contrasena  Contraseña para acceder al sistema.
     * @param especialidad Especialidad del revisor.
     **/
    public Revisor(String nombre, String apellido, String correo, String userAcceso, String contrasena, String especialidad) {
        super(nombre, apellido, correo);
        this.tipoRol=TipoDeRol.R;
        this.userAcceso= userAcceso;
        this.contrasena=contrasena;
        this.especialidad = especialidad;
        this.numeroArticulosRevisados = 0;
        this.articuloAsignado = null; // Inicialmente no tiene artículo asignado
    }
    
    /**
    * Obtiene la especialidad del revisor.
    * @return La especialidad del revisor.
    **/    
    public String getEspecialidad() {
        return especialidad;
    }

    /**
     * Obtiene el número de artículos revisados por el revisor.
     * @return El número de artículos revisados.
     **/
    public int getNumeroArticulosRevisados() {
        return numeroArticulosRevisados;
    }

     /**
     * Incrementa el número de artículos revisados por el revisor.
     **/
    public void incrementarNumeroArticulosRevisados() {
        this.numeroArticulosRevisados++;
    }

    /**
     * Asigna un artículo al revisor.
     * @param articulo El artículo a asignar.
     **/
    public void setArticuloAsignados(Articulo articulo) {
        this.articuloAsignado = articulo;
    }
   
    /**
     * Obtiene el artículo asignado al revisor.
     * @return El artículo asignado.
     **/
    public Articulo getArticuloAsignados() {
        return articuloAsignado;
    }

    /**
     * Obtiene la decisión del revisor sobre el artículo.
     * @return La decisión del revisor.
     **/
    public boolean getDecision() {
        return decision;
    }

    /**
     * Establece la decisión del revisor sobre el artículo.
     * @param decision La decisión del revisor.
     **/
    public void setdecision(boolean decision) {
        this.decision=decision;
    } 
    
     /**
     * Obtiene si el revisor ha tomado una decisión.
     * @return true si la decisión ha sido tomada, false en caso contrario.
     */
    public boolean getDecisionTomada() {
        return decisionTomada;
    }

     /**
     * Establece si el revisor ha tomado una decisión.
     * @param decisionTomada true si la decisión ha sido tomada, false en caso contrario.
     **/
    public void setDecisionTomada(boolean decisionTomada) {
        this.decisionTomada=decisionTomada;
    } 
    
    /**
     * Obtiene los comentarios del revisor sobre el artículo.
     * @return Los comentarios del revisor.
     **/
    public String getComentarios() {
        return comentarios;
    }
    
    /**
     * Agrega comentarios del revisor sobre el artículo.
     * @param comentarios Los comentarios del revisor.
     **/
    public void agregarComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    
    /**
     * Obtiene el nombre de usuario del revisor.
     * @return El nombre de usuario.
     **/
    public String getUserAcesso(){
        return userAcceso;
    }
    
     /**
     * Establece el nombre de usuario del revisor.
     * @param user El nombre de usuario.
     **/
    public void setUserAcceso(String user){
        this.userAcceso=user;

    }
    
     /**
     * Obtiene la contraseña del revisor.
     * @return La contraseña.
     **/
    public String getContrasena(){
        return contrasena;
    }

    /**
     * Establece la contraseña del revisor.
     * @param password La contraseña.
     **/
    public void setContraseña( String password){
        this.contrasena=password;
    }    

     /**
     * Obtiene el tipo de rol del revisor.
     * @return El tipo de rol.
     **/
    public TipoDeRol getTipoDeRol(){
        return tipoRol;
    }

    /**
    * Inicia sesión del revisor.
    * @param user     El nombre del revisor.
    * @param password La contraseña del revisor.
    * @return true si las credenciales son correctas, false en caso contrario.
    **/
    public boolean IniciarSesion(String user, String password){
        if (userAcceso.equals(user)&& contrasena.equals(password)){
            return true;
        } else{
            return false;
        }
    }

    /**
     * Asigna la tarea de revisar un artículo al revisor.
     **/
    @Override
    public void tareaAsignada() {
        int idArticulo= articuloAsignado.getCodigoArticulo();
        Aplicacion.procesarDecisionesTomadas("src\\main\\java\\com\\pooespol\\Informacion.txt\\ComentariosDecisiones.txt", idArticulo);     
        if (articuloAsignado != null && decisionTomada==false) {
            System.out.println("Revisión de artículo asignada: " + articuloAsignado.getTitulo());
            mostrarDetalleArticulo();
            tomarDecision();
            decisionTomada=true;
            
        } else if(decisionTomada) {
            System.out.println("Ya has tomado la decicsion sobre este articulo, muchas gracias.");
        }else {
            System.out.println("No se ha asignado ningún artículo para revisar.");
        }
    }

    /**
     * Toma la decisión del revisor sobre el artículo.
     * @return La decisión del revisor.
     **/
    public boolean tomarDecision() {
        System.out.println("¿Qué decisión toma sobre la revisión del artículo?");
        System.out.println("1. Aprobar");
        System.out.println("2. Desaprobar");
        System.out.print("Ingrese su elección (1 o 2): ");
        int opcion = sc.nextInt();
        sc.nextLine(); // Consumir la nueva línea después de nextInt()
        if (opcion == 1) {
            decision = true; // Aprobar
        } else if (opcion == 2) {
            decision = false; // Desaprobar
        } else {
            System.out.println("Opción no válida. Se considerará como desaprobado.");
            decision = false;
        }

        System.out.println("Su decisión ha sido registrada.");
        return decision;
    }

    /**
     * Muestra los detalles del artículo asignado.
     **/
    public void mostrarDetalleArticulo() {
        if (articuloAsignado != null) {
            System.out.println("\nDetalles del artículo asignado:");
            System.out.println("Título: " + articuloAsignado.getTitulo());
            System.out.println("Resumen: " + articuloAsignado.getResumen());
            System.out.println("Contenido: " + articuloAsignado.getContenido());
            System.out.println("Palabras clave: " + articuloAsignado.getPalabrasClave());
        } else {
            System.out.println("No se ha asignado ningún artículo para revisar.");
        }
    }

    /**
     * Devuelve una representación en cadena del revisor.
     * @return La representación en cadena del revisor.
     **/
    @Override
    public String toString() {
        return "Revisor: "+super.toString()+
                ", especialidad='" + especialidad + '\'' ;
    }
    
    /**
     * Guarda los comentarios del revisor sobre el artículo.
     * @param revisor El revisor que guarda los comentarios.
     **/
    public void guardarComentarios(Revisor revisor){
        int i= revisor.getArticuloAsignados().getRevisores().indexOf(revisor);
        i+=1;
        Aplicacion.escribirArchivo("src\\main\\java\\com\\pooespol\\Informacion.txt\\ComentariosDecisiones.txt","Revisor:"+revisor.getNombre()+" "+ revisor.getApellido()+", Articulo: "+articuloAsignado.getTitulo()+", Codigo: "+articuloAsignado.getCodigoArticulo()+", decision del Revisor"+i+": "+revisor.getDecision()+", comentarios del Revisor"+i+": "+revisor.getComentarios()+", decision ya tomada: "+decisionTomada );
    }
}

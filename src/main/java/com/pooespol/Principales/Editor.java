package com.pooespol.Principales;

import java.util.ArrayList;
import java.util.Scanner;

import com.pooespol.Publicacion.Articulo;
import com.pooespol.Tipos.*;

/**
 * Clase Editor que hereda de Usuario. Representa a un editor en el Sistema de Gestion Articulos.
 */
public class Editor extends Usuario {
    private String nombreJournal;
    private ArrayList<Articulo>ArticuloAsignados; // Nuevo atributo para almacenar el artículo asignado al revisor
    private boolean decision;
    private String userAcceso;
    private String contrasena;
    private TipoDeRol tipoRol;
    private boolean decisionTomada;
    public static  Scanner sc = new Scanner(System.in);

    /**
     * Constructor de la clase Editor.
     * @param nombre      Nombre del editor.
     * @param apellido    Apellido del editor.
     * @param correo      Correo electrónico del editor.
     * @param userAcceso  Usuario para acceder al sistema.
     * @param contrasena  Contraseña para acceder al sistema.
     * @param nombreJournal Nombre del journal al que pertenece el editor.
     **/    
    public Editor(String nombre, String apellido, String correo, String userAcceso, String contrasena, String nombreJournal) {
        super(nombre, apellido, correo);
        this.tipoRol=TipoDeRol.E;
        this.userAcceso= userAcceso;
        this.contrasena=contrasena;
        this.nombreJournal = nombreJournal;
        this.ArticuloAsignados = new ArrayList<>(); // Inicialmente no tiene artículo asignado

    }
    
    /**
     * Establece los artículos asignados al editor.
     * @param articulo Lista de artículos asignados.
     **/
    public void setArticuloAsignados(ArrayList<Articulo> articulo) {
        this.ArticuloAsignados = articulo;
    }
    
    /**
     * Obtiene la lista de artículos asignados al editor.
     * @return La lista de artículos asignados.
     **/
    public ArrayList<Articulo> getArticulosAsignado() {
        return ArticuloAsignados;
    }
    
    /**
     * Obtiene el nombre del journal al que pertenece el editor.
     * @return El nombre del journal.
     **/
    public String getNombreJournal() {
        return nombreJournal;
    }
    
    /**
     * Obtiene la decisión del editor sobre un artículo.
     * @return La decisión del editor.
     **/
    public boolean getDecision() {
        return decision;
    }
    
    /**
     * Obtiene el usuario de acceso del editor.
     * 
     * @return El usuario de acceso.
     **/
    public String getUserAcesso(){
        return userAcceso;
    }
    
    /**
     * Obtiene el estado de decisión tomada por el editor.
     * @return El estado de decisión tomada.
     **/
    public boolean getDecisionTomada() {
        return decisionTomada;
    }
    
    /**
     * Establece el usuario de acceso del editor.
     * @param user Usuario de acceso.
     **/
    public void setUserAcceso(String user){
        this.userAcceso=user;

    }
    
    /**
     * Obtiene la contraseña del editor.
     * @return La contraseña del editor.
     **/
    public String getContrasena(){
        return contrasena;
    }
    
    /**
     * Establece la contraseña del editor.
     * @param password Contraseña del editor.
     **/
    public void setContraseña( String password){
        this.contrasena=password;
    }
    
    /**
     * Establece la decisión del editor sobre un artículo.
     * @param decision Decisión del editor.
     **/
    public void setdecision(boolean decision) {
        this.decision=decision;
    }

    /**
     * Establece el estado de decisión tomada por el editor.
     * @param decisionTomada Estado de decisión tomada.
     **/
    public void setDecisionTomada(boolean decisionTomada) {
        this.decisionTomada=decisionTomada;
    } 
    
    /**
     * Obtiene el tipo de rol del editor.
     * @return El tipo de rol del editor.
     **/
    public TipoDeRol getTipoDeRol(){
        return tipoRol;
    }
    
    /**
     * Realiza el proceso de inicio de sesión para el editor.
     * @param user     Usuario para iniciar sesión.
     * @param password Contraseña para iniciar sesión.
     * @return true si el inicio de sesión es exitoso, false en caso contrario.
     **/
    public boolean IniciarSesion(String user, String password){
        if (userAcceso.equals(user)&& contrasena.equals(password)){
            return true;
        } else{
            return false;
        }
    }
    
    /**
     * Establece los artículos asignados al editor.
     * @param articulo Lista de artículos asignados.
     **/
    @Override
    public void tareaAsignada(){

    }
    
   /**
     * Método que maneja la tarea asignada al editor.
     * @param idArticulo ID del artículo a revisar.
     * @return true si el artículo se encuentra y se procesa, false si no se encuentra.
     **/
    public boolean tareaAsignada(int idArticulo) {
        System.out.println("Revisión de artículos pendientes para la revista " + nombreJournal);
        boolean articuloEncontrado = false;
        String decisionEditor;
        if( getDecision()){
            decisionEditor= "aprobado";
        } else{
            decisionEditor= "no aprobado";
        }
        for (Articulo a : ArticuloAsignados) {
            if (a.getCodigoArticulo() == idArticulo) {
                articuloEncontrado = true;
                mostrarDetalleArticulo(idArticulo);
    
                for (Revisor r : a.getRevisores()) {
                    System.out.println("Comentarios Revisor " + r.getNombre() + ": " + r.getComentarios());
                    if (r.getDecision()) {
                        System.out.println("Decisión Revisor " + r.getNombre() + ": Aprobado");
                    } else {
                        System.out.println("Decisión Revisor " + r.getNombre() + ": No aprobado");
                    }
                }
                System.out.println("");
                if(decisionTomada== false){
                    this.decision = tomarDecision(a);
                    return articuloEncontrado;
                } else{
        
                    System.out.println("Ya has tomado la decicsion sobre este articulo, revisa los otros articulos asignados y en caso de no tener, espere a mas articulos. Muchas Gracias.");
                    System.out.println("Su decision: "+decisionEditor);

                }
            }
        }
    
        if (!articuloEncontrado) {
            System.out.println("Artículo con ID " + idArticulo + " no ha sido encontrado en los artículos asignados para este editor.");

        }
        return articuloEncontrado;


    }
    
    /**
     * Permite al editor tomar una decisión sobre un artículo.
     * @param articulo Artículo sobre el cual se toma la decisión.
     * @return true si se aprueba el artículo, false si se rechaza.
     **/
    public boolean tomarDecision(Articulo articulo) {
        // Simulación de toma de decisión
        System.out.println("Registro de decision final del articulo: "+ articulo.getTitulo());
        System.out.println("Estado actual: " + articulo.getEstado());
        System.out.println("1. Aprobar");
        System.out.println("2. Rechazar");
        System.out.print("Ingrese su decisión (1 o 2): ");

        // Se utiliza un nuevo scanner ya que así no cerramos el scanner anterior
        int decision = sc.nextInt();
        sc.nextLine(); // Consumir la nueva línea pendiente
        
        if (decision==1){
            articulo.setEstado(EstadoArticulo.PUBLICADO);
            System.out.println("Artículo " + articulo.getEstado() + " exitosamente.");         
            return true;
        }else{
            articulo.setEstado(EstadoArticulo.NOPUBLICADO);
            System.out.println("Articulo "+articulo.getEstado());
            return false;
        }
        
        

        
    
    }
    
    /**
     * Muestra los detalles de un artículo asignado al editor. 
     * @param idArticulo ID del artículo del cual se muestran los detalles.
     **/
    public void mostrarDetalleArticulo(int idArticulo) {
        for(Articulo a: ArticuloAsignados){
            if(a.getCodigoArticulo()==idArticulo){
                System.out.println("\nDetalles del artículo asignado:");
                System.out.println("Título: " + a.getTitulo());
                System.out.println("Resumen: " + a.getResumen());
                System.out.println("Contenido: " + a.getContenido());
                System.out.println("Palabras clave: " + a.getPalabrasClave());
            }else{
                System.out.println("No se ha asignado ningún artículo para revisar.");

            }
            break; // Salir del bucle 

        }
    }
    
    /**
     * Devuelve una representación en cadena del editor.
     * @return La representación en cadena del editor.
     **/
    @Override
    public String toString() {
        return "Editor: " +super.toString()+
                ", nombreJournal='" + nombreJournal + '\'';
    }
}

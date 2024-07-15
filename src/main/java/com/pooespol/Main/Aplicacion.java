package com.pooespol.Main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.pooespol.Principales.*;
import com.pooespol.Proceso.Revision;
import com.pooespol.Publicacion.*;
import com.pooespol.Tipos.TipoDeRol;

import java.util.Random;

/**
 * Clase principal de la aplicación de gestión de artículos científicos.
 **/
public class Aplicacion {
    private static ArrayList<Usuario> usuarios = new ArrayList<>();
    private static ArrayList<Articulo> articulos = new ArrayList<>();
    public static Scanner sc = new Scanner(System.in);

    /**
     * Método principal que inicia la aplicación.
     * @param args Argumentos de la línea de comandos.
     **/
    public static void main(String[] args) {
        cargarUsuariosDesdeArchivo("src\\main\\java\\com\\pooespol\\Informacion.txt\\usuarios.txt"); // Cargar datos de usuarios desde archivo
        cargarArticulos("src\\main\\java\\com\\pooespol\\Informacion.txt\\Articulos.txt"); // Cargar datos de artículos desde archivo
        //hola

        System.out.println("\nBienvenido al sistema de gestión de artículos científicos");

        while (true) {
            mostrarMenu();
            int opcion = obtenerOpcion(sc);

            switch (opcion) {
                case 1:
                    someterArticulo(sc);
                    break;
                case 2:
                    iniciarSesion();
                    break;
        
                case 3:
                    System.out.println("Saliendo del sistema.");
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
            }
        }
    }

    /**
     * Muestra el menú principal de la aplicación.
     **/
    public static void mostrarMenu() {
        System.out.println("\nOpciones disponibles:");
        System.out.println("1. Someter artículo");
        System.out.println("2. Iniciar sesión");
        System.out.println("3. Salir");
        System.out.print("Ingrese la opción deseada: ");
    }

     /**
     * Obtiene la opción ingresada por el usuario desde el teclado.
     * @param sc El objeto Scanner para leer la entrada del usuario.
     * @return La opción seleccionada por el usuario.
     **/
    public static int obtenerOpcion(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número entero.");
            }
        }
    }

    /**
     * Solicita los datos para someter un nuevo artículo y lo registra en el sistema.
     * @param scanner El objeto Scanner para leer la entrada del usuario.
     **/
    public static void someterArticulo(Scanner scanner) {
        System.out.println("\nRegistro de datos del autor:");
        System.out.print("Nombre: ");
        String nombreAutor = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellidoAutor = scanner.nextLine();
        String correoAutor="";
        while (true) {
            System.out.print("Correo electrónico: ");
            correoAutor = scanner.nextLine();
            
            // Validar que solo haya un @ en el correo
            if (correoAutor.contains("@") && correoAutor.indexOf("@") == correoAutor.lastIndexOf("@")) {
                break;
            } else {
                System.out.println("Correo electrónico no válido. Inténtelo de nuevo.");
            }
        }
        System.out.print("Institución: ");
        String institucion = scanner.nextLine();
        System.out.print("Campo de investigación: ");
        String campoInvestigacion = scanner.nextLine();

        // Generar un código único para el autor (simulación)
        Random r=new Random();
        int i= r.nextInt(4);
        int codigoID = usuarios.size()+10000*i; // Ejemplo simple de generación de ID único

        Autor autor = new Autor(codigoID, nombreAutor, apellidoAutor, correoAutor, institucion, campoInvestigacion);
        usuarios.add(autor);

        autor.someterArticulo(scanner, articulos,autor);
        escribirArchivo("src\\main\\java\\com\\pooespol\\Informacion.txt\\usuarios.txt", "Autor,"+usuarios.size()+","+autor.getNombre()+","+autor.getApellido()+","+autor.getCorreo()+","+autor.getInstitucion()+","+autor.getCampoInvestigacion());
        escribirArchivo("src\\main\\java\\com\\pooespol\\Informacion.txt\\Investigadores.txt",  "Investigador: "+autor.toString());

        // Asignar revisores al artículo recién sometido
        Articulo articuloReciente = articulos.get(articulos.size() - 1);
        asignarRevisores(articuloReciente);
    }

    /**
     * Asigna revisores y un editor a un artículo recién sometido.
     * @param articulo El artículo al que se asignarán los revisores y el editor.
     **/
    public static void asignarRevisores(Articulo articulo) {
        ArrayList<Revisor> disponibles = obtenerRevisoresDisponibles();
        articulos.add(articulo);
    
        if (disponibles.size() < 2) {
            System.out.println("No hay suficientes revisores disponibles para asignar a este artículo.");
            return;
        }
    
        Revisor revisor1 = disponibles.get(0);
        Revisor revisor2 = disponibles.get(1);
    
        System.out.println("Revisores asignados automáticamente:");
        System.out.println("- " + revisor1.getNombre()+" "+revisor1.getApellido());
        System.out.println("- " + revisor2.getNombre()+" "+revisor2.getApellido());

        escribirArchivo("src\\main\\java\\com\\pooespol\\Informacion.txt\\Revisores.txt", revisor1.toString());
        escribirArchivo("src\\main\\java\\com\\pooespol\\Informacion.txt\\Revisores.txt", revisor2.toString());

    
        // Asignar artículo a los revisores
        revisor1.setArticuloAsignados(articulo);
        revisor2.setArticuloAsignados(articulo);
    
        // Agregar revisores a la lista de revisores del artículo
        articulo.agregarRevisor(revisor1);
        articulo.agregarRevisor(revisor2);
    
        // Asignar un editor al artículo
        ArrayList<Editor> editoresDisponibles = obtenerEditoresDisponibles();
        if (!editoresDisponibles.isEmpty()) {
            Random r=new Random();
            int i= r.nextInt(editoresDisponibles.size()-1);
            Editor editorAsignado = editoresDisponibles.get(i); // Simplemente asignamos el primer editor disponible
    
            editorAsignado.setArticuloAsignados(articulos);
            articulo.setEditor(editorAsignado);
            System.out.println("Editor asignado automáticamente:");
            System.out.println("- " + editorAsignado.getNombre()+" "+editorAsignado.getApellido());
            escribirArchivo("src\\main\\java\\com\\pooespol\\Informacion.txt\\Editores.txt",editorAsignado.toString());
    
            // Agregar editor al artículo
            articulo.setEditor(editorAsignado);
        } else {
            System.out.println("No hay editores disponibles para asignar a este artículo.");
        }
        escribirArchivo("src\\main\\java\\com\\pooespol\\Informacion.txt\\Articulos.txt", articulo.toString()+ ", Revisor1 :"+articulo.getRevisores().get(0).getNombre()+" "+articulo.getRevisores().get(0).getApellido()+", Revisor2: "+articulo.getRevisores().get(1).getNombre()+" "+articulo.getRevisores().get(1).getApellido()+","+" Editor :"+articulo.getEditor().getNombre()+" "+articulo.getEditor().getApellido());

        enviarCorreo(revisor1.getCorreo(), "Nuevo artículo asignado para revisión", "Estimado revisor,\n\nSe les ha asignado el artículo \"" 
        + articulo.getTitulo() + "\" para revisión. Por favor, revisen su cuenta para más detalles.\n\nSaludos,\nSistema de Gestión de Artículos Científicos");
        enviarCorreo(revisor2.getCorreo(), "Nuevo artículo asignado para revisión", "Estimado revisor,\n\nSe les ha asignado el artículo \"" 
        + articulo.getTitulo() + "\" para revisión. Por favor, revisen su cuenta para más detalles.\n\nSaludos,\nSistema de Gestión de Artículos Científicos");
       enviarCorreo(articulo.getEditor().getCorreo(), "Nuevo artículo asignado para revisión", "Estimado editor,\n\nSe le ha asignado el artículo \"" 
        + articulo.getTitulo() + "\" para revisión. Por favor, revise su cuenta para más detalles.\n\nSaludos,\nSistema de Gestión de Artículos Científicos");

    }
    
    /**
     * Obtiene una lista de revisores disponibles.
     * @return Una lista de revisores disponibles.
     */
    public static ArrayList<Revisor>  obtenerRevisoresDisponibles() {
        ArrayList<Revisor>  disponibles = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario instanceof Revisor) {
                Revisor revisor = (Revisor) usuario;
                if (revisor.getArticuloAsignados() == null) {
                    disponibles.add(revisor);
                }
            }
        }
        return disponibles;
    }

     /**
     * Obtiene una lista de editores disponibles.
     * @return Una lista de editores disponibles.
     **/
    public static ArrayList<Editor> obtenerEditoresDisponibles() {
        ArrayList<Editor> disponibles = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario instanceof Editor) {
                Editor editor = (Editor) usuario;
                if (editor.getArticulosAsignado().size()<5) {
                    disponibles.add(editor);
                }
            }
        }
        return disponibles;
    }

    /**
     * Inicia sesión para un usuario y permite ver el estado de los artículos.
     **/
    public static void iniciarSesion() {
        System.out.print("Usuario: ");
        String usuario = sc.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = sc.nextLine();

        Usuario usuarioEncontrado = null;
        TipoDeRol tipo = null;
        for (Usuario u : usuarios) {
            if (u instanceof Editor) {
                Editor editor = (Editor) u;
                if (editor.IniciarSesion(usuario, contrasena)) {
                    tipo = editor.getTipoDeRol();
                    usuarioEncontrado = editor;
                    break;
                }
            } else if (u instanceof Revisor) {
                Revisor revisor = (Revisor) u;
                if (revisor.IniciarSesion(usuario, contrasena)) {
                    tipo = revisor.getTipoDeRol();
                    usuarioEncontrado = revisor;
                    break;
                }
            }
        }

        if (usuarioEncontrado != null) {
            System.out.println("\nInicio de sesión exitoso como " + tipo + ": " + usuarioEncontrado.getNombre() + " " + usuarioEncontrado.getApellido());
            if (usuarioEncontrado instanceof Editor) {
                Editor editor = (Editor) usuarioEncontrado;
                System.out.println("Ingrese el id del articulo");
                int idArticulo=sc.nextInt();
                sc.nextLine();
                procesarDecisionesTomadas("src\\main\\java\\com\\pooespol\\Informacion.txt\\ComentariosDecisiones.txt", idArticulo);          
                procesarDecisionesTomadas("src\\main\\java\\com\\pooespol\\Informacion.txt\\Revision.txt", idArticulo);          
                boolean articuloEncontrado=editor.tareaAsignada(idArticulo);
                if(editor.getDecisionTomada()==false&& articuloEncontrado ){
                    editor.setDecisionTomada(true);                
                    verEstadoArticulo(idArticulo);
                                    
                }
            } else if (usuarioEncontrado instanceof Revisor) {
                Revisor revisor = (Revisor) usuarioEncontrado;
                revisor.tareaAsignada();

                if(revisor.getArticuloAsignados()!=null&& revisor.getComentarios()==null){
                    System.out.println("Agregue comentarios sobre el artículo:");
                    String comentario = sc.nextLine();
                    revisor.agregarComentarios(comentario);
                    revisor.guardarComentarios(revisor);
                }
            }
            System.out.println("\nPresione Enter para volver al menú principal.");
            sc.nextLine();
        } else {
            System.out.println("Usuario o contraseña incorrectos.");
        }
    }
    
    /**
     * Ver Estado Articulo permite buscar el articulo segun el id que haya ingresado el usuario.
     * @param idArticulo Es el id del articulo que querra ver la informacion.
     **/
    public static void verEstadoArticulo(int idArticulo) {
        

        boolean encontrado = false;
        for (Articulo articulo : articulos) {
            if (articulo.getCodigoArticulo() == idArticulo) {
                // Obtener revisores y editor asociados al artículo
                ArrayList<Revisor> revisores = articulo.getRevisores();
                Editor editor = articulo.getEditor();
                Revision r= new Revision(articulo, revisores.get(0),revisores.get(1),editor);
                enviarCorreo(articulo.getAutor().getCorreo(), "La revision ha concluido, aqui su informe con respecto a la misma.", r.imprimirRevision());
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Artículo no encontrado.");
            System.out.println("\nPresione Enter para volver al menú principal.");
        }
        
    }

    /**
     * Enviar Correo permite enviar un correo de manera real al destinatario que necesite.
     * @param destinatario es la direccion de correo de la persona que desea enviar el correo.
     * @param asunto es el asunto que se presentara en el correo.
     * @param cuerpo es el cuerpo que arma el mensaje que se mostrara en el correo.
     **/
    public static void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
    
        // Autenticación
        String username = "pooproyecto7@gmail.com";
        String password = "vqtz eryx ukur tfqs";

        // Crear la sesión
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }
        });
    
        try {
            // Crear el mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(cuerpo);
    
            // Enviar el mensaje
            Transport.send(message);
    
            System.out.println("Correo enviado exitosamente a " + destinatario);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Escribir Archivo permite guardar informacion recopilada en el .txt deseado.
     * @param nombreArchivo es la ruta del archivo .txt .
     * @param contenido es lo que se escribira en el archivo .txt .
     **/
    public static void escribirArchivo(String nombreArchivo, String contenido) {
        try {
            // Leer el contenido existente del archivo
            BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo));
            StringBuilder contenidoExistente = new StringBuilder();
            String primeraLinea = reader.readLine();
            boolean primeraLineaVacia = (primeraLinea == null || primeraLinea.isEmpty());

            // Si la primera línea no está vacía, agregarla al contenido existente
            if (primeraLinea != null) {
                contenidoExistente.append(primeraLinea).append(System.lineSeparator());
            }

            String linea;
            while ((linea = reader.readLine()) != null) {
                contenidoExistente.append(linea).append(System.lineSeparator());
            }
            reader.close();

            // Abrir el archivo para escribir
            BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo));
            if (primeraLineaVacia) {
                // Si la primera línea está vacía, escribir el nuevo contenido en la primera línea
                writer.write(contenido);
            } else {
                // Si la primera línea no está vacía, escribir el contenido existente
                writer.write(contenidoExistente.toString());
                // Y luego escribir el nuevo contenido en la segunda línea
                writer.write(contenido);
            }
            writer.newLine(); // Nueva línea después del nuevo contenido
            writer.close();

        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo " + nombreArchivo);
            e.printStackTrace();
        }
    }

    /**
     * Carga la lista de usuarios desde un archivo.
     * @param nombreArchivo La ruta del archivo de usuarios.
     */
    public static void cargarUsuariosDesdeArchivo(String nombreArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 7) { // Ajustar el número según la cantidad de campos esperados
                    String tipoUsuario = datos[0].trim();
                    int codigoID = Integer.parseInt(datos[1].trim());
                    String nombre = datos[2].trim();
                    String apellido = datos[3].trim();
                    String correo = datos[4].trim();
    
                    switch (tipoUsuario) {
                        case "Autor":
                            if (datos.length >= 6) {
                                String institucion = datos[5].trim();
                                String campoInvestigacion = datos[6].trim();
                                Autor autor = new Autor(codigoID, nombre, apellido, correo, institucion, campoInvestigacion);
                                usuarios.add(autor);
                            } else {
                                System.out.println("Error en el formato de línea para Autor: " + linea);
                            }
                            break;
                        case "Revisor":
                            if (datos.length >= 8) {
                                String acceso = datos[5].trim();
                                String contrasena = datos[6].trim();
                                String especialidad = datos[7].trim();
                                Revisor revisor = new Revisor(nombre, apellido, correo, acceso, contrasena, especialidad);
                                usuarios.add(revisor);
                            } else {
                                System.out.println("Error en el formato de línea para Revisor: " + linea);
                            }
                            break;
                        case "Editor":
                            if (datos.length >= 8) {
                                String acceso = datos[5].trim();
                                String contrasena = datos[6].trim();
                                String nombreJournal = datos[7].trim();
                                Editor editor = new Editor(nombre, apellido, correo, acceso, contrasena, nombreJournal);
                                usuarios.add(editor);
                            } else {
                                System.out.println("Error en el formato de línea para Editor: " + linea);
                            }
                            break;
                        default:
                            System.out.println("Tipo de usuario desconocido en el archivo: " + tipoUsuario);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error de formato numérico en el archivo: " + e.getMessage());
        }
    }

     /**
     * Carga la lista de artículos desde un archivo.
     * @param nombreArchivo La ruta del archivo de artículos.
     */
    public static void cargarArticulos(String nombreArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                // Procesar la línea para extraer la información necesaria
                String[] partes = linea.split(", ");
                String titulo = partes[0].split("=")[1].replace("'", "").trim();
                String nombreCompletoAutor = partes[1].split(":")[1].trim();
                String[] nombreAutorPartes = nombreCompletoAutor.split(" ");
                String nombreAutor = nombreAutorPartes[0].trim();
                String apellidoAutor = nombreAutorPartes[1].trim();
                int codigoArticulo = Integer.parseInt(partes[2].split("=")[1].trim());
                String resumen = partes[3].split("=")[1].replace("'", "").trim();
                String contenido = partes[4].split("=")[1].replace("'", "").trim();
                String palabrasClave = partes[5].split("=")[1].replace("'", "").trim();
                String nombreCompletoRevisor1 = partes[7].split(":")[1].trim();
                String[] nombreRevisor1Partes = nombreCompletoRevisor1.split(" ");
                String nombreRevisor1 = nombreRevisor1Partes[0].trim();
                String apellidoRevisor1 = nombreRevisor1Partes[1].trim();
                String nombreCompletoRevisor2 = partes[8].split(":")[1].trim();
                String[] nombreRevisor2Partes = nombreCompletoRevisor2.split(" ");
                String nombreRevisor2 = nombreRevisor2Partes[0].trim();
                String apellidoRevisor2 = nombreRevisor2Partes[1].trim();
                String nombreCompletoEditor = partes[9].split(":")[1].trim();
                String[] nombreEditorPartes = nombreCompletoEditor.split(" ");
                String nombreEditor = nombreEditorPartes[0].trim();
                String apellidoEditor = nombreEditorPartes[1].trim();
                
                
    
                // Buscar el autor, revisores y editor en la lista de usuarios
                Autor autor = (Autor) obtenerUsuarioPorNombre(nombreAutor, apellidoAutor);
                Revisor revisor1 = (Revisor) obtenerUsuarioPorNombre(nombreRevisor1, apellidoRevisor1);
                Revisor revisor2 = (Revisor) obtenerUsuarioPorNombre(nombreRevisor2, apellidoRevisor2);
                Editor editor = (Editor) obtenerUsuarioPorNombre(nombreEditor, apellidoEditor);
    
                // Crear el artículo y asignar las relaciones
                Articulo articulo = new Articulo(autor, codigoArticulo, titulo, resumen, contenido, palabrasClave);
                articulo.agregarRevisor(revisor1);
                articulo.agregarRevisor(revisor2);
                articulo.setEditor(editor);
    
                articulos.add(articulo);
                revisor1.setArticuloAsignados(articulo);
                revisor2.setArticuloAsignados(articulo);
                ArrayList<Articulo> articulos=editor.getArticulosAsignado();
                articulos.add(articulo);
                editor.setArticuloAsignados(articulos);
                
    
                System.out.println("Artículo cargado: " + titulo);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error de formato numérico en el archivo: " + e.getMessage());
        }
    }
    
    /**
     * Obtener Usuario permite obtener al usuario nomas por el nombre y apellido.
     * @param nombre es el nombre recibido.
     * @param apellido es el apellido recibido.
     */
    public static Usuario obtenerUsuarioPorNombre(String nombre, String apellido) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equalsIgnoreCase(nombre) && usuario.getApellido().equalsIgnoreCase(apellido)) {
                return usuario;
            }
        }
        return null;
    }
    
    /**
     * Obtener Usuario permite obtener al usuario nomas por el nombre y apellido.
     * @param nombreArchivo es la ruta del archivo .txt .
     * @param idArticulo Es el id del articulo que querra ver la informacion.
     */
    public static void procesarDecisionesTomadas(String nombreArchivo, int idArticulo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            br.readLine();
            String linea;
            
            while ((linea = br.readLine()) != null) {
                if (linea.contains("Codigo: " + idArticulo)) {
                    String[] partes = linea.split(", ");
                    
                    // Buscar el artículo por ID y actualizar los revisores
                    if(partes.length == 6 ){
                        for (Articulo a : articulos) {
                            if (a.getCodigoArticulo() == idArticulo) {
                                // Obtener nombre del revisor, decisión y comentarios
                                String nombreRevisor = partes[0].split(":")[1].split(" ")[0];
                                boolean decisionRevisor = Boolean.parseBoolean(partes[3].split(":")[1].trim());
                                boolean decisionTomada = Boolean.parseBoolean(partes[5].split(":")[1].trim());
                                String comentarios = partes[4].split(":")[1].trim();
                                
                                // Actualizar el revisor correspondiente
                                for (Revisor revisor : a.getRevisores()) {
                                    if (revisor.getNombre().equals(nombreRevisor)) {
                                        revisor.setdecision(decisionRevisor);
                                        revisor.agregarComentarios(comentarios);
                                        revisor.setDecisionTomada(decisionTomada);
                                        break;
                                    } 
                                }
                            }
                        }
                    }else if( partes.length==8){
                        for (Articulo a : articulos) {
                            if (a.getCodigoArticulo() == idArticulo) {
                                // Obtener nombre del revisor, decisión y comentarios
                                boolean decisionEditor = Boolean.parseBoolean(partes[6].split(":")[1].trim());
                                System.out.println(decisionEditor);
                                boolean decisionTomada = Boolean.parseBoolean(partes[7].split(":")[1].trim());
                                Editor editor=a.getEditor();
                                editor.setdecision(decisionEditor);
                                editor.setDecisionTomada(decisionTomada);
                                    
                            } 
                                
                        }

                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error de formato numérico en el archivo: " + e.getMessage());
        }
    }

}

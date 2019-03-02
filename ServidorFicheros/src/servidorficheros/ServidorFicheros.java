package servidorficheros;

import java.io.*;
import java.net.*;

public class ServidorFicheros {

    public static void main(String args[]) {

        // ¿¿Y SI AL SERVER NO LE DAN NINGUN ARGUMENTO?? MOSTRAMOS MENSAJE
        if (args.length != 0) {
            System.err.println("Debo recibir un argumento (nombre de fichero valido).");
        } else {

            try {
                    /**
                     * DETECCION OS
                     * La variable SystemFlag se pone a 1 si es windows, 2 si es unix/linux, 0 en cualquier otro caso.
                     */
                    int SystemFlag;
                    String sistema = System.getProperty("os.name");

                    if (sistema.contains("indow")) {
                        System.out.println("Detectado un sistema operativo windows el cual está soportado por este servidor de ficheros\n");
                        SystemFlag = 1;
                    } else {
                        if (sistema.contains("nix") | (sistema.contains("nux"))) {
                            System.out.print("Se detecta SO unix/linux. Sistema operativo " + sistema + " no está soportado");
                            SystemFlag = 2;
                        } else {
                            System.out.print("No se reconoce este SO. Sistema operativo " + sistema + " no está soportado");
                            SystemFlag = 0;
                        }
                    }
                
                // Creamos socket 
                DatagramSocket sSocket = new DatagramSocket(2000);

                // Creamos byte para mensajes
                byte[] nombre = new byte[100];
                DatagramPacket mensaje = new DatagramPacket(nombre, nombre.length);
                System.out.println("Esperando nombre de fichero");

                while (true) {
                    // ACEPTAMOS CONEXION
                    sSocket.receive(mensaje);
                    String datos = new String(mensaje.getData(), 0, mensaje.getLength());
                    System.out.println("\tNombre recibido: " + datos);
                
                    //COGEMOS EL FICHERO Y LO BUSCAMOS
                    
                    boolean EnviarFichero = false;

                    File f = new File("D:\\pruebas");
                    File[] matchingFiles = f.listFiles(new FilenameFilter() {

                        public boolean accept(File dir, String name) {
                            if (name.startsWith(datos) == true && name.endsWith(datos) == true) {
                                System.out.println("Se detecta el fichero indicado en la ruta D:\\pruebas");       
                            }                            
                            
                        return name.startsWith("test.txt");
                        }
                    }
                    );
                                    
                //-------------------------------------------------------------------
                //     PENDIENTE CODIGO PARA HACER EL ENVIO DEL FICHERO AL CLIENTE
                // ¿Hay que hacer que el cliente sea a su vez servidor con socket TCP?
                // Desarrollar codigo pendiente
                //-----------------------------------------------------------
                if (EnviarFichero == true){
                    //Enviamos fichero al cliente
                } else {
                    //Indicamos al cliente que el fichero no se va a enviar
                }
            }

            } catch (SocketException e) {
                System.err.println("Socket: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("E/S: " + e.getMessage());
            }
        }

    }

}

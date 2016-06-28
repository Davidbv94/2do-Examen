/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketException;
/**
 *
 * @author David
 */
public class ThreadEnviaS implements Runnable {
    private final PrincipalChatS main; 
    private ObjectOutputStream salida;
    private String mensaje;
    private Socket conexion; 
   
    public ThreadEnviaS(Socket conexion, final PrincipalChatS main){
        this.conexion = conexion;
        this.main = main;
        
    
        main.campoTexto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mensaje = event.getActionCommand();
                enviarDatos(mensaje); 
                main.campoTexto.setText("");
            }
        } 
        );
    } 
    
   
   private void enviarDatos(String mensaje){
      try {
         salida.writeObject("Servidor>>> " + mensaje);
         salida.flush(); 
         main.mostrarMensaje("Servidor>>> " + mensaje);
      } 
      catch (IOException ioException){ 
         main.mostrarMensaje("Error escribiendo Mensaje");
      }
      
   }

  
    public void mostrarMensaje(String mensaje) {
        main.areaTexto.append(mensaje);
    } 
   
    public void run() {
         try {
            salida = new ObjectOutputStream(conexion.getOutputStream());
            salida.flush(); 
        } catch (SocketException ex) {
        } catch (IOException ioException) {
          ioException.printStackTrace();
        } catch (NullPointerException ex) {
        }
    }   
   
} 

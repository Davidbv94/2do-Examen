/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
/**
 *
 * @author David
 */
public class PrincipalChatS extends JFrame{
    public JTextField campoTexto; 
    public JTextArea areaTexto; 
    private static ServerSocket servidor;
    private static Socket conexion;
    private static String ip = "127.0.0.1"; 
    
    public static PrincipalChatS main; 
    
    public PrincipalChatS(){
        super("Servidor"); 
        
        campoTexto = new JTextField(); 
        campoTexto.setEditable(false); 
        add(campoTexto, BorderLayout.NORTH); 
        
        areaTexto = new JTextArea(); 
        areaTexto.setEditable(false);
        add(new JScrollPane(areaTexto), BorderLayout.CENTER);
        areaTexto.setBackground(Color.red); 
        areaTexto.setForeground(Color.BLACK); 
        campoTexto.setForeground(Color.BLACK); 
        
        
        
        JMenu menuArchivo = new JMenu("Archivo"); 
        JMenuItem salir = new JMenuItem("Salir");
        menuArchivo.add(salir);
        
        JMenuBar barra = new JMenuBar();
        setJMenuBar(barra); 
        barra.add(menuArchivo); 
        
       
        salir.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) {
                    System.exit(0); 
                }
        });
        
        setSize(400, 420); 
        setVisible(true); 
    }
    

    public void mostrarMensaje(String mensaje) {
        areaTexto.append(mensaje + "\n");
    } 
    public void habilitarTexto(boolean editable) {
        campoTexto.setEditable(editable);
    }
 
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PrincipalChatS main = new PrincipalChatS(); 
        main.setLocationRelativeTo(null);  
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ExecutorService executor = Executors.newCachedThreadPool(); 
 
        try {
           
            servidor = new ServerSocket(11111, 100); 
            main.mostrarMensaje("Esperando Cliente ...");

            
            while (true){
                try {
                    conexion = servidor.accept();   

                    
                    main.mostrarMensaje("Conectado a : " + conexion.getInetAddress().getHostName());

                    main.habilitarTexto(true); 

                   
                    executor.execute(new ThreadRecibeS(conexion, main)); 
                    executor.execute(new ThreadEnviaS(conexion, main));
                } catch (IOException ex) {
                    Logger.getLogger(PrincipalChatS.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(PrincipalChatS.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
        }
        executor.shutdown();
    }
}


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

//DESARROLLADO POR MARCO ANTONIO CONRIQUEZ CABRERA

public class Laberinto extends Thread {
    
	public VentanaPrincipal VentanaP ;
        public char[][] laberinto={ 
	{'1','1','1','1','1','1','1','1','1','1'},
        {'1','0','1','0','0','0','0','0','0','1'},
        {'1','0','0','0','1','0','0','1','0','1'},
        {'1','1','1','1','0','0','1','1','1','1'},
        {'1','0','0','0','0','1','0','0','0','1'},
        {'1','0','1','1','1','1','0','1','0','1'},
        {'1','0','0','0','0','1','0','1','0','1'},
        {'1','1','0','1','1','1','0','1','0','1'},
        {'1','0','0','0','0','0','0','1','0','1'},
        {'1','1','1','1','1','1','1','1','1','1'},
		};
        
        Laberinto() 
	{
         JLabel Instrucciones1,Instrucciones2,Titulo, Desarrollador;
	 VentanaP = new VentanaPrincipal(this);
         VentanaP .actualizarLab(laberinto);
         //DISEÑO,POSICION Y TAMAÑO DEL JFRAME
         VentanaP .setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Para cerrar la aplicacion directamente
         Dimension ss = Toolkit.getDefaultToolkit ().getScreenSize ();
         Dimension frameSize = new Dimension ( 900, 690 );
         VentanaP .setLayout(null);
         VentanaP .setVisible(true);
         VentanaP . setResizable(false);
         VentanaP .setBounds ( ss.width / 2 - frameSize.width / 2, ss.height / 2 - frameSize.height / 2,
         frameSize.width, frameSize.height );
         //INSTRUCCIONES CON LABEL
         Titulo = new JLabel("LABERINTO RECURSIVO");
         Titulo.setBounds(525,10,400,60);
         Titulo.setForeground(Color.darkGray);
         Titulo.setFont(new Font("Serif", Font.BOLD, 20));
         VentanaP .add(Titulo);
         Instrucciones1 = new JLabel("1-Por favor seleccione entrada y salida y ubiquelos en el laberinto ");
         Instrucciones1.setBounds(100,560,500,40);
         VentanaP .add(Instrucciones1);
         Instrucciones2 = new JLabel("2- Si quiere editar, debe ir selecionando el boton editar y click haga click para crear o quitar muros");
         Instrucciones2.setBounds(100,580,600,40);
         VentanaP .add(Instrucciones2);
	}	
		
        public void reiniciar() {
                for(int i = 0; i < 10; i++){
                 for(int j = 0; j < 10; j++){
                    if(laberinto[i][j] != '1'){ // Los for recorren las filas y columnas  
                       laberinto[i][j] = '0';  
                     }
                   }   
                  }
	}
              
        public static void main(String[] args) 
	{
         Laberinto m = new Laberinto();
        }
	
	// ----------------- IMPLEMENTACIÓN DEL ALGORITMO ----------------- 
    public void resuelve(int x, int y)
    {
    	   if(pasos(x,y))     //Permite introducir las coordenadas (x,y) 
        {
          laberinto[x][y] = 'S';  //Introduce en las coordenadas x,y la entrada
        }
        else
        {
            JOptionPane.showMessageDialog(null, "No hay solucion en el laberinto"); 
        }
    }
    
    private boolean pasos(int x, int y) 
    {
        try{
            Thread.sleep(100);  //Para que el programa corra lento
        } catch (Exception v){
            
        }
    	 if (laberinto[x][y]=='S'){ // si hemos llegado a X quiere decir que hemos encontrado la solución
             JOptionPane.showMessageDialog(null, "Se a encontrado la salida del laberinto");
    		 return true; // Termina el algoritmo
    	 }
    	 
    	 if (laberinto[x][y]=='1'||laberinto[x][y]=='*') { // si llegamos a una pared o al mismo punto,
    		 return false; // entonces el laberinto no puede resolverse y termina.
    	 }
    	 
    	 // si no se cumple ninguna de estas dos situaciones, quiere decir que nos encontramos en un
    	 // caso intermedio, por lo tanto, que empezamos a recorrer o todavía no hemos llegado a nada
    	 laberinto[x][y]='*'; // marcamos la posición como visitada (si es la primera, en las coordenadas x e y 
    	 
    	 boolean result; // se coloca S de START)
    	 VentanaP.pintarcamino(x,y,laberinto);
    	 result=pasos(x-1, y); // intentamos ir hacia ARRIBA. 
    	 VentanaP.pintarcamino(x,y,laberinto);
    	 if (result) 
         return true; // si el resultado es el final, entonces el algoritmo termina
         VentanaP.pintarcamino(x,y,laberinto);
    	 result=pasos(x, y+1); // intentamos ir hacia la DERECHA. 
    	 VentanaP.pintarcamino(x,y,laberinto);
    	 if (result)
         return true; // si el resultado es el final, entonces el algoritmo termina
    	 VentanaP.pintarcamino(x,y,laberinto);
    	 result=pasos(x, y-1); // intentamos ir hacia la IZQUIERDA. 
    	 VentanaP.pintarcamino(x,y,laberinto);
    	 if (result) 
         return true; // si el resultado es el final, entonces el algoritmo termina
    	 VentanaP.pintarcamino(x,y,laberinto);
    	 result=pasos(x+1, y); // intentamos ir hacia ABAJO. 
    	 VentanaP.pintarcamino(x,y,laberinto);
    	 if (result)
    	 return true; // si el resultado es el final, entonces el algoritmo termina
    	 VentanaP.pintarcamino(x,y,laberinto);
    		    // si no hemos encontrado la solución en estos cuatros movimientos, volvemos atrás, aunque hay que
    	        // se ha conseguido el éxito, entonces el algoritmo termina y devuelve false.
    	 laberinto[x][y]='+'; // en el caso de no ser el resultado, se marca con +. Ya fue pisado
         VentanaP.pintarcamino(x,y,laberinto);
    		    return false; // vuelta atrás si la solución no se encuentra aquí
    }
	
    public synchronized void continueC(){
        notifyAll();
    }
    public synchronized void run(){  //Por la Thread, ejecutar principalmente
       while(true){
           resuelve(VentanaP.x,VentanaP.y);
           try{
               wait();
           } catch (Exception vv){
               
           }
       } 
    }

}

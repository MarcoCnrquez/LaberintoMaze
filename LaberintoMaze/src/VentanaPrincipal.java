
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Color;

public class VentanaPrincipal extends JFrame implements ActionListener{
     
	int x = 0;
        int y = 0;
        boolean entradaav = false, salidaav = false, editarav = false, reiniciarav = false;
        Laberinto laber;
        JButton laberinto[][];
        JButton entrada;
        JButton salida;
        JButton iniciar;
        JButton reiniciar;
        JButton editar;
	
	public VentanaPrincipal(Laberinto laber) 
	{

            this.laber = laber;
            laberinto = new JButton[laber.laberinto.length][laber.laberinto.length];
            //CREE LOS BOTONES EDITAR,ENTRADA Y REINICIAR
            editar = new JButton("Editar");
            editar.setBounds(525,100,90,30);
            editar.addActionListener(this);
            editar.setBackground(Color.gray);
            this.add(editar);
            
            entrada = new JButton("Entrada");
            entrada.setBounds(50,530,90,30);
            entrada.addActionListener(this);
            entrada.setBackground(Color.green);
            this.add(entrada);
            
            salida = new JButton("Salida");
            salida.setBounds(150,530,90,30);
            salida.addActionListener(this);
            salida.setBackground(Color.red);
            this.add(salida);
            
            reiniciar = new JButton("Reiniciar");
            reiniciar.setBounds(400,530,90,30);
            reiniciar.addActionListener(this);
            reiniciar.setBackground(Color.ORANGE);
            this.add(reiniciar);  
            
            iniciar = new JButton("Iniciar");
            iniciar.setBounds(300,530,90,30);
            iniciar.addActionListener(this);
            this.add(iniciar);
            
	    for(int i = 0; i < laber.laberinto[0].length; i++)	{
                for(int j = laber.laberinto[0].length - 1; j>= 0; j--){
                    laberinto[j][i]= new JButton();
                    laberinto[j][i].setBounds((i * 50)+5,(j*50)+5,50,50);
                    laberinto[j][i].setBackground(Color.white);
                    laberinto[j][i].addActionListener(this);
                    System.out.println(j);
                    this.add(laberinto[j][i]);
                }
            }
            repaint();

	}
        public void actualizarLab(char[][] m){   //Pintar las paredes e inicios del lab
            for(int i = 0; i < m[0].length; i++){
                for(int j = 0; j<m.length;j++){
                  if(m[i][j] == '1')
                  {
                      laberinto[i][j].setText("1");
                      laberinto[i][j].setForeground(Color.white);
                      laberinto[i][j].setBackground(Color.darkGray);
                  }
                  else if (m[i][j] == '0')
                  {
                        laberinto[i][j].setText("0");
                        laberinto[i][j].setBackground(Color.white);
                  }  
                  else if (m[i][j] == 'S')
                  {
                        laberinto[i][j].setBackground(Color.green);
                       
                  }
                  else if (m[i][j] == 'X')   
                  {
                        laberinto[i][j].setBackground(Color.yellow);
             
                  }
                  repaint();
                }
            }
        }
        
       public void pintarcamino(int i, int j, char[][] m){
           if(!(i == x && j == y)){
             if(m[i][j] == '*')    
                 laberinto[i][j].setBackground(Color.yellow); //Marca de amarillo la posicion visitada
             else if (m[i][j] == '+')
             laberinto[i][j].setBackground(Color.blue); //Azul cuando el camino fue recorrido pero regreso
           }
           repaint();
       } 

	@Override
	public void actionPerformed(ActionEvent e) 
	{
            if(e.getSource() == reiniciar)
            {
             laber.reiniciar();
             actualizarLab(laber.laberinto);
            }
		if (e.getSource() == iniciar) 
		{
	            if(!laber.isAlive())  //Devuelve true o false depende de la ejecucion
                        laber.start();
                    else 
                        laber.continueC();
		}
               
               if(e.getSource() == entrada)
                    entradaav = true;
               if(e.getSource()== editar)
                    editarav = true;
               if(e.getSource()== salida)
                    salidaav = true;
               if(e.getSource()== reiniciar)
                    reiniciarav = true;
               
               for(int i = 1; i<laberinto.length - 1; i++){
                  for(int j = 1; j< laberinto.length - 1; j++){
                     if(e.getSource() == laberinto[i][j]){
                        if(salidaav){
                            laber.laberinto[i][j] = 'S';
                            laberinto[i][j].setBackground(Color.red);
                            laberinto[i][j].setText("S");  //S de salida
                            salidaav = false;
                        } else if(entradaav){
                            laber.laberinto[i][j] = 'E';
                             laberinto[i][j].setBackground(Color.green);
                             laberinto[i][j].setText("E"); //E de entrada
                             x = i;
                             y = j;
                             entradaav = false;
                        }
                        if(editarav){
                           if(laber.laberinto[i][j] != '1'){
                               laberinto[i][j].setBackground(Color.darkGray);
                               laberinto[i][j].setText("1");
                               laberinto[i][j].setForeground(Color.white);
                               laber.laberinto[i][j] = '1';
                           }else {
                               laberinto[i][j].setBackground(Color.white);
                               laberinto[i][j].setText("0");
                               laberinto[i][j].setForeground(Color.black);
                               laber.laberinto[i][j] = '0';
                           } 
                           editarav = false;
                        }
                     }  
                  } 
               }
		
	}
}


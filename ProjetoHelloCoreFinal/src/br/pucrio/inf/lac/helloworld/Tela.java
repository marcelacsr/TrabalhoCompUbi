package br.pucrio.inf.lac.helloworld;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tela {
	// JFrame -------------------
	  static JLabel evalor1 = new JLabel("teste");
	  static JLabel evalor2 = new JLabel("segu");
	  static Component graf4Label = new JLabel();
	  static JLabel iconLabel = new JLabel();
	  static ImageIcon icon;
	// ---------------------------
	  
/*	  public static void main(String[] args ) {
		new  Tela("23","17");
	  }
	*/  
	
public Tela(int valor1, int valor2 ) {
		
	// JFrame ----------------------

	JFrame f = new JFrame("Resultados");
	JPanel mainPanel      =  new JPanel();
	JPanel textPanel      =  new JPanel();
	JPanel textPanel2     =  new JPanel();
	JPanel graficoPainel  =  new JPanel();
	 
	//Image display label
	 iconLabel.setIcon(null);
	 
	    textPanel.add(evalor1);
	    textPanel2.add(evalor2);
	    
	     mainPanel.add(iconLabel);
		 mainPanel.add(textPanel);
		 mainPanel.add(textPanel2);
		 mainPanel.add(graficoPainel);
		 
			textPanel.setBounds(0,0,150,20);
			textPanel2.setBounds(10,10,50,20);
			graficoPainel.setBounds(10,10,430,280);
			//graficoPainel.setBackground(Color.white);

			 f.getContentPane().add(mainPanel, BorderLayout.CENTER);
			 f.setSize(new Dimension(500,450)); // 390 
			 f.setVisible(true);		
			 
		//	 	Tela theClient = new Tela("meu texto aqui"); // instancia o JFrame
	    //-------------------------------
	
	 evalor1.setText("Registro Queda ="+ valor1);
	 evalor2.setText("Registro Não Queda ="+valor2); 
	 int numero1 = valor1;
	 int numero2 = valor2;
	 
	 
	 /////////---------------------------------------------- ///
	// if (numero1 > numero2) { // 15< queda

	if (numero1 >15) { // 15< queda
		 icon = new ImageIcon("src/imagens/queda.jpg");
	 }else if(numero1 == numero2){ // tropeço 
		 icon = new ImageIcon("src/imagens/tropeco.jpg");	 
	 } else {
		 icon = new ImageIcon("src/imagens/nqueda.jpg");
	 }
	 
	 
	 
	

	 iconLabel.setIcon(icon);	
	}


	
	
}

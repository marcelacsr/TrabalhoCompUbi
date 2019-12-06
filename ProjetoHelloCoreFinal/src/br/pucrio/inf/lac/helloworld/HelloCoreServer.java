package br.pucrio.inf.lac.helloworld;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import br.pucrio.inf.lac.helloworld.*;

import com.opencsv.CSVWriter;

import arquivos.lerArquivo;

//import com.opencsv.CSVUtilExample;
//import com.opencsv.CSVUtils;
//import com.opencsv.CSVWriter;

import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.serialization.Serialization;
import lac.cnet.sddl.objects.ApplicationObject;
import lac.cnet.sddl.objects.Message;
import lac.cnet.sddl.objects.PrivateMessage;
import lac.cnet.sddl.udi.core.SddlLayer;
import lac.cnet.sddl.udi.core.UniversalDDSLayerFactory;
import lac.cnet.sddl.udi.core.listener.UDIDataReaderListener;

public class HelloCoreServer implements UDIDataReaderListener<ApplicationObject> {
  SddlLayer  core;
  int        counter;
  static Main objTesta = new Main();
  static int Queda=0;
  static int Nqueda=0;  

  static WekaTeste2 objs	= new WekaTeste2();
  static String resultado="";

  
  public static void main(String[] args) {
    Logger.getLogger("").setLevel(Level.OFF);
     
    new HelloCoreServer();
  }

  public HelloCoreServer() {
    core = UniversalDDSLayerFactory.getInstance();
    core.createParticipant(UniversalDDSLayerFactory.CNET_DOMAIN);

    core.createPublisher();
    core.createSubscriber();

    Object receiveMessageTopic = core.createTopic(Message.class, Message.class.getSimpleName());
    core.createDataReader(this, receiveMessageTopic);

    Object toMobileNodeTopic = core.createTopic(PrivateMessage.class, PrivateMessage.class.getSimpleName());
    core.createDataWriter(toMobileNodeTopic);

    counter = 0;
    
    System.out.println("=== Server Started (Listening) ===");
	// apaga arquivo treinoTest.arff
//	 File arquivo = new File("treinoTest.arff");
//	 arquivo.delete();
	
/*	 try {
		arquivo.createNewFile();
		System.out.println("treinoTest.arff Criado!");	
	 } catch (IOException e1) {
		e1.printStackTrace();
	}
*/	 
// ------------ INICIAL para criar arquivo treinoTest.arff ---------------------------
lerArquivo objarq = new lerArquivo("treinoTest.arff"); 

// apaga o conteúdo e insere o cabeçalho
objarq.setCabecalho("cabecaWeka.txt");  
System.out.println("Cabeçalho inserido em treinoTest.arff aguardao cliente iniciar");
    
    
    
    
    synchronized (this) {
      try {
        this.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void onNewData(ApplicationObject topicSample) {
    Message message = (Message) topicSample;
    System.out.println("--------------------------------------------------------------------------------------------");
    System.out.println("Server - vai receber e escrever a mensagem enviada pelo cliente");
    System.out.println("Aqui ==> "+Serialization.fromJavaByteStream(message.getContent()));
    	
   	if (Serialization.fromJavaByteStream(message.getContent()).equals("FIMarquivo")) {
   	}else {
   		
		 resultado = objTesta.testa(Serialization.fromJavaByteStream(message.getContent()).toString());
		if (resultado=="1") {
			Queda++;
		}else {
			Nqueda++;
		}   		
   	}
	
	System.out.println("--------------------------------------------------------------------------------------------");
        
 //   ArrayList<String> linha = (ArrayList<String>) Serialization.fromJavaByteStream(message.getContent());
   
    String linha = (String) Serialization.fromJavaByteStream(message.getContent());
    try {
	//	CSVWriterExample(linha);
		// -------- carrega a linha que vem do cliente para o arquivo treino.arff ---------------
    	   if (linha.equals("FIMarquivo")) {
    	    	 System.out.println("Chegou no final do arquivo");
    	    	 System.out.println("Total de Queda="+Queda+ "/ Total de Não Queda= "+Nqueda);
   
       	    // JFrame ----------------------
    			 	Tela theClient = new Tela(Queda, Nqueda); // instancia o JFrame
    	    //-------------------------------
    	    

    	    	 
    	    	 // WekaTeste2 objs	= new WekaTeste2();
    	 //   System.out.println(objs.calculoWeka("0.36328125,0.421875,0.8317871"));//------ usando inteligencia artificial ---
    	 //   new Main().testa("0.36328125,0.421875,0.8317871");
    	    }else {
    	    	gravaEmArq("treinoTest.arff",linha);
    	    }
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
  //ArrayList<String> serializableContent = ; 
   // String csvFile = "C:/Users/Erica/Documents/smartfallGeradoServer.csv";
   //FileWriter writer = new FileWriter(csvFile);
   //writeline
    
    System.out.println("Server - mensagem recebida do cliente e escrita no server");
    
 
    
     PrivateMessage privateMessage = new PrivateMessage();
    privateMessage.setGatewayId(message.getGatewayId());
    privateMessage.setNodeId(message.getSenderId());

    synchronized (core) {
      counter++;
    }
    
    ApplicationMessage appMsg = new ApplicationMessage();
    appMsg.setContentObject(counter);
    privateMessage.setMessage(Serialization.toProtocolMessage(appMsg));

    core.writeTopic(PrivateMessage.class.getSimpleName(), privateMessage);
  }
  
  
  
  
  public static void gravaEmArq(String arq, String linha) throws IOException {
	  Vector<String> vet = new Vector<String>(); 
	  try {
	  File arquivo1 = new File(arq);
      FileReader fileReader = new FileReader(arquivo1);
	  BufferedReader bufferedReader = new BufferedReader(fileReader);
	  
	// ler o arquivo 
		try {
			while (bufferedReader.ready()){ // ler o arquivo guarda as informações no vetor para não apagar
				vet.add(bufferedReader.readLine());
			}
			
			FileWriter fileWriter = new FileWriter(arq);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			for (int i=0; i<vet.size();i++){ // pega do vetor e escreve oque já tinha
				bufferedWriter.write(vet.get(i).toString());
				bufferedWriter.newLine();
			}
			bufferedWriter.write(linha);
			// fecha o arquivo			
			bufferedReader.close();
			bufferedWriter.close(); 
			fileReader.close();
			bufferedReader.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  } catch (Exception e) {
		  System.out.println("erro no arquivo "+arq);
	  }
	  
  
  }
  
  
  
  public static void CSVWriterExample(String linha) throws IOException {
  
	  String csvFile = "c:/lixo/smartfallGeradoServer2.csv";	 	
	  System.out.println(" Servidor = ----> "+linha + " <----");
	  
	  try {
		  
		// create FileWriter object with file as parameter 
	      FileWriter outputfile = new FileWriter(csvFile); 

	   // create CSVWriter object writer object as parameter 
	      CSVWriter writer = new CSVWriter(outputfile); 
	      
	   // create a List which contains String array 
	      List<String[]> data = new ArrayList<String[]>();
      
	   // declaration and initialize String Array 
	     // String[] str = new String[linha.size()]; 
	     
	      ArrayList<String[]> list = new ArrayList<String[]>();
	      
	      StringBuilder sb = new StringBuilder();
	      
	      System.out.print("Imprimir cada registro recebido na lista de array: \n");
	   // ArrayList to Array Conversion 
	    //    for (int j = 0; j < linha.size(); j++) { 
	        	        	      	
	        	// Assign each value to String array 
	     //       str[j] = linha.get(j); 
	            //list.add(j, str);
	                     
	       //     writer.writeNext(str);
	            
	        //    System.out.print("linha ="+str[j]+"\n");
	         
		        
	       // } 
            
	       // System.out.print(" teste1  \n\n String Array[]: " + Arrays.toString(str));
	       	         	
	       // System.out.println("\n Escrevi no csv");
	        
	        writer.flush();
            writer.close();
	        
	  }
      catch (Exception e) {
          System.out.println("Error in CsvFileWriter.");
          e.printStackTrace();
      } 
}
}

package br.pucrio.inf.lac.helloworld;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import arquivos.lerArquivo;

import lac.cnclib.net.NodeConnection;
import lac.cnclib.net.NodeConnectionListener;
import lac.cnclib.net.mrudp.MrUdpNodeConnection;
import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.message.Message;


public class HelloCoreClient implements NodeConnectionListener {

  private static String		gatewayIP   = "127.0.0.1";
  private static int		gatewayPort = 5500;
  private static MrUdpNodeConnection	connection;
  private static int controle=0;
	static FileReader fileReader;
	static BufferedReader bufferedReader;

  
  
  public static void main(String[] args) {
      Logger.getLogger("").setLevel(Level.OFF);
      new HelloCoreClient();
//      inicia a Thred
      new Thread(t1).start();
  }

  public HelloCoreClient() {
      InetSocketAddress address = new InetSocketAddress(gatewayIP, gatewayPort);
      try {
          connection = new MrUdpNodeConnection();
          connection.addNodeConnectionListener(this);
          connection.connect(address);
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

  @Override
  public void connected(NodeConnection remoteCon) {
 
      
      //CSVReaderExample();
      //System.out.println(CSVReaderExample());
  //   String serializableContent = "Hello Edilson & Erica!!";
//      ArrayList<String> serializableContent = CSVReaderExample(); // acessa a função, imprime o conteúdo dela e retorna o line2

 /*    ApplicationMessage message = new ApplicationMessage();   
     ArrayList<String> serializableContent = CSVReaderExample();    
      message.setContentObject(serializableContent); 
      System.out.println("Aqui mensagem ="+message);

      try {
          connection.sendMessage(message);
      } catch (IOException e) {
          e.printStackTrace();
      }
      */
                 
  }

  
  
  private ArrayList<String> CSVReaderExample() {
	// TODO Auto-generated method stub
	  String csvFile = "C://lixo/smartfall.csv";
      
	  String line2 = null;
      CSVReader reader = null;
      ArrayList<String> avisos = new  ArrayList<String>(); 
     
      try {
          reader = new CSVReaderBuilder(new FileReader(csvFile)).withSkipLines(1).build();
          String[] line;
          while ((line = reader.readNext()) != null) {
       //       System.out.println("Reg [Ax= " + line[0] + ", Ay= " + line[1] + " , Az=" + line[2] + " , fall=" + line[3] + "]");
              line2 = "Reg [Ax= " + line[0] + ", Ay= " + line[1] + " , Az=" + line[2] + " , fall=" + line[3] + "]";
              avisos.add(line2);
              //System.out.println(line2);
          }
       //   System.out.println(avisos);
          
      } catch (IOException e) {
          e.printStackTrace();
      }
     
	// return line2;
      return avisos;

}

@Override
  public void newMessageReceived(NodeConnection remoteCon, Message message) {
      System.out.println("===-> "+ message.getContentObject());
	  System.out.println("Client - Recebe o contador de sincronização do Server");
  }
    
  // other methods

  @Override
  public void reconnected(NodeConnection remoteCon, SocketAddress endPoint, boolean wasHandover, boolean wasMandatory) {}

  @Override
  public void disconnected(NodeConnection remoteCon) {}

  @Override
  public void unsentMessages(NodeConnection remoteCon, List<Message> unsentMessages) {}

  @Override
  public void internalException(NodeConnection remoteCon, Exception e) {}

  private static Runnable t1 = new Runnable() {
	  
	   ApplicationMessage message = new ApplicationMessage(); 
	    public void run() {
	    
	    	/*
	    	 String serializableContent = "Hello na Thred & Erica!!";
	 	     message.setContentObject(serializableContent); 
	 	    // System.out.println("Aqui mensagem ="+message);

	 	      try {
	 	          connection.sendMessage(message);
	 	      } catch (IOException e) {
	 	          e.printStackTrace();
	 	      }
	   	*/
	    	
	    	int linha = 1;
			//lerArquivo objtexto = new lerArquivo("weka-teste-q1.csv");  /// com queda 
					lerArquivo objtexto = new lerArquivo("weka-teste-nq3.csv");  /// sem queda 
			//		lerArquivo objtexto = new lerArquivo("weka-teste-q2b.csv");  /// tropeço 
			
    	while(true){
				try {
					Thread.sleep(32); // atraso
					//String registroCSV = pegaRegCsv("smartfall.csv",linha);
//					lerArquivo objtexto = new lerArquivo("arquivo.txt");
					if (objtexto.getTexto(linha)!="") {
						System.out.println(objtexto.getTexto(linha));
						 message.setContentObject(objtexto.getTexto(linha));
				 	      try {
				 	          connection.sendMessage(message);
				 	      } catch (IOException e) {
				 	          e.printStackTrace();
				 	      }
					}else {
					//	System.out.println(controle);
						if (controle==1) {
							 message.setContentObject("FIMarquivo");						
							controle++;
							try {
					 	          connection.sendMessage(message);
					 	      } catch (IOException e) {
					 	          e.printStackTrace();
					 	      }
						}
						controle++;
					}
					
					linha++;
				
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	   			
	 
	    	 
	    	}	
	    }
	};

 private static String pegaRegCsv(String arquivo, int linha) {
	// abre o CSV  entrada.csv
	// ler a primeira linha
	// le a proxima linha
	// fecha arquivo csv
	String registro="vazio";
	
	  int readLinha=1;
	
	File arquivo2 = new File("c:/lixo/smartfall.csv");

	//leitura 
	try {
		fileReader = new FileReader(arquivo2);
		bufferedReader = new BufferedReader(fileReader);	
		
		try {
			while (bufferedReader.ready()){ // ler o arquivo
				if (readLinha==linha) {
					registro=bufferedReader.readLine();
				}
				readLinha++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		try {
			arquivo2.createNewFile();
			pegaRegCsv(arquivo,linha); // volta ao inicio do metodo
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
//			bufferedReader.close(); 


	
	return(registro);
}

}



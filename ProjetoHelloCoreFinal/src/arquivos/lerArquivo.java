package arquivos;

import java.io.*;
import java.util.Vector;

public class lerArquivo {

	static int l =0;
	static String text ="";
	static String texto ="";
	static String arquivo="";
	static File arquivGet;
	static File arquivSet;
	
	static File arquivo2;
	
	//leitura 
	FileReader fileReader;
	BufferedReader bufferedReader;
	
	// escrita 
	static FileWriter fileWriter;
	static BufferedWriter buffWriter;
	  
// guarda as informações que já estão no arquivo para nao perder	
	static Vector<String> vet = new Vector<String>();
	static Vector<String> vetCab = new Vector<String>();

	
	// leitura 
	  FileReader fileR;
	  BufferedReader buff;

		
	
	public  lerArquivo(String Abarquivo){
    arquivo=Abarquivo;
	}

	
	public String getTexto(int linha) {
		linha--;
		l=0;
		 texto="";	
		try {
			fileR = new FileReader(arquivo);
			buff= new BufferedReader(fileR);
			linha--;
			 while (buff.ready()){
				 text=buff.readLine();
				 if (linha==l) {
				 //System.out.println(text+" linha="+linha+"   l="+l);
				 texto=text;
				 }
				 l++;
			 }

			 buff.close();
			 fileR.close();
			 
		} 
		catch (FileNotFoundException e) {
			
			System.out.println(" => O Arquivo " +arquivo + " Não Existe" );
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			
		}		
		return texto;
	}
	
	
	public void setCabecalho(String arqCabeca) {
		// arquivo = arquivo para incluir
		/// arqCabeca arquivo com a informação de cabelalho
		try {
			arquivGet = new File(arquivo);
			fileReader = new FileReader(arquivGet);
		    bufferedReader = new BufferedReader(fileReader);
		    
		    if (arquivo.length()<1) {// arquivo vazio
				System.out.println("Arquivo "+ arquivo +" Vazio");
	 	      // incluir cabeçalho 
				CarregaVetArq(arqCabeca);
		    }else {
		    	CarregaVetArq(arqCabeca); //arquivGet.getName().toString());
		    }
		    
		    
		 // corre dentro do vet
		    try {
				fileWriter = new FileWriter(arquivo);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				
				for (int i=0; i<vetCab.size();i++){ // pega do vetor e escreve oque já tinha
					bufferedWriter.write(vetCab.get(i).toString());
					bufferedWriter.newLine();
					//System.out.println(vetCab.size());
				}

					// fecha o arquivo			
					bufferedReader.close();
					bufferedWriter.close(); 
				//	System.out.println("Conteudo: (" + texto +")  incluso em : (" + arquivo+")");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		    
		} catch (FileNotFoundException e) {
			System.out.println("cria arquivo "+arquivGet);
			try {
				arquivGet.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.exit(0); // deu erro ao criar arquivo sai 
			}
		//	escreveCsv(texto);
		}
	

		
		
		
	}
	
	private void CarregaVetArq(String cabecalho) {
		
		
		try {
			arquivo2 = new File(cabecalho);
			fileReader = new FileReader(arquivo2);
			bufferedReader = new BufferedReader(fileReader);
			
			System.out.println("CarregaVetArq vetCab  "+ cabecalho);
			
			
			// ler o arquivo 
						try {
							while (bufferedReader.ready()){ // ler o arquivo
								vetCab.add(bufferedReader.readLine());
								//System.out.println("==>");
								
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			
			
			
		} catch (FileNotFoundException e) {
			System.out.println("o Arquivo  "+ cabecalho  +" não existe");
			try {
				arquivo2.createNewFile();
			} catch (IOException e1) {
				System.out.println("Deu algum erro ao criar o arquivo: "+ cabecalho  +" ainda não existe");
				e1.printStackTrace();
			}
			
		}
		
		
	}	
	
	
}

package br.pucrio.inf.lac.helloworld;

import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.associations.Apriori;
import weka.classifiers.bayes.NaiveBayes;

public class WekaTeste2 {
	static String resposta;
	
	public static void main(String[] args ) {
		System.out.println(calculoWeka("0.36328125,0.421875,0.8317871"));
	/*	try {
			DataSource ds = new DataSource("src/br/pucrio/inf/lac/helloworld/weka-teste-q1.arff");
			Instances ins = ds.getDataSet();
			
			System.out.println(ins.toString());
			ins.setClassIndex(3);
			
			NaiveBayes nb = new NaiveBayes();
			nb.buildClassifier(ins); /// cria o classificador
			
			DenseInstance novo = new DenseInstance(4); // numero de atributos do arff
			novo.setDataset(ins); // associa novo com a instancia
			
/// atribui os valores para comparação de probalidade
			novo.setValue(0, "M");
			novo.setValue(1, "20-39");
			novo.setValue(2, "Nao");
			novo.setValue(3, "Nao");
			//novo.setValue(4, "Sim"); /// gasta_muito => não adicionar pois é oque é para prever
			
			double probabilidade[] = nb.distributionForInstance(novo); // faz a previsão de qual a percentagem 
			System.out.println("Sim= "+probabilidade[1]); // no arff gasta_muito 	{Nao, Sim}
			System.out.println("Não= "+probabilidade[0]); // no arff gasta_muito 	{Nao, Sim}
			
		} catch (Exception e) {
			//System.out.println("erro acessar aqrquivo");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/	}
	
	public static String calculoWeka(String linha) {
		resposta="";
		System.out.println(linha);

		String linha2 = linha;
		String array[] = new String[3];
		array = linha2.split(",");
//		System.out.println(linha+" array = "+array[1]);
		try {
			DataSource ds = new DataSource("weka-teste-q1.arff");
			Instances ins = ds.getDataSet();
			
		//	System.out.println(ins.toString());
			ins.setClassIndex(3); // define qual o atributo que quer fazer o teste
			
			NaiveBayes nb = new NaiveBayes();
			nb.buildClassifier(ins); /// cria o classificador
			
			DenseInstance novo = new DenseInstance(4); // numero de atributos no arff
			novo.setDataset(ins); // associa novo com a instancia
			
/// atribui os valores para comparação de probalidade   0.36328125,0.421875,0.8317871
			novo.setValue(0, Double.valueOf( array[0]).doubleValue()); // 	double d = Double.valueOf( array[0]).doubleValue();
			novo.setValue(1, Double.valueOf( array[1]).doubleValue());
			novo.setValue(2, Double.valueOf( array[2]).doubleValue());
			//novo.setValue(3, "Nao");/// gasta_muito => não adicionar pois é oque é para prever
			
			
			double probabilidade[] = nb.distributionForInstance(novo); // faz a previsão de qual a percentagem 
		//	System.out.println("Sim= "+probabilidade[1]); // no arff gasta_muito 	{Nao, Sim}
		//	System.out.println("Não= "+probabilidade[0]); // no arff gasta_muito 	{Nao, Sim}
			resposta="Queda= "+probabilidade[1]+" / Não Queda= "+probabilidade[0];
		} catch (Exception e) {
			//System.out.println("erro acessar aqrquivo");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return resposta;
	}
	
	
	public String regras() throws Exception{ // mastra as melhores regras de acossiação
		DataSource ds = new DataSource("weka-teste-q1.arff");
		Instances ins = ds.getDataSet();
		
		Apriori ap = new Apriori();
		
		ap.buildAssociations(ins);
		
		System.out.println(ap);
		return ap.toString();
		
		
		
	}

}

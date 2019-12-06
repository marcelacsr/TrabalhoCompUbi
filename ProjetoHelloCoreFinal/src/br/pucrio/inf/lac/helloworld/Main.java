package br.pucrio.inf.lac.helloworld;

import java.util.ArrayList;
import java.util.List;
 
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
 
public class Main {
 static String linhaArray[] = new String[3];
    public static void main(String[] args) {
    //    new Main().testa("0.36328125,0.421875,0.8317871");
    }
 
    public String  testa(String tlinha) {

		String linha2 = tlinha;
		String valor="";
	//	String linhaArray[] = new String[3];
		linhaArray = linha2.split(",");
		
        // we need those for creating new instances later
        final Attribute attributeSepalLength = new Attribute("sepallength");
        final Attribute attributeSepalWidth = new Attribute("sepalwidth");
        final Attribute attributePetalLength = new Attribute("petallength");
        final Attribute attributePetalWidth = new Attribute("petalwidth");
        final List<String> classes = new ArrayList<String>() {
            {
                add("Iris-setosa");
                add("Iris-versicolor");
                add("Iris-virginica");
            }
        };
 
        // Instances(...) requires ArrayList<> instead of List<>...
        ArrayList<Attribute> attributeList = new ArrayList<Attribute>(2) {
            {
                add(attributeSepalLength);
                add(attributeSepalWidth);
                add(attributePetalLength);
                add(attributePetalWidth);
                Attribute attributeClass = new Attribute("@@class@@", classes);
                add(attributeClass);
            }
        };
        // unpredicted data sets (reference to sample structure for new instances)
        //conjuntos de dados imprevisíveis (referência à estrutura de amostra para novas instâncias)
        Instances dataUnpredicted = new Instances("TestInstances",
                attributeList, 1);
        // último recurso é variável de destino
        dataUnpredicted.setClassIndex(dataUnpredicted.numAttributes() - 1); 
 
        // crie uma nova instância: essa deve cair no domínio setosa
        DenseInstance newInstanceSetosa = new DenseInstance(dataUnpredicted.numAttributes()) {
            {  // 
                setValue(attributeSepalLength, Double.valueOf(linhaArray[0]).doubleValue());
                setValue(attributeSepalWidth,  Double.valueOf(linhaArray[1]).doubleValue());
                setValue(attributePetalLength, Double.valueOf(linhaArray[2]).doubleValue());
               // setValue(attributePetalWidth, 0.4);
            }
        };
        // crie uma nova instância: essa deve cair no domínio virginica
        DenseInstance newInstanceVirginica = new DenseInstance(dataUnpredicted.numAttributes()) {
            {
                setValue(attributeSepalLength, 7);
                setValue(attributeSepalWidth, 3);
                setValue(attributePetalLength, 6.8);
                setValue(attributePetalWidth, 2.1);
            }
        };
         
        // instância para usar na previsão
        DenseInstance newInstance = newInstanceSetosa;
        // referência ao conjunto  dataset 
        newInstance.setDataset(dataUnpredicted);
 
        // importar modelo treinado pronto
        Classifier cls = null;
        try {
            cls = (Classifier) weka.core.SerializationHelper
                    .read("modelo-treinado-randomf.model");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cls == null)
            return null;
 
        //prever nova amostra
        try {
            double result = cls.classifyInstance(newInstance);
           // System.out.println("-----------------------------------------------------------------------------------------");
            System.out.println("Index of predicted class label: " + result + ", which corresponds to class: " + classes.get(new Double(result).intValue()));
            if (result==1) {
            //	System.out.println("Queda!");
            	valor="1";
            }else {
          //  	System.out.println("Não Queda!");
            	valor="0";
            }
         //   System.out.println("-----------------------------------------------------------------------------------------");
        
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return valor;
    }
}
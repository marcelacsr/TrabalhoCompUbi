//package main;
package br.pucrio.inf.lac.helloworld;
import java.io.BufferedReader;
import br.pucrio.inf.lac.helloworld.WekaTeste2;
import java.io.FileNotFoundException;
import java.io.FileReader;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.core.FastVector;
import weka.core.Instances;

public class WekaTest {
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}
		return inputReader;
	}

	public static Evaluation classify(Classifier model, Instances trainingSet, Instances testingSet) throws Exception {
		Evaluation evaluation = new Evaluation(trainingSet);
		model.buildClassifier(trainingSet);
		evaluation.evaluateModel(model, testingSet);
		return evaluation;
	}

	public static double calculateAccuracy(FastVector predictions) {
		double correct = 0;
		for (int i = 0; i < predictions.size(); i++) {
			NominalPrediction np = (NominalPrediction) predictions.elementAt(i);
			if (np.predicted() == np.actual()) {
				correct++;
			}
		}
		return 100 * correct / predictions.size();
	}

	public static Instances[][] crossValidationSplit(Instances data, int numberOfFolds) {
		Instances[][] split = new Instances[2][numberOfFolds];
		for (int i = 0; i < numberOfFolds; i++) {
			split[0][i] = data.trainCV(numberOfFolds, i);
			split[1][i] = data.testCV(numberOfFolds, i);
		}
		return split;
	}

	public static void main(String[] args) throws Exception {
		
		BufferedReader datafile = readDataFile("weka-queda-treino.arff"); // treino.arff")
		BufferedReader datafile2 = readDataFile("weka-teste-q1.arff");

		Instances treino = new Instances(datafile);
		
		Instances instanciaNova = new Instances(datafile);				

		treino.addAll(instanciaNova);

		
		Instances teste = new Instances(datafile2);

		treino.setClassIndex(treino.numAttributes() - 1);
		teste.setClassIndex(teste.numAttributes() - 1);


		// Use a set of classifiers
		Classifier modelo = new J48(); // a decision tree

		
		FastVector predictions = new FastVector();
		// For each training-testing split pair, train and test the classifier
		Evaluation validation = classify(modelo, treino, teste);
		
		predictions.appendElements(validation.predictions());
		
		
		// Uncomment to see the summary for each training-testing pair.
		// System.out.println(models[j].toString());

		// Calculate overall accuracy of current classifier on all splits
		double accuracy = calculateAccuracy(predictions);
		// Print current classifier's name and accuracy in a complicated,
		// but nice-looking way.

		System.out.println("Accuracy of " + modelo.getClass().getSimpleName() + ": " + String.format("%.2f%%", accuracy)
				+ "\n---------------------------------");
	}
}

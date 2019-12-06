package com.opencsv;

import com.opencsv.CSVReaderBuilder;

import br.pucrio.inf.lac.helloworld.HelloCoreServer;

import java.io.FileReader;
import java.io.IOException;

public class CSVReaderExample {
    
	public static void main(String[] args) {
    		
        String csvFile = "C:/Users/Erica/Documents/smartfall.csv";
        
        CSVReader reader = null;
        try {
            reader = new CSVReaderBuilder(new FileReader(csvFile)).withSkipLines(1).build();
            String[] line;
            while ((line = reader.readNext()) != null) {
                System.out.println("Reg [Ax= " + line[0] + ", Ay= " + line[1] + " , Az=" + line[2] + " , fall=" + line[3] + "]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }   

}
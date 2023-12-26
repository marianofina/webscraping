package org.example.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileLines {
    public static List<String> getLines (String nameFile) throws FileNotFoundException {
        try {
            FileReader fileReader = new FileReader(nameFile);
            List<String> lineas = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                lineas.add(linea);
            }

            return lineas;
        } catch (Exception e) {
            throw new FileNotFoundException(nameFile + " no encontrado");
        }
    }

    public static List<String> getLinesFromFile (File file) throws FileNotFoundException {
        try {
            FileReader fileReader = new FileReader(file);
            List<String> lineas = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                lineas.add(linea);
            }

            return lineas;
        } catch (Exception e) {
            throw new FileNotFoundException(file + " no encontrado");
        }
    }
}

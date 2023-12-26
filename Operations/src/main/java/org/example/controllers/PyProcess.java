package org.example.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PyProcess {
    public static void ejecutar() throws InterruptedException, IOException {
        String Script_Path = "C:\\INDEV Solutions\\WebScraping\\ProceImages\\Miner.py";
        ProcessBuilder Process_Builder = new ProcessBuilder("py", Script_Path).inheritIO();

        Process demo_Process = Process_Builder.start();
        demo_Process.waitFor();

        BufferedReader Buffered_Reader =
                new BufferedReader(new InputStreamReader(demo_Process.getInputStream()));
        String Output_line = "";

        while ((Output_line = Buffered_Reader.readLine()) != null) {
            System.out.println(Output_line);
        }

        demo_Process.destroy();
    }
}

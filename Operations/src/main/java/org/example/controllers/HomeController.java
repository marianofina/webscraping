package org.example.controllers;

import org.example.models.Pais;

import java.io.File;
import java.util.List;

public class HomeController {
    public static void ejecutar(Integer pais) {
        SystemLinkedin systemLinkedin = SystemLinkedin.getInstance();
        try {
            systemLinkedin.screenshot(pais);
            PyProcess.ejecutar();
            systemLinkedin.pushToDB();
        } catch (Exception e) {
            System.out.println("Error en home controller: " + e.getMessage());
        }
    }

    public static List<Pais> getPaises () {
        SystemLinkedin systemLinkedin = SystemLinkedin.getInstance();
        try {
            return systemLinkedin.getPaises();
        } catch (Exception e) {
            System.out.println("Error en home controller: " + e.getMessage());
            return null;
        }
    }

    public static void cargarEmpresas (File file) {
        try {
            SystemLinkedin.insertarEmpresasFile(file);
        } catch (Exception e) {
            System.out.println("Error en home controller: " + e.getMessage());
        }
    }

    public static void crearArchivo (String fecha) {
        SystemLinkedin systemLinkedin = SystemLinkedin.getInstance();
        try {
            systemLinkedin.createFileEmpresas(fecha);
        } catch (Exception e) {
            System.out.println("Error en home controller: " + e.getMessage());
        }
    }
}

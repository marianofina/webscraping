package org.example;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        /* Abrir Chrome (recordar cerrar antes las ventanas activas) desde CMD!!!
        "C:\Program Files\Google\Chrome\Application\chrome.exe" --remote-debugging-port=9222
        */

        // Establece la ubicación del controlador de Chrome - Controlador para Chrome Version 120
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("debuggerAddress", "localhost:9222");

        // Crea una instancia de ChromeDriver
        WebDriver driver = new ChromeDriver(options);

        // Obtiene las manijas (identificadores) de todas las pestañas
        List<String> handles = new ArrayList<>(driver.getWindowHandles());

        // Crea un array para almacenar las direcciones de las pestañas
        String[] urls = new String[handles.size()];

        // Itera sobre las pestañas y obtiene las direcciones
        for (int i = 0; i < handles.size(); i++) {
            // Cambia al identificador de la pestaña actual
            driver.switchTo().window(handles.get(i));

            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            // Now you can do whatever you need to do with it, for example copy somewhere
            try {
                FileUtils.copyFile(scrFile, new File("C:\\INDEV Solutions\\WebScraping Linkedin\\ProceImages\\images\\screenshot" + i + ".jpg"));
            } catch (Exception e) {
                throw new IOException();
            }
        }

        // Cierra el navegador (esto no cierra la instancia existente de Chrome)
        driver.quit();
    }
}
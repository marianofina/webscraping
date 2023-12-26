package org.example.controllers;

import org.apache.commons.io.FileUtils;
import org.example.db.SQLConnection;
import org.example.db.SQLMethods;
import org.example.logic.FileLines;
import org.example.logic.converters.EmpresaTransformer;
import org.example.models.Empresa;
import org.example.models.Pais;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SystemLinkedin {
    private static SystemLinkedin instance;
    private SystemLinkedin() {}
    public static SystemLinkedin getInstance() {
        if (instance == null) {
            instance = new SystemLinkedin();
        }
        return instance;
    }

    public static void insertarEmpresasFileName (String nameFile) throws FileNotFoundException, SQLException, ClassNotFoundException {
        SQLConnection connection = SQLConnection.getInstance();
        SQLMethods sqlMethods = new SQLMethods(connection.getConnection());
        List<String> empresas = FileLines.getLines(nameFile);
        for (String empresa : empresas) {
            Empresa e = EmpresaTransformer.transformEmpresasFile(empresa);
            sqlMethods.insertarEmpresa(e);
        }
    }

    public static void insertarEmpresasFile (File file) throws FileNotFoundException, SQLException, ClassNotFoundException {
        SQLConnection connection = SQLConnection.getInstance();
        SQLMethods sqlMethods = new SQLMethods(connection.getConnection());
        List<String> empresas = FileLines.getLinesFromFile(file);
        for (String empresa : empresas) {
            Empresa e = EmpresaTransformer.transformEmpresasFile(empresa);
            sqlMethods.insertarEmpresa(e);
        }
    }

    public boolean existeEmpresa (String link, Integer pais) throws SQLException, ClassNotFoundException {
        SQLConnection connection = SQLConnection.getInstance();
        SQLMethods sqlMethods = new SQLMethods(connection.getConnection());
        return sqlMethods.existeEmpresa(link, pais);
    }

    private WebDriver getChromeDriver() {
        /* Abrir Chrome (recordar cerrar antes las ventanas activas) desde CMD!!!
        "C:\Program Files\Google\Chrome\Application\chrome.exe" --remote-debugging-port=9222
        */

        // Establece la ubicación del controlador de Chrome - Controlador para Chrome Version 120
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("debuggerAddress", "localhost:9222");

        // Crea una instancia de ChromeDriver
        return new ChromeDriver(options);
    }

    public Integer getUltimoIdScreenshot (Integer pais) throws IOException {
        String path = "C:\\INDEV Solutions\\WebScraping\\ProceImages\\images\\";
        switch (pais) {
            case 1:
                path += "mex\\ultimo_id.txt";
                break;
            case 2:
                path += "uru\\ultimo_id.txt";
                break;
            case 3:
                path += "arg\\ultimo_id.txt";
                break;
            case 4:
                path += "chi\\ultimo_id.txt";
                break;
            case 5:
                path += "esp\\ultimo_id.txt";
                break;
        }

        File ultimo_id = new File(path);
        // Crea un objeto FileInputStream para leer el archivo
        FileInputStream fis = new FileInputStream(ultimo_id);

        StringBuilder id = new StringBuilder();

        // Lee bytes del archivo
        int byteLeido;
        while ((byteLeido = fis.read()) != -1) {
            id.append((char) byteLeido);
        }

        return Integer.parseInt(id.toString());
    }

    public void setUlimoIdScreenshot (Integer pais, Integer id) throws IOException {
        String path = "C:\\INDEV Solutions\\WebScraping\\ProceImages\\images\\";
        switch (pais) {
            case 1:
                path += "mex\\ultimo_id.txt";
                break;
            case 2:
                path += "uru\\ultimo_id.txt";
                break;
            case 3:
                path += "arg\\ultimo_id.txt";
                break;
            case 4:
                path += "chi\\ultimo_id.txt";
                break;
            case 5:
                path += "esp\\ultimo_id.txt";
                break;
        }

        File ultimo_id = new File(path);
        FileUtils.writeStringToFile(ultimo_id, id.toString());
    }

    private void insertarEmpresaNoDesc (String link_linkedin, Integer pais, Integer id_screenshot) throws SQLException, ClassNotFoundException {
        SQLConnection connection = SQLConnection.getInstance();
        SQLMethods sqlMethods = new SQLMethods(connection.getConnection());
        Empresa empresa = new Empresa();
        empresa.setLink(link_linkedin);
        empresa.setPais(pais);
        empresa.setEstadoEmpresa(1);
        empresa.setId_screenshot(id_screenshot);
        sqlMethods.insertarEmpresaNoDesc(empresa);
    }

    public void screenshot (Integer pais) throws IOException, SQLException, ClassNotFoundException {
        WebDriver driver = this.getChromeDriver();

        // Obtiene las manijas (identificadores) de todas las pestañas
        List<String> handles = new ArrayList<>(driver.getWindowHandles());

        String path = "C:\\INDEV Solutions\\WebScraping\\ProceImages\\images\\";
        switch (pais) {
            case 1:
                path += "mex\\";
                break;
            case 2:
                path += "uru\\";
                break;
            case 3:
                path += "arg\\";
                break;
            case 4:
                path += "chi\\";
                break;
            case 5:
                path += "esp\\";
                break;
        }

        // Obtenemos el ultimo id de screenshot
        Integer id = this.getUltimoIdScreenshot(pais);

        // Definir el porcentaje de zoom que deseas aplicar (puedes ajustar este valor)
        int porcentajeZoom = 125;

        // Itera sobre las pestañas y obtiene las direcciones
        for (String handle : handles) {
            // Cambia al identificador de la pestaña actual
            driver.switchTo().window(handle);

            // Ejecutar un script de JavaScript para cambiar el nivel de zoom
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String script = "document.body.style.zoom = '" + porcentajeZoom + "%';";
            js.executeScript(script);

            // Chequemos en la base de datos si existe la empresa mediante la url de la pestaña
            if (!this.existeEmpresa(driver.getCurrentUrl(), pais)) {
                File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
                // Now you can do whatever you need to do with it, for example copy somewhere
                FileUtils.copyFile(scrFile, new File(path + "original" + (id++) + ".jpg"));
                this.insertarEmpresaNoDesc(driver.getCurrentUrl(), pais, id);
            } else {
                driver.close();
            }
        }

        this.setUlimoIdScreenshot(pais, id);
    }

    public void actualizarEmpresaFileList (String nameFile, Integer pais) throws SQLException, ClassNotFoundException, FileNotFoundException {
        SQLConnection connection = SQLConnection.getInstance();
        SQLMethods sqlMethods = new SQLMethods(connection.getConnection());
        List<String> empresas = FileLines.getLines(nameFile);

        for (String empresa : empresas) {
            Empresa e = EmpresaTransformer.transformPushFile(empresa, pais);
            sqlMethods.actualizarEmpresa(e);
        }
    }

    public void pushToDB () throws SQLException, ClassNotFoundException, FileNotFoundException {

        String[] paises = {"mex", "uru", "arg", "chi", "esp"};

        for (String pais : paises) {
            int id_pais = switch (pais) {
                case "mex" -> 1;
                case "uru" -> 2;
                case "arg" -> 3;
                case "chi" -> 4;
                case "esp" -> 5;
                default -> 0;
            };

            this.actualizarEmpresaFileList(
                    "C:\\INDEV Solutions\\WebScraping\\ProceImages\\images\\" + pais + "\\push.txt",
                    id_pais);
        }
    }

    public ResultSet getEmpresasByFecha(String fecha) throws SQLException, ClassNotFoundException {
        SQLConnection connection = SQLConnection.getInstance();
        SQLMethods sqlMethods = new SQLMethods(connection.getConnection());
        return sqlMethods.getEmpresasByFecha(fecha);
    }

    public void createFileEmpresas(String fecha) throws SQLException, ClassNotFoundException, IOException {
        String path = "C:\\INDEV Solutions\\WebScraping";
        File file = new File(path + "\\empresas_" + fecha + ".csv");
        ResultSet empresas = this.getEmpresasByFecha(fecha);
        StringBuilder finalText = new StringBuilder();
        while (empresas.next()) {
            StringBuilder empresa = new StringBuilder();
            empresa.append(empresas.getString("emp_nombre")).append(",@@,");
            empresa.append(empresas.getString("emp_descripcion")).append(",@@,");
            empresa.append(empresas.getString("emp_empleos")).append(",@@,");
            empresa.append(empresas.getString("emp_link"));
            finalText.append(empresa.toString()).append("\n");
        }
        FileUtils.writeStringToFile(file, finalText.toString());
    }

    public List<Pais> getPaises() throws SQLException, ClassNotFoundException {
        SQLConnection connection = SQLConnection.getInstance();
        SQLMethods sqlMethods = new SQLMethods(connection.getConnection());
        ResultSet paises = sqlMethods.getPaises();
        List<Pais> paisesList = new ArrayList<>();
        while (paises.next()) {
            Pais pais = new Pais();
            pais.setId(paises.getInt("pai_id"));
            pais.setNombre(paises.getString("pai_nombre"));
            paisesList.add(pais);
        }

        return paisesList;
    }
}

package org.example.db;

import org.example.models.Empresa;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLMethods {
    private Connection connection;
    public SQLMethods (Connection connection) {
        this.connection = connection;
    }

    public void insertarEmpresa (Empresa empresa) {
        if (empresa.getFecha() == null) {
            empresa.setFecha(new Date(1672542000));
        }
        String query = "EXEC InsertarEmpresa '"
                + empresa.getNombre() + "', '"
                + empresa.getDescripcion() + "', '"
                + empresa.getEmpleos() + "', '"
                + empresa.getRubro() + "', '"
                + empresa.getLink() + "', '"
                + empresa.getFecha() + "', "
                + empresa.getPais() + ", "
                + empresa.getEstadoEmpresa();
        try {
            connection.createStatement().executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error al insertar empresa" + e.getMessage());
        }
    }

    public void insertarEmpresaNoDesc (Empresa empresa) throws SQLException {
        long tiempoActual = System.currentTimeMillis();
        String query = "EXEC InsertarEmpresaND '" + empresa.getLink() + "', '" + new Date(tiempoActual) + "', " + 2 + ", " + empresa.getPais() + ", " + empresa.getId_screenshot() + ";";
        connection.createStatement().executeUpdate(query);
    }

    public boolean existeEmpresa(String link, Integer pais) {
        String query = "SELECT * FROM empresas WHERE emp_link = '" + link + "' AND emp_pais_id = " + pais;
        try {
            return connection.createStatement().executeQuery(query).next();
        } catch (Exception e) {
            System.out.println("Error al buscar empresa" + e.getMessage());
            return false;
        }
    }

    public void actualizarEmpresa (Empresa empresa) {
        String query = "EXEC ActualizarEmpresa '"
                + empresa.getNombre() + "', '"
                + empresa.getDescripcion() + "', "
                + empresa.getEstadoEmpresa() + ", "
                + empresa.getEmpleos() + ", '"
                + empresa.getRubro() + "', "
                + empresa.getId_screenshot() + ", "
                + empresa.getPais() + ";";
        try {
            connection.createStatement().executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error al actualizar empresa" + e.getMessage());
        }
    }

    public ResultSet getEmpresasByFecha (String fecha) {
        String query = "SELECT * FROM empresas WHERE emp_estado_id = 1006 AND emp_fecha = '" + fecha + "'";
        try {
            return connection.createStatement().executeQuery(query);
        } catch (Exception e) {
            System.out.println("Error al buscar empresa: " + e.getMessage());
            return null;
        }
    }

    public ResultSet getPaises() {
        String query = "SELECT * FROM paises";
        try {
            return connection.createStatement().executeQuery(query);
        } catch (Exception e) {
            System.out.println("Error al buscar paises: " + e.getMessage());
            return null;
        }
    }
}

package org.example;

import java.sql.Connection;
import java.sql.Date;

public class SQLMethods {
    private Connection connection;
    public SQLMethods (Connection connection) {
        this.connection = connection;
    }

    public void insertarEmpresa (
            String nombre,
            String descripcion,
            Integer empleos,
            String rubro,
            String link,
            Date fecha,
            Integer pais,
            Integer estadoEmpresa) {
        if (fecha == null) {
            fecha = new Date(1672542000);
        }
        String query = "EXEC InsertarEmpresa '"
                + nombre + "', '"
                + descripcion + "', '"
                + empleos + "', '"
                + rubro + "', '"
                + link + "', '"
                + fecha + "', "
                + pais + ", "
                + estadoEmpresa;
        try {
            connection.createStatement().executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error al insertar empresa" + e.getMessage());
        }
    }

}

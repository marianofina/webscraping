package org.example.logic.converters;

import org.example.models.Empresa;

public class EmpresaTransformer {

    /**
     * Transforma una línea de texto a un objeto Empresa - Formato de línea:
     * nombre, descripcion, empleos, rubro, link, pais
     * @param line
     * @return
     */
    public static Empresa transformEmpresasFile (String line) {
        Empresa empresa = new Empresa();
        String[] data = line.split("\t");
        empresa.setNombre(data[0]);
        empresa.setDescripcion(data[1]);
        empresa.setEmpleos(Integer.parseInt(data[2]));
        empresa.setRubro(data[3]);
        empresa.setLink(data[4]);
        empresa.setPais(Integer.parseInt(data[5]));
        empresa.setFecha(null);
        empresa.setEstadoEmpresa(1);
        return empresa;
    }

    public static Empresa transformPushFile (String line, Integer pais) {
        Empresa empresa = new Empresa();
        String[] data = line.split(",@@,");
        empresa.setId_screenshot(Integer.parseInt(data[0]));
        empresa.setNombre(data[1]);
        empresa.setDescripcion(data[2]);
        empresa.setEmpleos(Integer.parseInt(data[3]));
        empresa.setRubro("TI");
        empresa.setEstadoEmpresa(1006);
        empresa.setPais(pais);
        return empresa;
    }


}

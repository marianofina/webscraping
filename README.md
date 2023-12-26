# Sistema de Webscraping
## Descripción 
Este sistema está diseñado para realizar la recolección de información de empresas a partir de perfiles de LinkedIn. Utiliza Java, Python y SQL Server para diferentes aspectos del proceso. 

## Componentes  

### Java (Backend):  
**Tecnología:**  
  - Desarrollado en Java. 
  - Utiliza Selenium para la automatización del navegador Chrome. 
  - Conexión a SQL Server para almacenar y verificar la información recolectada.
    
**Instrucciones de Ejecución:**  
  1. Clonar el repositorio. 
  2. Configurar las dependencias y el classpath. 
  3. Ejecutar el programa principal de Java (Main). 

### Python (Procesamiento de Imágenes):  
**Tecnología:**  
  - Desarrollado en Python. 
  - Utiliza bibliotecas de procesamiento de imágenes para manipular capturas de pantalla. 
  - Implementa efectos y tapado para resaltar la información importante.
    
**Instrucciones de Ejecución:**  
  - 1. Asegurarse de tener las bibliotecas necesarias instaladas (`pip install cv2 pytesseract`). 
  - 2. Ejecutar el script de procesamiento de imágenes. 

### SQL Server (Base de Datos):  
**Tecnología:**  
  - Almacena datos recolectados y evita duplicados. 
  - SQL Server utilizado como sistema de gestión de bases de datos.
    
**Instrucciones de Configuración:**  
  1. Configurar la conexión a la base de datos en el archivo de SQLConnection. 
  2. Ejecutar scripts SQL para crear las tablas necesarias y procedimientos almacenados (se utilizan en SQLMethods). 

## Configuración del Entorno  
**Java:**  
  - Asegurarse de tener Java y Maven instalados.
     
**Python:**  
  - Asegurarse de tener Python 3 instalado.
    
**SQL Server:**  
  - Configurar la conexión y crear la base de datos utilizando los scripts proporcionados.

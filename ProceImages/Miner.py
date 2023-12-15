import cv2
import numpy as np
import pytesseract
import os

pytesseract.pytesseract.tesseract_cmd = r'C:\Program Files\Tesseract-OCR\tesseract.exe'

cantidadImg = 0

# Función para contar la cantidad de archivos en un directorio
def count_files_in_directory(directory):
    return len([f for f in os.listdir(directory) if os.path.isfile(os.path.join(directory, f))])


# Indicamos el nombre del directorio y asignamos la cantidad de imágenes a la variable
directory = 'images'
cantidadImg = count_files_in_directory(directory)


with open("result.csv", "w") as r:
    for i in range(cantidadImg):
        # Abrimos las imágenes y unimos la tapa con la imagen
        img = cv2.imread(f"images/screenshot{i}.jpg")
        tapa = cv2.imread("utilities/tapa.png")
        res = cv2.add(img, tapa)

        # Pasamos a blanco el texto en gris
        lower_color = np.array([110, 110, 110])
        upper_color = np.array([170, 170, 170])
        mask = cv2.inRange(res, lower_color, upper_color)
        res[mask != 0] = [255, 255, 255]

        # Aplicamos efecto threshold
        binary_image = cv2.threshold(res, 150, 255, cv2.THRESH_BINARY)

        # Obtenemos el texto e indicamos el lenguaje
        text = pytesseract.image_to_string(res, lang='spa')

        # Guardamos text en un archivo
        with open("text.txt", "w") as f:
            f.write(text)

        with open("text.txt", "r") as f:
            text = f.read().splitlines()
            lineaAEscribir = text[0]
            print("Empresa: " + text[0])
            for (i, line) in enumerate(text):
                if "empleados" or "alumnos" or "seguidores" in line:
                    lineaAEscribir = lineaAEscribir + ",@@," + line
                    print("Empleados: " + line)
                if "vacantes" or "vacante" in line:
                    lineaAEscribir = lineaAEscribir + ",@@," + line[line.find("Hay ") + 4: line.find(" vacantes")]
                    print("Vacantes: " + line[line.find("Hay ") + 4: line.find(" vacantes")])
            r.write(lineaAEscribir + "\n")
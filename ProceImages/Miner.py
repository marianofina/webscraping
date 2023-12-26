import cv2
import numpy as np
import pytesseract
import os

pytesseract.pytesseract.tesseract_cmd = r'C:\Program Files\Tesseract-OCR\tesseract.exe'

arg_files = os.listdir("C:\\INDEV Solutions\\WebScraping\\ProceImages\\images\\arg")
chi_files = os.listdir("C:\\INDEV Solutions\\WebScraping\\ProceImages\\images\\chi")
esp_files = os.listdir("C:\\INDEV Solutions\\WebScraping\\ProceImages\\images\\esp")
mex_files = os.listdir("C:\\INDEV Solutions\\WebScraping\\ProceImages\\images\\mex")
uru_files = os.listdir("C:\\INDEV Solutions\\WebScraping\\ProceImages\\images\\uru")


def work(pais, archivo):
    # Abrimos las imágenes y unimos la tapa con la imagen
    img = cv2.imread(f"C:\\INDEV Solutions\\WebScraping\\ProceImages\\images\\{pais}\\{archivo}")
    tapa = cv2.imread("C:\\INDEV Solutions\\WebScraping\\ProceImages\\utilities\\tapa.png")
    res = cv2.add(img, tapa)

    # Pasamos a blanco el texto en gris
    lower_color = np.array([110, 110, 110])
    upper_color = np.array([170, 170, 170])
    mask = cv2.inRange(res, lower_color, upper_color)
    res[mask != 0] = [255, 255, 255]

    # Obtenemos el texto e indicamos el lenguaje
    text = pytesseract.image_to_string(res, lang='spa')
    return text


def procesarTexto(id_screenshot, text):
    text = text.splitlines()
    lineaAEscribir = id_screenshot + ",@@," + text[0]
    for linea in text:
        if ("empleado" in linea) or ("alumno" in linea) or ("seguidor" in linea):
            lineaAEscribir = lineaAEscribir + ",@@," + linea
        if "vacante" in linea:
            lineaAEscribir = lineaAEscribir + ",@@," + linea[linea.find("Hay ") + 4: linea.find(" vacante")]
    return lineaAEscribir


def getTextReady(pais):
    texto_procesado = ""
    for arch in eval(f"{pais}_files"):
        if arch.find("original") != -1 and arch.endswith(".jpg"):
            texto_crudo = work(pais, arch)
            id_screenshot = arch[8:arch.find(".jpg")]
            texto_procesado += procesarTexto(id_screenshot, texto_crudo) + "\n"

            # Modificamos el nombre del archivo para que no se vuelva a procesar
            os.rename(f"C:\\INDEV Solutions\\WebScraping\\ProceImages\\images\\{pais}\\{arch}",
                      f"C:\\INDEV Solutions\\WebScraping\\ProceImages\\images\\{pais}\\procesado{arch[8:]}")

    return texto_procesado


def main():
    for pais in ["arg", "chi", "esp", "mex", "uru"]:
        texto = getTextReady(pais)
        with open(f"C:\\INDEV Solutions\\WebScraping\\ProceImages\\images\\{pais}\\push.txt", "w") as f:
            f.write(texto)


main()

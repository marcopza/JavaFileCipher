# JavaFileCipher
# Informe final

## Funcionamiento del programa 
Para realizar este proyecto usamos Java para la lógica del programa y JavaFX para la interfaz que interactúa con el usuario. También usamos algunas librerías como: `javax.crypto`, `java.io` y `java.security`.

El software tiene un funcionamiento simple, donde pide al usuario que escoja un archivo para encriptar y una clave de encriptación con confirmación, generando un archivo “encrypted.txt”, Esto en el caso de querer encriptar un archivo. Para realizar una desencriptación el proceso es el mismo, seleccionar el archivo que se encuentra encriptado y proporcionar la clave con la que se encripto, el programa genera un archivo ”decrypted.txt” con la información.

El programa lo primero que realizar es una verificación de que las claves suministradas coincidan o no sean nulas, dado el caso, lanza una alarma al usuario. Después de esto, lo siguiente será crear o traer el archivo con la ruta que dio el usuario. Según el caso, el software creara un archivo de encriptación o desencriptación. 
Para la encriptación, el programa calcula el hash para el archivo original y lo escribe en el archivo de encriptación. Posterior a esto, se utiliza la clave del usuario y un factor especial de llave "PBKDF2WithHmacSHA1" para crear una clave secreta de 128 bits, la cual, en conjunto con el cifrado AES y un vector de inicialización, generan la clave con la cual se encriptara el archivo. donde los primeros 20 bits del archivo son de la clave.

Para desencriptar es un proceso similar, usando el archivo encriptado y la contraseña del usuario. Solo que, al iniciar, se extraen los primeros 20 bits que son la llave especial. También se realiza una verificación de la clave del usuario y la que se encuentra en los primeros 20 bits, corroborando que se puede desencriptar exitosamente el archivo.


## Dificultades presentadas
A continuación, se presentan el listado de las dificultades que surgieron en la realización del cifrador y descifrador de archivos utilizando Java. Cabe recalcar, que para el análisis de estas dificultades también fue considerado el proceso de desarrollo en términos de la estructuración del equipo. 


1.	**Problemas en la sincronización de horarios:** Aunque la organización y estructuración, así como la división de tareas del equipo fue eficiente, se presentaron conflictos para coordinar reuniones síncronas de los 3 miembros del equipo. Estos conflictos de horarios retrasaron el trabajo y fue necesario utilizar horarios no habituales para las reuniones.
2.	**Problemas con el algoritmo de cifrado SHA-1:** Intentando utilizar el algoritmo de cifrado SHA-1 se presentaron errores pues la aplicación no funcionaba correctamente. Estos problemas fueron solucionados, en gran medida, cuando se descubrió que los 20 primeros bytes del archivo cifrado son utilizados para guardar la información de la clave de verificación del algoritmo.

3.	**Problemas para encontrar y seleccionar librerías adecuadas para la implementación:** En un inicio fue necesarios investigar cual es la forma más sencilla de implementar un cifrador y descifrador de archivos utilizando Java y librerías existentes, por lo cual antes de iniciar con la implementación se presentaron problemas para decidir que librerías eran la mejor opción y se debían utilizar. Solucionado esta problemática se decidió utilizar java.security y javax.crypto como librerías principales.

4.	**Problemas de implementación por desconocimientos técnicos:** Durante la implementación de la funcionalidad principal de cifrar y descifrar archivos se presentaron algunos problemas causados por desconocimientos técnicos de las librerías, pero este problema fue rápidamente solucionado consultado la documentación de Oracle.

5.	**Problemas comprendiendo en detalle el flujo que debería tener la aplicación por falencias en conocimientos teóricos:** Al momento de analizar cual debía ser el flujo más simple que debería tener la aplicación, pero a su vez que satisficiera los requerimientos, surgieron inconvenientes debió a desconocimientos teóricos sobre los algoritmos de cifrado. Lo anterior fue solucionado consultando el material presentado en las clases.

## Conclusiones
La culminación del proyecto dio como resultado una aplicación programada en JAVA la cual funciona como un cifrador y descifrador de archivos utilizando SHA-1 y AES como algoritmos de cifrado. Por lo tanto, la mencionada aplicación permite que el usuario seleccione cualquier tipo de archivo de texto y muestra el nombre de dicho archivo en la UI de la aplicación para verificar que se cargo correctamente. Posterior a ello, la aplicación recibe un texto introducido por el usuario que servirá como clave de cifrado para el archivo cargado. Para terminar con el cifrado, la aplicación genera un nuevo archivo llamado encrypted.txt que tendrá toda la información del archivo seleccionado por el usuario, pero con la particularidad de que la información no podrá ser comprendida pues estará cifrada. Por último, se puede realizar la acción contraria pues el aplicativo permite seleccionar un archivo cifrado, introducir la clave utilizada para dicho archivo y generar un nuevo archivo llamado decrypted.txt con la información original. 

Por otro lado, el presente proyecto permitió tener un acercamiento tanto técnico como teórico a los algoritmos de cifrado. Pues nos permitió entender como y cuales son los requisitos mínimos para realizar una implementación básica de un cifrador y descifrador utilizando SHA-1. además, nos permitió entender teóricamente como funcionan los algoritmos de cifrados ya mencionados. 

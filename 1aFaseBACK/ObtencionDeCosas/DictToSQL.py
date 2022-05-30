##IMPORTAMOS FUNCION DE EXTRAER DATA DE UN TXT EN FIREBASE
import extractingDataFromTXT as extr
##Conectamos con nuestra base de datos 
import pymysql

#############
##CONEXION BD
#############

try:
    #database connection
    #Conectamos la base de datos a mi servidor local
    connection = pymysql.connect(host="localhost", user="root", passwd="", database="proyectointegrado")
    cursor = connection.cursor()

    print("Se ha conectado correctamente a la base de datos")
    
except pymysql.Error as e:
    
    print("No se puede conectar a la base de datos, error: ",e)
    
    
    
    
##STRINGS DE CADA DICCIONARIO
dictEspañol = 'listado-general.txt'



##CREAMOS UN RESULTADO ARRAYLIST DE LO QUE TE DEVUELVE LA FUNCIÓN EXTRAE DICCIONARIO
listadepalabritas =  extr.extractDictionary(dictEspañol)
palabrasEspañolasPataNegra = extr.isnotAccent(listadepalabritas)
palabrasTildeanasEspañolasPataNegra = extr.isAccent(listadepalabritas)




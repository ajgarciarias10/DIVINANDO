##IMPORTAMOS LIBRERIAS FIREBASE
from numpy import extract
##IMPORTAMOS FUNCION DE EXTRAER DATA DE UN TXT EN FIREBASE
import extractingDataFromTXT as extr
import pymysql

try:
    #database connection
    #Conectamos la base de datos a mi servidor local
    connection = pymysql.connect(host="localhost", user="root", passwd="", database="proyectointegrado")
    cursor = connection.cursor()

    print("Se ha conectado correctamente a la base de datos")
    
except pymysql.Error as e:
    
    print("No se puede conectar a la base de datos, error: ",e)
    
def insertVehicle(connection,id,pais) : 
    try :
        #preparamos la query y la ejecutamos
        insert1 = f"INSERT INTO paises(id_paises,pais)  VALUES ('{id}','{pais}')"                                                    
        curr = connection.cursor()
        curr.execute(insert1)
        connection.commit()
        
        #devolvemos que se haya insertado correctamente
        
        return(print("Datos insertados correctamente"))
    
    
    #en caso de que haya error lo mostramos    
        
    except pymysql.Error as e : 
        
        connection.commit()
        return(print("Error al insertar. Error: ",e))

def insertUrl(connection,id,url) : 
    try :
        #preparamos la query y la ejecutamos
        insert1 = f"INSERT INTO urlpaises(id,url)  VALUES ('{id}','{url}')"                                                    
        curr = connection.cursor()
        curr.execute(insert1)
        connection.commit()
        
        #devolvemos que se haya insertado correctamente
        
        return(print("Datos insertados correctamente"))
    
    
    #en caso de que haya error lo mostramos    
        
    except pymysql.Error as e : 
        
        connection.commit()
        return(print("Error al insertar. Error: ",e))


Paises=  extr.devuelvepaisEnEspañol()
urls =  extr.url()
for pais in Paises:
    insertVehicle(connection,None,pais)
for url in urls :
    insertUrl(connection,None,url)
        



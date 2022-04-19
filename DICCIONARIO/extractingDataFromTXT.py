
def extractSpanishDictionary():
    palabra=list()
    with open('listado-general.txt','r', encoding='utf-8') as f:
        for line in f:
            if(len(line)==6):
                palabra.append(line) 
                
    return palabra

print (extractSpanishDictionary())
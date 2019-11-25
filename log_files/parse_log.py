from pdb import set_trace as bp
import re
import json
LOG_FILE = './test.log'

def main():

        with open(LOG_FILE) as fd:
                raw = fd.read()
                raw = re.findall('(?:INFO).*',raw)
                raw = list(map(lambda x : x.replace('INFO: ', ''), raw))
                jslist = []
                for item in raw :
                        jslist.append(json.loads(item))

                #genero lista de disparos y marcados

                transiciones = []
                marcados = []
                for js in jslist :
                        transiciones.append(js['disparo'])
                        marcados.append(js['marcado'])
                bp()
                



def testInvariantePlaza(marcado):
        plazas      = [
                        [1,2,3],
                        [0,1,5]
                      ]

        invariantes = [1,3]

        for p, inv in zip(plazas,invariantes):
                for marca in marcado :
                        acc = 0
                        for k in p:
                                acc += marca[k]
                        if (acc != inv):
                                print ('Fallo invariante', p)
                                print ('Deberia ser : ',inv,'y es : ', acc )

if __name__ == '__main__':
        main()

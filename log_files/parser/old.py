
from pdb import set_trace as bp
import re
import json
import numpy
import copy
LOG_FILE = '../finalPresentacionvPrioBajada.log'
PETRI_FILE = '/home/dabratte/repos/Concurrente/src/petriNet/red_final_prio_bajada.json'
N_TRANSICIONES = 16 
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

                for i in range(N_TRANSICIONES) :
                        if transiciones.count(i) < 1:
                                print('La transicion : ', i, ' no se disparo nunca\n')

                for i in range(N_TRANSICIONES) :
                        print('La transicion : ', i, ' se disparo ', transiciones.count(i))

                for i in range(len(transiciones)):
                        if transiciones[i] == 1 :
                                transiciones[i] = 0

                
                remover_invariantes(transiciones,[7,0,2,3,4,15,5,6])
                remover_invariantes(transiciones,[13,12,14])
                remover_invariantes(transiciones,[8,10,11,9])
                probar_Tinv(transiciones, N_TRANSICIONES, marcados[-1])

def remover_invariantes(ts, invariantes):
        
        min_rep = len(ts)

        #obtengo cantidad de invariantes completos
        for inv in invariantes:
                if ts.count(inv) < min_rep:
                        min_rep = ts.count(inv)

        #remuevo las secuencias invariantes
        for inv in invariantes :
                for k in range(min_rep):
                        ts.remove(inv)
        resto_inv = list(filter(lambda x : x in invariantes,ts))
        print('El invariante ', invariantes, 'se repitio : ', min_rep)
        print('\nEl resto del invariante',invariantes , 'es :\n')
        print(resto_inv,'\n')

def probar_Tinv(ts,ntrns,marca_log):
        sigma = []
        for i in range(ntrns):
                sigma.append([ts.count(i)])

        for ind,val in enumerate(sigma):
                print('Sigma[', ind, '] : ', val)
        print()
        sigma = numpy.matrix(sigma)

        with open(PETRI_FILE) as fd :
                jobs = json.loads(fd.read())
                matI = numpy.matrix(jobs['petriNet']['incidence_matrix'])
                Mi   = numpy.matrix(jobs['petriNet']['init_marking'])
                Mf   = numpy.transpose((matI * sigma)) + Mi
                Mlog = numpy.matrix(marca_log)
                print('\nMarcado final atravez de invariantes : \n\n',Mf,'\n')
                print('\nMarcado final del log :\n\n', Mlog, '\n')
                print('\nComparacion : ', Mlog == Mf)


if __name__ == '__main__':
        main()

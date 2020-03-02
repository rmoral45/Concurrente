
from pdb import set_trace as bp
import re
import json
import numpy
import copy
#LOG_FILE = '../finalTest2_prioBajada.log'
LOG_FILE = '../finalTest2_prioSubida.log'
#LOG_FILE = '../finalTest2.log'
#PETRI_FILE = '/home/ramiro/repos/Concurrente/src/petriNet/red_final_prio_bajada.json'
PETRI_FILE = '/home/ramiro/repos/Concurrente/src/petriNet/red_final_prio_subida.json'
#PETRI_FILE = '/home/ramiro/repos/Concurrente/src/petriNet/red_final.json'
N_TRANSICIONES = 16
T_CODING = ['T0;','T1;','T2;','T3;','T4;','T5;','T6;','T7;','T8;','T9;','T10;','T11;','T12;','T13;','T14;','T15;'] 
def main():
         with open(LOG_FILE) as fd:
                raw = fd.read()
                raw = re.findall('(?:INFO).*',raw)
                raw = list(map(lambda x : x.replace('INFO: ', ''), raw))
                jslist = []
                for item in raw :
                        jslist.append(json.loads(item))

                '''
                    genero lista de disparos y marcados
                '''
                transiciones = []
                marcados = []
                for js in jslist :
                        transiciones.append(js['disparo'])
                        marcados.append(js['marcado'])

                '''
                    Saco Info de todos los disparos
                '''
                print('\nInfo sobre totalidad de disparos\n')
                for i in T_CODING :
                        if transiciones.count(i) < 1:
                                print('La transicion : ', i[:-1], ' no se disparo nunca\n')

                for i in T_CODING :
                        print('La transicion : ', i[:-1], ' se disparo ', transiciones.count(i))
                

                regex_autos    =  re.compile(\
                                    "(?P<TA>(T13;){1})" + \
                                    "(?P<SA>((?!T13;)(?!T12;).)*)" + \
                                    "(?P<TB>T12;{1})" + \
                                    "(?P<SB>((?!T12;)(?!T14;).)*)" + \
                                    "(?P<TC>T14;{1})",\
                                    re.MULTILINE)

                subst_autos = '--\\g<SA>--\\g<SB>--'

                regex_tren_0    =  re.compile(\
                                        "(?P<TA>(T7;){1})" + \
                                        "(?P<SA>((?!T7;)(?!T0;)(?!T1;).)*)" + \
                                        "(?P<TB>(T0;){1})" + \
                                        "(?P<SB>((?!T0;)(?!T2;).)*)" + \
                                        "(?P<TC>(T2;){1})" + \
                                        "(?P<SC>((?!T2;)(?!T3;).)*)" + \
                                        "(?P<TD>(T3;){1})" + \
                                        "(?P<SD>((?!T3;)(?!T4;).)*)" + \
                                        "(?P<TE>(T4;){1})" + \
                                        "(?P<SE>((?!T4;)(?!T15;).)*)" + \
                                        "(?P<TF>(T15;){1})" + \
                                        "(?P<SF>((?!T15;)(?!T5;).)*)" + \
                                        "(?P<TG>(T5;){1})" + \
                                        "(?P<SG>((?!T5;)(?!T6;).)*)" + \
                                        "(?P<TH>(T6;){1})" , \
                                        re.MULTILINE) 

                subst_tren_0 = '--\\g<SA>--\\g<SB>--\\g<SC>--\\g<SD>--\\g<SE>--\\g<SF>--\\g<SG>---'

                regex_tren_1    =  re.compile(\
                                        "(?P<TA>(T7;){1})" + \
                                        "(?P<SA>((?!T7;)(?!T1;)(?!T2;).)*)" + \
                                        "(?P<TB>(T1;){1})" + \
                                        "(?P<SB>((?!T1;)(?!T2;).)*)" + \
                                        "(?P<TC>(T2;){1})" + \
                                        "(?P<SC>((?!T2;)(?!T3;).)*)" + \
                                        "(?P<TD>(T3;){1})" + \
                                        "(?P<SD>((?!T3;)(?!T4;).)*)" + \
                                        "(?P<TE>(T4;){1})" + \
                                        "(?P<SE>((?!T4;)(?!T15;).)*)" + \
                                        "(?P<TF>(T15;){1})" + \
                                        "(?P<SF>((?!T15;)(?!T5;).)*)" + \
                                        "(?P<TG>(T5;){1})" + \
                                        "(?P<SG>((?!T5;)(?!T6;).)*)" + \
                                        "(?P<TH>(T6;){1})" , \
                                        re.MULTILINE) 

                subst_tren_1 = '--\\g<SA>--\\g<SB>--\\g<SC>--\\g<SD>--\\g<SE>--\\g<SF>--\\g<SG>---'

                regex_gente    =  re.compile(\
                                        "(?P<TA>(T8;){1})" + \
                                        "(?P<SA>((?!T8;).)*)" + \
                                        "(?P<TB>T10;{1})" + \
                                        "(?P<SB>((?!T10;).)*)" + \
                                        "(?P<TC>T11;{1})" + \
                                        "(?P<SC>((?!T11;).)*)" +\
                                        "(?P<TD>T9;{1})",\
                                        re.MULTILINE)
                subst_gente = '--\\g<SA>--\\g<SB>--\\g<SC>--'
                result = ''.join(transiciones)

                while  regex_autos.search(result)  or \
                       regex_tren_1.search(result) or \
                       regex_tren_0.search(result) or \
                       regex_gente.search(result)  :

                        result =  regex_autos.sub ( subst_autos , result, 1 )
                        result =  regex_tren_0.sub( subst_tren_0, result, 1 )
                        result =  regex_tren_1.sub( subst_tren_1, result, 1 )
                        result =  regex_gente.sub ( subst_gente , result, 1 )

                print('\nEliminacion de Invariantes lista !!!! \n')
                '''
                    Formateo lo restante para poder transformar a entero
                '''
                result = re.sub('-','',result,0)
                result = re.sub(';','',result,0)
                result = result.split('T')[1:]
                result = list(map(lambda x : int(x,10),result))

 
                probar_Tinv(result, N_TRANSICIONES, marcados[-1])


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
                print('\nComparacion : ', Mlog == Mf,'\n\n')


if __name__ == '__main__':
        main()

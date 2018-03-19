/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladorso;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.DefaultListModel;

/**
 *
 * @author Marcos
 */
public class Escalonamento {
    
    private List <Processo> listaProcessos = new ArrayList<>();
    private DefaultListModel listaexecutar = new DefaultListModel();
    private final PrintStream p = System.out;  

    public Escalonamento() {
    }

    
    public List<Processo> getListaProcessos() {
        return listaProcessos;
    }

    public void setListaProcessos(List<Processo> listaProcessos) {
        this.listaProcessos = listaProcessos;
    }

    public DefaultListModel getListaexecutar() {
        return listaexecutar;
    }

    public void setListaexecutar(DefaultListModel listaexecutar) {
        this.listaexecutar = listaexecutar;
    }
    
    

    

    
    /**
     * Round Robin cada processo recebe um quantum do sistema (100), se depois de acabar este quantum,
     * o processo ainda estiver executando, ele sofre preempção e a CPU é entregue à outro processo;
     * O processo que não terminou ainda, vai ao final da fila. 
     * @return 
     */
    public DefaultListModel RoundRobin(){

        int quantumSistema = 100;
        DefaultListModel lista = new DefaultListModel();
        
        while(!listaProcessos.isEmpty()){
            for(int i = 0; i < listaProcessos.size(); i++){
                
                Executando("\nProcesso: " + listaProcessos.get(i).getId() + " Quantum: " + listaProcessos.get(i).getQuantum() + "\n");

                if(listaProcessos.get(i).getQuantum() > quantumSistema){
                    listaProcessos.get(i).setQuantum(listaProcessos.get(i).getQuantum() - quantumSistema);

                    Executando("Processo " + listaProcessos.get(i).getId() + " não finalizado, quantum: " +
                            listaProcessos.get(i).getQuantum());
                }
                else{
                    lista.addElement("Processo " + listaProcessos.get(i).getId());
                    Executando("Processo terminado: ID " + listaProcessos.get(i).getId());
                    
                    listaProcessos.remove(i);
                }

            }
        }
        
        Executando("      ");
        return lista;
  
    }
    
    /**
     * Prioridades escolhe o processo de maior prioridade para ser executado, porém para ser justo,
     * a cada tick, a prioridade deste processo é decrementada para que processos de prioridade menor
     * tenham oportunidade de executar; A cada tick, neste código, é verificado se o quantum que o processo
     * necessita para terminar sua execução ainda é maior que o quantum do sistema; A cada verificação
     * dessa, a prioridade do processo escolhido é diminuida em 1. 
     * @return 
     */
    
    public DefaultListModel Prioridades(){
        
        DefaultListModel lista = new DefaultListModel();
        int quantumSistema = 100;
        
        while(!listaProcessos.isEmpty()){
            
            Processo escolhido = listaProcessos.get(0);

            for(int i = 0; i < listaProcessos.size(); i++){
                if(escolhido.getPrioridade() < listaProcessos.get(i).getPrioridade()){
                    escolhido = listaProcessos.get(i);      
                }
            }
            
            Executando("\nProcesso escolhido: " + escolhido.getId() + " Prior.: " + escolhido.getPrioridade() + 
                    " Quantum: " + escolhido.getQuantum());
            
            if(escolhido.getQuantum() > quantumSistema){
                if(escolhido.getPrioridade() == 0){
                   escolhido.setQuantum(escolhido.getQuantum() - quantumSistema); 
                   Executando("Processo "+ escolhido.getId() + " executando, quantum: " + escolhido.getQuantum()
                   + " prioridade: " + escolhido.getPrioridade());
                }else{
                    escolhido.setPrioridade(escolhido.getPrioridade() - 1);
                    escolhido.setQuantum(escolhido.getQuantum() - quantumSistema);
                    Executando("Processo "+ escolhido.getId() + " executando, quantum: " + escolhido.getQuantum()
                   + " prioridade: " + escolhido.getPrioridade());
                }
            }
            
            else{

                
                lista.addElement("Processo " + escolhido.getId() + " - Prioridade: " + escolhido.getPrioridade());
                Executando("\nTerminado o Processo " + escolhido.getId() + " - Prioridade: " + 
                        escolhido.getPrioridade() + "\n");
                listaProcessos.remove(escolhido);
            }
            
        }
        
        Executando("      ");
        return lista;
         
    }
    
    /**
     * Prioridades Dinâmicas atribui uma prioridade à um processo fazendo a divisão do quantum
     * dado pelo sistema pelo quantum do processo; Processos com quantums maiores, terão menor
     * prioridade, enquanto processos com quantums menores, terão maior prioridade;
     * Os processos com maior prioridade serão executados primeiro.
     * @return 
     */
    
    public DefaultListModel PrioridadesDinamicas(){
        
        DefaultListModel lista = new DefaultListModel();
        int quantumSistema = 1000;
        
        for(int i = 0; i < listaProcessos.size(); i++){
           int denominador = listaProcessos.get(i).getQuantum();
           int prioridade = quantumSistema / denominador;
           listaProcessos.get(i).setPrioridade(prioridade);  
        }

        while(!listaProcessos.isEmpty()){
            Processo escolhido = listaProcessos.get(0);
            
            for(int i = 0; i < listaProcessos.size(); i++){
                if(escolhido.getPrioridade() < listaProcessos.get(i).getPrioridade()){
                    escolhido = listaProcessos.get(i);
                }
            }

            
            Executando("\nExecutando: Processo " + escolhido.getId() + " - Prioridade: " + 
                    escolhido.getPrioridade() + "");
            lista.addElement("Processo " + escolhido.getId() + " - Prioridade: " + escolhido.getPrioridade());
            listaProcessos.remove(escolhido);
        }
        
        Executando("      ");
        return lista;
    }
    
    
    /**
     * Método que será usado pelos outros métodos para mostrar a execução passo a passo das técnicas 
     * de escalonamento na tela. 
     * @param mensagem 
     */
    public void Executando(String mensagem){
        
        listaexecutar.addElement(mensagem); 
    }
    
    
    
    
    
    /*public void Loteria(){
        
        Random random = new Random();
        int k = 10;
        for(int i = 0; i < listaProcessos.size(); i++){
            listaProcessos.get(i).setTicket(k);
            k++;
        }
        
        for(int j = 0; j < listaProcessos.size(); j++){
            int r = random.nextInt(k);
            r =+ 10;        
        }
        
    }*/
            
}



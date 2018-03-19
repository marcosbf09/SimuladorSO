/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladorso;

/**
 *
 * @author Marcos
 */
public class Processo {
    
    private String id;
    private int prioridade;
    private String tipo;
    private int quantum;
    private String estado;
    private int ticket;

    public Processo(String id, int prioridade, String tipo, int quantum, String estado) {
        this.id = id;
        this.prioridade = prioridade;
        this.tipo = tipo;
        this.quantum = quantum;
        this.estado = estado;
    }

    public Processo() {
    }
    
 

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }
    
    
    
    
}

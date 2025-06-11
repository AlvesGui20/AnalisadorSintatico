/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analises;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author valter
 */
public class TabelaReservadas {
    Map<Integer, String> tabela = new HashMap<Integer, String>();
    
    public TabelaReservadas() {
        tabela.put(1, "program");
        tabela.put(2, "uses");
        tabela.put(3, "begin");
        tabela.put(4, "end");
        //Todo: Adicionar demais palavras reservadas
    }
    
    public Boolean estaContido(String valor) {
        return tabela.containsValue(valor);
    }
    
}

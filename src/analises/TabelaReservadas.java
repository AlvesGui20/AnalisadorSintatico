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
        tabela.put(5, "const");
        tabela.put(6, "var");
        tabela.put(7, "integer");
        tabela.put(8, "boolean");
        tabela.put(9, "real");
        tabela.put(10, "string");
        tabela.put(11, "char");
        tabela.put(12, "array");
        tabela.put(13, "of");
        tabela.put(14, "write");
        tabela.put(15, "writln");
        tabela.put(16, "read");
        tabela.put(17, "readln");
        
        //Todo: Adicionar demais palavras reservadas
    }
    
    public Boolean estaContido(String valor) {
        return tabela.containsValue(valor);
    }
    
}

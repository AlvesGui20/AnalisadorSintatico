package analises;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class TabelaIdentificadores {
    private Map<Integer, Map<String, Object>> tabela;
    private int key;

    // Lista separada para armazenar apenas variáveis declaradas na seção var
    private List<String> listaVariaveis;

    public TabelaIdentificadores() {
        this.tabela = new HashMap<>();
        this.key = 1;
        this.listaVariaveis = new ArrayList<>();
    }

    public void adiciona(String nome, String tipo) {
        if (!estaContido(nome)) {
            Map<String, Object> dados = new HashMap<>();
            dados.put("nome", nome);
            dados.put("tipo", tipo);

            tabela.put(key, dados);
            key++;

            // Se for variável (tipo não for programa ou biblioteca), adiciona na lista de
            // variáveis
            if (!tipo.equals("programa") && !tipo.equals("biblioteca") && !tipo.equals("constante")) {
                listaVariaveis.add(nome);
            }
        }
    }

    public boolean estaContido(String nome) {
        for (Map<String, Object> dados : tabela.values()) {
            if (dados.get("nome").equals(nome)) {
                return true;
            }
        }
        return false;
    }

    public void imprimeTabela() {
        for (Map.Entry<Integer, Map<String, Object>> entry : tabela.entrySet()) {
            System.out.println("ID: " + entry.getKey() + " -> " + entry.getValue());
        }
    }

    public String getTipo(String nome) {
        for (Map<String, Object> dados : tabela.values()) {
            if (dados.get("nome").equals(nome)) {
                return dados.get("tipo").toString();
            }
        }
        return null;
    }

    public boolean isConstante(String nome) {
        for (Map<String, Object> dados : tabela.values()) {
            if (dados.get("nome").equals(nome) && "constante".equals(dados.get("tipo"))) {
                return true;
            }
        }
        return false;
    }

    // Método para obter a lista de variáveis declaradas
    public List<String> getListaVariaveis() {
        return listaVariaveis;
    }
}

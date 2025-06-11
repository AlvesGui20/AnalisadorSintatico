
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analises;

import javax.swing.JOptionPane;

/**
 *
 * @author valter
 */
public class AnaliseSintatica {
    private Token token;
    private AnaliseLexica mt;
    
    public AnaliseSintatica(String codigo) {
        this.token = new Token();
        this.mt = new AnaliseLexica(codigo);
        PROGRAM();
    }
    
    public void PROGRAM() {
        token = mt.geraToken();
        
        if (token.getStr().toString().equals("program")) {
            token = mt.geraToken();
            
            if (token.getTipo() == TiposToken.ID) {
                mt.getTabelaIdentificadores().adiciona(token.getStr().toString()); //Adiciona o token na tabela de símbolos
                token = mt.geraToken();
                
                if (token.getStr().toString().equals(";")) {
                    LIB();
                    CONST();
                    TYPE();
                    VAR();
                    
                    if (token.getStr().toString().equals(";")) 
                        token = mt.geraToken();
                    
                    
                    if (token.getStr().toString().equals("begin")) {
                        token = mt.geraToken();
                        
                        LISTA_COM();
                        
                        if (token.getStr().toString().equals("end")) {
                            token = mt.geraToken();
                            
                            if (token.getStr().toString().equals(".")) {
                                JOptionPane.showMessageDialog(null, "Arquivo compilado com sucesso!!!");
                                return;
                            }
                            else ERRO(".", token.getStr().toString());
                        }
                        else ERRO("end", token.getStr().toString());
                    }
                    else ERRO("begin", token.getStr().toString());
                }
                else ERRO(";", token.getStr().toString());
            }
            else ERRO("identificador", token.getStr().toString());
        }
        else ERRO("program", token.getStr().toString());
    }
    
    public void LIB() {
        token = mt.geraToken();
        
        if (token.getStr().toString().equals("uses")){
            token = mt.geraToken();
            
            if (token.getTipo() == TiposToken.ID) {
                LISTA_ID();
                
                if (token.getStr().toString().equals(";"))
                    return;
                else ERRO(";", token.getStr().toString());
            }
            else ERRO("identificador", token.getStr().toString());
        }
        else return; //λ
        
    }
    
    public void LISTA_ID() {
        token = mt.geraToken();
        if (token.getStr().toString().equals(",")){
            token = mt.geraToken();
            if (token.getTipo() == TiposToken.ID){
                LISTA_ID();
            }
            else ERRO("identificador", token.getStr().toString());
        }
        else return; //λ
    }
    
    public void CONST() {
        if (token.getStr().toString().equals(";")){
            token = mt.geraToken();
        }
        
        if (token.getStr().toString().equals("const")) {
            DEC_CONST();
        }
        else return; //λ
    }
    
    public void DEC_CONST() {
        token = mt.geraToken();
        
        if (token.getTipo() == TiposToken.ID) {
            token = mt.geraToken();
            
            if (token.getStr().toString().equals("=")) {
                token = mt.geraToken();
                
                if (token.getTipo() == TiposToken.CTE) {
                    token = mt.geraToken();
                    
                    if (token.getStr().toString().equals(";")) {
                        token = mt.geraToken();
                        DEC_CONST2();
                    }
                    else ERRO(";", token.getStr().toString());
                }
                else ERRO("constante", token.getStr().toString());
            }
            else ERRO("=", token.getStr().toString());
        }
        else ERRO("identificador", token.getStr().toString());
    }
    
    public void DEC_CONST2() {
        if (token.getTipo() == TiposToken.ID) {
            DEC_CONST();
        }
        return; //λ
    }
    
    public void TYPE() {
        return; //λ
    }
    
    public void VAR() {
        return; //λ
    }
    
    public void LISTA_COM() {
        return;
    }
    
    public void ERRO(String esperado, String obtido) {
        JOptionPane.showMessageDialog(null, "Ocorreu um erro na análise! Era esperado " + esperado + " e foi obtido " + obtido);
        return;
    }
}

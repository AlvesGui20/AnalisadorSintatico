/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analises;

/**
 *
 * @author valter
 */
public class AnaliseLexica {
    private String s;
    private Token token;
    private TabelaReservadas reservadas;
    private TabelaIdentificadores identificadores;
    private int i;
    private char c;
    
    public AnaliseLexica(String s) {
        this.s = s;
        this.i = 0;
        c = cabecote();
        reservadas = new TabelaReservadas();
        identificadores = new TabelaIdentificadores();
        i++;
    }
    
    public Token geraToken() {
        token = new Token();
        
        while ((c == ' ') || (c == '\n') || (c == '\t')) {
            c = cabecote();
            i++;
        }
        
        if (Character.isLetter(c)) { //Verifica tokens que inicial com letra
            while (Character.isLetterOrDigit(c) || (c == '_')) {
                token.add(c);
                c = cabecote();
                i++;
            }
            
            if (reservadas.estaContido(token.getStr().toString())) //identifica se o token é palavra reservada
                token.setTipo(TiposToken.PALAVRA_RESERVADA);
            else if (token.getStr().toString().equals("true") || token.getStr().toString().equals("false")) //identifica se o token é constante boolean
                token.setTipo(TiposToken.CTE);
            else token.setTipo(TiposToken.ID); //Caso contrário o token é um identificador
            
            return token;
        }
        else if ((Character.isDigit(c)) || (c == '-')) { //Constantes numéticas
            if (c == '-') { //verifica se é negativa
                token.add(c);
                c = cabecote();
                i++;
            }
            
            while (Character.isDigit(c)) {
                token.add(c);
                c = cabecote();
                i++;
            }
            
            if (c == '.') { //verifica se é real
                token.add(c);
                c = cabecote();
                i++;
                
                if (Character.isDigit(c)) { //verifica se há dígito depois do .
                    token.add(c);
                    c = cabecote();
                    i++;
                    
                    while (Character.isDigit(c)) {
                        token.add(c);
                        c = cabecote();
                        i++;
                    }
                    
                }
                else {
                    token.setTipo(TiposToken.ERRO); //retorna erro se não conter dígito depois do .
                    return token;
                }
            }
            
            token.setTipo(TiposToken.CTE);
            
            return token;
        }
        else if (c == '\'') { //Verifica constantes string ou char
            token.add(c);
            c = cabecote();
            i++;
            while ((c != '\'') && (i <= s.length())) {
                token.add(c);
                c = cabecote();
                i++;
            }
            
            token.add(c);
            c = cabecote();
            i++;
            token.setTipo(TiposToken.CTE);
            
            return token;
        }
        else if (c == ':') { //verifica :
            token.add(c);
            c = cabecote();
            i++;
            token.setTipo(TiposToken.PONTUACAO);
            
            return token;
        }
        else if (c == ';') { //verifica ;
            token.add(c);
            c = cabecote();
            i++;
            token.setTipo(TiposToken.PONTUACAO);
            
            return token;
        }
        else if (c == '.') { //verifica .
            token.add(c);
            c = cabecote();
            i++;
            token.setTipo(TiposToken.PONTUACAO);
            
            return token;
        }
        else if (c == ',') { //verifica ,
            token.add(c);
            c = cabecote();
            i++;
            token.setTipo(TiposToken.PONTUACAO);
            
            return token;
        }
         else if (c == '=') { //verifica ,
            token.add(c);
            c = cabecote();
            i++;
            token.setTipo(TiposToken.PONTUACAO);
            
            return token;
        }// Todo: adicionar outros símbolos
        else {
            token.add(c);
            System.out.println("C: " + c);
            token.setTipo(TiposToken.ERRO);
            
            return token;
        }
    }
    
    public char cabecote() {
        if ((i < s.length()) && (s.length() > 0)) {
            return s.charAt(i);
        }
        else return '?';
    }
    
    public TabelaIdentificadores getTabelaIdentificadores() {
        return this.identificadores;
    }
}

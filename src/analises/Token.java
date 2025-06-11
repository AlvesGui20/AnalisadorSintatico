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

public class Token {
    private StringBuffer str;
    private int tipo;
    
    public Token(StringBuffer str, int tipo) {
        this.str = str;
        this.tipo = tipo;
    }
    
    public Token() {
        this.str = new StringBuffer();
    }

    public StringBuffer getStr() {
        return str;
    }

    public void setStr(StringBuffer str) {
        this.str = str;
    }
    
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    public int getTipo() {
        return this.tipo;
    }
    
    public void add(char s) {
        this.str.append(s);
    }
}

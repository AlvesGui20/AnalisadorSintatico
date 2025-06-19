package analises;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class AnaliseSintatica {
    private Token token;
    private AnaliseLexica mt;
    private TabelaIdentificadores ti;
    private TabelaReservadas tr;

    public AnaliseSintatica(String codigo) {
        this.token = new Token();
        this.mt = new AnaliseLexica(codigo);
        this.ti = new TabelaIdentificadores();
        this.tr = new TabelaReservadas();
        PROGRAM();
        AnaliseSintatica.houveErroCompilacao = false;
    }

    public static boolean houveErroCompilacao = false;

    public void PROGRAM() {
        token = mt.geraToken();

        if (token.getStr().toString().equals("program")) {
            token = mt.geraToken();

            if (token.getTipo() == TiposToken.ID) {
                ti.adiciona(token.getStr().toString(), "programa");
                token = mt.geraToken();

                if (token.getStr().toString().equals(";")) {
                    LIB();
                    CONST();
                    VAR();

                    if (token.getStr().toString().equals(";"))
                        token = mt.geraToken();

                    if (token.getStr().toString().equals("begin")) {
                        token = mt.geraToken();

                        LISTA_COM();

                        if (token.getStr().toString().equals("end")) {
                            token = mt.geraToken();

                            if (token.getStr().toString().equals(".")) {

                                if (!AnaliseSintatica.houveErroCompilacao) {
                                    JOptionPane.showMessageDialog(null, "Arquivo compilado com sucesso!!!");
                                }
                                return;
                            } else
                                ERRO(".", token.getStr().toString());
                        } else
                            ERRO("end", token.getStr().toString());
                    } else
                        ERRO("begin", token.getStr().toString());
                } else
                    ERRO(";", token.getStr().toString());
            } else
                ERRO("identificador", token.getStr().toString());
        } else
            ERRO("program", token.getStr().toString());
    }

    public void LIB() {
        token = mt.geraToken();

        if (token.getStr().toString().equals("uses")) {
            token = mt.geraToken();

            if (token.getTipo() == TiposToken.ID) {
                LISTA_ID("biblioteca");

                if (token.getStr().toString().equals(";"))
                    return;
                else
                    ERRO(";", token.getStr().toString());
            } else
                ERRO("identificador", token.getStr().toString());
        } else
            return;
    }

    public void LISTA_ID(String type) {
        String nome = token.getStr().toString();

        if (ti.estaContido(nome)) {
            ERRO("novo identificador ", nome);
            return;
        }
        if (tr.estaContido(nome.toLowerCase())) {
            ERRO("novo identificador (palavra reservada)", nome);
            return;
        }

        ti.adiciona(nome, type);
        token = mt.geraToken();

        if (token.getStr().toString().equals(",")) {
            token = mt.geraToken();

            if (token.getTipo() == TiposToken.ID) {
                LISTA_ID(type);
            } else
                ERRO("identificador", token.getStr().toString());
        } else
            return;
    }

    public void CONST() {
        if (token.getStr().toString().equals(";")) {
            token = mt.geraToken();
        }

        if (token.getStr().toString().equals("const")) {
            token = mt.geraToken();
            DEC_CONST();
        } else
            return;
    }

    public void DEC_CONST() {
        if (token.getTipo() == TiposToken.ID) {

            if (ti.estaContido(token.getStr().toString())) {
                ERRO("novo identificador (já declarado)", token.getStr().toString());
                return;
            }
            if (tr.estaContido(token.getStr().toString())) {
                ERRO("novo identificador (palavra reservada)", token.getStr().toString());
                return;
            }

            ti.adiciona(token.getStr().toString(), "constante");
            token = mt.geraToken();

            if (token.getStr().toString().equals("=")) {
                token = mt.geraToken();

                if (token.getTipo() == TiposToken.CTE) {
                    token = mt.geraToken();

                    if (token.getStr().toString().equals(";")) {
                        token = mt.geraToken();
                        DEC_CONST2();
                    } else
                        ERRO(";", token.getStr().toString());
                } else
                    ERRO("constante", token.getStr().toString());
            } else
                ERRO("=", token.getStr().toString());
        } else
            ERRO("identificador", token.getStr().toString());
    }

    public void DEC_CONST2() {
        if (token.getTipo() == TiposToken.ID) {
            DEC_CONST();
        }
        return;
    }

    public void VAR() {
        if (token.getStr().toString().equals("var")) {
            token = mt.geraToken();
            DEC_VAR();
        } else
            return;
    }

    public void DEC_VAR() {

        if (token.getTipo() == TiposToken.ID) {
            LISTA_ID("var");

            if (token.getStr().toString().equals(":")) {

                token = mt.geraToken();

                DEC_VAR2();

            } else
                ERRO(":", token.getStr().toString());

        } else
            ERRO("Identificador", token.getStr().toString());
    }

    public void DEC_VAR2() {

        if (token.getStr().toString().equals("^")) {
            token = mt.geraToken();
        }

        DEC_VAR3();

    }

    public void DEC_VAR3() {

        if (!token.getStr().toString().equals("array")) {

            TIPO();

            token = mt.geraToken();

            if (token.getStr().toString().equals(";")) {

                token = mt.geraToken();

                DEC_VAR4();

            } else
                ERRO(";", token.getStr().toString());

        } else {
            token = mt.geraToken();

            if (token.getStr().toString().equals("[")) {
                token = mt.geraToken();

                INTERVALO();

                if (token.getStr().toString().endsWith("]")) {
                    token = mt.geraToken();

                    if (token.getStr().toString().equals(";")) {
                        token = mt.geraToken();
                        DEC_VAR4();
                    } else
                        ERRO(";", token.getStr().toString());
                } else
                    ERRO("]", token.getStr().toString());

            } else
                ERRO("[", token.getStr().toString());
        }

    }

    public void DEC_VAR4() {

        if (token.getTipo() == TiposToken.ID) {
            DEC_VAR();
        } else
            return;

    }

    public void INTERVALO() {

        if ((token.getTipo() == TiposToken.CTE)
                || (token.getTipo() == TiposToken.ID && ti.isConstante(token.getStr().toString()))) {
            token = mt.geraToken();

            if (token.getStr().toString().equals("..")) {
                token = mt.geraToken();

                if ((token.getTipo() == TiposToken.CTE)
                        || (token.getTipo() == TiposToken.ID && ti.isConstante(token.getStr().toString()))) {
                    INTERVALO2();
                } else
                    ERRO("constante", token.getStr().toString());
            } else
                ERRO("..", token.getStr().toString());
        } else
            ERRO("constante", token.getStr().toString());

    }

    public void INTERVALO2() {
        token = mt.geraToken();

        if (token.getStr().toString().equals(",")) {
            token = mt.geraToken();

            if ((token.getTipo() == TiposToken.CTE)
                    || (token.getTipo() == TiposToken.ID && ti.isConstante(token.getStr().toString()))) {
                token = mt.geraToken();

                if (token.getStr().toString().equals("..")) {
                    token = mt.geraToken();

                    if ((token.getTipo() == TiposToken.CTE)
                            || (token.getTipo() == TiposToken.ID && ti.isConstante(token.getStr().toString()))) {
                        INTERVALO2();
                    } else
                        ERRO("constante", token.getStr().toString());
                } else
                    ERRO("intervalo", token.getStr().toString());
            } else
                ERRO("constante", token.getStr().toString());
        } else
            return;
    }

    public void TIPO() {

        if (token.getStr().toString().equals("integer")) {
            return;
        } else if (token.getStr().toString().equals("real")) {
            return;
        } else if (token.getStr().toString().equals("boolean")) {
            return;
        } else if (token.getStr().toString().equals("string")) {
            return;
        } else if (token.getStr().toString().equals("char")) {
            return;
        } else
            ERRO("tipo", token.getStr().toString());
    }

    public void LISTA_COM() {
        while (token.getTipo() == TiposToken.ID || token.getStr().toString().equals("readln")
                || token.getStr().toString().equals("writeln") || token.getStr().toString().equals("read")
                || token.getStr().toString().equals("write")) {
            COMANDO();
        }
    }

    public void COMANDO() {
        if (token.getTipo() == TiposToken.ID) {
            ATRIB();
        } else if (token.getStr().toString().equals("readln") || token.getStr().toString().equals("read")) {
            ENTRADA();
        } else if (token.getStr().toString().equals("writeln") || token.getStr().toString().equals("write")) {
            SAIDA();
        } else {
            ERRO("comando válido (ID, readln ou writeln)", token.getStr().toString());
            token = mt.geraToken();
            return;
        }

        if (token.getStr().toString().equals(";")) {
            token = mt.geraToken();
        } else {
            ERRO(";", token.getStr().toString());
            token = mt.geraToken();
        }
    }

    private void SAIDA() {
        if (token.getStr().toString().equals("write")) {
            token = mt.geraToken();

            if (token.getStr().toString().equals("(")) {
                token = mt.geraToken();
                TERMO();
                LISTA_TERMO();

                if (token.getStr().toString().equals(")")) {
                    token = mt.geraToken();
                } else {
                    ERRO(")", token.getStr().toString());
                    token = mt.geraToken();
                }
            } else {
                ERRO("(", token.getStr().toString());
                token = mt.geraToken();
            }
        } else if (token.getStr().toString().equals("writeln")) {
            token = mt.geraToken();

            if (token.getStr().toString().equals("(")) {
                token = mt.geraToken();
                TERMO();
                LISTA_TERMO();

                if (token.getStr().toString().equals(")")) {
                    token = mt.geraToken();
                } else {
                    ERRO(")", token.getStr().toString());
                    token = mt.geraToken();
                }
            }
        }
    }

    private void ENTRADA() {
        if (token.getStr().toString().equals("read")) {
            token = mt.geraToken();

            if (token.getStr().toString().equals("(")) {
                token = mt.geraToken();
                LISTA_ID("var");

                if (token.getStr().toString().equals(")")) {
                    token = mt.geraToken();
                } else {
                    ERRO(")", token.getStr().toString());
                    token = mt.geraToken();
                }
            } else {
                ERRO("(", token.getStr().toString());
                token = mt.geraToken();
            }
        } else if (token.getStr().toString().equals("readln")) {
            token = mt.geraToken();

            if (token.getStr().toString().equals("(")) {
                token = mt.geraToken();
                TERMO();
                LISTA_TERMO();

                if (token.getStr().toString().equals(")")) {
                    token = mt.geraToken();
                } else {
                    ERRO(")", token.getStr().toString());
                    token = mt.geraToken();
                }
            }

        }
    }

    private void ATRIB() {
        String id = token.getStr().toString();
        boolean erroNoIdEsquerdo = false;

        if (!ti.estaContido(id)) {
            ERRO("variável declarada", id);
            erroNoIdEsquerdo = true;
        } else {
            String tipoId = ti.getTipo(id);
            if (tipoId.equals("programa") || tipoId.equals("biblioteca")) {
                ERRO("identificador válido (não pode ser biblioteca nem programa)", id);
                erroNoIdEsquerdo = true;
            }
        }
        token = mt.geraToken();
        if (!token.getStr().toString().equals(":")) {
            ERRO(":", token.getStr().toString());
        }
        token = mt.geraToken();
        if (!token.getStr().toString().equals("=")) {
            ERRO("=", token.getStr().toString());
        }
        token = mt.geraToken();
        TERMO();

    }

    public void TERMO() {
        if (token.getTipo() == TiposToken.ID) {
            String idDireita = token.getStr().toString();

            if (!ti.estaContido(idDireita)) {
                ERRO("variável declarada", idDireita);
            } else {
                String tipoDireita = ti.getTipo(idDireita);
                if (tipoDireita.equals("programa") || tipoDireita.equals("biblioteca")) {
                    ERRO("Um identificador valido", idDireita);
                }
            }
            token = mt.geraToken();

        } else if (token.getTipo() == TiposToken.CTE) {
            token = mt.geraToken();
        } else {
            ERRO("identificador ou constante", token.getStr().toString());
            token = mt.geraToken();
        }

    }

    public void LISTA_TERMO() {
        if (token.getStr().toString().equals(",")) {
            token = mt.geraToken();
            TERMO();
            LISTA_TERMO();
        } else {
            return;
        }
    }

    public void ERRO(String esperado, String obtido) {
        JOptionPane.showMessageDialog(null,
                "Ocorreu um erro na análise! Era esperado " + esperado + " e foi obtido " + obtido);
        AnaliseSintatica.houveErroCompilacao = true;
    }
}

package banco;

import java.util.ArrayList;
import java.util.List;

import banco.Coluna.Tipo;

public class RelacionamentoNN {
    private Tabela tabelaOrigem, tabelaDestino;
    private List<Coluna> colunas;

    protected RelacionamentoNN(Tabela tabelaOrigem, Tabela tabelaDestino) {
        this.tabelaOrigem = tabelaOrigem;
        this.tabelaDestino = tabelaDestino;
        colunas = new ArrayList<>();
    }

    public Tabela getTabelaOrigem() {
        return tabelaOrigem;
    }

    public void setTabelaOrigem(Tabela tabelaOrigem) {
        this.tabelaOrigem = tabelaOrigem;
    }

    public Tabela getTabelaDestino() {
        return tabelaDestino;
    }

    public void setTabelaDestino(Tabela tabelaDestino) {
        this.tabelaDestino = tabelaDestino;
    }

    public boolean addColuna(String nome, Tipo tipo) {
        Coluna coluna = new Coluna(nome, tipo);
        return colunas.add(coluna);
    }

    public boolean addColuna(String nome, Tipo tipo, boolean notNull, boolean unique, boolean autoIncrement) { // Separar os booleans
        Coluna coluna = new Coluna(nome, tipo, notNull, unique, autoIncrement);
        return colunas.add(coluna);
    }

    public String toScript() {
        int contador;
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE `");
        sb.append(tabelaOrigem.getNome() + "_has_" + tabelaDestino.getNome());
        sb.append("` (\n");
        for (Coluna coluna : tabelaOrigem.getPks()) {
            sb.append("\t`");
            sb.append(tabelaOrigem.getNome());
            sb.append("_");
            sb.append(coluna.getNome());
            sb.append("`");
            sb.append(coluna.tipoToString());
            sb.append(",");
            sb.append("\n");
        }
        for (Coluna coluna : tabelaDestino.getPks()) {
            sb.append("\t`");
            sb.append(tabelaDestino.getNome());
            sb.append("_");
            sb.append(coluna.getNome());
            sb.append("`");
            sb.append(coluna.tipoToString());
            sb.append(",");
            sb.append("\n");
        }
        for (Coluna coluna : colunas) {
            sb.append("\t");
            sb.append(coluna.toScript());
            sb.append(",");
            sb.append("\n");
        }
        sb.append("\t");
        sb.append("PRIMARY KEY (`");
        for (Coluna coluna : tabelaOrigem.getPks()) {
            sb.append(tabelaOrigem.getNome());
            sb.append("_");
            sb.append(coluna.getNome());
            sb.append("`");
            sb.append(",");
        }
        contador = tabelaDestino.getPks().size();
        for (Coluna coluna : tabelaDestino.getPks()) {
            contador--;
            sb.append("`");
            sb.append(tabelaDestino.getNome());
            sb.append("_");
            sb.append(coluna.getNome());
            sb.append("`");
            if (contador != 0) {
                sb.append(",");
            }
        }
        sb.append("),");
        sb.append("\n");
        sb.append("\t");
        sb.append("FOREIGN KEY (");
        contador = tabelaOrigem.getPks().size();
        for (Coluna coluna : tabelaOrigem.getPks()) {
            contador--;
            sb.append("`");
            sb.append(tabelaOrigem.getNome());
            sb.append("_");
            sb.append(coluna.getNome());
            sb.append("`");
            if (contador != 0) {
                sb.append(",");
            }
        }
        sb.append(") REFERENCES `");
        sb.append(tabelaOrigem.getNome());
        sb.append("`(");
        contador = tabelaOrigem.getPks().size();
        for (Coluna coluna : tabelaOrigem.getPks()) {
            contador--;
            sb.append("`");
            sb.append(coluna.getNome());
            sb.append("`");
            if (contador != 0) {
                sb.append(",");
            }
        }
        sb.append("),");
        sb.append("\n");
        sb.append("\t");
        sb.append("FOREIGN KEY (");
        contador = tabelaDestino.getPks().size();
        for (Coluna coluna : tabelaDestino.getPks()) {
            contador--;
            sb.append("`");
            sb.append(tabelaDestino.getNome());
            sb.append("_");
            sb.append(coluna.getNome());
            sb.append("`");
            if (contador != 0) {
                sb.append(",");
            }
        }
        sb.append(") REFERENCES `");
        sb.append(tabelaDestino.getNome());
        sb.append("`(");
        contador = tabelaDestino.getPks().size();
        for (Coluna coluna : tabelaDestino.getPks()) {
            contador--;
            sb.append("`");
            sb.append(coluna.getNome());
            sb.append("`");
            if (contador != 0) {
                sb.append(",");
            }
        }
        sb.append(")");
        sb.append("\n");
        sb.append(");");
        return sb.toString();
    }
}

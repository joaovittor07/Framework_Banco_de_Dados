package banco;

import java.util.ArrayList;
import java.util.List;

public class Tabela {
    private String nome;
    private List<Coluna> colunas;
    private List<Coluna> pks;
    private List<ForeignKey> fks;

    protected Tabela(String nome) {
        this.nome = nome;
        colunas = new ArrayList<>();
        pks = new ArrayList<>();
        fks = new ArrayList<>();
    }

    public Coluna addColuna(String nome, Coluna.Tipo tipo) {
        Coluna coluna = new Coluna(nome, tipo);
        colunas.add(coluna);
        return coluna;
    }

    public Coluna addColuna(String nome, Coluna.Tipo tipo, boolean notNull, boolean unique, boolean autoIncrement) { // Separar booleans
        Coluna coluna = new Coluna(nome, tipo, notNull, unique, autoIncrement);
        colunas.add(coluna);
        return coluna;
    }
    
    public boolean addPrimaryKey(Coluna pk) { // Fazer por texto também, além de por Coluna?
        boolean pertence = false;
        for (Coluna coluna : colunas) {
            if (coluna == pk) {
                pertence = true;
            }
        }
        if (pertence) {
            pks.add(pk);
        }
        return pertence;
    }

    protected boolean addForeignKey(ForeignKey fk) {
        return fks.add(fk);
    }

    public String toScript() {
        int contador;
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE `");
        sb.append(nome);
        sb.append("` (\n");
        contador = colunas.size();
        for (Coluna coluna : colunas) {
            contador--;
            sb.append("\t");
            sb.append(coluna.toScript());
            if (contador != 0 || pks.size() > 0 || fks.size() > 0) {
                sb.append(",");
            }
            sb.append("\n");
        }
        if (pks.size() > 0) {
            sb.append("\t");
            sb.append("PRIMARY KEY (");
            contador = pks.size();
            for (Coluna pk : pks) {
                contador--;
                sb.append("`");
                sb.append(pk.getNome());
                sb.append("`");
                if (contador != 0) {
                    sb.append(",");
                }
            }
            sb.append(")");
            if (fks.size() > 0) {
                sb.append(",");
            }
            sb.append("\n");
        }
        if (fks.size() > 0) {
            contador = fks.size();
            for (ForeignKey fk : fks) {
                contador--;
                sb.append("\t");
                sb.append("FOREIGN KEY (`");
                sb.append(fk.getFk().getNome());
                sb.append("`) REFERENCES `");
                sb.append(fk.getTabela().getNome());
                sb.append("`(`");
                sb.append(fk.getPkTabela().getNome());
                sb.append("`)");
                if (contador != 0) {
                    sb.append(",");
                }
            }
            sb.append("\n");
        }
        sb.append(");");
        return sb.toString();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Coluna> getColunas() {
        return colunas;
    }

    public void setColunas(List<Coluna> colunas) {
        this.colunas = colunas;
    }

    public List<Coluna> getPks() {
        return pks;
    }

    public void setPks(List<Coluna> pks) {
        this.pks = pks;
    }

}

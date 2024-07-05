package banco;

public class ForeignKey {
    private Coluna fk;
    private Tabela tabela;
    private Coluna pkTabela;

    protected ForeignKey(Coluna fk, Tabela tabela, Coluna pkTabela) {
        this.fk = fk;
        this.tabela = tabela;
        this.pkTabela = pkTabela;
    }

    protected Coluna getFk() {
        return fk;
    }
    
    protected void setFk(Coluna fk) {
        this.fk = fk;
    }
    
    protected Tabela getTabela() {
        return tabela;
    }

    protected void setTabela(Tabela tabela) {
        this.tabela = tabela;
    }

    protected Coluna getPkTabela() {
        return pkTabela;
    }

    protected void setPkTabela(Coluna pkTabela) {
        this.pkTabela = pkTabela;
    }

}

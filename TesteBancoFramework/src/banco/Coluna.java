package banco;

// https://www.w3schools.com/mysql/mysql_datatypes.asp
// https://www.w3schools.com/mysql/mysql_constraints.asp

public class Coluna {
    public enum Tipo {
        CHAR,// CHAR(1)
        VARCHAR,// VARCHAR(100)
        TEXT,// TEXT

        BOOL,// BOOL
        INT,// INT
        DECIMAL,// DECIMAL(10,2)

        DATE,// DATE
        TIME,// TIME
        DATETIME,// DATETIME
        YEAR,// YEAR
        TIMESTAMP// TIMESTAMP
	}
    private String nome;
    private Tipo tipo;
    private boolean notNull, unique, autoIncrement;

    protected Coluna(String nome, Tipo tipo) {
        this.nome = nome;
        this.tipo = tipo;
        
        notNull = false;
        unique = false;
        autoIncrement = false;
    }

    protected Coluna(String nome, Tipo tipo, boolean notNull, boolean unique, boolean autoIncrement) { // Separar os booleans
        this.nome = nome;
        this.tipo = tipo;

        this.notNull = notNull;
        this.unique = unique;
        this.autoIncrement = autoIncrement;
    }

    protected String tipoToString() {
        String string = " ";
        switch (tipo) {
            case BOOL:
                string += "BOOL";
                break;
            case CHAR:
                string += "CHAR(1)";
                break;
            case DATE:
                string += "DATE";
                break;
            case DATETIME:
                string += "DATETIME";
                break;
            case DECIMAL:
                string += "DECIMAL(10,2)";
                break;
            case INT:
                string += "INT";
                break;
            case TEXT:
                string += "TEXT";
                break;
            case TIME:
                string += "TIME";
                break;
            case TIMESTAMP:
                string += "TIMESTAMP";
                break;
            case VARCHAR:
                string += "VARCHAR(100)";
                break;
            case YEAR:
                string += "YEAR";
                break;
        }
        return string;
    }

    private String atributosToString() {
        String string = "";
        if (notNull) {
            string += " NOT NULL";
        }
        if (unique) {
            string += " UNIQUE";
        }
        if (autoIncrement) {
            string += " AUTO_INCREMENT";
        }
        return string;
    }

    protected String toScript() {
        StringBuilder sb = new StringBuilder();
        sb.append("`");
        sb.append(nome);
        sb.append("`");
        sb.append(tipoToString());
        sb.append(atributosToString());
        return sb.toString();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

}

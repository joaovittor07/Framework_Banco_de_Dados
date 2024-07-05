package banco;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class Banco {
    private String nome;
    private List<Tabela> tabelas;
    private List<RelacionamentoNN> relacionamentosNN;
    private String ip, porta, usuario, senha;

    public Banco(String nome) {
        this.nome = nome;
        tabelas = new ArrayList<>();
        relacionamentosNN = new ArrayList<>();
    }

    public Tabela createTable(String nome) {
        Tabela tabela = new Tabela(nome);
        tabelas.add(tabela);
        return tabela;
    }

    public boolean newRelacionamento11(Tabela tabelaOrigem, Coluna colunaOrigem, Tabela tabelaDestino, Coluna colunaDestino) {
        boolean existeTabelaOrigem = false;
        boolean existeColunaOrigem = false;
        boolean existeTabelaDestino = false;
        boolean existeColunaDestino = false;

        for (Tabela tabela : tabelas) {
            if (!existeTabelaOrigem && tabela == tabelaOrigem) {
                existeTabelaOrigem = true;
                for (Coluna coluna : tabela.getColunas()) {
                    if (coluna == colunaOrigem) {
                        existeColunaOrigem = true;
                        break;
                    }
                }
            }
            if (!existeTabelaDestino && tabela == tabelaDestino) {
                existeTabelaDestino = true;
                for (Coluna coluna : tabela.getColunas()) {
                    if (coluna == colunaDestino) {
                        existeColunaDestino = true;
                        break;
                    }
                }
            }
            if (existeTabelaOrigem && existeTabelaDestino) {
                break;
            }
        }
        if (existeTabelaOrigem && existeColunaOrigem && existeTabelaDestino && existeColunaDestino && colunaOrigem.isUnique()) { // Precisa ser UNIQUE
            return tabelaOrigem.addForeignKey(new ForeignKey(colunaOrigem, tabelaDestino, colunaDestino));
        } else {
            return false;
        }
    }

    public boolean newRelacionamentoN1(Tabela tabelaOrigem, Coluna colunaOrigem, Tabela tabelaDestino, Coluna colunaDestino) {
        boolean existeTabelaOrigem = false;
        boolean existeColunaOrigem = false;
        boolean existeTabelaDestino = false;
        boolean existeColunaDestino = false;

        for (Tabela tabela : tabelas) {
            if (!existeTabelaOrigem && tabela == tabelaOrigem) {
                existeTabelaOrigem = true;
                for (Coluna coluna : tabela.getColunas()) {
                    if (coluna == colunaOrigem) {
                        existeColunaOrigem = true;
                        break;
                    }
                }
            }
            if (!existeTabelaDestino && tabela == tabelaDestino) {
                existeTabelaDestino = true;
                for (Coluna coluna : tabela.getColunas()) {
                    if (coluna == colunaDestino) {
                        existeColunaDestino = true;
                        break;
                    }
                }
            }
            if (existeTabelaOrigem && existeTabelaDestino) {
                break;
            }
        }
        if (existeTabelaOrigem && existeColunaOrigem && existeTabelaDestino && existeColunaDestino) {
            return tabelaOrigem.addForeignKey(new ForeignKey(colunaOrigem, tabelaDestino, colunaDestino));
        } else {
            return false;
        }
    }

    public RelacionamentoNN newRelacionamentoNN(Tabela tabelaOrigem, Tabela tabelaDestino) {
        boolean existeTabelaOrigem = false;
        boolean existeTabelaDestino = false;
        boolean existePksTabelaOrigem = false;
        boolean existePksTabelaDestino = false;

        for (Tabela tabela : tabelas) {
            if (!existeTabelaOrigem && tabela == tabelaOrigem) {
                existePksTabelaOrigem = !tabela.getPks().isEmpty();
                existeTabelaOrigem = true;
            }
            if (!existeTabelaDestino && tabela == tabelaDestino) {
                existePksTabelaDestino = !tabela.getPks().isEmpty();
                existeTabelaDestino = true;
            }
            if (existeTabelaOrigem && existeTabelaDestino) {
                break;
            }
        }
        if (existeTabelaOrigem && existeTabelaDestino && existePksTabelaOrigem && existePksTabelaDestino) {
            RelacionamentoNN relacionamentoNN = new RelacionamentoNN(tabelaOrigem, tabelaDestino);
            relacionamentosNN.add(relacionamentoNN);
            return relacionamentoNN;
        } else {
            return null;
        }
    }

    public String toScript() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE DATABASE IF NOT EXISTS `");
        sb.append(nome);
        sb.append("`;");
        sb.append("\n");
        sb.append("USE `");
        sb.append(nome);
        sb.append("`;");
        for (Tabela tabela : tabelas) {
            sb.append("\n");
            sb.append(tabela.toScript());
        }
        for (RelacionamentoNN relacionamentoNN : relacionamentosNN) {
            sb.append("\n");
            sb.append(relacionamentoNN.toScript());
        }
        return sb.toString();
    }

    public boolean export(String nomeArquivo) {
        try (FileWriter fileWriter = new FileWriter(nomeArquivo); PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println(toScript());
            System.out.println("Exportado com sucesso");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setConnection(String ip, String porta, String usuario, String senha) {
        this.ip = ip;
        this.porta = porta;
        this.usuario = usuario;
        this.senha = senha;
    }

    public boolean execute(String nomeArquivo) {
        String url = "jdbc:mysql://" + ip + ":" + porta + "/";
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(url, usuario, senha);
            statement = connection.createStatement();

            BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo));
            String create = reader.readLine();
            StringBuilder sqlScript = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sqlScript.append(line);
                sqlScript.append(System.lineSeparator());
            }
            reader.close();

            statement.executeUpdate(create.substring(0, create.length() - 1));

            String[] sqlCommands = sqlScript.toString().split(";");
            for (String sqlCommand : sqlCommands) {
                if (!sqlCommand.trim().isEmpty()) {
                    statement.execute(sqlCommand.trim());
                }
            }

            System.out.println("Script SQL executado com sucesso!");
            return true;
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

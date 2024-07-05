package banco;

public class Main {
    public static void main(String[] args) throws Exception {

        // Criação do banco teste_banco:
        Banco banco = new Banco("teste_banco");

        // ----------------------------------------------------------------------------------------------------

        // Criação da tabela categoria:
        Tabela tabelaCategoria = banco.createTable("categoria");

        // Criação das colunas da tabela categoria:
        Coluna categoriaId = tabelaCategoria.addColuna("id", Coluna.Tipo.INT, false, false, true);
        tabelaCategoria.addColuna("nome", Coluna.Tipo.VARCHAR, true, true, false);

        // Definição da PK da tabela categoria:
        tabelaCategoria.addPrimaryKey(categoriaId);

        // ----------------------------------------------------------------------------------------------------

        // Criação da tabela item:
        Tabela tabelaItem = banco.createTable("item");

        // Criação das colunas da tabela item:
        Coluna itemId = tabelaItem.addColuna("id", Coluna.Tipo.INT, false, false, true);
        //Coluna itemId = tabelaItem.addColuna("id", Coluna.Tipo.INT);
        //Coluna itemId2 = tabelaItem.addColuna("id2", Coluna.Tipo.INT);
        tabelaItem.addColuna("nome", Coluna.Tipo.VARCHAR, true, true, false);
        tabelaItem.addColuna("preco", Coluna.Tipo.DECIMAL, true, false, false);
        tabelaItem.addColuna("descricao", Coluna.Tipo.TEXT);
        Coluna itemCategoriaId = tabelaItem.addColuna("categoria_id", Coluna.Tipo.INT, true, false, false);

        // Definição da PK da tabela item:
        tabelaItem.addPrimaryKey(itemId);
        //tabelaItem.addPrimaryKey(itemId2);

        // Criação do relacionamento muitos para um entre as tabelas item e categoria:
        banco.newRelacionamentoN1(tabelaItem, itemCategoriaId, tabelaCategoria, categoriaId);

        // ----------------------------------------------------------------------------------------------------

        // Criação da tabela usuario:
        Tabela tabelaUsuario = banco.createTable("usuario");

        // Criação das colunas da tabela usuario:
        Coluna usuarioId = tabelaUsuario.addColuna("id", Coluna.Tipo.INT, false, false, true);
        tabelaUsuario.addColuna("nome", Coluna.Tipo.VARCHAR, true, false, false);
        tabelaUsuario.addColuna("email", Coluna.Tipo.VARCHAR);

        // Definição da PK da tabela usuario:
        tabelaUsuario.addPrimaryKey(usuarioId);

        // ----------------------------------------------------------------------------------------------------

        // Criação da tabela endereco:
        Tabela tabelaEndereco = banco.createTable("endereco");

        // Criação das colunas da tabela endereco:
        Coluna enderecoId = tabelaEndereco.addColuna("id", Coluna.Tipo.INT, false, false, true);
        tabelaEndereco.addColuna("cep", Coluna.Tipo.VARCHAR, true, false, false);
        tabelaEndereco.addColuna("rua", Coluna.Tipo.VARCHAR, true, false, false);
        tabelaEndereco.addColuna("numero", Coluna.Tipo.INT, true, false, false);
        Coluna enderecoUsuarioId = tabelaEndereco.addColuna("usuario_id", Coluna.Tipo.INT, true, true, false); // Precisa ser UNIQUE para 1:1

        // Definição da PK da tabela endereco:
        tabelaEndereco.addPrimaryKey(enderecoId);

        // Criação do relacionamento um para um entre as tabelas usuario e endereco:
        banco.newRelacionamento11(tabelaEndereco, enderecoUsuarioId, tabelaUsuario, usuarioId);

        // ----------------------------------------------------------------------------------------------------

        // Criação da tabela carrinho:
        Tabela tabelaCarrinho = banco.createTable("carrinho");

        // Criação das colunas da tabela carrinho:
        Coluna carrinhoId = tabelaCarrinho.addColuna("id", Coluna.Tipo.INT, false, false, true);
        Coluna carrinhoUsuarioId = tabelaCarrinho.addColuna("usuario_id", Coluna.Tipo.INT, true, false, false);

        // Definição da PK da tabela carrinho:
        tabelaCarrinho.addPrimaryKey(carrinhoId);
        
        // Criação do relacionamento muitos para um entre as tabelas carrinho e usuario:
        banco.newRelacionamentoN1(tabelaCarrinho, carrinhoUsuarioId, tabelaUsuario, usuarioId);

        // Criação do relacionamento muitos para muitos entre itens e carrinhos:
        RelacionamentoNN carrinhoHasItem = banco.newRelacionamentoNN(tabelaCarrinho, tabelaItem);

        // Criação da coluna create_timestamp na tabela de relacionamento muitos para muitos entre itens e carrinhos:
        carrinhoHasItem.addColuna("create_timestamp", Coluna.Tipo.TIMESTAMP);

        // ----------------------------------------------------------------------------------------------------

        // Informar ip, porta, usuario e senha para acessar o banco:
        banco.setConnection("127.0.0.1", "3307", "root", "");

        // Script do banco teste_banco:
        System.out.println(banco.toScript());

        // Exportar script do banco teste_banco para o arquivo script.sql:
        banco.export("script.sql");

        // Executar script
        banco.execute("script.sql");

    }
}
/*
 * Fazer:
 *  Documentação
 *  Diagrama de Classes
 */
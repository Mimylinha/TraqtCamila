package helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * Define uma tabela do SQLite.
 */
public class SQLiteTableDefinition {
    private String name;
    private List<SQLiteColumnDefinition> columns = new ArrayList<>();
    /**
     * Obtém o nome da tabela.
     * @return Nome da tabela.
     */
    public String getName() {
        return name;
    }
    /**
     * Atribui o nome da tabela.
     * @param name Nome da tabela.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Obtém a lista de definições de colunas desta tabela.
     * @return Lista de definições de colunas da tabela.
     */
    public List<SQLiteColumnDefinition> getColumnDefinitions() {
        return columns;
    }
    /**
     * Obtém a lista de nomes das colunas desta tabela.
     * @return Lista de nomes das colunas desta tabela.
     */
    public String[] getColumnNames() {
        String[] columnNames = new String[columns.size()];
        for (int i = 0; i < columns.size(); i++) {
            columnNames[i] = columns.get(i).getName();
        }
        return columnNames;
    }
}


package helpers;

/**
 * Contém métodos de auxilio ao uso do SQLite.
 */
public class SQLiteHelpers {
    /**
     * Constroi o comando CREATE TABLE conforme os metadados definidos pelo
     parâmetro table.
     * @param table Um objeto do tipo SQLiteTableDefinition com os metadados da
    tabela para qual se deseja gerar o comando.
     * @return Uma String com o comando CREATE TABLE construído.
     */
    public static String buildCreateStatement(SQLiteTableDefinition table) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        sb.append("CREATE TABLE " + table.getName() + " (\n");
        for (SQLiteColumnDefinition col : table.getColumnDefinitions()) {
            if (!isFirst) {
                sb.append(",\n");
            }
            sb.append("\t" + col.getName() + " " + col.getTypeDescription());
            if (col.isPrimaryKey())
                sb.append(" PRIMARY KEY");
            if (col.isAutoIncrement())
                sb.append(" AUTOINCREMENT");
            isFirst = false;
        }
        sb.append("\n);");
        return sb.toString();
    }
}


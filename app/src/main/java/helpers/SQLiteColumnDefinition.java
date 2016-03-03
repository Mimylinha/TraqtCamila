package helpers;

/**
 * Define uma coluna em uma tabela do SQLite.
 */
public class SQLiteColumnDefinition {
    //
    // -- CAMPOS
    private String name;
    private DataType dataType;
    private int length = 0;
    private boolean isPrimaryKey = false;
    private boolean isAutoIncrement = false;
    //
    // -- CONSTRUTORES
    public SQLiteColumnDefinition(String name, DataType dataType) {
        this.name = name;
        this.dataType = dataType;
    }
    public SQLiteColumnDefinition(String name, DataType dataType, boolean
            isPrimaryKey, boolean isAutoIncrement) {
        this.name = name;
        this.dataType = dataType;
        this.isPrimaryKey = isPrimaryKey;
        this.isAutoIncrement = isAutoIncrement;
    }
    public SQLiteColumnDefinition(String name, DataType dataType, int length) {
        this.name = name;
        this.dataType = dataType;
        this.length = length;
    }
    public SQLiteColumnDefinition(String name, DataType dataType, int length,
                                  boolean isPrimaryKey, boolean isAutoIncrement) {
        this.name = name;
        this.dataType = dataType;
        this.length = length;
        this.isPrimaryKey = isPrimaryKey;
        this.isAutoIncrement = isAutoIncrement;
    }
    //
    // -- MËTODOS DE ACESSO DAS PROPRIEDADES
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public DataType getDataType() {
        return dataType;
    }
    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }
    public void setIsPrimaryKey(boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }
    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }
    public void setIsAutoIncrement(boolean isAutoIncrement) {
        this.isAutoIncrement = isAutoIncrement;
    }
    //
    // -- API PÚBLICA
    /**
     * Obtém a descrição do tipo de dado.
     */
    public String getTypeDescription() {
        String result = "";
        switch (dataType) {
            case Integer:
                result = "INTEGER";
                break;
            case Real:
                result = "REAL";
                break;
            case Text:
                result = "TEXT";
                break;
            case Blob:
                result = "BLOB";
                break;
        }
        if (dataType == DataType.Text && length > 0)
            result += "(" + length + ")";
        return result;
    }
    //
    // -- TIPOS INTERNOS
    public enum DataType {
        Integer,
        Real,
        Text,
        Blob
    }
}


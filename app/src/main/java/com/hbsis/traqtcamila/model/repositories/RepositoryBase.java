package com.hbsis.traqtcamila.model.repositories;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;
import helpers.SQLiteTableDefinition;

import com.hbsis.traqtcamila.model.TraqtDbHelper;
import com.hbsis.traqtcamila.model.entities.EntityBase;

/**
 * Classe base de um repositório de dados.
 * O repositório base assume trabalhar com objetos que herdam da classe "BaseEntity"
 e que tem o campo ID como chave-primária.
 */
public abstract class RepositoryBase<T extends EntityBase> {
    //
    // -- CAMPOS
    protected TraqtDbHelper dbHelper;
    protected SQLiteTableDefinition tableDefinition;
    //
    // -- CONSTRUTORES
    /**
     * Cria uma instância desse repositório.
     * @param dbHelper Objeto dbHelper para manipulação do banco de dados.
     * @param tableDefinition Definição da tabela com a qual esse repositório irá
    trabalhar.
     */
    public RepositoryBase(TraqtDbHelper dbHelper, SQLiteTableDefinition
            tableDefinition) {
        this.dbHelper = dbHelper;
        this.tableDefinition = tableDefinition;
    }
    /**
     * Adiciona um novo registro do objeto ao banco de dados, gerando um novo
     identificador.
     * @param obj Objeto que deseja inserir no banco de dados.
     * @return Identificador do registro inserido ou -1 caso tenha ocorrido alguma
    falha.
     */
    public long insert(T obj) {
        ContentValues contentValues = obj.getContentValues();
        return dbHelper.getWritableDatabase().insert(tableDefinition.getName(),
                null, contentValues);
    }
    /**
     * Atualiza um registro existente no banco de dados.
     * @param obj O objeto representando o registro
     * @return A quantidade de registros afetados por essa operação.
     */
    public long update(T obj) {
        ContentValues contentValues = obj.getContentValues();
        String whereClause = "id = ?";
        String[] whereArgs = { String.valueOf(obj.getId()) };
        return dbHelper.getWritableDatabase()
                .update(tableDefinition.getName(),
                        contentValues,
                        whereClause,
                        whereArgs);
    }
    /**
     * Exclui um registro do banco de dados.
     * @param obj O registro que deseja excluir.
     * @return A quantidade de registros afetados por essa operação.
     */
    public int delete(T obj) {
        String whereClause = "id = ?";
        String[] whereArgs = { String.valueOf(obj.getId()) };
        return dbHelper.getWritableDatabase().delete(tableDefinition.getName(),
                whereClause, whereArgs);
    }
    /**
     * Obtém um Cursor para enumerar todos os regitros dessa entidade.
     * @return Um objeto do tipo Cursor selecionando todos os registros da entidade.
     */
    public Cursor getCursorForAll() {
        return dbHelper.getReadableDatabase().query(tableDefinition.getName(),
                tableDefinition.getColumnNames(),
                null, null, null, null, null);
    }
    /**
     * Obtém um Cursor para enumerar os registros de acordo com os critérios de
     seleção.
     * @param selection Um predicate definindo os critérios de busca.
     * @param selectionArgs Uma lista de valores correspondentes aos critérios de
    busca passados.
     * @return Um objeto do tipo Cursor selecionando os registros de acordo com os
    critérios.
     */
    public Cursor getCursorWhere(String selection, String ... selectionArgs) {
        return dbHelper.getReadableDatabase().query(tableDefinition.getName(),
                tableDefinition.getColumnNames(),
                selection, selectionArgs, null, null, null);
    }
    /**
     * Obtém um registro a partir de seu identificador.
     * @param id O identificador do registro.
     * @return O registro conforme seu identificador ou nulo se não for encontrado.
     */
    public T findById(int id) {
        String selection = "id = ?";
        String[] selectionArgs = { String.valueOf(id) };
        Cursor cursor = getCursorWhere(selection, selectionArgs);
        if (cursor.moveToFirst()) {
            return fromCursor(cursor);
        }
        return null;
    }
    /**
     * Obtém a lista completa de registros dessa entidade.
     * @return Uma lista com todos os registros da entidade.
     */
    public List<T> findAll() {
        List<T> objects = new ArrayList<>();
        Cursor cursor = getCursorForAll();
        while (cursor.moveToNext()) {
            objects.add(fromCursor(cursor));
        }
        return objects;
    }
    /**
     * Obtém a lista de registros de acordo com os critérios passados.
     * @param selection Um Predicate SQLite com os critério de seleção dos
    registros.
     * @param selectionArgs A lista de valores correspondentes aos critérios de
    seleção.
     * @return Uma lista com os registros obedecendo os critérios de busca.
     */
    public List<T> findWhere(String selection, String ... selectionArgs) {
        List<T> objects = new ArrayList<>();
        Cursor cursor = getCursorWhere(selection, selectionArgs);
        while (cursor.moveToNext()) {
            objects.add(fromCursor(cursor));
        }
        return objects;
    }
    //
    // -- MÉTODOS ABSTRATOS
    /**
     * Obtém uma instância do objeto da entidade a partir de um cursor.
     * @param cursor O cursor que contém os dados para serem carregados na nova
    instância.
     * @return Uma nova instância contendo os dados do cursor.
     */
    protected abstract T fromCursor(Cursor cursor);
}

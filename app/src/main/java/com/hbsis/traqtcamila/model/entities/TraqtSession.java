package com.hbsis.traqtcamila.model.entities;

import android.content.ContentValues;
import android.database.Cursor;

import helpers.SQLiteColumnDefinition;
import helpers.SQLiteTableDefinition;

/**
 * Entidade representando uma sessão de uma atividade do Traqt.
 */
public class TraqtSession extends EntityBase {
    //
    // -- CAMPOS
    private int activityId;
    private long startTime;
    private long endTime;
    private long elapsedTime;
    private int totalReptitions;
    //
    // -- MÉTODOS DE ACESSO DAS PROPRIEDADES
    public int getActivityId() {
        return activityId;
    }
    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }
    public long getStartTime() {
        return startTime;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    public long getEndTime() {
        return endTime;
    }
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
    public long getElapsedTime() {
        return elapsedTime;
    }
    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
    public int getTotalReptitions() {
        return totalReptitions;
    }
    public void setTotalReptitions(int totalReptitions) {
        this.totalReptitions = totalReptitions;
    }
    //
    // -- MÉTODOS DE APOIO
    /**
     * Retorna uma instância dessa entidade a partir de um cursor.
     * @param cursor O Cursor do qual serão extraídos os dados.
     * @return Uma instância representando os dados do cursor.
     */
    public static TraqtSession fromCursor(Cursor cursor) {
        TraqtSession session = new TraqtSession();
        session.setId(cursor.getInt(cursor.getColumnIndex(TraqtSession.COLUMN_ID)));
        session.setActivityId(cursor.getInt(cursor.getColumnIndex(TraqtSession.COLUMN_ACTIVITY_ID)));
        session.setStartTime(cursor.getLong(cursor.getColumnIndex(TraqtSession.COLUMN_START_TIME)));
        session.setEndTime(cursor.getLong(cursor.getColumnIndex(TraqtSession.COLUMN_END_TIME)));
        session.setElapsedTime(cursor.getLong(cursor.getColumnIndex(TraqtSession.COLUMN_ELAPSED_TIME)));
        session.setTotalReptitions(cursor.getInt(cursor.getColumnIndex(TraqtSession.COLUMN_TOTAL_REPETITIONS)));
        return  session;
    }
    /**
     * Obtém um objeto do tipo ContentValues com os valores dessa instância.
     * @return Um objeto ContentValues com os valores dessa instância.
     */
    @Override
    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TraqtSession.COLUMN_ACTIVITY_ID, getActivityId());
        contentValues.put(TraqtSession.COLUMN_START_TIME, getStartTime());
        contentValues.put(TraqtSession.COLUMN_END_TIME, getEndTime());
        contentValues.put(TraqtSession.COLUMN_ELAPSED_TIME, getElapsedTime());
        contentValues.put(TraqtSession.COLUMN_TOTAL_REPETITIONS,
                getTotalReptitions());
        return contentValues;
    }
    /**
     * Obtém a definição SQLite dessa tabela.
     * @return Um objeto do tipo SQLiteTableDefinition com os metadados dessa
    entidade.
     */
    public static SQLiteTableDefinition getTableDefinition() {
        SQLiteTableDefinition tableDefinition = new SQLiteTableDefinition();
        tableDefinition.setName(TABLE_NAME);
        tableDefinition.getColumnDefinitions().add(new
                SQLiteColumnDefinition(COLUMN_ID, SQLiteColumnDefinition.DataType.Integer, true,
                true));
        tableDefinition.getColumnDefinitions().add(new
                SQLiteColumnDefinition(COLUMN_ACTIVITY_ID,
                SQLiteColumnDefinition.DataType.Integer));
        tableDefinition.getColumnDefinitions().add(new
                SQLiteColumnDefinition(COLUMN_START_TIME,
                SQLiteColumnDefinition.DataType.Integer));
        tableDefinition.getColumnDefinitions().add(new
                SQLiteColumnDefinition(COLUMN_END_TIME, SQLiteColumnDefinition.DataType.Integer));
        tableDefinition.getColumnDefinitions().add(new
                SQLiteColumnDefinition(COLUMN_ELAPSED_TIME,
                SQLiteColumnDefinition.DataType.Integer));
        tableDefinition.getColumnDefinitions().add(new
                SQLiteColumnDefinition(COLUMN_SESSION_OUTCOME,
                SQLiteColumnDefinition.DataType.Integer));
        tableDefinition.getColumnDefinitions().add(new
                SQLiteColumnDefinition(COLUMN_TOTAL_REPETITIONS,
                SQLiteColumnDefinition.DataType.Integer));
        return tableDefinition;
    }
    //
    // -- CONSTANTES
    public static final String TABLE_NAME = "session";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ACTIVITY_ID = "activityId";
    public static final String COLUMN_START_TIME = "startTime";
    public static final String COLUMN_END_TIME = "endTime";
    public static final String COLUMN_ELAPSED_TIME = "elapsedTime";
    public static final String COLUMN_SESSION_OUTCOME = "sessionOutcome";
    public static final String COLUMN_TOTAL_REPETITIONS = "totalRepetitions";
}


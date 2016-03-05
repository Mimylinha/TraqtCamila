package com.hbsis.traqtcamila.model.entities;

import android.content.ContentValues;
import android.database.Cursor;

import com.hbsis.traqtcamila.model.Category;
import com.hbsis.traqtcamila.model.DataStore;
import com.hbsis.traqtcamila.model.SessionParameters;
import com.hbsis.traqtcamila.model.repositories.SessionRepository;

import helpers.SQLiteColumnDefinition;
import helpers.SQLiteTableDefinition;

/**
 * Entidade representando uma atividade do Traqt.
 */
public class TraqtActivity
        extends EntityBase
        implements SessionParameters {
    //
    // -- CAMPOS
    private String name;
    private String description;
    private int category;
    private boolean enableVibration;
    private boolean remindOnSunday;
    private boolean remindOnMonday;
    private boolean remindOnTuesday;
    private boolean remindOnWednesday;
    private boolean remindOnThursday;
    private boolean remindOnFriday;
    private boolean remindOnSaturday;
    private int reminderHour;
    private int reminderMinute;
    private int repetitions;
    private long timeLimit;
    //
    // -- MÉTODOS DE ACESSO DAS PROPRIEDADES
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getCategory() {
        return category;
    }
    public void setCategory(int category) {
        this.category = category;
    }
    public boolean isEnableVibration() {
        return enableVibration;
    }
    public void setEnableVibration(boolean enableVibration) {
        this.enableVibration = enableVibration;
    }

    public Category getCategoryInfo() {
        if (getCategory() >= Category.values().length)
            return null;
        return Category.values()[getCategory()];
    }

    public boolean isRemindOnSunday() {
        return remindOnSunday;
    }
    public void setRemindOnSunday(boolean remindOnSunday) {
        this.remindOnSunday = remindOnSunday;
    }
    public boolean isRemindOnMonday() {
        return remindOnMonday;
    }
    public void setRemindOnMonday(boolean remindOnMonday) {
        this.remindOnMonday = remindOnMonday;
    }
    public boolean isRemindOnTuesday() {
        return remindOnTuesday;
    }
    public void setRemindOnTuesday(boolean remindOnTuesday) {
        this.remindOnTuesday = remindOnTuesday;
    }
    public boolean isRemindOnWednesday() {
        return remindOnWednesday;
    }
    public void setRemindOnWednesday(boolean remindOnWednesday) {
        this.remindOnWednesday = remindOnWednesday;
    }
    public boolean isRemindOnThursday() {
        return remindOnThursday;
    }
    public void setRemindOnThursday(boolean remindOnThursday) {
        this.remindOnThursday = remindOnThursday;
    }
    public boolean isRemindOnFriday() {
        return remindOnFriday;
    }
    public void setRemindOnFriday(boolean remindOnFriday) {
        this.remindOnFriday = remindOnFriday;
    }
    public boolean isRemindOnSaturday() {
        return remindOnSaturday;
    }
    public void setRemindOnSaturday(boolean remindOnSaturday) {
        this.remindOnSaturday = remindOnSaturday;
    }
    public int getReminderHour() {
        return reminderHour;
    }
    public void setReminderHour(int reminderHour) {
        this.reminderHour = reminderHour;
    }
    public int getReminderMinute() {
        return reminderMinute;
    }
    public void setReminderMinute(int reminderMinute) {
        this.reminderMinute = reminderMinute;
    }
    public int getRepetitions() {
        return repetitions;
    }
    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }
    /**
     * Get the Activity Time Limit in seconds.
     * @return The time limit in seconds.
     */
    public long getTimeLimit() {
        return timeLimit;
    }
    /**
     * Sets the Activity Time Limit in seconds.
     * @param timeLimit The time limit in seconds.
     */
    public void setTimeLimit(long timeLimit) {
        this.timeLimit = timeLimit;
    }
    //
    // -- MÉTODOS DE APOIO
    /**
     * Retorna uma instância dessa entidade a partir de um cursor.
     * @param cursor O Cursor do qual serão extraídos os dados.
     * @return Uma instância representando os dados do cursor.
     */
    public static TraqtActivity fromCursor(Cursor cursor) {
        TraqtActivity act = new TraqtActivity();
        act.setId(cursor.getInt(cursor.getColumnIndex(TraqtActivity.COLUMN_ID)));
        act.setName(cursor.getString(cursor.getColumnIndex(TraqtActivity.COLUMN_NAME)));
        act.setDescription(cursor.getString(cursor.getColumnIndex(TraqtActivity.COLUMN_DESCRIPTION)));
        act.setCategory(cursor.getInt(cursor.getColumnIndex(TraqtActivity.COLUMN_CATEGORY)));
        act.setEnableVibration(cursor.getInt(cursor.getColumnIndex(TraqtActivity.COLUMN_ENABLE_VIBRATIONS)) != 0);
        act.setRemindOnSunday(cursor.getInt(cursor.getColumnIndex(TraqtActivity.COLUMN_REMIND_SUNDAY)) != 0);
        act.setRemindOnMonday(cursor.getInt(cursor.getColumnIndex(TraqtActivity.COLUMN_REMIND_MONDAY)) != 0);
        act.setRemindOnTuesday(cursor.getInt(cursor.getColumnIndex(TraqtActivity.COLUMN_REMIND_TUESDAY)) != 0);
        act.setRemindOnWednesday(cursor.getInt(cursor.getColumnIndex(TraqtActivity.COLUMN_REMIND_WEDNESDAY)) != 0);
        act.setRemindOnThursday(cursor.getInt(cursor.getColumnIndex(TraqtActivity.COLUMN_REMIND_THURSDAY)) != 0);
        act.setRemindOnFriday(cursor.getInt(cursor.getColumnIndex(TraqtActivity.COLUMN_REMIND_FRIDAY)) != 0);
        act.setRemindOnSaturday(cursor.getInt(cursor.getColumnIndex(TraqtActivity.COLUMN_REMIND_SATURDAY)) != 0);
        act.setReminderHour(cursor.getInt(cursor.getColumnIndex(TraqtActivity.COLUMN_REMINDER_HOUR)));
        act.setReminderMinute(cursor.getInt(cursor.getColumnIndex(TraqtActivity.COLUMN_REMINDER_MINUTE)));
        act.setRepetitions(cursor.getInt(cursor.getColumnIndex(TraqtActivity.COLUMN_REPETITIONS)));
        act.setTimeLimit(cursor.getLong(cursor.getColumnIndex(TraqtActivity.COLUMN_TIME_LIMIT)));
        return act;
    }
    /**
     * Obtém um objeto do tipo ContentValues com os valores dessa instância.
     * @return Um objeto ContentValues com os valores dessa instância.
     */
    @Override
    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TraqtActivity.COLUMN_NAME, getName());
        contentValues.put(TraqtActivity.COLUMN_DESCRIPTION, getDescription());
        contentValues.put(TraqtActivity.COLUMN_CATEGORY, getCategory());
        contentValues.put(TraqtActivity.COLUMN_ENABLE_VIBRATIONS,
                isEnableVibration());
        contentValues.put(TraqtActivity.COLUMN_REMIND_SUNDAY,
                isRemindOnSaturday());
        contentValues.put(TraqtActivity.COLUMN_REMIND_MONDAY, isRemindOnMonday());
        contentValues.put(TraqtActivity.COLUMN_REMIND_TUESDAY,
                isRemindOnTuesday());
        contentValues.put(TraqtActivity.COLUMN_REMIND_WEDNESDAY,
                isRemindOnWednesday());
        contentValues.put(TraqtActivity.COLUMN_REMIND_THURSDAY,
                isRemindOnThursday());
        contentValues.put(TraqtActivity.COLUMN_REMIND_FRIDAY, isRemindOnFriday());
        contentValues.put(TraqtActivity.COLUMN_REMIND_SATURDAY,
                isRemindOnSaturday());
        contentValues.put(TraqtActivity.COLUMN_REMINDER_HOUR, getReminderHour());
        contentValues.put(TraqtActivity.COLUMN_REMINDER_MINUTE,
                getReminderMinute());
        contentValues.put(TraqtActivity.COLUMN_REPETITIONS, getRepetitions());
        contentValues.put(TraqtActivity.COLUMN_TIME_LIMIT, getTimeLimit());
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
                SQLiteColumnDefinition(COLUMN_NAME, SQLiteColumnDefinition.DataType.Text));
        tableDefinition.getColumnDefinitions().add(new
                SQLiteColumnDefinition(COLUMN_DESCRIPTION, SQLiteColumnDefinition.DataType.Text));
        tableDefinition.getColumnDefinitions().add(new
                SQLiteColumnDefinition(COLUMN_CATEGORY, SQLiteColumnDefinition.DataType.Integer));
        tableDefinition.getColumnDefinitions().add(new
                SQLiteColumnDefinition(COLUMN_ENABLE_VIBRATIONS,
                SQLiteColumnDefinition.DataType.Integer));
        tableDefinition.getColumnDefinitions().add(new
                SQLiteColumnDefinition(COLUMN_REMIND_SUNDAY,
                SQLiteColumnDefinition.DataType.Integer));
        tableDefinition.getColumnDefinitions().add(new
                SQLiteColumnDefinition(COLUMN_REMIND_MONDAY,
                SQLiteColumnDefinition.DataType.Integer));
        tableDefinition.getColumnDefinitions().add(new
                SQLiteColumnDefinition(COLUMN_REMIND_TUESDAY,
                SQLiteColumnDefinition.DataType.Integer));
        tableDefinition.getColumnDefinitions().add(new
                SQLiteColumnDefinition(COLUMN_REMIND_WEDNESDAY,
                SQLiteColumnDefinition.DataType.Integer));
        tableDefinition.getColumnDefinitions().add(new
                SQLiteColumnDefinition(COLUMN_REMIND_THURSDAY,
                SQLiteColumnDefinition.DataType.Integer));
        tableDefinition.getColumnDefinitions().add(new
                SQLiteColumnDefinition(COLUMN_REMIND_FRIDAY,
                SQLiteColumnDefinition.DataType.Integer));
        tableDefinition.getColumnDefinitions().add(new
                SQLiteColumnDefinition(COLUMN_REMIND_SATURDAY,
                SQLiteColumnDefinition.DataType.Integer));
        tableDefinition.getColumnDefinitions().add(new
                SQLiteColumnDefinition(COLUMN_REMINDER_HOUR,
                SQLiteColumnDefinition.DataType.Integer));
        tableDefinition.getColumnDefinitions().add(new
                SQLiteColumnDefinition(COLUMN_REMINDER_MINUTE,
                SQLiteColumnDefinition.DataType.Integer));
        tableDefinition.getColumnDefinitions().add(new
                SQLiteColumnDefinition(COLUMN_REPETITIONS,
                SQLiteColumnDefinition.DataType.Integer));
        tableDefinition.getColumnDefinitions().add(new
                SQLiteColumnDefinition(COLUMN_TIME_LIMIT,
                SQLiteColumnDefinition.DataType.Integer));
        return tableDefinition;
    }

    //
    // -- IMPLEMENTAÇÃO DA INTERFACE: "SessionParameters"
    @Override
    public void saveSession(long startTime, long endTime, long elapsedTime, int
            totalRepetitions) {
        // Configura um novo objeto com os dados da sessão
        TraqtSession session = new TraqtSession();
        session.setActivityId(getId());
        session.setStartTime(startTime);
        session.setEndTime(endTime);
        session.setElapsedTime(elapsedTime);
        session.setTotalReptitions(totalRepetitions);
        // Salva a nova sessão no repositório
        SessionRepository repository = DataStore.getInstance().getSessionRepository();
        repository.insert(session);
    }
    //
    // -- CONSTANTES
    public static final String TABLE_NAME = "activity";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_ENABLE_VIBRATIONS = "enableVibration";
    public static final String COLUMN_REMIND_SUNDAY = "reminderOnSunday";
    public static final String COLUMN_REMIND_MONDAY = "remindOnMonday";
    public static final String COLUMN_REMIND_TUESDAY = "remindOnTuesday";
    public static final String COLUMN_REMIND_WEDNESDAY = "remindOnWednesday";
    public static final String COLUMN_REMIND_THURSDAY = "remindOnThursday";
    public static final String COLUMN_REMIND_FRIDAY = "remindOnFriday";
    public static final String COLUMN_REMIND_SATURDAY = "remindOnSaturday";
    public static final String COLUMN_REMINDER_HOUR = "reminderHour";
    public static final String COLUMN_REMINDER_MINUTE = "reminderMinute";
    public static final String COLUMN_REPETITIONS = "repetitions";
    public static final String COLUMN_TIME_LIMIT = "timeLimit";
}


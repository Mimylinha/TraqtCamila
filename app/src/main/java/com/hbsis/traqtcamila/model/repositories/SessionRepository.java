package com.hbsis.traqtcamila.model.repositories;

import android.database.Cursor;

import com.hbsis.traqtcamila.model.TraqtDbHelper;
import com.hbsis.traqtcamila.model.entities.TraqtSession;

/**
 * Repositório para entidade de Sessões.
 */
public class SessionRepository extends RepositoryBase<TraqtSession> {
    public SessionRepository(TraqtDbHelper dbHelper) {
        super(dbHelper, TraqtSession.getTableDefinition());
    }
    //
    // -- IMPLEMENTAÇÃO DOS MÉTODOS ABSTRATOS
    @Override
    protected TraqtSession fromCursor(Cursor cursor) {
        return  TraqtSession.fromCursor(cursor);
    }
}

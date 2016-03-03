package com.hbsis.traqtcamila.model.repositories;

import android.database.Cursor;

import com.hbsis.traqtcamila.model.TraqtDbHelper;
import com.hbsis.traqtcamila.model.entities.TraqtActivity;

/**
 * Repositório para entidade de Atividades.
 */
public class ActivityRepository extends RepositoryBase<TraqtActivity> {
    public ActivityRepository(TraqtDbHelper dbHelper) {
        super(dbHelper, TraqtActivity.getTableDefinition());
        // Verifica se deve alimentar a base com dados de testes
        if (findAll().size() == 0) {
            TraqtActivity act = new TraqtActivity();
            act.setName("Abdominais");
            act.setCategory(0);
            act.setEnableVibration(true);
            act.setRepetitions(100);
            insert(act);
            act = new TraqtActivity();
            act.setName("Caminhada");
            act.setCategory(0);
            act.setDescription("Caminhada diária.");
            act.setTimeLimit(1800);
            insert(act);
            act.setName("Estudar para concurso");
            act.setCategory(1);
            act.setDescription("Estudar para o concurso da prefeitura.");
            act.setTimeLimit(3600);
            insert(act);
            act = new TraqtActivity();
            act.setName("Mantras");
            act.setCategory(2);
            act.setDescription("Recitação de mantras.");
            act.setEnableVibration(true);
            act.setRepetitions(108);
            act.setTimeLimit(300);
            insert(act);
            act = new TraqtActivity();
            act.setName("Práticar Leitura Dinâmica");
            act.setCategory(3);
            act.setDescription("Praticar leitura dinâmica e memorização.");
            act.setTimeLimit(900);
            insert(act);
        }
    }
    //
    // -- IMPLEMENTAÇÃO DOS MÉTODOS ABSTRATOS
    @Override
    protected TraqtActivity fromCursor(Cursor cursor) {
        return TraqtActivity.fromCursor(cursor);
    }
}

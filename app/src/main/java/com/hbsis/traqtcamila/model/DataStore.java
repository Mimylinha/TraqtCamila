package com.hbsis.traqtcamila.model;

import android.content.Context;

import com.hbsis.traqtcamila.model.repositories.ActivityRepository;
import com.hbsis.traqtcamila.model.repositories.SessionRepository;
import com.hbsis.traqtcamila.model.repositories.TraqtDbHelper;

/**
 * Um Singleton agrupando os repositórios das entidades do Traqt para facilitar o
 acesso aos dados.
 */
public class DataStore {
    //
    // -- CAMPOS
    Context context;
    TraqtDbHelper dbHelper;
    ActivityRepository activityRepository;
    SessionRepository sessionRepository;
    //
    // -- CONSTRUTORES
    private DataStore(Context context) {
        this.context = context;
        this.dbHelper = new TraqtDbHelper(context);
    }
    static DataStore instance;
    /**
     * Inicializa este Singleton.
     * @param context Um objeto do tipo Context para ser usado pela instância
    compartilhada.
     */
    public static void initialize(Context context) {
        instance = new DataStore(context);
    }
    /**
     * Obtém a instância compartilhada dessa classe.
     * @return A instância atual do DataStore.
     */
    public static DataStore getInstance() {
        return instance;
    }
    //
    // -- PROPRIEDADES
    /**
     * Obtém o repositório de atividades.
     * @return Uma instância do repositório de atividades.
     */
    public ActivityRepository getActivityRepository() {
        if (activityRepository == null)
            activityRepository = new ActivityRepository(dbHelper);
        return activityRepository;
    }
    /**
     * Obtém o repositório de sessões.
     * @return Uma instância do repositório de sessões.
     */
    public SessionRepository getSessionRepository() {
        if (sessionRepository == null)
            sessionRepository = new SessionRepository(dbHelper);
        return sessionRepository;
    }
}


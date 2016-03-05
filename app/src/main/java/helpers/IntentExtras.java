package helpers;

import android.app.Activity;

/**
 * Uma interface para declaração de constantes compartilhadas pelo projeto.
 */
public interface IntentExtras {
    //
    // -- IDENTIFICADORES PARA INTENTS
    /**
     * Identificador do Intent Extra de ID de uma Atividade.
     */
    String ACTIVITY_ID_EXTRA = "activityId";
    /**
     * Identificador do Intent Extra de Horario de Início da Sessão
     */
    String SESSION_START_TIME_EXTRA = "sessionStartTime";
    /**
     * Identificador do Intent Extra de Repetições de uma Sessão
     */
    String SESSION_REPETITIONS_EXTRA = "sessionRepetitions";
    /**
     * Identificador do Intent Extra de Duração de uma Sessão
     */
    String SESSION_DURATION_EXTRA = "sessionDuration";
    //
    // -- REQUEST CODES
    /**
     * Código de Request para o formulário.
     */
    int REQUEST_FORM = 1;
    /**
     * Código de Request para uma Sessão do Traqt.
     */
    int REQUEST_SESSION = 2;
    //
    // -- RESULT CODES
    /**
     * Sinaliza um resultado de inserção de registro.
     */
    int RESULT_INSERTED = Activity.RESULT_FIRST_USER + 1;
    /**
     * Sinaliza um resultado de atualização de registro.
     */
    int RESULT_UPDATED = Activity.RESULT_FIRST_USER + 2;
}

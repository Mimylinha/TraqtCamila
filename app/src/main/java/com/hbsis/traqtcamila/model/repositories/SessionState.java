package com.hbsis.traqtcamila.model.repositories;

/**
 * Enumera os estados possíveis de uma Sessão do Traqt.
 */
public enum SessionState {
    /**
     * Uma sessão recém criada que ainda não foi iniciada.
     */
    New,

    /**
     * Sessão esta em andamento.
     */
    Running,

    /**
     * Sessão esta em pausa.
     */
    Paused,

    /**
     * Sessão foi concluída conforme os parâmetros que o usuário estipulou.
     */
    Completed,

    /**
     * O usuário cancelou a sessão antes de conclui-lá conforme seus parâmetros.
     */
    Cancelled
}

package com.hbsis.traqtcamila.model;

/**
 * Uma interface declarando os parâmetros usados por uma sessão.
 */
public interface SessionParameters {

    /**
     * Obtém a quantidade limite de repetições da sessão.
     * @return O limite estipulado de repetições.
     */
    int getRepetitions();

    /**
     * Obtém o tempo limite da sessão.
     * @return O limite de tempo estipulado para a sessão.
     */
    long getTimeLimit();

    /**
     * Obtém o paramêtro que determina se o dispositivo deve vibrar quando uma repetição for adicionada.
     * @return Verdadeiro se a vibração estiver habilitada.
     */
    boolean isEnableVibration();

    /**
     * Salva uma sessão.
     * @param startTime Representação do horário em que a sessão iniciou.
     * @param endTime Representação do horário em que a sessão foi concluída.
     * @param elapsedTime Tempo transcorrido na sessão.
     * @param totalRepetitions Quantidade total de repetições.
     */
    void saveSession(long startTime, long endTime, long elapsedTime, int
            totalRepetitions);

}

package com.hbsis.traqtcamila.model.repositories;

import android.content.Context;
import android.os.Vibrator;

import com.hbsis.traqtcamila.model.SessionParameters;
import com.hbsis.traqtcamila.model.SessionState;
import com.hbsis.traqtcamila.utils.Chronometer;

/**
 * Gerencia uma Sessão do Traqt.
 */
public class SessionTrack {

    //
    // -- CAMPOS INTERNOS

    private Vibrator vibrator;

    private SessionParameters parameters;
    private boolean enableVibration;
    private SessionState sessionState = SessionState.New;
    private int currentRepetitions = 0;
    private long startTime;
    private Chronometer chronometer = new Chronometer();

    // Listeners
    private OnUpdateTimeListener onUpdateTimeListener;
    private OnCompleteListener onCompleteListener;

    //
    // -- CONSTRUTORES

    /**
     * Cria uma instância dessa classe de acordo com as configurações dos parâmetros.
     * @param parameters Um objeto que implemente a interface 'SessionParameter' estipulando os parâmetros que o usuário deseja para a sessão.
     */
    public SessionTrack(Context context, SessionParameters parameters) {
        // Configura as propriedades dessa instância de acordo com os parâmetros recebidos
        vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);

        this.parameters = parameters;
        this.enableVibration = parameters.isEnableVibration();

        //
        //-- Configura o cronometro --
        //
        // Configura o evento de atualização do tempo
        chronometer.setOnUpdateListener(new Chronometer.OnUpdateListener() {
            @Override
            public void onUpdate(long elapsedTime, Long remainingTime) {
                if (onUpdateTimeListener != null)
                    onUpdateTimeListener.onUpdateTime(elapsedTime, remainingTime);
            }
        });

        // Verifica se um tempo limite foi determinado e configura o cronometro
        if (parameters.getTimeLimit() > 0) {
            chronometer.setTimeLimit(parameters.getTimeLimit());
            chronometer.setOnCompleteListener(new Chronometer.OnCompleteListener() {
                @Override
                public void onComplete() {
                    if (onCompleteListener != null)
                        onCompleteListener.onComplete();
                }
            });
        }
    }

    //
    // -- PROPRIEDADES

    /**
     * Obtém a contagem atual de repetições da sessão.
     * @return Quantas repetições foram feitas nessa sessão.
     */
    public int getCurrentRepetitions() {
        return currentRepetitions;
    }

    public SessionState getSessionState() {
        return sessionState;
    }

    public void setOnUpdateTimeListener(OnUpdateTimeListener onUpdateTimeListener) {
        this.onUpdateTimeListener = onUpdateTimeListener;
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    public boolean isEnableVibration() {
        return enableVibration;
    }

    public void setEnableVibration(boolean enableVibration) {
        this.enableVibration = enableVibration;
    }

    //
    // -- MÉTODOS PÚBLICOS

    public void startSession() {
        // Só deve fazer alguma coisa se for uma nova sessão
        if (sessionState == SessionState.New) {
            // Configura os dados sessão
            startTime = System.currentTimeMillis();
            chronometer.start();
            sessionState = SessionState.Running;
        }
    }

    public void pauseSession() {
        if (sessionState == SessionState.Running) {
            chronometer.stop();
            sessionState = SessionState.Paused;
        }
    }

    public void resumeSession() {
        if (sessionState == SessionState.Paused) {
            chronometer.start();
            sessionState = SessionState.Running;
        }
    }

    public void cancelSession() {
        if (sessionState == SessionState.Running || sessionState == SessionState.Paused) {
            completeSession(true);
            if (onCompleteListener != null)
                onCompleteListener.onComplete();
        }
    }

    public void addRepetition() {
        if (sessionState == SessionState.Running) {
            // Vibra o dispositivo
            if (enableVibration && vibrator.hasVibrator()) {
                vibrator.vibrate(100);
            }

            currentRepetitions++;
            checkCompletion();
        }
    }

    //
    // -- MÉTODOS PRIVADOS

    /**
     * Avalia os critérios da atividade selecionada com o estado atual da sessão para determinar se ela foi concluída.
     */
    private void checkCompletion() {
        // Verifica se esta medindo as repetições e se o limite foi atingido
        if (parameters.getRepetitions() > 0 && currentRepetitions >= parameters.getRepetitions()) {
            completeSession(false);
            if (onCompleteListener != null)
                onCompleteListener.onComplete();
        }
    }

    /**
     * Marca a sessão atual como concluída.
     * @param isCancelled Flag indicando se a sessão foi conluida naturalmente ou se foi cancelada.
     */
//    private void completeSession(boolean isCancelled) {
//        // Verifica se o estado da sessão não é cancelado ou completo
//        if (sessionState != SessionState.Cancelled && sessionState != SessionState.Completed) {
//            chronometer.stop();
//            sessionState = isCancelled ? SessionState.Cancelled : SessionState.Completed;
//        }
//    }

    private void completeSession(boolean isCancelled) {
        // Verifica se o estado da sessão não é cancelado ou completo
        if (sessionState != SessionState.Cancelled && sessionState !=
                SessionState.Completed) {
            chronometer.stop();
            sessionState = isCancelled ? SessionState.Cancelled :
                    SessionState.Completed;
            // Salva a sessão
            long endTime = System.currentTimeMillis();
            parameters.saveSession(startTime, endTime, chronometer.getElapsedSeconds(),
                    currentRepetitions);
        }
    }

    //
    // -- TIPOS INTERNOS

    /**
     * Definição de interface para um callback que é chamado quando o tempo da sessão atualiza seus valores.
     */
    public interface OnUpdateTimeListener {

        /**
         * Chamado quando o cronometro da sessão atualiza seus valores.
         * @param elapsedTime O tempo transcorrido em segundos.
         * @param remainingTime O tempo restante em segundos.
         */
        void onUpdateTime(long elapsedTime, Long remainingTime);

    }

    /**
     * Interface definition for a callback to be invoked when the chronometer completes running.
     */
    public interface OnCompleteListener {

        /**
         * Chamado quando a sessão é completada por ter alcançado o tempo limite.
         */
        void onComplete();

    }

}

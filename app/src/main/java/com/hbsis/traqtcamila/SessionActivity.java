package com.hbsis.traqtcamila;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hbsis.traqtcamila.model.DataStore;
import com.hbsis.traqtcamila.model.SessionState;
import com.hbsis.traqtcamila.model.SessionTrack;
import com.hbsis.traqtcamila.model.entities.TraqtActivity;
import com.hbsis.traqtcamila.utils.Duration;

import helpers.IntentExtras;

public class SessionActivity extends AppCompatActivity implements IntentExtras {

    //
    // -- CAMPOS
    TraqtActivity selectedActivity;
    SessionTrack sessionTrack;
    TextView chronometerTextView;
    TextView repetitionsTextView;

    //
    // -- CICLO DE VIDA DA ATIVIDADE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                // Lista de opções que serão apresentadas na caixa de diálogo
                final String[] options = {
                        OPTION_PAUSE,
                        OPTION_STOP_SESSION,
                        OPTION_DISABLE_VIBRATION
                };
                // Modifica as opções de acordo com o estado da sessão
                if (sessionTrack.getSessionState() == SessionState.Paused)
                    options[0] = OPTION_RESUME;
                if (!sessionTrack.isEnableVibration())
                    options[2] = OPTION_ENABLE_VIBRATION;
                //
                // Constroi uma caixa de diálogo usando a lista de opções
                new AlertDialog.Builder(SessionActivity.this)
                        .setTitle("Opções da Sessão")
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        if (options[which].equals(OPTION_PAUSE))
                                            sessionTrack.pauseSession();
                                        else
                                            sessionTrack.resumeSession();
                                        break;
                                    case 1:
                                        sessionTrack.cancelSession();
                                        break;
                                    case 2:
                                        sessionTrack.setEnableVibration(!sessionTrack.isEnableVibration());
                                        break;
                                }
                            }
                        }).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Obtém os elementos que criamos
        chronometerTextView = (TextView)findViewById(R.id.chronometer_text_view);
        repetitionsTextView = (TextView)findViewById(R.id.repetitions_text_view);

        // Obtém a informação da atividade a partir do Intent
        int traqtActivityId = getIntent().getIntExtra(ACTIVITY_ID_EXTRA, 0);
        selectedActivity =
                DataStore.getInstance().getActivityRepository().findById(traqtActivityId);
        // Fecha essa tela caso o ID não corresponda a algum registro de atividade
        if (selectedActivity == null) {
            Toast.makeText(this, "Atividade inválida.", Toast.LENGTH_LONG).show();
            finish();
        }
        getSupportActionBar().setTitle(selectedActivity.getName());

        // Configura a sessão
        sessionTrack = new SessionTrack(this, selectedActivity);
        sessionTrack.setOnUpdateTimeListener(new SessionTrack.OnUpdateTimeListener() {
            @Override
            public void onUpdateTime(long elapsedTime, Long remainingTime) {
                String time = new Duration((remainingTime == null) ? elapsedTime :
                        remainingTime).getFormattedDuration();
                chronometerTextView.setText(time);
            }
        });

        sessionTrack.setOnCompleteListener(new SessionTrack.OnCompleteListener() {
            @Override
            public void onComplete() {
                new AlertDialog.Builder(SessionActivity.this)
                        .setTitle("Traqt")
                        .setMessage("A sessão foi concluída!")
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                // Carrega o resultado da sessão
                                Intent intent = new Intent();
                                intent.putExtra(SESSION_START_TIME_EXTRA, System.currentTimeMillis());
                                intent.putExtra(SESSION_DURATION_EXTRA, chronometerTextView.getText().toString());
                                intent.putExtra(SESSION_REPETITIONS_EXTRA, sessionTrack.getCurrentRepetitions());
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        })
                        .show();
            }
        });

        // Configura a View para receber toques
        if ((selectedActivity != null) && (selectedActivity.getRepetitions() > 0)) {
            // Configura um listener para eventos de Tap simples
            GestureDetector.SimpleOnGestureListener listener = new
                    GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onSingleTapUp(MotionEvent e) {
                            sessionTrack.addRepetition();
                            updateRepetitions();
                            return true;
                        }
                    };
            final GestureDetector detector = new GestureDetector(this, listener);
            // Configura a View base dessa Activity para monitorar os eventos de Toque
            //  usando o listener que configuramos anteriormente
            View view = (View) findViewById(R.id.session_view);
            view.setOnTouchListener( new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    detector.onTouchEvent(event);
                    return true;
                }
            });
            updateRepetitions();
        } else {
            repetitionsTextView.setVisibility(View.INVISIBLE);
        }
        getSupportActionBar().setTitle(selectedActivity.getName());
        sessionTrack.startSession();
    }

    //
    // -- MÉTODOS AUXILIARES
    void updateRepetitions() {
        repetitionsTextView.setText("" +
                sessionTrack.getCurrentRepetitions() + " de " +
                selectedActivity.getRepetitions());
    }

    //
// -- CONSTANTES
    private static final String OPTION_PAUSE = "Pausar";
    private static final String OPTION_RESUME = "Retomar";
    private static final String OPTION_STOP_SESSION = "Parar Sessão";
    private static final String OPTION_DISABLE_VIBRATION = "Desabilitar Vibração";
    private static final String OPTION_ENABLE_VIBRATION = "Habilitar Vibração";

}

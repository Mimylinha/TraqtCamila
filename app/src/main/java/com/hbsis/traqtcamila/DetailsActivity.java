package com.hbsis.traqtcamila;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.hbsis.traqtcamila.model.DataStore;
import com.hbsis.traqtcamila.model.entities.TraqtActivity;
import com.hbsis.traqtcamila.model.entities.TraqtSession;
import com.hbsis.traqtcamila.utils.Duration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import helpers.IntentExtras;


public class DetailsActivity extends AppCompatActivity implements IntentExtras {

    //
    // -- CAMPOS
    private TraqtActivity selectedActivity;

    //
    // -- CICLO DE VIDA DA ATIVIDADE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, SessionActivity.class);
                intent.putExtra(ACTIVITY_ID_EXTRA, selectedActivity.getId());
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Configura os elementos
        TextView maxRepetitionsTextView = (TextView)findViewById(R.id.max_repetitions_text_view);
        TextView timeLimitTextView = (TextView)findViewById(R.id.time_limit_text_view);
        TextView historyTextView = (TextView)findViewById(R.id.history_text_view);

        // Extrai os extras do Intent para obter o código da Atividade Traqt selecionada
        int traqtActivityId = getIntent().getIntExtra(ACTIVITY_ID_EXTRA, 0);
        selectedActivity = DataStore.getInstance().getActivityRepository().findById(traqtActivityId);
        if (selectedActivity != null) {
            // Carrega os dados da atividade
            maxRepetitionsTextView.setText("Repetições máximas: " + selectedActivity.getRepetitions());
            timeLimitTextView.setText("Tempo limite: " + new Duration(selectedActivity.getTimeLimit()).getFormattedDuration());
            getSupportActionBar().setTitle(selectedActivity.getName());
            // Carrega o histórico da atividade
            List<TraqtSession> sessions =
                    DataStore.getInstance().getSessionRepository().findWhere(TraqtSession.COLUMN_ACTIVITY_ID + " = ?", String.valueOf(selectedActivity.getId()));
            if ((sessions != null) && (sessions.size() > 0)){
                StringBuilder sessionsBuilder = new StringBuilder();
                sessionsBuilder.append("Sessões desta atividade:\n");
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                for (TraqtSession session : sessions) {
                    String duration = new
                            Duration(session.getElapsedTime()).getFormattedDuration();
                    Date startDate = new Date(session.getStartTime());
                    sessionsBuilder.append("- Sessão em " + formatter.format(startDate) +
                            ", repetições: " + session.getTotalReptitions() +
                            ", tempo: " + duration + "\n");
                }
                historyTextView.setText(sessionsBuilder.toString());
            } else {
                historyTextView.setText("Não há sessões registradas para essa atividade.");
            }
        } else {
            // O ID passado é inválido, encerra a atividade retornando a anterior
            finish();
        }
    }

}

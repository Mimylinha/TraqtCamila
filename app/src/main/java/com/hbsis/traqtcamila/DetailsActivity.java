package com.hbsis.traqtcamila;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
    private int traqtActivityId;
    private TextView maxRepetitionsTextView;
    private TextView timeLimitTextView;
    private TextView historyTextView;
    private ImageView categoryImageView;

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
                intent.putExtra(ACTIVITY_ID_EXTRA, traqtActivityId);
                startActivityForResult(intent, REQUEST_SESSION);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Inicializa e Configura os elementos
        maxRepetitionsTextView = (TextView)findViewById(R.id.max_repetitions_text_view);
        timeLimitTextView = (TextView)findViewById(R.id.time_limit_text_view);
        historyTextView = (TextView)findViewById(R.id.history_text_view);
        categoryImageView = (ImageView)findViewById(R.id.category_image_view);

        // Extrai os extras do Intent para obter o código da Atividade Traqt selecionada
        traqtActivityId = getIntent().getIntExtra(ACTIVITY_ID_EXTRA, 0);
        refreshData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // "Infla" o menu adicionando seus items ao ActionBar
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit) {
            Intent intent = new Intent(this, FormActivity.class);
            intent.putExtra(ACTIVITY_ID_EXTRA, selectedActivity.getId());
            startActivityForResult(intent, REQUEST_FORM);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_FORM) {
            // Caso um registro tenha sido inserido atualiza o Spinner
            if (resultCode == RESULT_UPDATED)
                refreshData();
        } else if (requestCode == REQUEST_SESSION) {
            if (resultCode == RESULT_OK) {
                // Extrai os Extras do Intent
                Date startDate = new Date(data.getLongExtra(SESSION_START_TIME_EXTRA,
                        0));
                String duration = data.getStringExtra(SESSION_DURATION_EXTRA);
                int repetitions = data.getIntExtra(SESSION_REPETITIONS_EXTRA, 0);
                // Concatena com o TextView de Histórico
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                historyTextView.setText(historyTextView.getText().toString() +
                        "- Sessão em " + formatter.format(startDate) +
                        ", repetições: " + repetitions +
                        ", tempo: " + duration + "\n");
            }
        }
    }

    //
    // -- MÉTODOS PRIVADOS

    void refreshData() {
        TraqtActivity selectedActivity =
                DataStore.getInstance().getActivityRepository().findById(traqtActivityId);
        if (selectedActivity != null) {
            // Carrega os dados da atividade
            maxRepetitionsTextView.setText("Repetições máximas: " +
                    selectedActivity.getRepetitions());
            timeLimitTextView.setText("Tempo limite: " + new
                    Duration(selectedActivity.getTimeLimit()).getFormattedDuration());
            getSupportActionBar().setTitle(selectedActivity.getName());
            // Carrega o histórico da atividade
            categoryImageView.setImageResource(selectedActivity.getCategoryInfo().getIconResourceId());
            List<TraqtSession> sessions =
                    DataStore.getInstance().getSessionRepository().findWhere(TraqtSession.COLUMN_ACTIVITY_ID + " = ?", String.valueOf(selectedActivity.getId()));
            if (sessions.size() > 0) {
                StringBuilder sessionsBuilder = new StringBuilder();
                sessionsBuilder.append("Sessões desta atividade:\n");
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                for (TraqtSession session : sessions) {
                    String duration = new
                            Duration(session.getElapsedTime()).getFormattedDuration();
                    Date startDate = new Date(session.getStartTime());
                    sessionsBuilder.append("- Sessão em " + formatter.format(startDate)
                            +
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

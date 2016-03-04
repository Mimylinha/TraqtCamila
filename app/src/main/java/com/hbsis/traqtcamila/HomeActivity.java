package com.hbsis.traqtcamila;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.hbsis.traqtcamila.model.DataStore;
import com.hbsis.traqtcamila.model.entities.TraqtActivity;

import java.util.List;

import helpers.IntentExtras;

public class HomeActivity extends AppCompatActivity  implements IntentExtras {

    //
    // -- CAMPOS

    DataStore dataStore;
    List<TraqtActivity> allActivities;

    //
    // -- CICLO DE VIDA DA ATIVIDADE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Inicializa o Singleton de armazenamento de dados
        DataStore.initialize(this);
        dataStore = DataStore.getInstance();

        // Configura um Spinner para listar as atividades
        allActivities = dataStore.getActivityRepository().findAll();
        String[] activitiesNames = new String[allActivities.size()];
        for (int i = 0; i < allActivities.size(); i++) {
            activitiesNames[i] = allActivities.get(i).getName();
        }
        ArrayAdapter<String> activitiesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, activitiesNames);
        final Spinner activitiesSpinner = (Spinner)findViewById(R.id.activities_spinner);
        activitiesSpinner.setAdapter(activitiesAdapter);

        Button selectActivityButton = (Button)findViewById(R.id.select_activity_button);
        selectActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtém o item selecionado
                TraqtActivity act =
                        allActivities.get(activitiesSpinner.getSelectedItemPosition());
                // Cria e configura o Intent, e inicia a nova atividade
                Intent intent = new Intent(HomeActivity.this, DetailsActivity.class);
                intent.putExtra(ACTIVITY_ID_EXTRA, act.getId());
                startActivity(intent);
            }
        });
    }

}

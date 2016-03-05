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
    Spinner activitiesSpinner;

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
                Intent intent = new Intent(HomeActivity.this, FormActivity.class);
                startActivityForResult(intent, REQUEST_FORM);
            }
        });

        // Inicializa o Singleton de armazenamento de dados
        DataStore.initialize(this);
        dataStore = DataStore.getInstance();

        // Configura um Spinner para listar as atividades
        activitiesSpinner = (Spinner)findViewById(R.id.activities_spinner);
        refreshActivitiesSpinner();

        Button selectActivityButton = (Button)findViewById(R.id.select_activity_button);
        selectActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtém o item selecionado
                TraqtActivity act = allActivities.get(activitiesSpinner.getSelectedItemPosition());

                // Cria e configura o Intent, e inicia a nova atividade
                Intent intent = new Intent(HomeActivity.this, DetailsActivity.class);
                intent.putExtra(ACTIVITY_ID_EXTRA, act.getId());
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_FORM) {
            // Caso um registro tenha sido inserido atualiza o Spinner
            if (resultCode == RESULT_INSERTED)
                refreshActivitiesSpinner();
        }
    }

    //
    // -- MÉTODOS PRIVADOS

    void refreshActivitiesSpinner() {
        allActivities = dataStore.getActivityRepository().findAll();
        String[] activitiesNames = new String[allActivities.size()];
        for (int i = 0; i < allActivities.size(); i++) {
            activitiesNames[i] = allActivities.get(i).getName();
        }
        ArrayAdapter<String> activitiesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, activitiesNames);
        activitiesSpinner.setAdapter(activitiesAdapter);
    }
}

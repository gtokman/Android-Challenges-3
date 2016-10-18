package com.garytokman.tokmangary_ce07.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.garytokman.tokmangary_ce07.R;
import com.garytokman.tokmangary_ce07.model.Person;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_PERSON = "EXTRA_PERSON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.personDetailName);
        Person person = (Person) getIntent().getSerializableExtra(EXTRA_PERSON);
        textView.setText(person.mName);

    }
}

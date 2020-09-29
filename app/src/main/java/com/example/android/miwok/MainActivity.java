/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView numbers,family,phrases,colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);
        numbers = findViewById(R.id.numbers);
        family = findViewById(R.id.family);
        colors = findViewById(R.id.colors);
        phrases = findViewById(R.id.phrases);

        numbers.setOnClickListener(this);
        family.setOnClickListener(this);
        colors.setOnClickListener(this);
        phrases.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

            switch (view.getId()){
                case R.id.numbers:
                    Intent numbersIntent = new Intent(this,NumbersActivity.class);
                    startActivity(numbersIntent);
                    break;

                case R.id.family:
                    Intent familyIntent = new Intent(this,FamilyMembersActivity.class);
                    startActivity(familyIntent);
                    break;

                case R.id.colors:
                    Intent colorsIntent = new Intent(this,ColorsActivity.class);
                    startActivity(colorsIntent);
                    break;

                case R.id.phrases:
                    Intent phrasesIntent = new Intent(this,PhrasesActivity.class);
                    startActivity(phrasesIntent);
                    break;
            }

    }
}

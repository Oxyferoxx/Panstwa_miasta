package pl.michalgajewski.panstwa_miasta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    EditText edidTextLogin;
    EditText editTextPassword;
    CheckBox checkBoxRemember;

    Button buttonLogin, buttonRegister;


    private SharedPreferences sharedPreferences;
    private SQLiteData data;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        data = new SQLiteData(this);
        sharedPreferences = this.getSharedPreferences("LoginData", MODE_PRIVATE);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        checkBoxRemember = (CheckBox) findViewById(R.id.checkBoxRemember);
        edidTextLogin = (EditText) findViewById(R.id.editTextLogin);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        preferences = this.getSharedPreferences("loginData", MODE_PRIVATE);
        loadData();

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = edidTextLogin.getText().toString();
                String password = editTextPassword.getText().toString();
                if (!(login.trim().length() > 3 && login.trim().length() < 15)) {
                    Toast.makeText(getApplicationContext(), "Login musi mieścić się w przedziale od 3 do 15 znaków", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!(password.trim().length() > 5 && password.trim().length() < 15)) {
                    Toast.makeText(getApplicationContext(), "Hasło musi zawierać od 5 do 15 znaków", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (data.checkPassword(login, password)) {
                    Intent i = new Intent(v.getContext(), Game.class);
                    startActivity(i);
                    saveData();
                }
            }
        });
    }


    private void loadData() {
        edidTextLogin.setText(preferences.getString("login", ""));
        editTextPassword.setText(preferences.getString("password", ""));
        checkBoxRemember.setChecked(preferences.getBoolean("remember", false));
    }

    private void saveData() {
        if (checkBoxRemember.isChecked()) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("login", edidTextLogin.getText().toString());
            editor.putString("password", editTextPassword.getText().toString());
            editor.putBoolean("remember", true);
            editor.commit();
        }


    }
}


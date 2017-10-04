package pl.michalgajewski.panstwa_miasta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Malczan on 03.10.2017.
 */

public class RegisterActivity extends Activity {
    EditText editLogin, editPassword, editPassword2;
    Button makeRegister;
    private SQLiteData data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        data = new SQLiteData(this);
        editLogin = (EditText)findViewById(R.id.editLogin);
        editPassword = (EditText)findViewById(R.id.editPassword);
        editPassword2 = (EditText)findViewById(R.id.editPassword2);

        makeRegister = (Button)findViewById(R.id.buttonRegister);
        makeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = editLogin.getText().toString();
                String password = editPassword.getText().toString();
                String passwordRepeat = editPassword2.getText().toString();
                if (!password.equals(passwordRepeat)){
                    Toast.makeText(getApplicationContext(), "Hasła muszą być jednkowe", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (data.isUserExist(login)){
                    Toast.makeText(getApplicationContext(), "Taki użytkownik już istnieje", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    data.addUser(login, password);
                    Toast.makeText(getApplicationContext(), "Rejestracja przebiegła pomyślnie - powrót do logowania", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                }
            }
        });
    }

}

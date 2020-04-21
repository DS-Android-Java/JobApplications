package example.abhiandriod.tablelayoutexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initiate a button
        loginButton = (Button) findViewById(R.id.loginBtn);//Primero casting con el boton
        username = (EditText)findViewById(R.id.userName);
        password = (EditText)findViewById(R.id.password);

        // perform click event on the button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//Evento escuchador del boton
                valida();
            }
        });
    }

    public void valida(){
        if(username.getText().toString().equals("") || password.getText().toString().equals("")){
            Toast.makeText(this,"You must complete all the fields", Toast.LENGTH_LONG).show();
        }else{
            if(!username.getText().toString().contains("@")){
                Toast.makeText(this,"The user must be a email", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"Validating", Toast.LENGTH_LONG).show();
                Intent i = new Intent(this,formActivity.class);
                startActivity(i);
            }
        }
    }


}

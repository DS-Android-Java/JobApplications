package example.abhiandriod.tablelayoutexample;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import example.abhiandriod.tablelayoutexample.ui.DatePickerFragment;

public class formActivity extends AppCompatActivity {

    private Spinner spinnerP;
    private Spinner spinnerAP;
    private Button btnUpload;
    private EditText etDatePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        ArrayList<String> paises = new ArrayList<>();
        paises.add("Costa Rica");
        paises.add("USA");
        paises.add("Rusia");
        paises.add("Mexico");
        paises.add("Inglaterra");
        paises.add("España");
        paises.add("Panama");
        spinnerP = findViewById(R.id.spinnerP);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, paises);
        spinnerP.setAdapter(adaptador);


        ArrayList<String> puestos = new ArrayList<>();
        puestos.add("System Engineer");
        puestos.add("Bitch");
        puestos.add("Software Tester");
        puestos.add("Data Analist");
        puestos.add("Electronic Engineer");
        puestos.add("Data Miner");
        spinnerAP = findViewById(R.id.spinnerAP);
        ArrayAdapter<String> adaptadorAP = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, puestos);
        spinnerAP.setAdapter(adaptadorAP);

        etDatePicker = (EditText)findViewById(R.id.etDatePicker);
        etDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.etDatePicker:
                        showDatePickerDialog();
                        break;
                }
            }
        });

        btnUpload = (Button)findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadResume();
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                etDatePicker.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void uploadResume(){
        Toast.makeText(this,"Uploading Resume" , Toast.LENGTH_LONG).show();
    }

}

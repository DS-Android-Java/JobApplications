package example.abhiandriod.tablelayoutexample.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import example.abhiandriod.tablelayoutexample.JobApplicationsActivity;
import example.abhiandriod.tablelayoutexample.R;
import example.abhiandriod.tablelayoutexample.logicadenegocio.JobApplication;
import example.abhiandriod.tablelayoutexample.ui.DatePickerFragment;

public class UpdateRegistrationForm extends AppCompatActivity {

    private Spinner spinnerPU;
    private Spinner spinnerAPU;
    private Button btnUploadU;
    private EditText etDatePickerU;
    private EditText etFirstNameU;
    private EditText etLastNameU;
    private EditText etStreetAddressU;
    private EditText etStreetAddress2U;
    private EditText etCityU;
    private EditText etStateU;
    private EditText etPostalCodeU;
    private EditText etEmailU;
    private EditText etAreaCodeU;
    private EditText etPhoneNumberU;
    private Button sentBtnU;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_registration_form);


        String primaryDark = "#3791a4";
        String primary = "#1fa1bc";
        String background = "#b8141b";

        etFirstNameU = (EditText)findViewById(R.id.etFirstNameU);
        etLastNameU = (EditText)findViewById(R.id.etLastNameU);
        etStreetAddressU = (EditText)findViewById(R.id.etStreetAddressU);
        etStreetAddress2U = (EditText)findViewById(R.id.etStreetAddress2U);
        etCityU = (EditText)findViewById(R.id.etCityU);
        etStateU = (EditText)findViewById(R.id.etStateU);
        etPostalCodeU = (EditText)findViewById(R.id.etPostalCodeU);
        etEmailU = (EditText)findViewById(R.id.etEmailU);
        etAreaCodeU = (EditText)findViewById(R.id.etAreaCodeU);
        etPhoneNumberU = (EditText)findViewById(R.id.etPhoneNumberU);
        sentBtnU = (Button)findViewById(R.id.btnSentU);

        ArrayList<String> paises = new ArrayList<>();
        paises.add("Costa Rica");
        paises.add("USA");
        paises.add("Rusia");
        paises.add("Mexico");
        paises.add("Inglaterra");
        paises.add("España");
        paises.add("Panama");
        spinnerPU = findViewById(R.id.spinnerPU);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, paises);
        spinnerPU.setAdapter(adaptador);


        ArrayList<String> puestos = new ArrayList<>();
        puestos.add("System Engineer");
        puestos.add("Software Tester");
        puestos.add("Data Analist");
        puestos.add("Electronic Engineer");
        puestos.add("Data Miner");
        spinnerAPU = findViewById(R.id.spinnerAPU);
        ArrayAdapter<String> adaptadorAP = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, puestos);
        spinnerAPU.setAdapter(adaptadorAP);

        etDatePickerU = (EditText)findViewById(R.id.etDatePickerU);
        etDatePickerU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.etDatePicker:
                        showDatePickerDialog();
                        break;
                }
            }
        });

        btnUploadU = (Button)findViewById(R.id.btnUploadU);
        btnUploadU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadResumeU();
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras != null){
                JobApplication aux = (JobApplication) getIntent().getSerializableExtra("jobapplication");
                etFirstNameU.setText(aux.getFirstName());
                etLastNameU.setText(aux.getLastName());
                etStreetAddressU.setText(aux.getStreetAddress());
                etStreetAddress2U.setText(aux.getSecondStreetAddress());
                etCityU.setText(aux.getCity());
                etStateU.setText(aux.getState());
                etPostalCodeU.setText(aux.getPostalCode());
                etEmailU.setText(aux.getEmail());
                etAreaCodeU.setText(aux.getAreaCode());
                etPhoneNumberU.setText(aux.getPhoneNumber());
                etDatePickerU.setText(aux.getDate());

                sentBtnU.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editJobApplication();
                    }
                });
        }
        //Aca se prepara el popup
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.90), (int)(alto * 0.95));
    }
    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                etDatePickerU.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void editJobApplication(){
        if(validate()){
            JobApplication jobApplication = new JobApplication(
                    etFirstNameU.getText().toString(),
                    etLastNameU.getText().toString(),
                    etStreetAddressU.getText().toString(),
                    etStreetAddress2U.getText().toString(),
                    etCityU.getText().toString(),
                    etStateU.getText().toString(),
                    etPostalCodeU.getText().toString(),
                    etEmailU.getText().toString(),
                    etAreaCodeU.getText().toString(),
                    etPhoneNumberU.getText().toString(),
                    etDatePickerU.getText().toString(),
                    spinnerAPU.getSelectedItem().toString(),
                    spinnerPU.getSelectedItem().toString()
            );
            Intent intent = new Intent(getBaseContext(), JobApplicationsActivity.class);
            intent.putExtra("jobapplication", jobApplication);
            startActivity(intent);
            finish();
        }
    }

    public void uploadResumeU(){
        Toast.makeText(this,"Uploading Resume" , Toast.LENGTH_LONG).show();
    }

    public boolean validate(){
        int valido=0;

        if (TextUtils.isEmpty(this.etFirstNameU.getText())) {
            etFirstNameU.setError("Required First Name");//Asi se le coloca un mensaje de error en los campos en android
            valido++;
        }
        if(TextUtils.isEmpty(this.etLastNameU.getText())){
            etLastNameU.setError("Required Last Name");
            valido++;
        }
        if(TextUtils.isEmpty(this.etStreetAddressU.getText())){
            etStreetAddressU.setError("Required Street Addres");
            valido++;
        }
        if(TextUtils.isEmpty(this.etStreetAddress2U.getText())){
            etStreetAddress2U.setError("Required Second Street Address");
            valido++;
        }
        if(TextUtils.isEmpty(this.etCityU.getText())){
            etCityU.setError("Required City");
            valido++;
        }
        if(TextUtils.isEmpty(this.etStateU.getText())){
            etStateU.setError("Required State");
            valido++;
        }
        if(TextUtils.isEmpty(this.etPostalCodeU.getText())){
            etPostalCodeU.setError("Required Postal Code");
            valido++;
        }
        if(TextUtils.isEmpty(this.etEmailU.getText())){
            etEmailU.setError("Required Email");
            valido++;
        }
        if(TextUtils.isEmpty(this.etAreaCodeU.getText())){
            etAreaCodeU.setError("Required Area Code");
            valido++;
        }
        if(TextUtils.isEmpty(this.etPhoneNumberU.getText())){
            etPhoneNumberU.setError("Required Phone Number");
            valido++;
        }
        if(TextUtils.isEmpty(this.etDatePickerU.getText())){
            etDatePickerU.setError("Required Date");
            valido++;
        }
        if (valido > 0) {
            Toast.makeText(getApplicationContext(), "You must complete all the fields", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


}

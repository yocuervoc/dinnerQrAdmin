package co.edu.unal.dinnerqradmin.activities;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import co.edu.unal.dinnerqradmin.R;


public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dish = database.getInstance().getReference("restaurant");
    private FirebaseAuth mAuth;
    private EditText TextEmail, TextPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextEmail = (EditText)findViewById(R.id.etMail);
        TextPassword = (EditText)findViewById(R.id.edPassworlogin);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
    }

    public void login(View view){
        //Obtenemos el email y la contraseña desde las cajas de texto
        String email = TextEmail.getText().toString().trim();
        String password  = TextPassword.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Ingresando  en linea...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Welcome :)", Toast.LENGTH_SHORT).show();
                            Intent opcione = new Intent(MainActivity.this, OptionsActivity.class);
                            startActivity(opcione);

                        } else {
                            Toast.makeText(MainActivity.this, "Error en usuario o contraseña", Toast.LENGTH_LONG).show();
                        }

                        progressDialog.dismiss();

                        // ...
                    }
                });

    }

    public void registeractivity(View view){
        Intent resgistrar = new Intent(this, RegisterActivity.class);
        startActivity(resgistrar);
    }




}

/*
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dish = database.getInstance().getReference("restaurant");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String id = dish.push().getKey();
        Plato plato = new Plato();
        plato.setId(id);
        plato.setName("mute");
        plato.setDescription("Santandereano");
        plato.setPrice(12000);
        dish.child("restaurante1").child(id).setValue(plato);
        /*
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (Exception e){

        }


        id = dish.push().getKey();
        plato.setId(id);
        plato.setName("hamburguesa");
        plato.setDescription("grade");
        plato.setPrice(18000);
        dish.child("restaurante1").child(id).setValue(plato);

        id = dish.push().getKey();
        plato.setId(id);
        plato.setName("pizza");
        plato.setDescription("de piña");
        plato.setPrice(12000);
        dish.child("restaurante1").child(id).setValue(plato);


        id = dish.push().getKey();
        plato.setId(id);
        plato.setName("lomo");
        plato.setDescription("a la pimienta");
        plato.setPrice(21000);
        dish.child("restaurante1").child(id).setValue(plato);


        id = dish.push().getKey();
        plato.setId(id);
        plato.setName("macarrones");
        plato.setDescription("con queso");
        plato.setPrice(7000);
        dish.child("restaurante2").child(id).setValue(plato);


        id = dish.push().getKey();
        plato.setId(id);
        plato.setName("Fresas con crema");
        plato.setDescription("postre regular");
        plato.setPrice(5000);
        dish.child("restaurante2").child(id).setValue(plato);


        id = dish.push().getKey();
        plato.setId(id);
        plato.setName("cerveza bacata");
        plato.setDescription("negra");
        plato.setPrice(7500);
        dish.child("restaurante2").child(id).setValue(plato);

        id = dish.push().getKey();
        plato.setId(id);
        plato.setName("alitas");
        plato.setDescription("apnadas");
        plato.setPrice(21000);
        dish.child("restaurante3").child(id).setValue(plato);


    }
}
*/

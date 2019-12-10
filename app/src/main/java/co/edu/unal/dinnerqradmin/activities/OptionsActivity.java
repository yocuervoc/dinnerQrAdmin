package co.edu.unal.dinnerqradmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import co.edu.unal.dinnerqradmin.R;
import co.edu.unal.dinnerqradmin.clases.Cliente;



public class OptionsActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView nombreUser;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference cliente = database.getInstance().getReference("client");
    static String qrContend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        nombreUser = (TextView) findViewById(R.id.Tvname);
        mAuth = FirebaseAuth.getInstance();

        final FirebaseUser currentUser = mAuth.getCurrentUser();
        nombreUser.setText(currentUser.getEmail());
        cliente.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Cliente currentClient = snapshot.getValue(Cliente.class);

                    if (currentClient.geteMail().equals(currentUser.getEmail())) {
                        nombreUser.setText(currentClient.getName());
                        qrContend= currentClient.getName();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        nombreUser.setText(currentUser.getEmail());
    }
    public void menu(View view){
        Intent i = new Intent(this, RestaurantActivity.class);
        startActivity(i);
    }

    public void bills(View view){
        Intent i = new Intent(this, BillActivity.class);
        startActivity(i);
    }


    @Override
    public void onBackPressed() {
        Intent optionsActivity = new Intent(this, OptionsActivity.class);
        startActivity(optionsActivity);


    }
}

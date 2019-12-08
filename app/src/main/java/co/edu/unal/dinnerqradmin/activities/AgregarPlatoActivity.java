package co.edu.unal.dinnerqradmin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import co.edu.unal.dinnerqradmin.R;
import co.edu.unal.dinnerqradmin.clases.Cliente;
import co.edu.unal.dinnerqradmin.clases.Plato;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static co.edu.unal.dinnerqradmin.activities.DetalleLista.idUser;

public class AgregarPlatoActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dish = database.getInstance().getReference("restaurant");
    private EditText nombre;
    private EditText descripcion;
    private EditText precio;
    String restaurantName;
    final DatabaseReference cliente = database.getInstance().getReference("client");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_plato);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();


        cliente.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for( DataSnapshot snapshot: dataSnapshot.getChildren()){

                    Cliente currentClient = snapshot.getValue(Cliente.class);

                    if(currentClient.geteMail().equals(currentUser.getEmail())){
                        idUser = currentClient.getId();
                        restaurantName = currentClient.getName();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public  void agregarplato(View view){
        nombre = (EditText)findViewById(R.id.etNombrePlato);
        descripcion = (EditText)findViewById(R.id.etDescripcionPlato);
        precio = (EditText)findViewById(R.id.etPrecioPlato);

        String name = nombre.getText().toString();
        String description = descripcion.getText().toString();
        Double price =  Double.parseDouble(precio.getText().toString());
        String id = dish.push().getKey();
        Plato plato = new Plato();
        plato.setId(id);
        plato.setName(name);
        plato.setDescription(description);
        plato.setPrice(price);
        dish.child(restaurantName).child(id).setValue(plato);

        nombre.setText("");
        descripcion.setText("");
        precio.setText("");
        Toast.makeText(this, "plato agregado exitosamente", Toast.LENGTH_SHORT).show();
    }
}

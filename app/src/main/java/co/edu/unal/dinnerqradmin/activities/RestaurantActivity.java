package co.edu.unal.dinnerqradmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import co.edu.unal.dinnerqradmin.R;
import co.edu.unal.dinnerqradmin.clases.Plato;
import co.edu.unal.dinnerqradmin.soport.Adaptador;

import static co.edu.unal.dinnerqradmin.activities.OptionsActivity.qrContend;

public class RestaurantActivity extends AppCompatActivity {

    private TextView nombreRestaurante;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ListView listView;

    /////
    private ListView lvItems;
    private Adaptador adaptador;
    private ArrayList<Plato> arrayEntidad = new ArrayList<>();
    /////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        //listView = (ListView)findViewById(R.id.lvPlatos);
        nombreRestaurante = (TextView)findViewById(R.id.tvAllBills);

        nombreRestaurante.setText(qrContend);
        lvItems = (ListView) findViewById(R.id.lvBills);

        DatabaseReference restaurante = database.getInstance().getReference("restaurant").child(qrContend);
        restaurante.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for( DataSnapshot snapshot: dataSnapshot.getChildren()){
                    try {
                        Plato plato = snapshot.getValue(Plato.class);
                        //Log.e("Nombre", ""+plato.getName());
                        llenarItems(plato.getId(), plato.getName(), plato.getPrice(), plato.getDescription());
                    }catch (Exception e){

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void llenarItems(String id, String nombre, double precio, String descripcion){

        Plato plato = new Plato();
        plato.setId(id);
        plato.setName(nombre);
        plato.setPrice(precio);
        plato.setDescription(descripcion);

        arrayEntidad.add(plato);

        adaptador = new Adaptador(this, arrayEntidad);
        lvItems.setAdapter(adaptador);
    }
    @Override public void onBackPressed() {
        Intent optionsActivity = new Intent(this, OptionsActivity.class);
        startActivity(optionsActivity);
    }
    public  void agregarPlato(View view){
        Intent i = new Intent(this, AgregarPlatoActivity.class);
        startActivity(i);
    }
}

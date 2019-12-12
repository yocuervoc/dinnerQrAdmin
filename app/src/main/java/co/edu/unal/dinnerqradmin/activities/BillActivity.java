package co.edu.unal.dinnerqradmin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import co.edu.unal.dinnerqradmin.R;
import co.edu.unal.dinnerqradmin.clases.Bill;
import co.edu.unal.dinnerqradmin.clases.Plato;
import co.edu.unal.dinnerqradmin.clases.StringString;
import co.edu.unal.dinnerqradmin.soport.Adaptador;
import co.edu.unal.dinnerqradmin.soport.BillListAdapter;
import co.edu.unal.dinnerqradmin.soport.BillListAdapter2;
import co.edu.unal.dinnerqradmin.soport.BillSoport;
import co.edu.unal.dinnerqradmin.soport.PlatoSoport;

import static co.edu.unal.dinnerqradmin.activities.DetalleLista.billName;
import static co.edu.unal.dinnerqradmin.activities.OptionsActivity.qrContend;

public class BillActivity extends AppCompatActivity {
    private TextView precio;
    private ListView lvBill;
    private double total;


    ArrayList<BillSoport> peopleList = new ArrayList<>();
    ArrayList<String> verdaderiId = new ArrayList<>();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference platos1 = database.getInstance().getReference("bill").child(qrContend);


    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        precio = (TextView)findViewById(R.id.tvAllBills);
        lvBill = (ListView)findViewById(R.id.lvBills);

        context = this;

        ////
        lvBill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                dialogo1.setTitle("Cobrar factura");
                dialogo1.setMessage("AL cobrar esta factura desaparecerade la lista  ");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        aceptar(position);
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        cancelar();
                    }
                });
                dialogo1.show();

            }
            public void aceptar(int position) {

                lvBill.getItemAtPosition(position);


                BillSoport p = (BillSoport) lvBill.getItemAtPosition(position);
                Log.e("item", ""+p.getName());
                platos1.child(p.getName()).removeValue();


            }

            public void cancelar() {
                finish();
            }
        });
        /////
        lista1();

    }

    public void onStart() {
        super.onStart();

    }

    public void lista1(){
        platos1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for( DataSnapshot snapshot: dataSnapshot.getChildren()){
                    verdaderiId.add(snapshot.getKey());
                    total=0;
                    for(DataSnapshot i: snapshot.getChildren()){

                        PlatoSoport p = i.getValue(PlatoSoport.class);
                        total+=p.getPrecio();
                    }
                    peopleList.add(new BillSoport(snapshot.getKey(), Double.toString(total)));
                    Log.e("key "+snapshot.getKey(), "total "+total);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        precio.setText("facturas por cobrar");

        BillListAdapter adapter = new BillListAdapter(this, R.layout.adapter_view_layout, peopleList);
        lvBill.setAdapter(adapter);

    }


}

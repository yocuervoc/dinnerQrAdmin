package co.edu.unal.dinnerqradmin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.edu.unal.dinnerqradmin.R;
import co.edu.unal.dinnerqradmin.clases.Plato;
import co.edu.unal.dinnerqradmin.soport.Adaptador;
import co.edu.unal.dinnerqradmin.soport.BillListAdapter;
import co.edu.unal.dinnerqradmin.soport.BillSoport;

import static co.edu.unal.dinnerqradmin.activities.OptionsActivity.qrContend;

public class BillActivity extends AppCompatActivity {
    private TextView precio;
    private ListView lvBill;
    private double total;
    //final  ArrayList<String> idPlato = new ArrayList<>();
    ArrayList<BillSoport> peopleList = new ArrayList<>();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference platos1 = database.getInstance().getReference("bill").child(qrContend);
    final ArrayList<String> idPlatos = new ArrayList<>();
    Context contex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        precio = (TextView)findViewById(R.id.tvAllBills);
        lvBill = (ListView)findViewById(R.id.lvBills);
        ///////////////

        lista1();
        ////


        Log.e("tam d final de idplato", " "+idPlatos.size());
        for (int i=0; i<idPlatos.size(); i++){
            System.out.println(i);
            Log.e("id plato", ""+idPlatos.get(i));
        }

        //lista2();

        //precio.setText(Double.toString(total));



    }
    public void set(){
        precio.setText(Double.toString(total));
        BillListAdapter adapter = new BillListAdapter(this, R.layout.adapter_view_layout, peopleList);
        lvBill.setAdapter(adapter);
        Log.e("si se lama", "_________________");
    }

    public void lista1(){
        platos1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for( DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String [] ids = snapshot.getValue().toString().split(",|\\=|\\}");

                    for(int j =0; j< ids.length; j++){
                        Log.e("idsssss",""+ids[j]);
                        idPlatos.add(ids[j]);
                    }
                }
                lista2();


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void lista2(){
        DatabaseReference platos = database.getInstance().getReference("restaurant").child(qrContend);
        platos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for( DataSnapshot snapshot: dataSnapshot.getChildren()){

                    Plato plato = snapshot.getValue(Plato.class);

                        for(String i: idPlatos){
                            Log.e("id plaoooooooooooooooo", ""+idPlatos.get(0));
                            if(i.equals(plato.getId())){
                                peopleList.add(new BillSoport(plato.getName(), Double.toString(plato.getPrice())));
                                total += plato.getPrice();
                            }
                        }

                }
                set();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}

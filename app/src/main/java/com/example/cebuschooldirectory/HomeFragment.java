package com.example.cebuschooldirectory;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.okhttp.internal.DiskLruCache;

import java.sql.RowId;

public class HomeFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();;
    private CollectionReference SchoolRef =  firebaseFirestore.collection("Schools");
    private SchoolAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView schoolfirestorelist= v.findViewById(R.id.firestore_schoollist);

        setRecyclerView();

         schoolfirestorelist.setHasFixedSize(true);
         schoolfirestorelist.setLayoutManager(new LinearLayoutManager(getActivity()));
         schoolfirestorelist.setAdapter(adapter);

         new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                 ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
             @Override
             public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                 return false;
             }

             @Override
             public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
             }
         }).attachToRecyclerView(schoolfirestorelist);

      adapter.setOnItemClickListener(new SchoolAdapter.OnItemClickListener() {
          @Override
          public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
              Fragment fragment = new MapSchoolFragment();
              Bundle bundle = new Bundle();
              String title = documentSnapshot.getString("schoolname");
              String vlat =  documentSnapshot.getString("schoollatitude");
              String vlong = documentSnapshot.getString("schoollongitude");
              bundle.putString("schoollatitude", vlat);
              bundle.putString("schoollongitude", vlong);
              bundle.putString("schoolname", title);
              fragment.setArguments(bundle);
              //Toast.makeText(getActivity(), title + " " + vlat + " " + vlong, Toast.LENGTH_SHORT).show();
              getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
          }
      });

        return v;
    }


    private void setRecyclerView(){
        Query query =SchoolRef;
        FirestoreRecyclerOptions<SchoolModel> options = new FirestoreRecyclerOptions.Builder<SchoolModel>()
                .setQuery(query, SchoolModel.class)
                .build();
        adapter = new SchoolAdapter(options);


    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

}
package com.example.firebasecrud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class GaleriaFotos extends AppCompatActivity {

    ArrayList<String> imagelist;
    RecyclerView recyclerView;
    StorageReference root;
    ProgressBar progressBar;
    ImageButton imageButton;
    ImageAdapter adapter;
    LinearLayoutManager mLayoutManager;
    android.content.Context Context;

    ImageButton imageButtonAdd;

    private static final int File = 1;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria_fotos);


        imageButton = (ImageButton) findViewById(R.id.backButton);

        imageButtonAdd = (ImageButton) findViewById(R.id.imageButtonAdd);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GaleriaFotos.this, MainActivity.class);
                startActivity(i);
            }
        });

        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GaleriaFotos.this, addImage.class);
                startActivity(i);
            }
        });


        Recycler();

    }



    private void Recycler() {
        mLayoutManager = new LinearLayoutManager(this);
        adapter = new ImageAdapter(imagelist, this);
        Content();
        deleteSwipe();
    }

    private void Content() {

        imagelist=new ArrayList<>();
        recyclerView= findViewById(R.id.recyclerview);
        adapter=new ImageAdapter(imagelist,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(null));
        progressBar= findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        FirebaseApp.initializeApp(Context);
        StorageReference listRef = FirebaseStorage.getInstance().getReference().child("Imagenes");

        listRef.listAll().addOnSuccessListener(listResult -> {
            for(StorageReference file:listResult.getItems()){
                file.getDownloadUrl().addOnSuccessListener(uri -> {
                    imagelist.add(uri.toString());
                    Log.e("Itemvalue",uri.toString());
                }).addOnSuccessListener(uri -> {
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                });
            }
        });
    }

    private void deleteSwipe() {

        ItemTouchHelper.SimpleCallback touchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                String deletedCourse = imagelist.get(viewHolder.getAdapterPosition());

                imagelist.remove(viewHolder.getAdapterPosition());

                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
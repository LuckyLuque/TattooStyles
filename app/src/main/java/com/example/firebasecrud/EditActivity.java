package com.example.firebasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditActivity extends AppCompatActivity {

    @BindView(R.id.nameEditText)
    EditText mNameEditText;

    @BindView(R.id.animeEditText)
    EditText mAnimeEditText;

    @BindView(R.id.urlEditText)
    EditText mUrlEditText;

    @BindView(R.id.descriptionEditText)
    EditText mDescriptionEditText;

    @BindView(R.id.characterButton)
    Button mCharacterButton;

    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        String mKey= Objects.requireNonNull(getIntent().getExtras()).getString("key");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(mKey);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Character charater = dataSnapshot.getValue(Character.class);

                if (charater.getName() != null) {
                    mNameEditText.setText(charater.getName());
                }

                if (charater.getAnime()!= null) {
                    mAnimeEditText.setText(charater.getAnime());
                }

                if (charater.getDescription() != null) {
                    mDescriptionEditText.setText(charater.getDescription());
                }

                mUrlEditText.setText(String.valueOf(charater.getUrl()));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(EditActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mCharacterButton.setOnClickListener(v -> {
            mDatabaseReference.child("name").setValue(mNameEditText.getText().toString());
            mDatabaseReference.child("email").setValue(mAnimeEditText.getText().toString());
            mDatabaseReference.child("description").setValue(mDescriptionEditText.getText().toString());
            mDatabaseReference.child("username").setValue(Integer.parseInt(mUrlEditText.getText().toString()));
            finish();
        });
    }
}
package com.example.firebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.characterImageView)
    ImageView mCharacterImageView;

    @BindView(R.id.backgroundImageView)
    ImageView mBackgroundImageView;

    @BindView(R.id.nameTextView)
    TextView mNameTextView;

    @BindView(R.id.descriptionTextView)
    TextView mDescriptionTextView;

    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        String mKey= Objects.requireNonNull(getIntent().getExtras()).getString("key");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(mKey);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Character charater = dataSnapshot.getValue(Character.class);

                assert charater != null;
                if (charater.getName() != null) {
                    mNameTextView.setText(charater.getName());
                }

                if (charater.getDescription() != null) {
                    mDescriptionTextView.setText(charater.getDescription());
                }

                int[] charactersImages= {R.drawable.tat1,R.drawable.tat4,R.drawable.tat3,R.drawable.tat5,R.drawable.tat6,R.drawable.tat7,R.drawable.tat8,R.drawable.tat9,
                        R.drawable.tat10,R.drawable.tat11,R.drawable.tat12,R.drawable.tat13};
                int[] backgroundsImages= {R.drawable.tat1,R.drawable.tat4,R.drawable.tat3,R.drawable.tat5,R.drawable.tat6,R.drawable.tat7,R.drawable.tat8,R.drawable.tat9,
                        R.drawable.tat10,R.drawable.tat11,R.drawable.tat12,R.drawable.tat13};

                if (charater.getUrl() < 12) {
                    mCharacterImageView.setImageResource(charactersImages[charater.getUrl()]);
                }

                if (charater.getUrl() < 12) {
                    mBackgroundImageView.setImageResource(backgroundsImages[charater.getUrl()]);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
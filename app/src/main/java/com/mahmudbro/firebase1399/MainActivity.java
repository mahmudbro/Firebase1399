package com.mahmudbro.firebase1399;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnSend;
    private EditText etMessage;
    private RecyclerView rvChat;
    private ChatAdapter chatAdapter;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference chatReference = database.getReference().child("chat");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSend = findViewById(R.id.btnSend);
        etMessage = findViewById(R.id.etMessage);
        rvChat = findViewById(R.id.rvChat);

        chatAdapter = new ChatAdapter(this);
        rvChat.setAdapter(chatAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvChat.setLayoutManager(layoutManager);

        getChatData();
    }

    private void getChatData() {

        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                List <Chat> temporalChat = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    Chat chat = data.getValue(Chat.class);
                    temporalChat.add(chat);
                }
                chatAdapter.setData(temporalChat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

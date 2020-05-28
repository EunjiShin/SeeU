package com.example.seeu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.seeu.MyReview.ListViewItem;
import com.example.seeu.MyReview.MyListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyReviewActivity extends AppCompatActivity {

    ListView myRList;
    ArrayList<ListViewItem> arrayList = new ArrayList<ListViewItem>();
    MyListAdapter arrayAdapter;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);

        /*Action Bar(Title bar) 받아와서 없애기*/
        ActionBar ab = getSupportActionBar();
        ab.hide();

        myRList = (ListView)findViewById(R.id.myRList);
        arrayAdapter = new MyListAdapter();
        myRList.setAdapter(arrayAdapter);

        db = FirebaseFirestore.getInstance();

        db.collection("Posting")
                .whereEqualTo("UserID", "lHntXjWawWvDAfc1hRpU")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String str=document.get("text").toString().trim();
                                //arrayAdapter.addItem(str,str,str);
                                arrayAdapter.addItem("울산 현대예술관 소공연장","A","짱 불편함 ;;");
                                myRList.invalidateViews();
                                Log.d("FireStore READ", document.getId() + " => " + document.getData());
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        /*
        * DB에서 현재 Login한 유저의 글 정보를 불러와서 List 항목 만들기
        * */
    }
}
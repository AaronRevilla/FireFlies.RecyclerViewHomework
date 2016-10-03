package com.example.aaron.recyclerviewhomework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.aaron.greendaoexample.db.User;
import com.example.aaron.recyclerView.UsersAdapter;

import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_USER_RESULT = 1;
    RecyclerView rvContacts;
    TextView layoutTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutTitle = ((TextView) findViewById(R.id.layoutTitle));

        rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        rvContacts.addItemDecoration(itemDecoration);
        rvContacts.setItemAnimator(new SlideInUpAnimator());

        // Create adapter passing in the sample user data
        UsersAdapter adapter = new UsersAdapter(this);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        rvContacts.setHasFixedSize(true);
        //rvContacts.setLayoutManager(new GridLayoutManager(this, 2));

    }

    public void addContact(View view) {
        Intent addUserIntent = new Intent(this, AddUserActivity.class);
        //addUserIntent.putExtra("Name", personName.getText().toString());
        addUserIntent.putExtra("isUpdate", false);
        startActivityForResult(addUserIntent, NEW_USER_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_USER_RESULT){
            if(resultCode == RESULT_OK){

                User newUser = new User();
                newUser.setName(data.getStringExtra("nameUser"));
                newUser.setAge(Integer.parseInt(data.getStringExtra("ageUser")));
                newUser.setPhone(data.getStringExtra("phoneUser"));
                newUser.setEmail(data.getStringExtra("emailUser"));
                newUser.setImg(data.getStringExtra("imgUser"));

                UsersAdapter adapter = ((UsersAdapter) rvContacts.getAdapter());
                adapter.addUser(newUser);
                rvContacts.setAdapter(adapter);
            }
        }
    }

    public void showLayout(View view) {

        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(this, findViewById(R.id.buttonShowItemsLayout));
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.pop_up_items, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                changeViewLayout(item.getItemId());
                return true;
            }
        });

        popup.show(); //showing popup menu

    }

    public void changeViewLayout(int id){
        switch (id){
            case R.id.vertical:
                layoutTitle.setText("Linear Layout Vertical");
                rvContacts.setLayoutManager(new LinearLayoutManager(this));
                break;

            case R.id.horizontal:
                layoutTitle.setText("Linear Layout Horizontal");
                rvContacts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                break;

            case R.id.grid:
                layoutTitle.setText("Grid Layout");
                rvContacts.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
                break;

            case R.id.staggered:
                layoutTitle.setText("Staggered Layout");
                rvContacts.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                break;
        }
    }
}

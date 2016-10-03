package com.example.aaron.recyclerView;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aaron.greendaoexample.db.DaoMaster;
import com.example.aaron.greendaoexample.db.DaoSession;
import com.example.aaron.greendaoexample.db.User;
import com.example.aaron.greendaoexample.db.UserDao;
import com.example.aaron.recyclerviewhomework.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Oscar Aaron Revilla Escalona on 10/2/2016.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    // Store a member variable for the contacts
    public List<User> mContacts;

    // Store the context for easy access
    public Context mContext;
    DaoSession dbSession;
    public int NUM_USERS = 25;

    public UsersAdapter(Context context){
        mContext = context;
        createDBConn();
        mContacts = loadUsersFromDB();
        if(mContacts.size() == 0){
            createUsers(NUM_USERS);
        }
    }

    public void createDBConn(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getContext(), "user-db-recyclerview-v1", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        dbSession = daoMaster.newSession();
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("DEBUG onCVH", viewType + "");
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout

        View contactView = inflater.inflate(R.layout.user_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final UsersAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        User contact = mContacts.get(position);
        final int positionAux = position;

        // Set item views based on your views and data model
        TextView userN = viewHolder.userName;
        TextView userP = viewHolder.userPhone;
        TextView userE = viewHolder.userEmail;
        TextView userA = viewHolder.userAge;
        ImageView userI = viewHolder.userImage;


        userN.setText("Name: " + contact.getName());
        userP.setText("Phone: " + contact.getPhone());
        userE.setText("Email:" + contact.getEmail());
        userA.setText("Age:" + contact.getAge().toString());
        Glide.with(getContext())
                .load(contact.getImg())
                .override(500, 500)
                .centerCrop()
                .placeholder(R.drawable.ic_account_circle_black_48dp)
                .into(userI);

        ImageButton button = viewHolder.messageButton;
        button.setImageResource(R.drawable.ic_delete_black_48dp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.onClick(v);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("DEBUG getItemViewType", super.getItemViewType(position)+ "");
        return super.getItemViewType(position);
    }

    public void removeUserAt(int position){
        User usr = mContacts.get(position);

        //eliminate from DB
        UserDao usrDao = dbSession.getUserDao();
        usrDao.deleteByKey(usr.getId());

        mContacts.remove(position);
        notifyItemRemoved(position);

    }

    public List<User> loadUsersFromDB(){
        UserDao userDao = dbSession.getUserDao();
        List<User> userList = userDao.loadAll();

        Collections.sort(userList, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u2.getInsertion_date().compareTo( u1.getInsertion_date());
            }
        });

        return userList;
    }

    public void addUser(User newUser){
        newUser.setInsertion_date(new Date());
        UserDao usrDao = dbSession.getUserDao();
        usrDao.insert(newUser);
        mContacts = loadUsersFromDB();
        notifyItemInserted(0);
    }

    public void createUsers(int numUsers){
        String[] nameList = {   "Oscar",
                                "Aaron",
                                "Fernando" ,
                                "Yendry",
                                "Chaz",
                                "Anand",
                                "Khuman",
                                "Whiliam"};
        String[] lastNameList = {   "SMITH",
                                    "JOHNSON",
                                    "JONES",
                                    "MILLER",
                                    "WILSON",
                                    "MOORE",
                                    "TAYLOR"};
        String[] imgList = {
                "http://img.timeinc.net/time/daily/2010/1011/poy_nomination_agassi.jpg",
                "http://engineering.unl.edu/images/staff/Kayla_Person-small.jpg",
                "https://d.ibtimes.co.uk/en/full/1356835/number-2-u-s-president-barack-obama-second-most-admired-person-planet.jpg",
                "https://assets.entrepreneur.com/content/16x9/822/1392752611-take-page-from-rock-refer-yourself-third-person.jpg",
                "https://si.wsj.net/public/resources/images/ED-AM674_person_G_20101206164729.jpg",
                "http://stanlemmens.nl/wp/wp-content/uploads/2014/07/bill-gates-wealthiest-person.jpg",
                "http://static.tvgcdn.net/mediabin/showcards/celebs/m-o/thumbs/michael-emerson_sc_768x1024.png",
                "http://static.tvgcdn.net/mediabin/showcards/celebs/g-i/thumbs/henry-ian-cusick_sc_768x1024.png",
                "http://www.ofwcmedia.com/1386-455-large/the-theology-of-the-body-and-the-single-person-cd-christopher-west.jpg",
                "https://lh5.googleusercontent.com/-89xTT1Ctbrk/AAAAAAAAAAI/AAAAAAAABcc/Kg0vilTzpKI/photo.jpg"
        };

        Integer[] ageList = {20 , 30, 40, 50, 60, 55, 35, 36, 54, 14, 36, 24, 23 ,26};

        for (int i = 0; i < numUsers; i++){
            User aux = new User();
            aux.setName(nameList[i%nameList.length] + " " + lastNameList[i%lastNameList.length]);
            aux.setEmail(nameList[i%nameList.length] + lastNameList[i%lastNameList.length] + "@gmail.com");
            aux.setPhone(String.valueOf(i * 10000));
            aux.setAge(ageList[i%ageList.length]);
            aux.setImg(imgList[i%imgList.length]);
            aux.setInsertion_date(new Date());
            UserDao usrDao = dbSession.getUserDao();
            usrDao.insert(aux);
        }
    }

    //ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {    // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView userImage;
        public TextView userName;
        public TextView userPhone;
        public TextView userEmail;
        public TextView userAge;

        public ImageButton messageButton;
        public Context context;
        public List<User> listContacts;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            userName = (TextView) itemView.findViewById(R.id.userName);
            userPhone = (TextView) itemView.findViewById(R.id.userPhone);
            userEmail = (TextView) itemView.findViewById(R.id.userEmail);
            userAge = (TextView) itemView.findViewById(R.id.userAge);
            userImage = (ImageView) itemView.findViewById(R.id.imageView);

            messageButton = (ImageButton) itemView.findViewById(R.id.message_button);



            this.context = context;
            this.listContacts = listContacts;
            // Attach a click listener to the entire row view
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); // gets item position

            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                //Contact user = mContacts.get(position);
                // We can access the data within the views
                //Log.d("DDD",  user.getName());
                removeUserAt(position);
            }
        }

    }
}

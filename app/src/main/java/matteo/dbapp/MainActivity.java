package matteo.dbapp;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "sample-db").build();
        AppDatabase.getAppDatabase(getApplicationContext());

        new PopulateDbAsync().execute();

    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }
    private User addUser(final AppDatabase db, User user) {
        db.userDao().insertAll(user);
        return user;
    }

    private class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Perform pre-adding operation here.
        }

        @Override
        protected Void doInBackground(Void... voids) {


            db.userDao().deleteAll();


            User user1 = new User();
            user1.setFirstName("Matteo");
            user1.setLastName("Sausto");
            user1.setUid(1);
            addUser(db, user1);

            User user2 = new User();
            user2.setFirstName("Alessandro");
            user2.setLastName("Frangiamone");
            user2.setUid(2);
            addUser(db, user2);

            User user3 = new User();
            user3.setFirstName("Matteo");
            user3.setLastName("Mazza");
            user3.setUid(3);
            addUser(db, user3);

            User user4 = new User();
            user4.setFirstName("Francesco");
            user4.setLastName("Riva");
            user4.setUid(4);
            addUser(db, user4);

            User user5 = new User();
            user5.setFirstName("Carlo");
            user5.setLastName("Cassanelli");
            user5.setUid(5);
            addUser(db, user5);

            Log.d("Db", "Create");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //To after addition operation here.
        }
    }

    private class FoundById extends AsyncTask<Integer, Void, User>{
        @Override
        protected User doInBackground(Integer... id) {
            User user = db.userDao().findById(id[0]);
            return user;
        }

        @Override
        protected void onPostExecute(User user){
            final TextView nameTexView = (TextView) findViewById(R.id.firstNameView);
            final TextView surnameTexView = (TextView) findViewById(R.id.lastNameView);
            nameTexView.setText(user.getFirstName());
            surnameTexView.setText(user.getLastName());
        }
    }

    public void onClick(View v) {
        int uId = Integer.parseInt(String.valueOf(((EditText) findViewById(R.id.userId)).getText()));
        new FoundById().execute(uId);
    }
}
package pradyumna.simhansapp.feedback;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import pradyumna.simhansapp.R;

public class Feedback extends AppCompatActivity {

    //Variables
    EditText nameT, emailT, phT, msgT;
    Button snd;
    TextView thnxText;

    //Database Linker

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        //Status bar Color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.orangeMain));
        }

        //Action Bar Object
        ActionBar actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#F1D548"));

        // Set BackgroundDrawable
        assert actionBar != null;
        actionBar.setBackgroundDrawable(colorDrawable);

        //Initialization
        nameT = (EditText) findViewById(R.id.editText);
        emailT = (EditText) findViewById(R.id.editText2);
        phT = (EditText) findViewById(R.id.editText3);
        msgT = (EditText) findViewById(R.id.editText4);
        snd = (Button) findViewById(R.id.button5);
        thnxText = (TextView) findViewById(R.id.thanksText);

        snd.setOnClickListener(v -> {
            String tst = msgT.getText().toString();
            if (tst == "" || tst.equals("")) {

                Context context = getBaseContext();
                CharSequence text = "Please Enter a Message";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else {

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("feedback");

                Person person = new Person();
                person.setName(nameT.getText().toString());
                person.setEmail(emailT.getText().toString());
                person.setPh(phT.getText().toString());
                person.setMsg(msgT.getText().toString());

                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

                // Creating new user node, which returns the unique key value
                String id = mDatabase.push().getKey();
                // new user node would be /users/$userid/

                // pushing user to 'users' node using the userId
                mDatabase.child(id).setValue(person);

                Context context = getApplicationContext();
                CharSequence text = "Thanks for your Feedback!";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();


                thnxText.setVisibility(View.VISIBLE);
                nameT.setText(" ");
                emailT.setText(" ");
                phT.setText(" ");
                msgT.setText(" ");


            }
        });

    }


    public static class Person {
        //name and address string
        private String name;
        private String email;
        private String ph;
        private String msg;

        public Person() {
            /*Blank default constructor essential for Firebase*/
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPh() {
            return ph;
        }

        public void setPh(String ph) {
            this.ph = ph;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
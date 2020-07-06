package org.d3ifcool.timework;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import java.util.Random;
import android.widget.TimePicker;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
    }


    public void createProfile(View view) {

        DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);

        try{
            Account account = databaseAdapter.getAccount();
            if(account.getmIsLogin() == 1) {
                Intent gotoMain = new Intent(this,MainActivity.class) ;
                startActivity(gotoMain);
            }else{

            }
        }catch (Exception e) {
            EditText username = (EditText) findViewById(R.id.username_edit_text);
            Quotes quotes = new Quotes(this);
            long i = databaseAdapter.addAccount(new Account(username.getText().toString(),
                    R.drawable.pencil,1,quotes.getmOtherQuotes().get(0)));

            if (i ==1 ) {
                Intent createProfile = new Intent(this,MainActivity.class) ;
                startActivity(createProfile);
            }else{
                startActivity(getIntent());
            }


        }


    }


    public void changeAvatar(View view) {
        ImageView avatar[] = new ImageView[4];
        final int avatarImage[] = {R.drawable.tanay,R.drawable.ic_launcher_background,R.drawable.back,R.drawable.pencil};
        AlertDialog.Builder showListAvatar = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.avatar_list,null);
        showListAvatar.setView(dialogView);
        showListAvatar.setTitle("Pilih Avatar");
        final AlertDialog alertDialog = showListAvatar.create();
        alertDialog.show();

        avatar[0] = (ImageView) dialogView.findViewById(R.id.avatar1_image_view);
        avatar[1] = (ImageView) dialogView.findViewById(R.id.avatar2_image_view);
        avatar[2] = (ImageView) dialogView.findViewById(R.id.avatar3_image_view);
        avatar[3] = (ImageView) dialogView.findViewById(R.id.avatar4_image_view);

        for(int n=0;n<avatar.length;n++){
            final int finalN = n;
            avatar[n].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.hide();
                    CircleImageView avatarImageView = (CircleImageView) findViewById(R.id.avatar_image_view);
                    avatarImageView.setImageResource(avatarImage[finalN]);
                }
            });
        }

    }


}

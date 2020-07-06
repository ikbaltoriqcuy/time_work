package org.d3ifcool.timework;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountAcivity extends Activity {

    private TextView mUsername;
    private  CircleImageView avatar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_acivity);

        Quotes quotes = new Quotes(this);


        //try{
            Account account = showAccount();

            avatar = (CircleImageView) findViewById(R.id.avatar_image_view) ;
            mUsername = (TextView) findViewById(R.id.username_text_view) ;
            TextView myQuote = (TextView) findViewById(R.id.quote_text_view);

            mUsername.setText(account.getmUsername().toString());
            avatar.setImageResource(account.getmImage());
            myQuote.setText(account.getQuote());

        //}catch (Exception e) {

        //}

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
                    Account account = showAccount();

                    CircleImageView avatarImageView = (CircleImageView) findViewById(R.id.avatar_image_view);
                    avatarImageView.setImageResource(avatarImage[finalN]);

                    account.setmImage(avatarImage[finalN]);
                    sendToDatabase(account,account.getmUsername());
                }
            });
        }

    }

    public void sendToDatabase(Account account,String currUsername){
        DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);
        databaseAdapter.updateAccount(account,currUsername);
    }

    public Account showAccount(){
        DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);
        Account account = databaseAdapter.getAccount();
        return account;
    }


    public void editQuote(View view) {

        AlertDialog.Builder listScheduleQuotes = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_list_quotes,null);
        ListView listSchedule = (ListView) dialogView.findViewById(R.id.list_quote);
        listSchedule.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                getQuotes()));

        listScheduleQuotes.setView(dialogView);

        AlertDialog alertDialog = listScheduleQuotes.create();
        alertDialog.show();
    }


    private ArrayList<String> getQuotes(){
        DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);
        Quotes quotes = null;
        try{
            quotes = databaseAdapter.getAllQuote();;
        }catch (Exception e) {
            //listView.setAdapter(new ArrayAdapter<String>(this,
            //android.R.layout.simple_list_item_1,quotes.getmOtherQuotes()));
        }

        return quotes.getmOtherQuotes();

    }

    public void editUsername(View view) {
        AlertDialog.Builder editUsername = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.edit_username,null);

        editUsername.setView(dialogView);
        editUsername.setTitle("Edit Username ");


        editUsername.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText edit = (EditText) dialogView.findViewById(R.id.username_edit_text);
                mUsername.setText(edit.getText().toString());

                Account account = showAccount();
                account.setmUsername(edit.getText().toString());

                DatabaseAdapter databaseAdapter = new DatabaseAdapter(AccountAcivity.this);
                databaseAdapter.updateAccount(account,showAccount().getmUsername());

            }
        });

        editUsername.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //nothing
            }
        });
        AlertDialog alertDialog = editUsername.create();
        alertDialog.show();


    }
}

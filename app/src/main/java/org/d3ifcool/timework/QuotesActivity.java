package org.d3ifcool.timework;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class QuotesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        ListView listView = (ListView) findViewById(R.id.list_quote);
        listView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,getQuotes()));


        AlertDialog.Builder showContentOption = new AlertDialog.Builder(this);
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.quote_content_option,null);
        showContentOption.setView(dialogView);

        final AlertDialog alertDialog = showContentOption.create();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {

                Button myQuote = (Button) dialogView.findViewById(R.id.my_quote_button);
                Button edit = (Button) dialogView.findViewById(R.id.edit_button);
                Button hapus = (Button) dialogView.findViewById(R.id.hapus_button);


                if(position >3){
                    alertDialog.show();

                    myQuote.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DatabaseAdapter databaseAdapter = new DatabaseAdapter(QuotesActivity.this);

                            Account account = databaseAdapter.getAccount();
                            account.setQuote(getQuotes().get(position));

                            databaseAdapter.updateAccount(account,account.getmUsername());


                            //refresh data
                            Intent intent = new Intent(QuotesActivity.this,MainActivity.class);
                            intent.putExtra("current_tab",2);
                            startActivity(intent);
                            QuotesActivity.this.finish();

                        }
                    });

                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent showAddQuoActivity = new Intent(QuotesActivity.this,AddQuoActivity.class);
                            showAddQuoActivity.putExtra("quote",getQuotes().get(position));
                            startActivity(showAddQuoActivity);
                            QuotesActivity.this.finish();
                        }
                    });

                    hapus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.hide();
                            final AlertDialog.Builder messageAlert = new AlertDialog.Builder(QuotesActivity.this);
                            View viewDeleteMessage = inflater.inflate(R.layout.delete_message,null);
                            messageAlert.setView(viewDeleteMessage);
                            messageAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //nothing
                                }
                            });
                            messageAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DatabaseAdapter databaseAdapter = new DatabaseAdapter(QuotesActivity.this);
                                    databaseAdapter.deleteQuote(getQuotes().get(position));

                                    //refresh data
                                    Intent intent = new Intent(QuotesActivity.this,MainActivity.class);
                                    intent.putExtra("current_tab",1);
                                    startActivity(intent);
                                    QuotesActivity.this.finish();

                                }
                            });

                            AlertDialog alert = messageAlert.create();
                            alert.show();


                        }
                    });

                }else {
                    edit.setActivated(false);
                    hapus.setActivated(false);

                }

                return false;
            }
        });

    }

    public void addQuotes(View view){
        Intent quotesActivity = new Intent(this,AddQuoActivity.class);
        startActivity(quotesActivity);
        this.finish();
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
}

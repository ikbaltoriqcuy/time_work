package org.d3ifcool.timework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddQuoActivity extends AppCompatActivity {

    private String quote;
    private Button databaseButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quo);
        databaseButton = (Button) findViewById(R.id.database_button);
        Bundle extra = getIntent().getExtras();

        if (extra !=null){
            databaseButton.setText("Update");
            quote = extra.getString("quote","");

            if(!quote.equals("")) {
                EditText dataQuote = (EditText) findViewById(R.id.quote_edit_text);
                dataQuote.setText(quote);
            }
        }else{
            databaseButton.setText("Tambah");
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("current_tab",1);
            startActivity(intent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }



    public void addQuotes(View view) {
        EditText dataQuote = (EditText) findViewById(R.id.quote_edit_text);
        DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);

        if(databaseButton.getText().equals("Tambah")){
            databaseAdapter.addQuote(dataQuote.getText().toString());

        }else if(databaseButton.getText().equals("Update")){
            databaseAdapter.updateQuote(dataQuote.getText().toString(),quote);
        }

        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("current_tab",1);
        this.finish();
        startActivity(intent);
    }

    public void reset (View view){
        EditText dataQuote = (EditText) findViewById(R.id.quote_edit_text);
        dataQuote.setText("");
    }
}

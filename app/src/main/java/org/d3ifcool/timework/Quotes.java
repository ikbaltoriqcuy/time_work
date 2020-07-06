package org.d3ifcool.timework;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by cool on 2/26/2018.
 */

public class Quotes {
    private ArrayList<String> mAllQuotes ;

    public Quotes(Context context) {
        String[] defaultQuotes = context.getResources().getStringArray(R.array.default_quotes);
        mAllQuotes = new ArrayList<>();

        for (String data:defaultQuotes)
            mAllQuotes.add(data);
    }


    public void setQuote(String quote){
        mAllQuotes.add(quote);
    }

    public ArrayList<String> getmOtherQuotes() {
        return mAllQuotes;
    }
}

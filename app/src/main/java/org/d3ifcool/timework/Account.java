package org.d3ifcool.timework;

/**
 * Created by cool on 3/3/2018.
 */

public class Account {
    private  String mUsername;
    private  int mImage ;
    private  int mIsLogin;
    private String quote;



    public Account(String mUsername, int mImage, int mIsLogin, String quote) {
        this.mUsername = mUsername;
        this.mImage = mImage;
        this.mIsLogin = mIsLogin;
        this.quote = quote;
    }



    public String getmUsername() {
        return mUsername;
    }

    public int getmImage() {
        return mImage;
    }

    public int getmIsLogin() {
        return mIsLogin;
    }

    public String getQuote() {
        return quote;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }

    public void setmIsLogin(int mIsLogin) {
        this.mIsLogin = mIsLogin;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}

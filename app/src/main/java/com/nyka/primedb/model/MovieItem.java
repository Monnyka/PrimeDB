package com.nyka.primedb.model;

public class MovieItem {

    private String mImageUrl;
    private String mCastName;
    private String mCharacter;


    public MovieItem (String ImageUrl, String CastName, String Character){

        mImageUrl = ImageUrl;
        mCastName= CastName;
        mCharacter =Character;
    }


    public String getmImageUrl(){

        return mImageUrl;
    }

    public String getmCastName(){
        return mCastName;
    }

    public String getmCharacter(){
        return mCharacter;
    }

}

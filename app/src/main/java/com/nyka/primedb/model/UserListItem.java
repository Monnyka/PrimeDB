package com.nyka.primedb.model;

public class UserListItem {

    private String mListName;
    private String mListDesc;
    private String mListTotal;
    private String mListID;

    public UserListItem(String listName, String listDesc, String listTotal, String listID){

        mListName=listName;
        mListDesc=listDesc;
        mListTotal=listTotal;
        mListID=listID;


    }

    public String getListName() {
        return mListName;
    }

    public String getListDesc() {
        return mListDesc;
    }

    public String getListTotal() {
        return mListTotal;
    }

    public String getListID(){return mListID;}
}

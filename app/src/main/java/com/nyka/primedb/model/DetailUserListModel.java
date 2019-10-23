package com.nyka.primedb.model;

public class DetailUserListModel {

    private String mListName;
    private String mListID;

    public DetailUserListModel(String listName, String listID){
        mListName=listName;
        mListID=listID;
    }

    public String getListName() {
        return mListName;
    }

    public String getListID(){
        return mListID;
    }
}

package com.nyka.primedb;

public class UserListItem {

    private String mListName;
    private String mListDesc;
    private String mListTotal;

    public UserListItem(String listName, String listDesc, String listTotal){

        mListName=listName;
        mListDesc=listDesc;
        mListTotal=listTotal;

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
}

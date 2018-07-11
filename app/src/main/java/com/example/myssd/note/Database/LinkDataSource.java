package com.example.myssd.note.Database;


import com.example.myssd.note.Link.MyLink;

import java.util.List;

import io.reactivex.Flowable;

public interface LinkDataSource {

    Flowable<MyLink> getOneLink(String link);
    Flowable<List<MyLink>> getAllLinks();
    void insertLink(MyLink... links);
    void updateLink(MyLink... links);
    void deleteLink(MyLink link);
    void deleteAllLinks();
}

package com.semmle.example;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;

public class TransactionExample {
  public void good(DatastoreService datastore) throws EntityNotFoundException {
    Transaction t = datastore.beginTransaction();
    try {
      Key key = KeyFactory.createKey("Foo", "Bar");
      Entity entity = datastore.get(key);
      entity.setProperty("baz", "10");
      
      datastore.put(t, entity);
      
      t.commit();
    } finally {
      if (t.isActive())
        t.rollback();
    }
  }
  
  public void bad(DatastoreService datastore) throws EntityNotFoundException {
    Transaction t = datastore.beginTransaction();
    try {
      Key key = KeyFactory.createKey("Foo", "Bar");
      Entity entity = datastore.get(key);
      entity.setProperty("baz", "10");
      
      datastore.put(t, entity);
    } finally {
      if (t.isActive())
        t.rollback();
    }
  }
}


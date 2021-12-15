package com.jules.migration;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.jules.models.Order;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;

@ChangeLog
public class OrderChangeLog001 {

    @ChangeSet(order = "001", id = "creating index on number of order", author = "jules-n")
    public void insertIndex(MongoDatabase mongo) {
        var collection = mongo.getCollection(Order.COLLECTION_NAME);
        collection.createIndex(new BasicDBObject("number", 1), new IndexOptions().unique(true));
    }
}

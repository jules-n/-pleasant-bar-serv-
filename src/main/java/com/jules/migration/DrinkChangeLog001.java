package com.jules.migration;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.jules.models.Drink;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;

@ChangeLog
public class DrinkChangeLog001 {

    @ChangeSet(order = "001", id = "creating unique index for drink name", author = "jules-n")
    public void insertIndex(MongoDatabase mongo) {
        var collection = mongo.getCollection(Drink.COLLECTION_NAME);
        collection.createIndex(new BasicDBObject("name", 1), new IndexOptions().unique(true));
    }
}

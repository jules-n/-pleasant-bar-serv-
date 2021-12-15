package com.jules.migration;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.jules.models.Sequence;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


@ChangeLog
public class SequenceChangeLog001 {

    @ChangeSet(order = "001", id = "insert first value of sequence", author = "jules-n")
    public void insertIndex(MongoDatabase mongo) {
        var collection = mongo.getCollection(Sequence.COLLECTION_NAME);
        Document document = new Document().append("count", 0);
        collection.insertOne(document);
    }
}

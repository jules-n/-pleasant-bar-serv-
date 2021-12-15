package com.jules.migration;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.jules.models.User;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ChangeLog
public class UserChangeLog001 {

    @ChangeSet(order = "001", id = "creating unique index on username", author = "jules-n")
    public void insertIndex(MongoDatabase mongo) {
        var collection = mongo.getCollection(User.COLLECTION_NAME);
        collection.createIndex(new BasicDBObject("username", 1), new IndexOptions().unique(true));
    }

    @ChangeSet(order = "002", id = "zero-admin creating", author = "jules-n")
    public void zeroAdminCreating(MongoDatabase mongo) {
        var collection = mongo.getCollection(User.COLLECTION_NAME);
        Document document = new Document();
        document.append("username", "alice-f");
        document.append("password", new BCryptPasswordEncoder().encode("rabbit"));
        document.append("role", "admin");
        collection.insertOne(document);
    }
}

package com.my.suicidenote.db;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.my.suicidenote.db.object.Advice;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrii_Manuiev
 */
public class AdviceHelper {

    private static final String ADVICE_COLLECTION_NAME = "advices";
    private static DBCollection advicesCollections = MongoDB.findCollection(ADVICE_COLLECTION_NAME);;
    
    public static List<Advice> getAdvices() {
        List<Advice> advices = new ArrayList<Advice>();

        DBCursor cursor = advicesCollections.find();
        while (cursor.hasNext()) {
            DBObject dbo = cursor.next();
            Advice advice = Advice.fromDBObject(dbo);
            advices.add(advice);
        }
        cursor.close();
        return advices;
    }
}
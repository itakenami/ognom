package ognom;

import ognom.db.Connection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.WriteResult;
import ognom.annotation.AnnotationSuport;
import org.bson.types.ObjectId;

/**
 *
 * @author itakenami
 */
public abstract class Model {

    protected ObjectId _id;
    private BasicDBObject dbo;
    private DBCollection coll;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId id) {
        _id = id;
    }

    public <T> T save() {

        coll = getConectionForCollection();

        try {
            dbo = DBObjectUtil.toBasicDBObject(this);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage()); //Erro de conversão
        }

        afterSave(dbo);


        if (get_id() == null) {

            try {
                WriteResult r = coll.save(dbo);
            } catch (Exception ex) {
                throw new RuntimeException("save() => Error trying to save in a collection: " + ex.getMessage());
            }

        } else {

            BasicDBObject bo = new BasicDBObject("_id", get_id());
            try {
                WriteResult r = coll.update(bo, dbo);
            } catch (Exception ex) {
                throw new RuntimeException("save() => Error trying to update in a collection: " + ex.getMessage());
            }

        }

        try {
            return DBObjectUtil.toModel(this.getClass(), dbo);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage()); //Erro de Conversão
        }

    }

    public <T> T delete() {

        coll = getConectionForCollection();

        BasicDBObject dbo = new BasicDBObject("_id", get_id());
        afterDelete(dbo);

        try {
            WriteResult r = coll.remove(dbo);
        } catch (Exception ex) {
            throw new RuntimeException("delete() => Error trying to delete in a collection: " + ex.getMessage());
        }


        try {
            return DBObjectUtil.toModel(this.getClass(), dbo);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage()); //Erro de Conversão
        }

    }

    public BasicDBObject getBasicDBObject() {
        if(dbo==null){
            dbo = new BasicDBObject();
        }
        return dbo;
    }

    protected DBCollection getDBCollection() {
        if(coll==null){
            coll = getConectionForCollection();
        }
        return coll;
    }
    
    public void addField(String key, Object value){
        getBasicDBObject().append(key, value);
    }
    
    public void removeField(String key){
        getBasicDBObject().removeField(key);
    }
    
    public Object getField(String key){
        return getBasicDBObject().get(key);
    }

    protected void afterSave(BasicDBObject dbo) {
    }

    protected void afterDelete(BasicDBObject dbo) {
    }
    
    private DBCollection getConectionForCollection() {
        try {
            return Connection.getInstance().getDB().getCollection(AnnotationSuport.getEntity(this.getClass()));
        } catch (Exception ex) {
            throw new RuntimeException("Error when trying to get the connection to a collection: " + ex.getMessage());
        }
    }
}

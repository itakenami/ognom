package ognom;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.util.JSON;
import java.util.ArrayList;
import java.util.List;
import ognom.annotation.AnnotationSuport;
import ognom.db.Connection;
import org.bson.types.ObjectId;

/**
 *
 * @author itakenami
 */
public class Finder {

    private static DBCollection getDBColl(Class<?> c) {
        String nome_collection = AnnotationSuport.getEntity(c);
        try {
            return Connection.getInstance().getDB().getCollection(nome_collection);
        } catch (Exception ex) {
            throw new RuntimeException("getDBColl(Class<?> c) => Error when trying to get the connection to a collection: " + ex.getMessage());
        }

    }

    private static Class getClassForName(Class<?> c, BasicDBObject dbo) {

        if (!dbo.containsField(CONST.CLASS_NAME)) {
            throw new RuntimeException("getClassForName(Class<?> c, BasicDBObject dbo) => Attribute " + CONST.CLASS_NAME + " not found.");
        }

        try {
            ClassLoader classLoader = c.getClassLoader();
            return classLoader.loadClass((String) dbo.get(CONST.CLASS_NAME));
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("getClassForName(Class<?> c, BasicDBObject dbo) => Model not found for attribute " + CONST.CLASS_NAME + " of the collection.");
        } catch (Exception ex2) {
            throw new RuntimeException("getClassForName(Class<?> c, BasicDBObject dbo) => Error trying to get the class defined in the attribute collection: " + ex2.getMessage());
        }


    }

    private static <T> T findOne(Class<?> c, BasicDBObject obj, BasicDBObject field) {

        BasicDBObject dbo;

        try {
            dbo = field==null?(BasicDBObject) getDBColl(c).findOne(obj):(BasicDBObject) getDBColl(c).findOne(obj,field);
        } catch (Exception ex) {
            System.out.println("findOne(Class<?> c, BasicDBObject obj, BasicDBObject field) => Error trying to search for findOne method (obj BasicDBObject) Mongo Drive: " + ex.getMessage());
            return null;
        }

        if (dbo == null) {
            return null;
        }

        try {
            return DBObjectUtil.toModel(getClassForName(c, dbo), dbo);
        } catch (Exception ex) {
            System.out.println("findOne(Class<?> c, BasicDBObject obj, BasicDBObject field) => Error trying to convert the BasicDBObject in Model: " + ex.getMessage());
            return null;
        }

    }

    private static <T> List<T> find(Class<?> c, BasicDBObject obj, BasicDBObject field) {


        List<T> lista = new ArrayList<T>();
        DBCursor dbc;

        try {
            //obj = null, busca por todos
            dbc = obj == null ? dbc = getDBColl(c).find() : field==null?getDBColl(c).find(obj):getDBColl(c).find(obj,field);
        } catch (Exception ex) {
            System.out.println("find(Class<?> c, BasicDBObject obj, BasicDBObject field) => Error trying to search for method find (obj BasicDBObject) Mongo Drive: " + ex.getMessage());
            return null;
        }

        //Verificar se dbc retirna null ou size 0
        if (dbc == null) {
            return null;
        }


        while (dbc.hasNext()) {

            BasicDBObject dbo = null;
            try {
                dbo = (BasicDBObject) dbc.next();
            } catch (Exception ex) {
                System.out.println("find(Class<?> c, BasicDBObject obj, BasicDBObject field) => Error when trying to get a record return find (obj BasicDBObject) Mongo Drive: " + ex.getMessage());
            }

            if (dbo != null) {

                try {
                    lista.add((T) DBObjectUtil.toModel(getClassForName(c, dbo), dbo));
                } catch (Exception ex) {
                    System.out.println("find(Class<?> c, BasicDBObject obj, BasicDBObject field) => Error trying to convert the BasicDBObject in Model: " + ex.getMessage());
                }

            }

        }

        return lista;

    }

    public static <T> T findOne(Class<T> c, String json) {
        return findOne(c, json, "");
    }
    
    public static <T> T findOne(Class<T> c, String json, String json_field) {
        BasicDBObject obj = null;
        BasicDBObject fields = null;
        try {
            obj = DBObjectUtil.toBasicDBObject(json);
            if(!json_field.equals("")){
                fields = DBObjectUtil.toBasicDBObject(json_field);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return findOne(c, obj, fields);
    }
    
    public static <T> List<T> find(Class<T> c, String json) {
        return find(c, json, "");
    }
    
    public static <T> List<T> find(Class<T> c, String json, String json_field) {
        BasicDBObject obj = null;
        BasicDBObject fields = null;
        try {
            obj = (BasicDBObject) JSON.parse(json);
            if(!json_field.equals("")){
                fields = DBObjectUtil.toBasicDBObject(json_field);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return find(c, obj, fields);
    }
    

    public static <T> T findOne(Class<T> c, Model m) {
        BasicDBObject obj = null;
        try {
            obj = DBObjectUtil.toBasicDBObject(m);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return findOne(c, obj, null);
    }


    public static <T> List<T> find(Class<T> c, Model m) {
        BasicDBObject obj = null;
        try {
            obj = DBObjectUtil.toBasicDBObject(m);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return find(c, obj, null);
    }

    public static <T> T findById(Class<T> c, ObjectId id) {
        BasicDBObject obj = new BasicDBObject("_id", id);
        return findOne(c, obj, null);
    }

    public static <T> List<T> findAll(Class<T> c) {
        BasicDBObject obj = null;
        return find(c, obj, null);
    }
}

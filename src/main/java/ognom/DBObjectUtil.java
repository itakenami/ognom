package ognom;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ognom.annotation.AnnotationSuport;
import ognom.annotation.Autoload;
import org.bson.types.ObjectId;

/**
 *
 * @author itakenami
 */
public class DBObjectUtil {
    
    private static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }
    
    
    private static String join(Object[] array, String separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = "";
        }

        int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return "";
        }

        StringBuilder buf = new StringBuilder(noOfItems * 16);

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    protected static BasicDBObject toBasicDBObject(String json) throws Exception {
        try {
            return (BasicDBObject) JSON.parse(json);
        } catch (Exception ex) {
            throw new Exception("toBasicDBObject(String json) => Error when trying to convert a JSON String BasicDBObject: " + ex.getMessage());
        }

    }

    protected static BasicDBObject toBasicDBObject(Model model) throws Exception {

        //Cria um BasicDBObject a partir de um model

        try {

            BasicDBObject dbo = new BasicDBObject();
            dbo.append(CONST.CLASS_NAME, model.getClass().getName());//Adiciona o atributo principal

            Field[] campos = model.getClass().getDeclaredFields();

            if (model.get_id() != null) {
                dbo.append("_id", model.get_id());
            }

            for (int x = 0; x < campos.length; x++) {

                Field campo = campos[x];
                campo.setAccessible(true);

                //Se o campo for transiente nao deve adicionar
                if (AnnotationSuport.isTransient(campo)) {
                    continue;
                }

                String nome_campo = campo.getName();
                Object valor_campo = campo.get(model);

                //se o valor do campo estiver vazio também não deve adicionar
                if (valor_campo == null) {
                    continue;
                }

                if (valor_campo instanceof List) { //Se o campo for uma Lista

                    ArrayList<Model> list = (ArrayList<Model>) valor_campo;

                    boolean aLoad = false;

                    if (campo.isAnnotationPresent(Autoload.class)) {
                        aLoad = true;
                    }

                    BasicDBList valor_campo_convertido = new BasicDBList();

                    for (int y = 0; y < list.size(); y++) {

                        Model m = list.get(y);

                        if (aLoad) {
                            Autoload autoload = AnnotationSuport.getAutoload(campo);
                            if (autoload.fields().length > 0) {
                                m = (Model) Finder.findOne(autoload.model(), "{_id:{$oid:'" + m.get_id().toString() + "'}}","{" + CONST.CLASS_NAME+ ":1," + join(autoload.fields(), ":1,") + ":1}");
                            } else {
                                m = (Model) Finder.findById(autoload.model(), m.get_id());
                            }

                        }

                        valor_campo_convertido.add(toBasicDBObject(m));
                    }

                    dbo.append(nome_campo, valor_campo_convertido);

                } else if (valor_campo instanceof Model) { //Se o campo for um campo

                    Model m = (Model) valor_campo;

                    if (campo.isAnnotationPresent(Autoload.class)) {

                        Autoload autoload = AnnotationSuport.getAutoload(campo);

                        if (autoload.fields().length > 0) {
                            m = (Model) Finder.findOne(autoload.model(), "{_id:{$oid:'" + m.get_id().toString() + "'}}","{" + CONST.CLASS_NAME+ ":1," + join(autoload.fields(), ":1,") + ":1}");
                        } else {
                            m = (Model) Finder.findById(autoload.model(), m.get_id());
                        }


                    }

                    dbo.append(nome_campo, toBasicDBObject(m));

                } else { //Se for um atributo normal

                    dbo.append(nome_campo, valor_campo);

                }
            }

            Iterator<String> it = model.getBasicDBObject().keySet().iterator();
            while (it.hasNext()) {
                String nome_campo = it.next();
                dbo.append(nome_campo, model.getBasicDBObject().get(nome_campo));
            }

            return dbo;

        } catch (IllegalArgumentException ex1) {
            throw new Exception("toBasicDBObject(Model model) => Error when trying to convert a Model in BasicDBObject (IllegalArgumentException): " + ex1.getMessage());
        } catch (IllegalAccessException ex2) {
            throw new Exception("toBasicDBObject(Model model) => Error when trying to convert a Model in BasicDBObject (IllegalAccessException): " + ex2.getMessage());
        } catch (Exception ex3) {
            throw new Exception("toBasicDBObject(Model model) => Error when trying to convert a Model in BasicDBObject: " + ex3.getMessage());
        }
    }

    protected static <T> T toModel(Class<?> c, DBObject dbo) throws Exception {

        try {

            //Cria o objeto de Model a ser retornado
            Constructor cons = c.getDeclaredConstructor();
            cons.setAccessible(true);
            T mo = (T) cons.newInstance();

            //Obtem as chaves dos campos do DBObject
            Iterator<String> it = dbo.keySet().iterator();
            while (it.hasNext()) {

                String nome_campo = it.next();
                Object valor_campo = dbo.get(nome_campo);

                if (nome_campo.equals(CONST.CLASS_NAME)) {
                    continue;
                }

                Field f;

                if (valor_campo instanceof ObjectId) {
                    Method m = mo.getClass().getMethod("set_id", ObjectId.class);
                    m.invoke(mo, valor_campo);
                    continue;
                } else {
                    try {//Verifica se o campo existe no model
                        f = mo.getClass().getDeclaredField(nome_campo);
                        f.setAccessible(true);
                    } catch (NoSuchFieldException ex) {
                        //Se o campo não exsite adiciona no dbo do model
                        Model obj_model = (Model) mo;
                        obj_model.getBasicDBObject().append(nome_campo, valor_campo);
                        continue;
                    }
                }

                if (valor_campo instanceof BasicDBList) { //Se for uma lista de objetos


                    BasicDBList cmp = (BasicDBList) valor_campo;
                    List<Model> am = new ArrayList<Model>();

                    for (int x = 0; x < cmp.size(); x++) {

                        BasicDBObject bo = (BasicDBObject) cmp.get(x);


                        ClassLoader classLoader = c.getClassLoader();
                        Class class_name = classLoader.loadClass((String) bo.get(CONST.CLASS_NAME));


                        Model m = (Model) toModel(class_name, bo);
                        am.add(m);

                    }

                    f.set(mo, am);

                } else if (valor_campo instanceof BasicDBObject) {


                    BasicDBObject cmp = (BasicDBObject) valor_campo;

                    ClassLoader classLoader = c.getClassLoader();
                    Class class_name = classLoader.loadClass((String) cmp.get(CONST.CLASS_NAME));

                    f.set(mo, toModel(class_name, (DBObject) valor_campo));

                } else {

                    f.set(mo, valor_campo);

                }
            }

            return mo;
        } catch (Exception ex) {
            throw new Exception("toModel(Class<?> c, DBObject dbo) => Error when trying to convert a DBOBJECT in Model: " + ex.toString());
        }


    }
}

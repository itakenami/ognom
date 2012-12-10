package ognom.annotation;

import java.lang.reflect.Field;

/**
 *
 * @author itakenami
 */
public class AnnotationSuport {
    
     public static String getEntity(Class<?> e) {
            Entity anotacao = e.getAnnotation(Entity.class);
            if(anotacao==null){
                return e.getSimpleName().toLowerCase();
            }
            if(anotacao.name()==null){
                return e.getSimpleName().toLowerCase();
            }else{
                return anotacao.name();
            }
     }
     
     public static boolean isTransient(Field campo){
         return campo.isAnnotationPresent(Transient.class);
     }
     
     public static Autoload getAutoload(Field campo) {
         return campo.getAnnotation(Autoload.class);
     }
     
}

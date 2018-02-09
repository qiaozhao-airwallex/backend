package lemonstream.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;

public class MyBeanUtils extends BeanUtilsBean{

    public static void nullAndArrayAwareBeanCopy(Object dest, Object source) throws Exception {
        new BeanUtilsBean() {
            @Override
            public void copyProperty(Object dest, String name, Object value)
                    throws IllegalAccessException, InvocationTargetException {
                if(value != null) {
                    if (!(value instanceof Collection)) {
                        super.copyProperty(dest, name, value);
                    } else {
                        try {
                            Object destProperty = getPropertyUtils().getProperty(dest, name);
                            Collection destCollection = (Collection) destProperty;
                            Collection srcCollection = ((Collection) value);
                            destCollection.retainAll(srcCollection);
                            Iterator srcCollectionIter = srcCollection.iterator();
                            while (srcCollectionIter.hasNext()) {
                                Object srcCollectionItem = srcCollectionIter.next();
                                Object destCollectionItem = findField(destCollection, "id", srcCollectionItem);
                                if (destCollectionItem != null) {
                                    nullAndArrayAwareBeanCopy(destCollectionItem, srcCollectionItem);
                                } else {
                                    destCollection.add(srcCollectionItem);
                                }
                            }
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            private Object findField(Collection destCollection, String matchFieldName, Object matchObject) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
                Iterator iter = destCollection.iterator();
                while (iter.hasNext()) {
                    Object destItem = iter.next();
                    Object idProperty = getPropertyUtils().getSimpleProperty(destItem, matchFieldName);
                    Object matchValue = getPropertyUtils().getSimpleProperty(matchObject, matchFieldName);
                    if (idProperty != null && idProperty.equals(matchValue)) {
                        return destItem;
                    }
                }
                return null;
            }
        }.copyProperties(dest, source);
    }
}

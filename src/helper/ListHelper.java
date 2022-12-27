package helper;

import java.util.List;

public abstract class ListHelper {

    public static <E> boolean allValuesEqual(List<E> list) {
        if (!list.isEmpty()) {
            E comparatorObject = list.get(0);
            for (E object : list) {
                if (object == null || !object.equals(comparatorObject)) {
                    return false;
                }
            }
        }
        return true;
    }
}

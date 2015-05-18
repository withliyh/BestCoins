package li.withliyh.bestcoins.db.dao;

import li.withliyh.bestcoins.db.Check;
import li.withliyh.bestcoins.db.ToDo;

/**
 * Created by Administrator on 2015/5/18.
 */
public class CheckHelper {

    public void addCheck(Check check) {
        if (check != null) check.save();
    }

    public void addBatchCheck(ToDo toDo) {

    }
}

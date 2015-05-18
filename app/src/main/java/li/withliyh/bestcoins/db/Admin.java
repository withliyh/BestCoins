package li.withliyh.bestcoins.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Administrator on 2015/5/18.
 */

@Table(name = "Admin")
public class Admin extends Model {
    @Column(name = "Name")
    public String name;
}

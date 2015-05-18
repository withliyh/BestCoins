package li.withliyh.bestcoins.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by Administrator on 2015/5/18.
 */
@Table(name = "ToDo")
public class ToDo extends Model{
    @Column(name = "Patient")
    public Patients patient;

    @Column(name = "TotalCount")
    public int totalCount;

    @Column (name = "ConsumeCount")
    public int consumeCount;

    @Column (name = "Check")
    public List<Check> checkList;
}

package li.withliyh.bestcoins.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/5/18.
 */
@Table(name = "Check")
public class Check extends Model {

    @Column(name = "Date")
    public Date date;

    @Column(name = "Admin")
    public Admin admin;

    @Column(name = "Patient")
    public Patients patient;

    @Column(name = "ConsumeMoney")
    public int consumeMoney;

    @Column(name = "PrePay")
    public boolean prePay;

    @Column(name = "HasPay")
    public boolean hasPay;

    @Column(name = "PayDate")
    public Date payDate;

    @Column(name = "Materials")
    public List<Materials> materials;

    @Column(name = "ToDo")
    public ToDo toDo;

}

package cn.jae.trouristmap.greendao.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @ProjectName : TouristMap
 * @Author : Jae
 * @Time : 2023/4/28 11:41
 * @Description :
 */
@Entity
public class TrouristListTable {
    @Id
    private Long id;
    private String trouristName;
    private String trouristCreateTime;
    private String trouristModifyTime;
    private int position;
    @Generated(hash = 1814762429)
    public TrouristListTable(Long id, String trouristName,
            String trouristCreateTime, String trouristModifyTime, int position) {
        this.id = id;
        this.trouristName = trouristName;
        this.trouristCreateTime = trouristCreateTime;
        this.trouristModifyTime = trouristModifyTime;
        this.position = position;
    }
    @Generated(hash = 1162487755)
    public TrouristListTable() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTrouristName() {
        return this.trouristName;
    }
    public void setTrouristName(String trouristName) {
        this.trouristName = trouristName;
    }
    public String getTrouristCreateTime() {
        return this.trouristCreateTime;
    }
    public void setTrouristCreateTime(String trouristCreateTime) {
        this.trouristCreateTime = trouristCreateTime;
    }
    public String getTrouristModifyTime() {
        return this.trouristModifyTime;
    }
    public void setTrouristModifyTime(String trouristModifyTime) {
        this.trouristModifyTime = trouristModifyTime;
    }
    public int getPosition() {
        return this.position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
}

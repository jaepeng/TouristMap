package cn.jae.trouristMap.greendao.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @ProjectName : TouristMap
 * @Author : Jae
 * @Time : 2023/4/28 11:44
 * @Description :
 */
@Entity
public class TrouristDetailTable {
    @Id
    private Long id;
    private String latitude;
    private String longitude;
    private String tips;
    private String targetPlaceName;
    private String creaetDate;
    private String lastModifiedDate;
    private int position;
    @Generated(hash = 1189847079)
    public TrouristDetailTable(Long id, String latitude, String longitude,
            String tips, String targetPlaceName, String creaetDate,
            String lastModifiedDate, int position) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tips = tips;
        this.targetPlaceName = targetPlaceName;
        this.creaetDate = creaetDate;
        this.lastModifiedDate = lastModifiedDate;
        this.position = position;
    }
    @Generated(hash = 1110533496)
    public TrouristDetailTable() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLatitude() {
        return this.latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongitude() {
        return this.longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getTips() {
        return this.tips;
    }
    public void setTips(String tips) {
        this.tips = tips;
    }
    public String getTargetPlaceName() {
        return this.targetPlaceName;
    }
    public void setTargetPlaceName(String targetPlaceName) {
        this.targetPlaceName = targetPlaceName;
    }
    public String getCreaetDate() {
        return this.creaetDate;
    }
    public void setCreaetDate(String creaetDate) {
        this.creaetDate = creaetDate;
    }
    public String getLastModifiedDate() {
        return this.lastModifiedDate;
    }
    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    public int getPosition() {
        return this.position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
}

package com.example.readbigfile;

import java.util.List;

/**
 * Created by TanJieXi on 2018/4/8.
 */

public class Bean {

    private List<UserBean> user;
    private List<UserinfoBean> userinfo;

    @Override
    public String toString() {
        return "Bean{" +
                "user=" + user +
                ", userinfo=" + userinfo +
                '}';
    }

    public List<UserBean> getUser() {
        return user;
    }

    public void setUser(List<UserBean> user) {
        this.user = user;
    }

    public List<UserinfoBean> getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(List<UserinfoBean> userinfo) {
        this.userinfo = userinfo;
    }

    public static class UserBean {
        @Override
        public String toString() {
            return "UserBean{" +
                    "address='" + address + '\'' +
                    ", birthControl='" + birthControl + '\'' +
                    ", birthday='" + birthday + '\'' +
                    ", children=" + children +
                    ", createdate='" + createdate + '\'' +
                    ", five_insured=" + five_insured +
                    ", general=" + general +
                    ", heigth=" + heigth +
                    ", hiv='" + hiv + '\'' +
                    ", holergasia='" + holergasia + '\'' +
                    ", hypertension=" + hypertension +
                    ", id=" + id +
                    ", identityid='" + identityid + '\'' +
                    ", identityidheadimg='" + identityidheadimg + '\'' +
                    ", jwhname='" + jwhname + '\'' +
                    ", low_insured=" + low_insured +
                    ", mastercardid='" + mastercardid + '\'' +
                    ", name='" + name + '\'' +
                    ", nation='" + nation + '\'' +
                    ", oldMan=" + oldMan +
                    ", physical='" + physical + '\'' +
                    ", pregnant='" + pregnant + '\'' +
                    ", region_code='" + region_code + '\'' +
                    ", sex='" + sex + '\'' +
                    ", sugarDiabetes=" + sugarDiabetes +
                    ", tel='" + tel + '\'' +
                    ", tuberculosis=" + tuberculosis +
                    ", villagesName='" + villagesName + '\'' +
                    ", waist=" + waist +
                    ", wealth='" + wealth + '\'' +
                    ", img='" + img + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", nowaddr='" + nowaddr + '\'' +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    '}';
        }

        /**
         * address : 四川省郫县红光镇铁门３组８４号
         * birthControl : 2
         * birthday : 1973-10-28
         * children : 2
         * createdate : 2016-11-27 12:59:36.0
         * five_insured : 2
         * general : 2
         * heigth : 166.5
         * hiv : 2
         * holergasia : 2
         * hypertension : 2
         * id : 20788
         * identityid : 510921197310289114
         * identityidheadimg : http://112.45.156.185:8288/files/images/20788/20180313134845888243.jpg
         * jwhname : 新村河边街社区居委会
         * low_insured : 2
         * mastercardid : 510921197310289114
         * name : 江勇
         * nation : 汉
         * oldMan : 2
         * physical : 2
         * pregnant : 2
         * region_code : 510106026002
         * sex : 男
         * sugarDiabetes : 2
         * tel : 18200130138
         * tuberculosis : 2
         * villagesName : 人民北路街道
         * waist : 32
         * wealth : 2
         * img : http://api.znjtys.com:80/files/images/24220/20171204034211773374.jpeg
         * nickname : 上善若水
         * nowaddr : 四川省成都市
         * phoneNumber : 028-5555555
         */


        private String address;
        private String birthControl;
        private String birthday;
        private int children;
        private String createdate;
        private int five_insured;
        private int general;
        private double heigth;
        private String hiv;
        private String holergasia;
        private int hypertension;
        private int id;
        private String identityid;
        private String identityidheadimg;
        private String jwhname;
        private int low_insured;
        private String mastercardid;
        private String name;
        private String nation;
        private int oldMan;
        private String physical;
        private String pregnant;
        private String region_code;
        private String sex;
        private int sugarDiabetes;
        private String tel;
        private int tuberculosis;
        private String villagesName;
        private int waist;
        private String wealth;
        private String img;
        private String nickname;
        private String nowaddr;
        private String phoneNumber;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBirthControl() {
            return birthControl;
        }

        public void setBirthControl(String birthControl) {
            this.birthControl = birthControl;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getChildren() {
            return children;
        }

        public void setChildren(int children) {
            this.children = children;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public int getFive_insured() {
            return five_insured;
        }

        public void setFive_insured(int five_insured) {
            this.five_insured = five_insured;
        }

        public int getGeneral() {
            return general;
        }

        public void setGeneral(int general) {
            this.general = general;
        }

        public double getHeigth() {
            return heigth;
        }

        public void setHeigth(double heigth) {
            this.heigth = heigth;
        }

        public String getHiv() {
            return hiv;
        }

        public void setHiv(String hiv) {
            this.hiv = hiv;
        }

        public String getHolergasia() {
            return holergasia;
        }

        public void setHolergasia(String holergasia) {
            this.holergasia = holergasia;
        }

        public int getHypertension() {
            return hypertension;
        }

        public void setHypertension(int hypertension) {
            this.hypertension = hypertension;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIdentityid() {
            return identityid;
        }

        public void setIdentityid(String identityid) {
            this.identityid = identityid;
        }

        public String getIdentityidheadimg() {
            return identityidheadimg;
        }

        public void setIdentityidheadimg(String identityidheadimg) {
            this.identityidheadimg = identityidheadimg;
        }

        public String getJwhname() {
            return jwhname;
        }

        public void setJwhname(String jwhname) {
            this.jwhname = jwhname;
        }

        public int getLow_insured() {
            return low_insured;
        }

        public void setLow_insured(int low_insured) {
            this.low_insured = low_insured;
        }

        public String getMastercardid() {
            return mastercardid;
        }

        public void setMastercardid(String mastercardid) {
            this.mastercardid = mastercardid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public int getOldMan() {
            return oldMan;
        }

        public void setOldMan(int oldMan) {
            this.oldMan = oldMan;
        }

        public String getPhysical() {
            return physical;
        }

        public void setPhysical(String physical) {
            this.physical = physical;
        }

        public String getPregnant() {
            return pregnant;
        }

        public void setPregnant(String pregnant) {
            this.pregnant = pregnant;
        }

        public String getRegion_code() {
            return region_code;
        }

        public void setRegion_code(String region_code) {
            this.region_code = region_code;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getSugarDiabetes() {
            return sugarDiabetes;
        }

        public void setSugarDiabetes(int sugarDiabetes) {
            this.sugarDiabetes = sugarDiabetes;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public int getTuberculosis() {
            return tuberculosis;
        }

        public void setTuberculosis(int tuberculosis) {
            this.tuberculosis = tuberculosis;
        }

        public String getVillagesName() {
            return villagesName;
        }

        public void setVillagesName(String villagesName) {
            this.villagesName = villagesName;
        }

        public int getWaist() {
            return waist;
        }

        public void setWaist(int waist) {
            this.waist = waist;
        }

        public String getWealth() {
            return wealth;
        }

        public void setWealth(String wealth) {
            this.wealth = wealth;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getNowaddr() {
            return nowaddr;
        }

        public void setNowaddr(String nowaddr) {
            this.nowaddr = nowaddr;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }

    public static class UserinfoBean {
        @Override
        public String toString() {
            return "UserinfoBean{" +
                    "birthday='" + birthday + '\'' +
                    ", cardID='" + cardID + '\'' +
                    ", createdate='" + createdate + '\'' +
                    ", credentials='" + credentials + '\'' +
                    ", edittime='" + edittime + '\'' +
                    ", gendercode='" + gendercode + '\'' +
                    ", hfx='" + hfx + '\'' +
                    ", hid=" + hid +
                    ", hjaddr='" + hjaddr + '\'' +
                    ", householderrelationship=" + householderrelationship +
                    ", hrstatus=" + hrstatus +
                    ", id=" + id +
                    ", isflowing=" + isflowing +
                    ", ispoor=" + ispoor +
                    ", jobcode=" + jobcode +
                    ", mastercardid='" + mastercardid + '\'' +
                    ", name='" + name + '\'' +
                    ", nationcode='" + nationcode + '\'' +
                    ", nationother='" + nationother + '\'' +
                    ", regionCode='" + regionCode + '\'' +
                    ", state='" + state + '\'' +
                    ", userid='" + userid + '\'' +
                    ", uuid='" + uuid + '\'' +
                    ", persontel='" + persontel + '\'' +
                    ", nowaddr='" + nowaddr + '\'' +
                    ", archiving_time='" + archiving_time + '\'' +
                    ", bloodtype=" + bloodtype +
                    ", drinkingwater=" + drinkingwater +
                    ", educationcode=" + educationcode +
                    ", fueltype=" + fueltype +
                    ", hukouflag=" + hukouflag +
                    ", kitchenexhaust=" + kitchenexhaust +
                    ", livestockcolumn=" + livestockcolumn +
                    ", marrystatuscode=" + marrystatuscode +
                    ", population_class='" + population_class + '\'' +
                    ", syntrophus=" + syntrophus +
                    ", toilet=" + toilet +
                    '}';
        }

        /**
         * birthday : 1973-10-28
         * cardID : 510921197310289114
         * createdate : 2018-03-13 13:48:42.0
         * credentials : 1
         * edittime : 2018-03-13 13:48:42.0
         * gendercode : 1
         * hfx :
         * hid : 1008
         * hjaddr : 四川省郫县红光镇铁门３组８４号
         * householderrelationship : 27
         * hrstatus : 0
         * id : 989426
         * isflowing : 0
         * ispoor : 1
         * jobcode : -1
         * mastercardid : 510921197310289114
         * name : 江勇
         * nationcode : 01
         * nationother : 汉
         * regionCode :
         * state : 0
         * userid : 20788
         * uuid : 301afc59-2682-11e8-a356-0021f6c01c98
         * persontel : 13333333333
         * nowaddr : mk
         * archiving_time : 2017-10-19 10:46:49.0
         * bloodtype : 0
         * drinkingwater : 0
         * educationcode : 0
         * fueltype : 0
         * hukouflag : 0
         * kitchenexhaust : 0
         * livestockcolumn : 0
         * marrystatuscode : 0
         * population_class : 1,
         * syntrophus : 0
         * toilet : 0
         */


        private String birthday;
        private String cardID;
        private String createdate;
        private String credentials;
        private String edittime;
        private String gendercode;
        private String hfx;
        private int hid;
        private String hjaddr;
        private int householderrelationship;
        private int hrstatus;
        private int id;
        private int isflowing;
        private int ispoor;
        private int jobcode;
        private String mastercardid;
        private String name;
        private String nationcode;
        private String nationother;
        private String regionCode;
        private String state;
        private String userid;
        private String uuid;
        private String persontel;
        private String nowaddr;
        private String archiving_time;
        private int bloodtype;
        private int drinkingwater;
        private int educationcode;
        private int fueltype;
        private int hukouflag;
        private int kitchenexhaust;
        private int livestockcolumn;
        private int marrystatuscode;
        private String population_class;
        private int syntrophus;
        private int toilet;

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getCardID() {
            return cardID;
        }

        public void setCardID(String cardID) {
            this.cardID = cardID;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getCredentials() {
            return credentials;
        }

        public void setCredentials(String credentials) {
            this.credentials = credentials;
        }

        public String getEdittime() {
            return edittime;
        }

        public void setEdittime(String edittime) {
            this.edittime = edittime;
        }

        public String getGendercode() {
            return gendercode;
        }

        public void setGendercode(String gendercode) {
            this.gendercode = gendercode;
        }

        public String getHfx() {
            return hfx;
        }

        public void setHfx(String hfx) {
            this.hfx = hfx;
        }

        public int getHid() {
            return hid;
        }

        public void setHid(int hid) {
            this.hid = hid;
        }

        public String getHjaddr() {
            return hjaddr;
        }

        public void setHjaddr(String hjaddr) {
            this.hjaddr = hjaddr;
        }

        public int getHouseholderrelationship() {
            return householderrelationship;
        }

        public void setHouseholderrelationship(int householderrelationship) {
            this.householderrelationship = householderrelationship;
        }

        public int getHrstatus() {
            return hrstatus;
        }

        public void setHrstatus(int hrstatus) {
            this.hrstatus = hrstatus;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsflowing() {
            return isflowing;
        }

        public void setIsflowing(int isflowing) {
            this.isflowing = isflowing;
        }

        public int getIspoor() {
            return ispoor;
        }

        public void setIspoor(int ispoor) {
            this.ispoor = ispoor;
        }

        public int getJobcode() {
            return jobcode;
        }

        public void setJobcode(int jobcode) {
            this.jobcode = jobcode;
        }

        public String getMastercardid() {
            return mastercardid;
        }

        public void setMastercardid(String mastercardid) {
            this.mastercardid = mastercardid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNationcode() {
            return nationcode;
        }

        public void setNationcode(String nationcode) {
            this.nationcode = nationcode;
        }

        public String getNationother() {
            return nationother;
        }

        public void setNationother(String nationother) {
            this.nationother = nationother;
        }

        public String getRegionCode() {
            return regionCode;
        }

        public void setRegionCode(String regionCode) {
            this.regionCode = regionCode;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getPersontel() {
            return persontel;
        }

        public void setPersontel(String persontel) {
            this.persontel = persontel;
        }

        public String getNowaddr() {
            return nowaddr;
        }

        public void setNowaddr(String nowaddr) {
            this.nowaddr = nowaddr;
        }

        public String getArchiving_time() {
            return archiving_time;
        }

        public void setArchiving_time(String archiving_time) {
            this.archiving_time = archiving_time;
        }

        public int getBloodtype() {
            return bloodtype;
        }

        public void setBloodtype(int bloodtype) {
            this.bloodtype = bloodtype;
        }

        public int getDrinkingwater() {
            return drinkingwater;
        }

        public void setDrinkingwater(int drinkingwater) {
            this.drinkingwater = drinkingwater;
        }

        public int getEducationcode() {
            return educationcode;
        }

        public void setEducationcode(int educationcode) {
            this.educationcode = educationcode;
        }

        public int getFueltype() {
            return fueltype;
        }

        public void setFueltype(int fueltype) {
            this.fueltype = fueltype;
        }

        public int getHukouflag() {
            return hukouflag;
        }

        public void setHukouflag(int hukouflag) {
            this.hukouflag = hukouflag;
        }

        public int getKitchenexhaust() {
            return kitchenexhaust;
        }

        public void setKitchenexhaust(int kitchenexhaust) {
            this.kitchenexhaust = kitchenexhaust;
        }

        public int getLivestockcolumn() {
            return livestockcolumn;
        }

        public void setLivestockcolumn(int livestockcolumn) {
            this.livestockcolumn = livestockcolumn;
        }

        public int getMarrystatuscode() {
            return marrystatuscode;
        }

        public void setMarrystatuscode(int marrystatuscode) {
            this.marrystatuscode = marrystatuscode;
        }

        public String getPopulation_class() {
            return population_class;
        }

        public void setPopulation_class(String population_class) {
            this.population_class = population_class;
        }

        public int getSyntrophus() {
            return syntrophus;
        }

        public void setSyntrophus(int syntrophus) {
            this.syntrophus = syntrophus;
        }

        public int getToilet() {
            return toilet;
        }

        public void setToilet(int toilet) {
            this.toilet = toilet;
        }
    }
}

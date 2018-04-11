package com.example.readbigfile;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
                "userinfo=" + userinfo +
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
        /**
         * address : 四川省仪陇县马鞍镇大梁村一组1号
         * birthControl : 2
         * birthday : 1986-06-01
         * children : 2
         * createdate : 2015-08-13 22:27:03.0
         * five_insured : 2
         * general : 2
         * heigth : 174
         * hiv : 2
         * holergasia : 2
         * hypertension : 2
         * id : 91
         * identityid : 511324198606010479
         * identityidheadimg : http://api.znjtys.com:80/files/images/91/20180319211239688222.jpg
         * low_insured : 2
         * mastercardid : 511324198606010479
         * name : 唐海波
         * nation : 汉
         * nickname : 唐海波
         * oldMan : 2
         * physical : 2
         * pregnant : 2
         * sex : 男
         * sugarDiabetes : 2
         * tel : 15884593445
         * tuberculosis : 2
         * waist : 65
         * wealth : 2
         * img : https://znjtys.com/view/images/dct.png
         * nowaddr : 燕郊镇
         * nums : C53380
         * phoneNumber : q
         * grouping : 04
         * jwhname : 青石桥社区居委会
         * region_code : 510104020007
         * villagesName : 督院街街道办事处
         */

        private String address;
        private String birthControl;
        private String birthday;
        private String children;
        private String createdate;
        private String five_insured;
        private String general;
        private String heigth;
        private String hiv;
        private String holergasia;
        private String hypertension;
        private String id;
        private String identityid;
        private String identityidheadimg;
        private String low_insured;
        private String mastercardid;
        private String name;
        private String nation;
        private String nickname;
        private String oldMan;
        private String physical;
        private String pregnant;
        private String sex;
        private String sugarDiabetes;
        private String tel;
        private String tuberculosis;
        private String waist;
        private String wealth;
        private String img;
        private String nowaddr;
        private String nums;
        private String phoneNumber;
        private String grouping;
        private String jwhname;
        private String region_code;
        private String villagesName;


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

        public String getChildren() {
            return children;
        }

        public void setChildren(String children) {
            this.children = children;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getFive_insured() {
            return five_insured;
        }

        public void setFive_insured(String five_insured) {
            this.five_insured = five_insured;
        }

        public String getGeneral() {
            return general;
        }

        public void setGeneral(String general) {
            this.general = general;
        }

        public String getHeigth() {
            return heigth;
        }

        public void setHeigth(String heigth) {
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

        public String getHypertension() {
            return hypertension;
        }

        public void setHypertension(String hypertension) {
            this.hypertension = hypertension;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
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

        public String getLow_insured() {
            return low_insured;
        }

        public void setLow_insured(String low_insured) {
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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getOldMan() {
            return oldMan;
        }

        public void setOldMan(String oldMan) {
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

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getSugarDiabetes() {
            return sugarDiabetes;
        }

        public void setSugarDiabetes(String sugarDiabetes) {
            this.sugarDiabetes = sugarDiabetes;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getTuberculosis() {
            return tuberculosis;
        }

        public void setTuberculosis(String tuberculosis) {
            this.tuberculosis = tuberculosis;
        }

        public String getWaist() {
            return waist;
        }

        public void setWaist(String waist) {
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

        public String getNowaddr() {
            return nowaddr;
        }

        public void setNowaddr(String nowaddr) {
            this.nowaddr = nowaddr;
        }

        public String getNums() {
            return nums;
        }

        public void setNums(String nums) {
            this.nums = nums;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getGrouping() {
            return grouping;
        }

        public void setGrouping(String grouping) {
            this.grouping = grouping;
        }

        public String getJwhname() {
            return jwhname;
        }

        public void setJwhname(String jwhname) {
            this.jwhname = jwhname;
        }

        public String getRegion_code() {
            return region_code;
        }

        public void setRegion_code(String region_code) {
            this.region_code = region_code;
        }

        public String getVillagesName() {
            return villagesName;
        }

        public void setVillagesName(String villagesName) {
            this.villagesName = villagesName;
        }
    }

    public static class UserinfoBean<T> {
        /**
         * applicationdate : 2018-01-12
         * archiving_time : 2017-09-18 13:04:14.0
         * birthday : 1977-11-14
         * bloodtype : 0
         * buildemployeeid :
         * buildemployeename :
         * buildorgid :
         * buildorgname :
         * cardID : 310230197711143133
         * code : 51041110620000557001
         * conditions : 1
         * contactperson :
         * createdate : 2017-09-18 13:04:14.0
         * credentials : 1
         * disability :
         * drinkingwater : 0
         * drugallergyhistory :
         * edittime : 2018-03-31 18:24:24.0
         * educationcode : 0
         * exposurehistory :
         * fueltype : 0
         * gendercode : 1
         * hfx : {}
         * hid : 255
         * hjaddr : 上海市宝山区共和新路4719弄91号601室
         * householderrelationship : 27
         * hrstatus : 0
         * hukouflag : 0
         * id : 602153
         * isflowing : 0
         * ispoor : 2
         * jobcode : -1
         * kitchenexhaust : 0
         * livestockcolumn : 0
         * marrystatuscode : 0
         * mastercardid : 310230197711143133
         * name : 蒋剑锋
         * namepinyin : jjf
         * nationcode : 01
         * nationother : 汉
         * nowaddr : 上海市宝山区共和新路4719弄91号601室
         * nowhid : 918
         * nums :
         * oldhid : 1
         * otherdrugallergyhistory :
         * otherpaymentwaystring :
         * paymentwaystring :
         * persontel : 13917334372
         * photo :
         * population_class :
         * regionCode : 510411106
         * regionID : 2cf46280-3386-4791-ac56-69708e1bae9b
         * restype : 0
         * rhblood : 0
         * state : 0
         * syntrophus : 0
         * toilet : 0
         * userid : 693825
         * uuid : d0d0c24c-9c2e-11e7-ab16-5254007bf379
         * workorgname :
         * dname : 余祥武
         * responsibilityid : 2852
         * ver : 第三版
         * print : 1
         * contacetel : 18628883861
         * disabilityother :
         * syntrophusother :
         * townname : 督院街街道办事处
         */
        private String applicationdate;
        private String archiving_time;
        private String birthday;
        private String bloodtype;
        private String buildemployeeid;
        private String buildemployeename;
        private String buildorgid;
        private String buildorgname;
        private String cardID;
        private String code;
        private String conditions;
        private String contactperson;
        private String createdate;
        private String credentials;
        private String disability;
        private String drinkingwater;
        private String drugallergyhistory;
        private String edittime;
        private String educationcode;
        private String exposurehistory;
        private String fueltype;
        private String gendercode;
        private T hfx;
        private String hid;
        private String hjaddr;
        private String householderrelationship;
        private String hrstatus;
        private String hukouflag;
        private String id;
        private String isflowing;
        private String ispoor;
        private String jobcode;
        private String kitchenexhaust;
        private String livestockcolumn;
        private String marrystatuscode;
        private String mastercardid;
        private String name;
        private String namepinyin;
        private String nationcode;
        private String nationother;
        private String nowaddr;
        private String nowhid;
        private String nums;
        private String oldhid;
        private String otherdrugallergyhistory;
        private String otherpaymentwaystring;
        private String paymentwaystring;
        private String persontel;
        private String photo;
        private String population_class;
        private String regionCode;
        private String regionID;
        private String restype;
        private String rhblood;
        private String state;
        private String syntrophus;
        private String toilet;
        private String userid;
        private String uuid;
        private String workorgname;
        private String dname;
        private String responsibilityid;
        private String ver;
        private String print;
        private String contacetel;
        private String disabilityother;
        private String syntrophusother;
        private String townname;

        @Override
        public String toString() {
            return "UserinfoBean{" +
                    "hfx=" + hfx +
                    '}';
        }

        public static HfxBean fromJson(String json, Class clazz) {
            Gson gson = new Gson();
            Type objectType = type(UserinfoBean.class, clazz);
            return gson.fromJson(json, objectType);
        }

        public String toJson(Class<String> clazz) {
            Gson gson = new Gson();
            Type objectType = type(UserinfoBean.class, clazz);
            return gson.toJson(this, objectType);
        }

        static ParameterizedType type(final Class raw, final Type... args) {
            return new ParameterizedType() {
                public Type getRawType() {
                    return raw;
                }

                public Type[] getActualTypeArguments() {
                    return args;
                }

                public Type getOwnerType() {
                    return null;
                }
            };
        }


        public String getApplicationdate() {
            return applicationdate;
        }

        public void setApplicationdate(String applicationdate) {
            this.applicationdate = applicationdate;
        }

        public String getArchiving_time() {
            return archiving_time;
        }

        public void setArchiving_time(String archiving_time) {
            this.archiving_time = archiving_time;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getBloodtype() {
            return bloodtype;
        }

        public void setBloodtype(String bloodtype) {
            this.bloodtype = bloodtype;
        }

        public String getBuildemployeeid() {
            return buildemployeeid;
        }

        public void setBuildemployeeid(String buildemployeeid) {
            this.buildemployeeid = buildemployeeid;
        }

        public String getBuildemployeename() {
            return buildemployeename;
        }

        public void setBuildemployeename(String buildemployeename) {
            this.buildemployeename = buildemployeename;
        }

        public String getBuildorgid() {
            return buildorgid;
        }

        public void setBuildorgid(String buildorgid) {
            this.buildorgid = buildorgid;
        }

        public String getBuildorgname() {
            return buildorgname;
        }

        public void setBuildorgname(String buildorgname) {
            this.buildorgname = buildorgname;
        }

        public String getCardID() {
            return cardID;
        }

        public void setCardID(String cardID) {
            this.cardID = cardID;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getConditions() {
            return conditions;
        }

        public void setConditions(String conditions) {
            this.conditions = conditions;
        }

        public String getContactperson() {
            return contactperson;
        }

        public void setContactperson(String contactperson) {
            this.contactperson = contactperson;
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

        public String getDisability() {
            return disability;
        }

        public void setDisability(String disability) {
            this.disability = disability;
        }

        public String getDrinkingwater() {
            return drinkingwater;
        }

        public void setDrinkingwater(String drinkingwater) {
            this.drinkingwater = drinkingwater;
        }

        public String getDrugallergyhistory() {
            return drugallergyhistory;
        }

        public void setDrugallergyhistory(String drugallergyhistory) {
            this.drugallergyhistory = drugallergyhistory;
        }

        public String getEdittime() {
            return edittime;
        }

        public void setEdittime(String edittime) {
            this.edittime = edittime;
        }

        public String getEducationcode() {
            return educationcode;
        }

        public void setEducationcode(String educationcode) {
            this.educationcode = educationcode;
        }

        public String getExposurehistory() {
            return exposurehistory;
        }

        public void setExposurehistory(String exposurehistory) {
            this.exposurehistory = exposurehistory;
        }

        public String getFueltype() {
            return fueltype;
        }

        public void setFueltype(String fueltype) {
            this.fueltype = fueltype;
        }

        public String getGendercode() {
            return gendercode;
        }

        public void setGendercode(String gendercode) {
            this.gendercode = gendercode;
        }

        public T getHfx() {
            return hfx;
        }

        public void setHfx(T hfx) {
            this.hfx = hfx;
        }

        public String getHid() {
            return hid;
        }

        public void setHid(String hid) {
            this.hid = hid;
        }

        public String getHjaddr() {
            return hjaddr;
        }

        public void setHjaddr(String hjaddr) {
            this.hjaddr = hjaddr;
        }

        public String getHouseholderrelationship() {
            return householderrelationship;
        }

        public void setHouseholderrelationship(String householderrelationship) {
            this.householderrelationship = householderrelationship;
        }

        public String getHrstatus() {
            return hrstatus;
        }

        public void setHrstatus(String hrstatus) {
            this.hrstatus = hrstatus;
        }

        public String getHukouflag() {
            return hukouflag;
        }

        public void setHukouflag(String hukouflag) {
            this.hukouflag = hukouflag;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIsflowing() {
            return isflowing;
        }

        public void setIsflowing(String isflowing) {
            this.isflowing = isflowing;
        }

        public String getIspoor() {
            return ispoor;
        }

        public void setIspoor(String ispoor) {
            this.ispoor = ispoor;
        }

        public String getJobcode() {
            return jobcode;
        }

        public void setJobcode(String jobcode) {
            this.jobcode = jobcode;
        }

        public String getKitchenexhaust() {
            return kitchenexhaust;
        }

        public void setKitchenexhaust(String kitchenexhaust) {
            this.kitchenexhaust = kitchenexhaust;
        }

        public String getLivestockcolumn() {
            return livestockcolumn;
        }

        public void setLivestockcolumn(String livestockcolumn) {
            this.livestockcolumn = livestockcolumn;
        }

        public String getMarrystatuscode() {
            return marrystatuscode;
        }

        public void setMarrystatuscode(String marrystatuscode) {
            this.marrystatuscode = marrystatuscode;
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

        public String getNamepinyin() {
            return namepinyin;
        }

        public void setNamepinyin(String namepinyin) {
            this.namepinyin = namepinyin;
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

        public String getNowaddr() {
            return nowaddr;
        }

        public void setNowaddr(String nowaddr) {
            this.nowaddr = nowaddr;
        }

        public String getNowhid() {
            return nowhid;
        }

        public void setNowhid(String nowhid) {
            this.nowhid = nowhid;
        }

        public String getNums() {
            return nums;
        }

        public void setNums(String nums) {
            this.nums = nums;
        }

        public String getOldhid() {
            return oldhid;
        }

        public void setOldhid(String oldhid) {
            this.oldhid = oldhid;
        }

        public String getOtherdrugallergyhistory() {
            return otherdrugallergyhistory;
        }

        public void setOtherdrugallergyhistory(String otherdrugallergyhistory) {
            this.otherdrugallergyhistory = otherdrugallergyhistory;
        }

        public String getOtherpaymentwaystring() {
            return otherpaymentwaystring;
        }

        public void setOtherpaymentwaystring(String otherpaymentwaystring) {
            this.otherpaymentwaystring = otherpaymentwaystring;
        }

        public String getPaymentwaystring() {
            return paymentwaystring;
        }

        public void setPaymentwaystring(String paymentwaystring) {
            this.paymentwaystring = paymentwaystring;
        }

        public String getPersontel() {
            return persontel;
        }

        public void setPersontel(String persontel) {
            this.persontel = persontel;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getPopulation_class() {
            return population_class;
        }

        public void setPopulation_class(String population_class) {
            this.population_class = population_class;
        }

        public String getRegionCode() {
            return regionCode;
        }

        public void setRegionCode(String regionCode) {
            this.regionCode = regionCode;
        }

        public String getRegionID() {
            return regionID;
        }

        public void setRegionID(String regionID) {
            this.regionID = regionID;
        }

        public String getRestype() {
            return restype;
        }

        public void setRestype(String restype) {
            this.restype = restype;
        }

        public String getRhblood() {
            return rhblood;
        }

        public void setRhblood(String rhblood) {
            this.rhblood = rhblood;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getSyntrophus() {
            return syntrophus;
        }

        public void setSyntrophus(String syntrophus) {
            this.syntrophus = syntrophus;
        }

        public String getToilet() {
            return toilet;
        }

        public void setToilet(String toilet) {
            this.toilet = toilet;
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

        public String getWorkorgname() {
            return workorgname;
        }

        public void setWorkorgname(String workorgname) {
            this.workorgname = workorgname;
        }

        public String getDname() {
            return dname;
        }

        public void setDname(String dname) {
            this.dname = dname;
        }

        public String getResponsibilityid() {
            return responsibilityid;
        }

        public void setResponsibilityid(String responsibilityid) {
            this.responsibilityid = responsibilityid;
        }

        public String getVer() {
            return ver;
        }

        public void setVer(String ver) {
            this.ver = ver;
        }

        public String getPrint() {
            return print;
        }

        public void setPrint(String print) {
            this.print = print;
        }

        public String getContacetel() {
            return contacetel;
        }

        public void setContacetel(String contacetel) {
            this.contacetel = contacetel;
        }

        public String getDisabilityother() {
            return disabilityother;
        }

        public void setDisabilityother(String disabilityother) {
            this.disabilityother = disabilityother;
        }

        public String getSyntrophusother() {
            return syntrophusother;
        }

        public void setSyntrophusother(String syntrophusother) {
            this.syntrophusother = syntrophusother;
        }

        public String getTownname() {
            return townname;
        }

        public void setTownname(String townname) {
            this.townname = townname;
        }

        public static class HfxBean {
            private String father;
            private String fatheremark;
            private String mother;
            private String motheremark;
            private String brother;
            private String brotheremark;
            private String children;
            private String childrenremark;

            @Override
            public String toString() {
                return "{" +
                        "father='" + father + '\'' +
                        ", fatheremark='" + fatheremark + '\'' +
                        ", mother='" + mother + '\'' +
                        ", motheremark='" + motheremark + '\'' +
                        ", brother='" + brother + '\'' +
                        ", brotheremark='" + brotheremark + '\'' +
                        ", children='" + children + '\'' +
                        ", childrenremark='" + childrenremark + '\'' +
                        '}';
            }

            public String getFather() {
                return father;
            }

            public void setFather(String father) {
                this.father = father;
            }

            public String getFatheremark() {
                return fatheremark;
            }

            public void setFatheremark(String fatheremark) {
                this.fatheremark = fatheremark;
            }

            public String getMother() {
                return mother;
            }

            public void setMother(String mother) {
                this.mother = mother;
            }

            public String getMotheremark() {
                return motheremark;
            }

            public void setMotheremark(String motheremark) {
                this.motheremark = motheremark;
            }

            public String getBrother() {
                return brother;
            }

            public void setBrother(String brother) {
                this.brother = brother;
            }

            public String getBrotheremark() {
                return brotheremark;
            }

            public void setBrotheremark(String brotheremark) {
                this.brotheremark = brotheremark;
            }

            public String getChildren() {
                return children;
            }

            public void setChildren(String children) {
                this.children = children;
            }

            public String getChildrenremark() {
                return childrenremark;
            }

            public void setChildrenremark(String childrenremark) {
                this.childrenremark = childrenremark;
            }
        }
    }
}

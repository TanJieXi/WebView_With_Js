package com.example.newblue.jumppart.bpart;

/**
 * ================================================
 * 作者: leilei
 * 版本: 1.0
 * 创建日期: 2017/12/9 17:48
 * 描述:
 * 修订历史:
 * ================================================
 */

public class FamilyBean {
    public String _id;
    public String uname;
    public String uidcard;
    public String image;
    private String isSign;
    private int signState;
    private String abateTime;

    public String getIsSign() {
        return isSign;
    }

    public void setIsSign(String isSign) {
        this.isSign = isSign;
    }

    public int getSignState() {
        return signState;
    }

    public void setSignState(int signState) {
        this.signState = signState;
    }

    public String getAbateTime() {
        return abateTime;
    }

    public void setAbateTime(String abateTime) {
        this.abateTime = abateTime;
    }
}

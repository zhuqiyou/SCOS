package es.source.code.activity.Bean;

import java.io.Serializable;

/**
 * 实体类a
 */
public class HelpListBean implements Serializable {
    private String HelpName;//Helpname    proName
    private int HelpImg; //HelpImag     imgUrl

    public HelpListBean(String HelpName, int HelpImg) {
        this.HelpName = HelpName;
        this.HelpImg = HelpImg;
    }

    public String getHelpName() {
        return HelpName;
    }

    public void setHelpName(String helpName) {
        HelpName = helpName;
    }

    public int getHelpImg() {
        return HelpImg;
    }

    public void setHelpImg(int helpImg) {
        HelpImg = helpImg;
    }
}
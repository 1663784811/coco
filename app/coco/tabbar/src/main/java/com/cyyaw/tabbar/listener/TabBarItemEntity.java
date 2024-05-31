package com.cyyaw.tabbar.listener;

public class TabBarItemEntity  implements CustomTabEntity{


    private String tabTitle;

    private int tabUnselectedIcon;

    private int tabSelectedIcon;

    public TabBarItemEntity(String tabTitle, int tabUnselectedIcon, int tabSelectedIcon) {
        this.tabTitle = tabTitle;
        this.tabUnselectedIcon = tabUnselectedIcon;
        this.tabSelectedIcon = tabSelectedIcon;
    }

    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    public void setTabSelectedIcon(int tabSelectedIcon) {
        this.tabSelectedIcon = tabSelectedIcon;
    }

    public void setTabUnselectedIcon(int tabUnselectedIcon) {
        this.tabUnselectedIcon = tabUnselectedIcon;
    }

    @Override
    public String getTabTitle() {
        return tabTitle;
    }

    @Override
    public int getTabSelectedIcon() {
        return tabSelectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return tabUnselectedIcon;
    }
}

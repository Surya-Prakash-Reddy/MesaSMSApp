package com.mesa.sms.layout;

public class RootMessageItem {
    private final int mRootMessageIcon;

    private final String mTitle;

    private final String mContent;

    /**
     * Constructor
     * @param mRootMessageIcon message icon
     * @param mTitle title
     * @param mContent content
     */
    public RootMessageItem(int mRootMessageIcon, String mTitle, String mContent) {
        this.mRootMessageIcon = mRootMessageIcon;
        this.mTitle = mTitle;
        this.mContent = mContent;
    }

    public int getRootMessageIcon() {
        return mRootMessageIcon;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getContent() {
        return mContent;
    }
}

package com.mesa.sms.layout;

public class RootMessageItem {

    private final String mTitle;

    private final String mContent;

    /**
     * Constructor
     * @param mTitle title
     * @param mContent content
     */
    public RootMessageItem(String mTitle, String mContent) {
        this.mTitle = mTitle;
        this.mContent = mContent;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getContent() {
        return mContent;
    }
}

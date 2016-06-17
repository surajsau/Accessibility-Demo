package com.halfplatepoha.accesibility;

/**
 * Created by surajkumarsau on 05/06/16.
 */
public class ViewIdTextModel {

    private String viewId;
    private String text;

    public ViewIdTextModel(String viewId, String text) {
        this.viewId = viewId;
        this.text = text;
    }

    public String getViewId() {
        return viewId;
    }

    public String getText() {
        return text;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public void setText(String text) {
        this.text = text;
    }
}

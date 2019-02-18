package com.adaptivelearning.server.FancyModel;

import com.adaptivelearning.server.Model.Category;

import java.util.LinkedList;
import java.util.List;

public class FancyCategory {

    // id
    private int categoryId;

    // name
    private String categoryStr;

    // is correct
    private boolean isApproved;

    public FancyCategory() {
    }

    public FancyCategory toFancyCategoryMapping(Category category){
        this.categoryId = category.getCategoryId();
        this.categoryStr = category.getName();
        this.isApproved = category.isApproved();
        return this;
    }

    public List<FancyCategory> toFancyCategoryListMapping(List<Category> categories){
        LinkedList<FancyCategory> fancyCategoryList= new LinkedList<>();
        for (Category category:
                categories) {
            FancyCategory fancyCategory = new FancyCategory();
            fancyCategoryList.addLast(fancyCategory.toFancyCategoryMapping(category));
        }
        return fancyCategoryList;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryStr() {
        return categoryStr;
    }

    public void setCategoryStr(String categoryStr) {
        this.categoryStr = categoryStr;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}

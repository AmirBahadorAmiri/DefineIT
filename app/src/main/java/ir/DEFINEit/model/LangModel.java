/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 6:00 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.model;

public class LangModel {

    private final String name, code, image;

    public LangModel(String name, String code, String image) {
        this.name = name;
        this.code = code;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getCode() {
        return code;
    }
}

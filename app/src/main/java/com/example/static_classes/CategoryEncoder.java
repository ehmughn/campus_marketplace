package com.example.static_classes;

public class CategoryEncoder {

    public static String toCode(String category) {
        String returnString = "";
        if(category.equals("Clothes"))
            returnString = "CLTH";
        else if(category.equals("Crafts"))
            returnString = "CRFT";
        else if(category.equals("Foods and Drinks"))
            returnString = "FOOD";
        else if(category.equals("Shoes"))
            returnString = "SHOE";
        else if(category.equals("Study Materials"))
            returnString = "STDY";
        else if(category.equals("Uniform"))
            returnString = "UNIF";
        return returnString;
    }

}

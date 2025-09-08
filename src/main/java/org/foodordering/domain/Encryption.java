package org.foodordering.domain;

import java.util.ArrayList;
import java.util.List;

public class Encryption {
    protected static int key = 16;
    public static String encrypt(String password) {

        char[] charArray = password.toCharArray();
        int localkey =key;
        StringBuilder builder = new StringBuilder();
        for(char c : charArray){
                c += (char) (localkey*(localkey+=3));
                builder.append(c);

        }return builder.toString();


    }

}

package com.udemy.spring.helpers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UrlHelper {
    public static String UrlDecode(String valor) {
        try
        {
            return URLDecoder.decode(valor, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            return "";
        }
    }

    public static List<Integer> ListIntDecode(String valor, String separador) {
        return Arrays.asList(valor.split(separador)).stream().map(valorInt -> Integer.parseInt(valorInt)).collect(Collectors.toList());
    }
}
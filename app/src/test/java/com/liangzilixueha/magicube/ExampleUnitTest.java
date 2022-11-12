package com.liangzilixueha.magicube;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

import com.google.gson.Gson;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String json="{\"Red\":100,\"Green\":100,\"Blue\":100}";
        Pixiv pixiv=new Gson().fromJson(json,Pixiv.class);
        System.out.println(pixiv.toString()+" "+pixiv.Green);
    }
}
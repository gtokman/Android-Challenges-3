package com.garytokman.tokmangary_ce01.model;
// Gary Tokman
// MDF3 - 1610
// Person

import android.content.Context;

import com.garytokman.tokmangary_ce01.datebase.PersonDatabase;

public class Person {
    private String mFirstName;
    private String mLastName;
    private int mAge;
    private PersonDatabase mPersonDatabase;

    public Person(String firstName, String lastName, int age, Context context) {
        mFirstName = firstName;
        mLastName = lastName;
        mAge = age;
        mPersonDatabase = PersonDatabase.getInstance(context);
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public int getAge() {
        return mAge;
    }

    public PersonDatabase getPersonDatabase() {
        return mPersonDatabase;
    }

    public String getAgeToString() {
        return String.valueOf(mAge);
    }

    @Override
    public String toString() {
        return mFirstName + " " + mLastName;
    }
}

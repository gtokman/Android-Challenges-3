package com.garytokman.tokmangary_ce05.model;
// Gary Tokman
// MDF3 - 1610
// Songs

import com.garytokman.tokmangary_ce05.R;

import java.util.ArrayList;
import java.util.List;

public class Songs {

    public static List<Song> getSongs() {

        List<Song> songs = new ArrayList<>();

        songs.add(new Song("Disclosure", "Flume", R.drawable.album1, R.raw.disclosure));
        songs.add(new Song("Helix", "Flume", R.drawable.album2, R.raw.helix));
        songs.add(new Song("Redhead", "Flume", R.drawable.album3, R.raw.redhead));

        return songs;
    }
}

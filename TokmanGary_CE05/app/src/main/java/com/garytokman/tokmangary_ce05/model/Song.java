package com.garytokman.tokmangary_ce05.model;
// Gary Tokman
// MDF3 - 1610
// TokmanGary_CE05

public class Song {

    private String mTrackName;
    private String mArtist;
    private int mSongId;
    private int mImageId;

    public Song(String name, String artist, int imageId, int songId) {
        mTrackName = name;
        mArtist = artist;
        mImageId = imageId;
        mSongId = songId;
    }

    public int getSongId() {
        return mSongId;
    }

    public int getImageId() {
        return mImageId;
    }

    public String getArtist() {
        return mArtist;
    }

    @Override
    public String toString() {
        return mTrackName + " - " + mArtist;
    }

}

package com.garytokman.tokmangary_ce05.model;
// Gary Tokman
// MDF3 - 1610
// Song

public class Song {

    private final String mTrackName;
    private final String mArtist;
    private final int mSongId;
    private final int mImageId;

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

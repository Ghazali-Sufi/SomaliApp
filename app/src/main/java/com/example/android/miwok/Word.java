package com.example.android.miwok;

public class Word {

    private String mDefaultTranslation;
    private String mSomaliTranslation;
    private int mImageResourceId = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;
    private int mAudioPlayback;

    public Word(String defaultTranslation, String somaliTranslation, int audioPlayback){
        mDefaultTranslation = defaultTranslation;
        mSomaliTranslation = somaliTranslation;
        mAudioPlayback = audioPlayback;
    }

    public Word(String defaultTranslation, String somaliTranslation, int imageResourceId,int audioPlayback){
        mDefaultTranslation = defaultTranslation;
        mSomaliTranslation = somaliTranslation;
        mImageResourceId = imageResourceId;
        mAudioPlayback = audioPlayback;
    }

    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    public int getmAudioPlayback() {
        return mAudioPlayback;
    }

    @Override
    public String toString() {
        return "word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mSomaliTranslation='" + mSomaliTranslation + '\'' +
                ", mImageResourceId=" + mImageResourceId +
                ", mAudioPlayback=" + mAudioPlayback +
                '}';
    }

    public String getSomaliTranslation(){
        return mSomaliTranslation;
    }

    public int getmImageResourceId(){
        return mImageResourceId;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }
}

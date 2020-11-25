package com.snowtam.io.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotamResponse {


    @SerializedName("features")
    @Expose
    private List<Feature> features = null;

    class Feature {
        @SerializedName("properties") @Expose
        private Properties properties;

        @Override
        public String toString() {
            return "Feature{" +
                    "properties=" + properties +
                    '}';
        }
    }

    class Properties {
        @SerializedName("lat") @Expose private Double lat;
        @SerializedName("lon") @Expose private Double lon;
        @SerializedName("countryCode") @Expose private String countryCode; // "countryCode": "NOR",
        @SerializedName("publisherNOF") @Expose private String publisherNOF; // "publisherNOF": "SNOWTAM",
        @SerializedName("translations") @Expose private List<Translation> translations = null;

        @Override
        public String toString() {
            return "Properties{" +
                    "lat=" + lat +
                    ", lon=" + lon +
                    ", countryCode='" + countryCode + '\'' +
                    ", publisherNOF='" + publisherNOF + '\'' +
                    ", translations=" + translations +
                    '}';
        }
    }

    class Translation {
        @SerializedName("formattedText") @Expose private String formattedText;

        @Override
        public String toString() {
            return "Translation{" +
                    "formattedText='" + formattedText + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NotamResponse{" +
                "features=" + features +
                '}';
    }
}

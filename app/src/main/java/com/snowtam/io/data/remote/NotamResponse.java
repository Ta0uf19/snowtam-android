package com.snowtam.io.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.snowtam.io.data.local.entity.AirportNotam;
import com.snowtam.io.utils.SnowtamDecoder;

import java.util.List;

public class NotamResponse {


    @SerializedName("features")
    @Expose
    private List<Feature> features = null;

    /**
     * Get first snowtam
     * @return
     */
    public AirportNotam getFirstSnowtam() {
        AirportNotam notam = new AirportNotam();
        for(Feature feature : features) {
            // Get snowtam code + country
            if(feature.properties.publisherNOF.compareTo("SNOWTAM") == 0) {
                // clean string
                String clean = SnowtamDecoder.clean(feature.properties.translations.get(0).formattedText);
                notam.setRawSnowtam(clean);
                notam.setCountryCode(feature.properties.countryCode);
                notam.setAirportCode(feature.properties.location);
            };
            // Get geodata
            if(feature.properties.lat != null && feature.properties.lon != null) {
                notam.setLat(feature.properties.lat);
                notam.setLog(feature.properties.lon);
            }
        }
        return notam.getRawSnowtam() != null ? notam : null;
    }

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
        @SerializedName("lat") @Expose private Double lat = 0.0d;
        @SerializedName("lon") @Expose private Double lon = 0.0d;
        @SerializedName("countryCode") @Expose  private String countryCode; // "countryCode": "NOR",
        @SerializedName("location") @Expose  private String location;
        @SerializedName("publisherNOF") @Expose private String publisherNOF; // "publisherNOF": "SNOWTAM",
        @SerializedName("translations") @Expose private List<Translation> translations = null;

        @Override
        public String toString() {
            return "Properties{" +
                    "lat=" + lat +
                    ", lon=" + lon +
                    ", countryCode='" + countryCode + '\'' +
                    ", location='" + location + '\'' +
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

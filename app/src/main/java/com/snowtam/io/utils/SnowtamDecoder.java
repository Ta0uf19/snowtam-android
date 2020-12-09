package com.snowtam.io.utils;

import com.snowtam.io.data.local.entity.decoder.Friction;
import com.snowtam.io.data.local.entity.decoder.SnowtamItem;
import com.snowtam.io.data.local.entity.decoder.RunwayStates;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SnowtamDecoder {
    // A -> value
    private List<SnowtamItem> list;
    private String codedString;

    private SnowtamDecoder(String coded) {
        this.list = new ArrayList<SnowtamItem>();
        this.codedString = coded;
    }


    /**
     * Decode snowtam
     * @param coded
     * @return List of items with picture, name and value
     */
    public static List<SnowtamItem> decode(String coded) {

        /**
         * ([ABCDEFGHJKLMPS])\)\s*([A-Z0-9\/]*)[\s\t]*
         * ([NRT])\)\s*([A-Z0-9\/\ .,-]*)[\s\t]*
         */

        SnowtamDecoder stream = new SnowtamDecoder(coded);

        List<SnowtamItem> list = stream
                .parseABC()
                .parseNRT()
                .build();

        return list;
    }

    /**
     * Encode snowtam
     * @param snowtamItems
     * @return String of encoded snowtam
     */

    public static String encode(List<SnowtamItem> snowtamItems) {


        return null;
    }


    /**
     * Clean string and remove html tags
     * Return only SNOWTAM code
     * @param sequence
     * @return
     */
    public static String clean(CharSequence sequence) {
        String regex = "&lt;pre&gt;([\\s\\S]*)&lt;\\/pre&gt;";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(sequence);

        if(matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * Parse ABCDEFGHJKLMPS
     */
    private SnowtamDecoder parseABC() {

        String regex = "([ABCDEFGHJKLMPS])\\)\\s*([A-Z0-9\\/]*)[\\s\\t]*";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(codedString);

        while(matcher.find()) {
            String attr = matcher.group(1).trim();
            String value = matcher.group(2).trim();

            SnowtamItem snowtamItem = processingSnowtam(attr, value);
            list.add(snowtamItem);
        }

        return this;
    }

    /**
     * Parse NRT
     */
    private SnowtamDecoder parseNRT() {
        return this;
    }

    private List<SnowtamItem> build() {
        return this.list;
    }

    /**
     * Processing snowtam and handling each case separatly
     * @param attr
     * @param value
     * @return
     */
    private SnowtamItem processingSnowtam(String attr, String value) {

        String picture = ("attr_"+attr).toLowerCase();
        // create object
        SnowtamItem snowtamItem = new SnowtamItem(attr, value, picture);

        String[] split = null;
        StringBuilder builder = null;

        switch (snowtamItem.getAttr()) {
            case "A":

                snowtamItem.setName("Aérodrome");
                snowtamItem.setValue(value);
                break;

            case "B":
                // Date formating
                SimpleDateFormat sdf = new SimpleDateFormat("MMddhhmm");
                Date date = null;
                try {
                    date = sdf.parse(value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                date.setYear(new Date().getYear());
                snowtamItem.setValue(date.toString());
                snowtamItem.setName("Date observation");

                break;
            case "C":
                snowtamItem.setName("Piste");
                snowtamItem.setValue("Runway "  + value);
                break;
            case "D":
                /*
                 * D) 930 => CLEARED RUNWAY LENGTH 930M
                 * * E LARGEUR DÉBLAYÉE SI INFÉRIEURE A LA LARGEUR DE PISTE PUBLIÉE (en m, si
                 * décalée à gauche ou à droite par rapport à l'axe, ajouter "L" ou "R" après les chiffres)
                 * => “Cleared runway width”
                 */

                break;
            case "E":
                // E LARGEUR DÉBLAYÉE SI INFÉRIEURE A LA LARGEUR DE PISTE PUBLIÉE (en m, si décalée à gauche ou à droite par rapport à l'axe, ajouter "L" ou "R" après les chiffres)
                snowtamItem.setValue("CLEARED RUNWAY WIDTH " + value);
                snowtamItem.setName("Cleared runway length");

                break;

            case "F":
                // F CONDITIONS SUR TOUTE LA LONGUEUR DE LA PISTE
                // * F) 1/1/7 => Threshold: DAMP / Mid runway: DAMP / Roll out: ICE

                split = value.split("/");
                builder = new StringBuilder();

                for(int i = 0; i < split.length; i++) {
                    if(i == 0)builder.append("Threshold: ");
                    if(i == 1)builder.append("Mid runway: ");
                    if(i == 2)builder.append("Roll out: ");
                    // append state of runway
                    builder.append(RunwayStates.getState(Integer.parseInt(split[i])));
                    builder.append(" / ");
                }

                snowtamItem.setValue(builder.toString());
                snowtamItem.setName("Condition de la piste");

                break;
            case "G":
                //  G ÉPAISSEUR MOYENNE (en mm) SUR CHAQUE TIERS DE LA LONGUEUR TOTALE DE LA PISTE (note XX si non mesurable or non significatif. La precision doit etre de 20mm pour la
                //neige seche, 10mm pour la neige humide et 3mm pour le slush)
                split = value.split("/");
                builder = new StringBuilder();
                //builder.append("MEAN DEPTH ");
                for(int i = 0; i < split.length; i++) {
                    if (i == 0) builder.append("Threshold: ");
                    if (i == 1) builder.append("Mid runway: ");
                    if (i == 2) builder.append("Roll out: ");

                    if(split[i].compareTo("XX") != 0) {
                        builder.append(Integer.parseInt(split[i]));
                        builder.append("mm");
                    }
                    else {
                        builder.append("Non mésuré");
                    }
                    builder.append(" / ");
                }
                snowtamItem.setValue(builder.toString());
                snowtamItem.setName("ÉPAISSEUR MOYENNE (mm)");

                break;
            case "H":
                // H COEFFICIENT DE FROTTEMENT MESURÉ OU ESTIMÉ POUR CHAQUE TIERS DE LA PISTE
                // 37/31/41 GRT     -> BRAKING ACTION Threshold: MEDIUM TO GOOD / Mid runway: MEDIUM / Roll Out: GOOD / Instrument: Grip tester
                split = value.split("/");
                builder = new StringBuilder();
                //builder.append("BRAKING ACTION ");
                for(int i = 0; i < split.length; i++) {
                    if(i == 0)builder.append("Threshold: ");
                    if(i == 1)builder.append("Mid runway: ");
                    if(i == 2)builder.append("Roll out: ");
                    // append state of runway
                    int score = Integer.parseInt(split[i]);
                    int pscore = 0;    // between 1 and 5
                    if(score > 40) {
                        pscore = 5;
                    }
                    else if(score>= 36 && score <= 39) {
                        pscore = 4;
                    }
                    else if(score>= 30 && score <= 35) {
                        pscore = 3;
                    }
                    else if(score >= 26 && score <= 29) {
                        pscore = 2;
                    }
                    else if(score <= 25) {
                        pscore = 1;
                    }

                    builder.append(Friction.get(pscore));
                    builder.append(" / ");
                }

                snowtamItem.setValue(builder.toString());
                snowtamItem.setName("Coeff frottement");

                break;
            case "J":
                // * J CONGÈRES CRITIQUES : Hauteur (cm). Distance (m) du bord de la piste suivis de "L", "R"
                //ou "LR", s'il y a lieu (« Critical snowbanks »)
                break;
        }

        return snowtamItem;
    }

}

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

public class PhraseCounter {
    public static void main (String[] args) throws Exception {
        // obviously this path should be replaced with whatever the path is on your machine to the text of the book you want
        // i was using files that are available on https://tanach.us
        File file = new File("/Users/adam/code/drop-press/tnkh/Esther.acc.txt"); 
        
        HashMap<String, Integer> phraseCounts = new HashMap<String, Integer>(); // is this right
        
        BufferedReader br = new BufferedReader(new FileReader(file)); 
        
        String st; 
        while ((st = br.readLine()) != null)  {
            analyzePasuk(st, phraseCounts, 3, false);
        }

        int count = 0;

        // TODO: print phraseCounts
        for (Map.Entry<String, Integer> entry : phraseCounts.entrySet()) {
            if (entry.getValue() != 1) {
                System.out.println(legibleize(entry.getKey()) + entry.getValue());
            } else {
                count++;
            }
        }
        System.out.println(count);
    }

    public static String legibleize(String phrase) {
        String legible = new String("");
        for (int i = 0; i < phrase.length(); i++) {
            char taam = phrase.charAt(i);
            legible += getName(taam) + " " + getLevel(taam) + ", ";
        }
        return legible;
    }

    public static String getName(char trop) {
        int num = (int) trop;
        switch (num) {
        case 1425: return "Etnachta";
        case 1426: return "Segol";
        case 1427: return "Shalshelet";
        case 1428: return "Zakef Katon";
        case 1429: return "Zakef Gadol";
        case 1430: return "Tipcha";
        case 1431: return "Revii";
        case 1432: return "Tzinorit*";
        case 1433: return "Pashta";
        case 1434: return "Yetiv";
        case 1435: return "Tevir";
        case 1436: return "Azla";
        case 1437: return "Geresh Mukdam*";
        case 1438: return "Gershayim";
        case 1439: return "Karnei Parah";
        case 1440: return "Telisha Gedolah";
        case 1441: return "Pazer";
        case 1442: return "Etnach Hafukh*";
        case 1443: return "Munach";
        case 1444: return "Mahpach";
        case 1445: return "Mercha";
        case 1446: return "Mercha Kefula";
        case 1447: return "Darga";
        case 1448: return "Kadma";
        case 1449: return "Telisha Ketana";
        case 1450: return "Yerach Ben Yomo";
        case 1451: return "Oleh*";
        case 1452: return "Iluy*";
        case 1453: return "Dehi*";
        case 1454: return "Zarqa";
        case 1475: return "Sof Pasuk";
        }
        return "";
    }

    public static void analyzePasuk (String pasuk, HashMap<String, Integer> phraseCounts, int threshold, boolean excludeConj) {
        // char[] stack = new char[]{' ', ' ', ' ', ' ', ' '};
        String phrase = new String("");
        boolean initialSofPasukFlag = false;
        for (int i = 0; i < pasuk.length(); i++) {
            char taam = pasuk.charAt(i);
            
            if (!isTrop(taam)) {
                continue;
            }
            if ((int)taam == 1475 && !initialSofPasukFlag) {
                initialSofPasukFlag = true;
                continue;
            }
            initialSofPasukFlag = true;
            int level = getLevel(taam);
            // System.out.print(getName(taam) + level);
            if (excludeConj && level == 4) {
                continue;
            }
            phrase += taam;
            if (level <= threshold) {
                int count = phraseCounts.containsKey(phrase) ? phraseCounts.get(phrase) : 0;
                phraseCounts.put(phrase, count + 1);
                phrase = new String("");
            }
        }
        // if (initialSofPasukFlag) System.out.println();
    }

    public static boolean isTrop (char maybeTaam) {
        int val = (int) maybeTaam;
        return ((val >= 1425 && val <= 1454) || val == 1475);
    }

    public static int getLevel (char taam) {
        int val = (int) taam;        
        if (taam == 1475 || taam == 1425) return 0;
        if (taam <= 1430) return 1;
        if (taam <= 1435 || taam == 1454) return 2;
        if (taam <= 1441) return 3;
        return 4;
    }

    public static int getIndex (char taam) {
        return (int)taam - 1425;
    }

    public static void recordTransition (char from, char to, int[][] transitions) {
        transitions[getIndex(from)][getIndex(to)]++;
    }
}

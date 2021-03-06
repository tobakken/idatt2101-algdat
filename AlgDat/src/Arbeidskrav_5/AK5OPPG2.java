package Arbeidskrav_5;

import java.util.*;

public class AK5OPPG2 {
    public static void main(String[] args) {
        int m = 10000019; //Første primtall større enn 10mill.
        int[] originalTabell = fyllMedRandom(m);

        HashTabellLinearProbing htLin = new HashTabellLinearProbing(m);
        HashTabellKvadratiskProbing htKvad = new HashTabellKvadratiskProbing(m);
        HashTabellDobbelHash htDob = new HashTabellDobbelHash(m);

        double stopp = m>>1;
        Date start = new Date();
        for (int i = 0; i <stopp; i++) {
            htKvad.leggTil(originalTabell[i]);
            //System.out.println(originalTabell[i]);
        }
        Date stop = new Date();

        float kollisjoner = htKvad.kollisjoner;
        System.out.printf("Antall kollisjoner pr innsetting: %.4f", kollisjoner/stopp);
        System.out.println("\nFyllingsgrad: " + stopp/m);
        System.out.println("Tidsbruk i millisekunder: " + (stop.getTime()- start.getTime()));

    }
    public static int[] fyllMedRandom(int m){
        Random rand = new Random();
        int[] tabell = new int[m];
        tabell[0] = 1;
        for (int i = 0; i < tabell.length-1; i++) {
            //Legger til tilfeldig tall mellom 1 og 10 til forrige tall for å få kun unike verdier.
            tabell[i+1] = tabell[i]+ (rand.nextInt(9)+1);
        }
        for (int i = 0; i < tabell.length; i++) {
            int randomIndex = rand.nextInt(tabell.length);
            int temp = tabell[randomIndex];
            tabell[randomIndex] = tabell[i];
            tabell[i] = temp;
        }
        return tabell;
    }
}

class HashTabellLinearProbing{
    int[] tabell;
    int kollisjoner = 0;

    public HashTabellLinearProbing(int m){
        this.tabell = new int[m];
    }

    public int leggTil(int k){
        int m = tabell.length;
        int h = hashfunk(k, m);
        for (int i = 0; i < m; ++i) {
            int j = probe(h, i, m);
            //Java oppretter tabellen med 0-ere på de tomme plassene.
            if (tabell[j] == 0) {
                tabell[j] = k;
                return 0;
            }
            kollisjoner++;
        }
        return -1;
    }

    private int hashfunk(int k, int m){
        return k % m;
    }

    private int probe(int h, int i, int m){
        return (h+i) % m;
    }
}

class HashTabellKvadratiskProbing {
    int[] tabell;
    int kollisjoner = 0;

    public HashTabellKvadratiskProbing(int m){
        this.tabell = new int[m];
    }

    public int leggTil(int k){
        int m = tabell.length;
        int h = hashfunk(k, m);
        for (int i = 0; i < m; ++i) {
            int j = probeKvadratisk(h, i, m);
            //Java oppretter tabellen med 0-ere på de tomme plassene.
            if (tabell[j] == 0) {
                tabell[j] = k;
                return 0;
            }
            kollisjoner++;
        }
        return -1;
    }

    private int hashfunk(int k, int m){
        return k % m;
    }

    private int probeKvadratisk(int h, int i, int m){
        return Math.abs(h + 3*i + 5*(i*i)) % m;
    }
}

class HashTabellDobbelHash {
    int[] tabell;
    int kollisjoner = 0;

    public HashTabellDobbelHash(int m){
        this.tabell = new int[m];
    }

    public int leggTil(int k){
        int m = tabell.length;
        int h1 = hashfunk(k, m);
        int h2 = hashfunk2(k, m);
        for (int i = 0; i < m; ++i) {
            int j = probeDobbel(h1, h2, i, m);
            //Java oppretter tabellen med 0-ere på de tomme plassene.
            if (tabell[j] == 0) {
                tabell[j] = k;
                return 0;
            }
            kollisjoner++;
        }
        return -1;
    }

    private int hashfunk(int k, int m){
        return k % m;
    }

    private int hashfunk2(int k, int m){
        return k % (m-1) + 1;
    }

    private int probeDobbel(int h1, int h2, int i, int m){
        return (h1 + i*h2) % m;
    }
}
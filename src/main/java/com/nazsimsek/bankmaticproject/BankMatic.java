package com.nazsimsek.bankmaticproject;

import com.sun.webpane.platform.event.KeyCodeMap;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by naz on 22.5.2015.
 */
public class BankMatic {

    LinkedHashMap<Integer, Integer> moneyTable;
    File file;

    public BankMatic() throws IOException {  // text teki verieri hashmap in i�ine dolduruyoruz. hashmap<para de�eri, para adedi>

        moneyTable = new LinkedHashMap<Integer, Integer>();

        file = new File("moneyTable.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufReader = new BufferedReader(fileReader);

        String line;
        while ((line = bufReader.readLine()) != null) {
            String[] lineElement = line.split(" ");

            moneyTable.put(Integer.parseInt(lineElement[0]), Integer.parseInt(lineElement[1]));

        }
        fileReader.close();


    }

    public LinkedHashMap<Integer, Integer> resultTable(int moneyValue)         //hangi paradan ne kadar verilece�ini hesaplar bunlar� yeni bir hashmap i�inde d�nd�r�r
    {
        LinkedHashMap<Integer, Integer> resultTable = new LinkedHashMap<Integer, Integer>();
        //for (int i=0;i<=moneyTable.size();i++)
        int mod = 0;
        int per = 0;                // istenen para i�in gereken banknot say�s�
        for (Map.Entry<Integer, Integer> e : moneyTable.entrySet()) {

            int key = e.getKey();
            int value = e.getValue();

            if (moneyValue < key) {
                resultTable.put(key, 0);
                continue;
            } else if (moneyValue > key) {
                mod = moneyValue % key;
                per = (moneyValue - mod) / key;

                if (per > value)        // gereken banknot say�s� mevcut banknottan b�y�kse bankamatikteki t�m banknotlar� vermi� oluyor
                    per = value;

                resultTable.put(key, per);
                moneyValue -= key * per;
            }
        }

        return resultTable;


    }
    public int giveMoney(LinkedHashMap<Integer, Integer> resultTable) {
        int sum = 0;
        for (Map.Entry<Integer, Integer> r : resultTable.entrySet()) {
            int key = r.getKey();
            int value = r.getValue();
            sum += key * value;
        }
        return sum;
    }

    public void account(int money) throws IOException {
        LinkedHashMap<Integer, Integer> resultTable = resultTable(money);           //bankan�n verebilece�i banknotlar hesaplan�yor
        updateTable(moneyTable, resultTable);             //verilecek banknot miktarlar�, veritaban�ndaki banknot say�lar�ndan ��kart�l�p vt g�ncelleniyor

        System.out.println();
        System.out.println("Sistemin verebilecegi toplam miktar:" + giveMoney(resultTable));
        System.out.println();
        System.out.println("Verilebilecek banknot miktarlari:");
        System.out.println();
        displayTableHasRow(resultTable);
        System.out.println();
        System.out.println("-----");
        System.out.println("Bankada kalan banknot miktari:");
        displayTable(moneyTable);

        updateDbTxt(moneyTable);


    }

    public void updateTable(LinkedHashMap<Integer, Integer> oldMap, LinkedHashMap<Integer, Integer> newMap) {
        for (Map.Entry<Integer, Integer> r : newMap.entrySet()) {
            int key = r.getKey();
            int oldValue = oldMap.get(key);
            int decValue = newMap.get(key);
            Integer newValue = (Integer) oldValue - (Integer) decValue;
            oldMap.put(key, newValue);
        }


    }

    public int inputInt() {            //klavyeden veri al�r. E�er girilen de�er int de�ilse -1 d�nd�r�r.
        int input;
        System.out.println("Cekmek istediginiz miktari girin:");
        try {
            Scanner scan = new Scanner(System.in);
            input = scan.nextInt();


        } catch (NumberFormatException e) {
            System.out.println("Rakam girisi yapin.");
            input = -1;
        }

        return input;

    }

    public void displayTable(LinkedHashMap<Integer, Integer> table) {
        for (Map.Entry<Integer, Integer> r : table.entrySet()) {
            int key = r.getKey();
            int value = r.getValue();

            System.out.println(key + "TL  -->" + value + " adet");
        }
    }

    public void displayTableHasRow(LinkedHashMap<Integer, Integer> table) {
        for (Map.Entry<Integer, Integer> r : table.entrySet()) {
            int key = r.getKey();
            int value = r.getValue();
            if (value != 0)
                System.out.println(key + "TL  -->" + value + " adet");
        }

    }

    public void updateDbTxt(LinkedHashMap<Integer, Integer> map) throws IOException {

        String line;


        FileWriter writer = new FileWriter("moneyTable.txt");

        BufferedWriter bufWr = new BufferedWriter(writer);


        for (Map.Entry<Integer, Integer> r : map.entrySet()) {
            String key = r.getKey().toString();
            String value = r.getValue().toString();

            line = key + " " + value;
            bufWr.write(line);
            bufWr.newLine();
        }


        bufWr.close();
    }

    public void setMoney() throws IOException {  //bankamatikteki paraların miktarını 100 yapar.

        String line="";


        FileWriter writer = new FileWriter("moneyTable.txt");

        BufferedWriter bufWr = new BufferedWriter(writer);

        for (Map.Entry<Integer, Integer> r : moneyTable.entrySet()) {
            String key = r.getKey().toString();

            line = key + " " + "100";
            bufWr.write(line);
            bufWr.newLine();
        }


        bufWr.close();


    }

}

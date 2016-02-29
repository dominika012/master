package com.master.gauss.service;

import android.util.Log;

import com.master.gauss.core.Fraction;
import com.master.gauss.core.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class InfiniteSolutionService {

    public  String[] paramString = { "m", "n", "p", "r", "s", "t", "u", "v", "w", "z" };

   private Fraction[][] fractionMatrix;

    private  int columns;
    private  int rows;

    private  List<String> params;
    private  List<Map<String, Fraction>> rowsMaps;
    private  Map<String, Map<String, Fraction>> result;

    private StringBuilder output;

    public InfiniteSolutionService(int rows, int columns, Fraction[][] fractionMatrix){

        params = new ArrayList<String>(Arrays.asList(paramString));
        rowsMaps = new ArrayList<Map<String, Fraction>>();
        result = new TreeMap<String, Map<String, Fraction>>();
        output = new StringBuilder();

        this.rows = rows;
        this.columns = columns;

        this.fractionMatrix = fractionMatrix;
    }

    private String sign(Fraction fraction){
        if(!fraction.isPositive())
            return " ";
        return " + ";
    }


    private String stringVariables(Map<String,Fraction> map){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Fraction> entry : map.entrySet()) {
            if(!entry.getKey().equals("")){
                sb.append(sign(entry.getValue()) + entry.getValue() + entry.getKey());
            }
        }
        return sb.toString();
    }



    private String stringValue(Map<String,Fraction> map, String string){
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, Fraction> entry : map.entrySet()) {
            if(entry.getKey().equals("")){
                if(string!=" = ") string = sign(entry.getValue());
                sb.append(string + entry.getValue());
            }
        }
        return sb.toString();
    }

    private boolean zeroMap(Map<String,Fraction> map){
        for(Fraction value : map.values()){
            if(value.getNumerator()==0)
                return true;
        }
        return false;
    }

    private void printMap(Map<String,Fraction> map, String string){
            output.append(stringVariables(map));
            output.append(stringValue(map, string));
            output.append("\n");
    }


    private void printResult(Map<String, Map<String, Fraction>> map){
        for(Map.Entry<String, Map<String,Fraction>> mapEntry : map.entrySet()){
            String key = mapEntry.getKey();
            output.append(key + " = ");
            printMap(findMapValueByKey(map, key), " ");
        }
    }

    private Map<String, Fraction> multiplyByConst(Map<String, Fraction> map, Fraction fraction) {
        Map<String, Fraction> returnedMap = new TreeMap<String, Fraction>();
        for (String s : map.keySet()) {
            returnedMap.put(s, map.get(s).multiply(fraction));
        }
        return returnedMap;
    }

    private Map<String, Fraction> multiplyByConstBesides(Map<String, Fraction> map, Fraction fraction, List<String> strings) {
        Map<String, Fraction> returnedMap = new TreeMap<String, Fraction>();
        for (String s : map.keySet()) {
            if (!strings.contains(s))
                returnedMap.put(s, map.get(s).multiply(fraction));
        }
        return returnedMap;
    }

    private Map<String, Fraction> divideByConst(Map<String, Fraction> map, Fraction fraction) {
        Map<String, Fraction> returnedMap = new TreeMap<String, Fraction>();
        for (String s : map.keySet()) {
            returnedMap.put(s, map.get(s).divide(fraction));
        }
        return returnedMap;
    }

    // sum all maps at list
    private Map<String, Fraction> add(List<Map<String, Fraction>> maplist) {
        Map<String, Fraction> result = new TreeMap<String, Fraction>();
        for (Map<String, Fraction> map : maplist) {
            for (Map.Entry<String, Fraction> entry : map.entrySet()) {
                String key = entry.getKey();
                Fraction current = result.get(key);
                result.put(key, current == null ? entry.getValue() : entry.getValue().add(current));
            }
        }
        return result;
    }

    private void addToRowsMap() {
        for (int i = rows - 1; i >= 0; i--) {
            Map<String, Fraction> map = new TreeMap<String, Fraction>();
            for (int j = i; j < columns; j++) {
                StringBuilder s = new StringBuilder("x");
                s.append(j);
                    map.put(s.toString(), fractionMatrix[i][j]);
            }
            map.put("", fractionMatrix[i][columns]);
            rowsMaps.add(map);
        }
    }


    private Fraction findConstantOfParameter(Map<String,Fraction> map){
        Fraction f=new Fraction(1,1);
        for (Map.Entry<String,Fraction> entry : map.entrySet()) {
            if (entry.getKey().startsWith("x")) {
                f = entry.getValue();
            }
        }
        return f;
    }

    private String findIndexOfParameter(Map<String,Fraction> map){
        for (Map.Entry<String,Fraction> entry : map.entrySet()) {
            if (entry.getKey().startsWith("x")) {
                return entry.getKey().substring(1, entry.getKey().length());
            }
        }
        return null;
    }

    private void removeParameter(Map<String,Fraction> map){
        Iterator<Map.Entry<String, Fraction>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Fraction> entry = iter.next();
            if (entry.getKey().startsWith("x"))
                iter.remove();
        }
    }

    private Map<String, Fraction> findMapValueByKey(Map<String, Map<String, Fraction>> map, String key) {
        return result.get(key);
    }

    private Fraction findDoubleValueByKey(Map<String, Fraction> map, String key) {
        return map.get(key);
    }

    private Map<String, Fraction> substituteMaps(Map<String, Map<String, Fraction>> maps, Map<String, Fraction> map,
                                                     String key) {
        Map<String,Fraction> returnedMap = multiplyByConst(findMapValueByKey(maps, key), findDoubleValueByKey(map, key).multiply(new Fraction(-1, 1)));
        return returnedMap;
    }

    private void removeFromMapByKey(Map<String, Fraction> map, String key) {
        Iterator<Map.Entry<String, Fraction>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Fraction> entry = iter.next();
            if (entry.getKey().equals(key))
                iter.remove();
        }
    }

    private int numberOfParameters(Map<String, Fraction> map) {
        int counter = 0;
        for (String s : map.keySet()) {
            if (s.startsWith("x"))
                counter += 1;
        }
        return counter;
    }

    private void parametrize(Map<String,Fraction> map){
        int numberOfParams = numberOfParameters(map);
        Map<String, Fraction> tempMap = new TreeMap<String, Fraction>();
        Iterator<Map.Entry<String, Fraction>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Fraction> entry = iter.next();
            String s = entry.getKey();
            Fraction fraction = entry.getValue();
            if (s.startsWith("x") && numberOfParams>1) {
                iter.remove();
                tempMap.put(params.get(0), fraction.multiply(new Fraction(-1,1)));

                Map<String, Fraction> m = new TreeMap<String, Fraction>();
                m.put(params.get(0), new Fraction(1,1));
                result.put(s, m);

                output.append("NECH " + s + " = " + params.get(0));

                params.remove(0);
                numberOfParams-=1;
                output.append("\n");
            }
        }
        map.putAll(tempMap);
    }

    private String findLastKey(Map<String, Fraction> map){
        List<String> list = new ArrayList<String>(map.keySet());
        return list.get(list.size());
    }

    public String solve(){

        addToRowsMap();

        for(int row = 0; row<rows;row++) {

            // START OF SUBSTITUTION

            Map<String, Fraction> mapOfCurrentRow = rowsMaps.get(row);
            if (!zeroMap(mapOfCurrentRow)) {
                output.append("\n");
                output.append("MOMENTALNY RIADOK");
                output.append("\n");
                printMap(mapOfCurrentRow, " = ");

                List<Map<String, Fraction>> listOfMapsAtRow = new ArrayList<Map<String, Fraction>>();
                Map<String, Fraction> tempMap = new TreeMap<String, Fraction>();


                for (int i = 0; i < columns; i++) {
                    String s = "x" + i;
                    if (result.get(s) != null && mapOfCurrentRow.get(s) != null) {
                        Map<String, Fraction> subs = substituteMaps(result, mapOfCurrentRow, s);
                        listOfMapsAtRow.add(subs);
                        removeFromMapByKey(mapOfCurrentRow, s);
                    }
                }

                listOfMapsAtRow.add(mapOfCurrentRow);
                tempMap = add(listOfMapsAtRow);

                output.append("\n");


                output.append("PO SUBSTITUCII A SCITANI");
                output.append("\n");
                printMap(tempMap, " = ");
                tempMap = multiplyByConst(tempMap, new Fraction(-1,1));

                // END OF SUBSTITUTION

                output.append("\n");

                //PARAMETRIZATION
                parametrize(tempMap);
                output.append("\n");

                tempMap = multiplyByConst(tempMap, new Fraction(-1,1));
                //SORT BECAUSE OF ITERATING AT MAP
                output.append("\n");


                //DIVIDE ALL ROW BY VALUE OF PARAMETER
                tempMap = divideByConst(tempMap, findConstantOfParameter(tempMap));
                //------------------------------
                //MESS
                //FIXME
                String key = "x" + findIndexOfParameter(tempMap);

                Fraction value = tempMap.get(key);

                removeParameter(tempMap);

                output.append(key + " =");

                result.put(key, tempMap);
                printMap(findMapValueByKey(result, key), " ");

                output.append("--------------------------------------------");
                output.append("\n");
                output.append("VYSLEDKY");
                output.append("\n");
                printResult(result);
                output.append("\n");
                output.append("--------------------------------------------");
                output.append("\n");
                output.append("--------------------------------------------");
            }
        }
            return output.toString();
    }

}

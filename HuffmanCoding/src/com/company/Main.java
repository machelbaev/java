// По данной непустой строке s длины не более 10^4, состоящей из строчных букв латинского алфавита,
// постройте оптимальный беспрефиксный код. В первой строке выведите количество различных букв k,
// встречающихся в строке, и размер получившейся закодированной строки. В следующих k строках запишите
// коды букв в формате "letter: code". В последней строке выведите закодированную строку.

//Sample Input 1:
//a

//Sample Output 1:
//1 1
//a: 0
//0

package com.company;

import java.util.*;

public class Main {

    static ArrayList<Letter> letters;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        letters = new ArrayList<>();
        parseString(input);
        for (Letter let : letters)
            System.out.println(let.letter + " " + let.iterations);

        ArrayList<Letter> copy = new ArrayList<>();
        copy.addAll(letters);
        int n = letters.size();
        if (n == 1)
            copy.get(0).code = "0";
        for (int i = 0; i < n-1; i++){
            Letter[] min = findMin(letters);
            letters.remove(min[0]);
            letters.remove(min[1]);
            ArrayList<Letter> children1 = new ArrayList<>();
            if (min[0].children != null)
                children1.addAll(Arrays.asList(min[0].children));
            children1.add(min[0]);
            for (Letter let: children1){
                let.code = "1" + let.code;
            }
            ArrayList<Letter> children2 = new ArrayList<>();
            if (min[1].children != null)
                children2.addAll(Arrays.asList(min[1].children));
            children2.add(min[1]);
            for (Letter let: children2){
                let.code = "0" + let.code;
            }
            Letter newLetter = new Letter(children1.toArray(new Letter[children1.size()]),
                    children2.toArray(new Letter[children2.size()]),
                    min[0].iterations + min[1].iterations);
            letters.add(newLetter);
        }

        Map<Character, String> map = new HashMap<>();
        for (int i = 0; i < copy.size(); i++){
            map.put(copy.get(i).letter, copy.get(i).code);
        }
        String code = new String();
        for (int i = 0; i < input.length(); i++){
            code += map.get(input.charAt(i));
        }

        //output
        System.out.println(copy.size() + " " + code.length());
        for (Letter elem : copy){
            System.out.println(elem.toString());
        }
        System.out.println(code);
    }

    private static void parseString(String s) {
        for (int i = 0; i < s.length(); i++){
            boolean flag = false;
            for (int j = 0; j < letters.size(); j++){
                if (letters.get(j).letter == s.charAt(i)) {
                    letters.get(j).iterations++;
                    flag = true;
                    break;
                }
            }
            if (!flag)
                letters.add(new Letter(s.charAt(i), 1));
        }
    }

    private static Letter[] findMin(ArrayList<Letter> arr){
        int minIt = arr.get(0).iterations;
        Letter[] min = new Letter[] {arr.get(0), arr.get(1)} ;
        for (int i = 1; i < arr.size(); i++){
            if (minIt >= arr.get(i).iterations){
                minIt = arr.get(i).iterations;
                Letter temp = min[0];
                min = new Letter[] {arr.get(i), temp};
            }
        }
        return min;
    }
}

class Letter implements Comparable<Letter>{
    char letter;
    int iterations;
    String code = "";
    Letter[] children;

    public Letter(char letter, int iterations){
        this.letter = letter;
        this.iterations = iterations;
    }

    public Letter(Letter[] children1, Letter[] children2, int iterations){
        ArrayList<Letter> children = new ArrayList<>();
        children.addAll(Arrays.asList(children1));
        children.addAll(Arrays.asList(children2));
        this.children = children.toArray(new Letter[children.size()]);
        this.iterations = iterations;
    }

    @Override
    public String toString(){
        return letter + ": " + code;
    }

    @Override
    public int compareTo(Letter o) {
        return Integer.compare(iterations, o.iterations);
    }
}

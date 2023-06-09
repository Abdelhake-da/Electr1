/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package elect1ammad;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.TextArea;

/**
 *
 * @author Mr_Abdelhake
 */
public class Opperation {

    public String[][] convertDoubleMatrixToString(double[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length + 1;

        String[][] stringMatrix = new String[rows][columns];
        for (int row = 0; row < rows; row++) {
            stringMatrix[row][0] = "P" + (row + 1);
            for (int col = 1; col < columns; col++) {
                if (matrix[row][col - 1] == -1) {
                    stringMatrix[row][col] = "-";
                } else {
                    stringMatrix[row][col] = Double.toString(matrix[row][col - 1]);
                }
            }
        }

        return stringMatrix;
    }

    public void AffichMat(double[][] mat) {
        for (double[] mat1 : mat) {
            for (int j = 0; j < mat[0].length; j++) {
                System.out.print(mat1[j] + " | ");
            }
            System.out.println("");
        }
    }

    public void afficheVect(int[] mat) {
        for (int mat1 : mat) {
            System.out.println(mat + " | ");
        }
    }

    public double getMax(double[][] mat) {
        double max = mat[0][0];
        for (double[] mat1 : mat) {
            for (int j = 0; j < mat[0].length; j++) {
                if (max < mat1[j]) {
                    max = mat1[j];
                }
            }
        }

        return max;
    }

    public double getMin(double[][] mat) {
        double min = mat[0][0];
        for (double[] mat1 : mat) {
            for (int j = 0; j < mat[0].length; j++) {
                if (min > mat1[j]) {
                    min = mat1[j];
                }
            }
        }

        return min;
    }

    public List readCSV(String file) {
        List<List<String>> records = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
        }
        return records;
    }

    public void creatCSVFile(String csvData, String nameFile) throws IOException {
        String filePath = "src\\data\\" + nameFile + ".csv";
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write the CSV data to the file
            writer.write(csvData);
        } catch (IOException e) {
        }
    }

    public Parametre creatMatrice(List l) {
        int element = l.size() - 3;
        int creater = ((List) l.get(2)).size();
        double c;
        double d;
        double[] poid = new double[creater];
        double[][] MPerf = new double[element][creater];
        c = Double.parseDouble((String) ((List) l.get(0)).get(0));
        d = Double.parseDouble((String) ((List) l.get(1)).get(0));

        for (int i = 0; i < creater; i++) {
            poid[i] = Double.parseDouble(((List) l.get(02)).get(i).toString());
        }

        for (int i = 0; i < element; i++) {
            for (int j = 0; j < creater; j++) {
                MPerf[i][j] = Double.parseDouble(((List) l.get(i + 3)).get(j).toString());
            }

        }
        return new Parametre(d, c, poid, MPerf);
    }

    public double[][] creatMCorcondance(double[][] mp, double[] poid) {
        double somme = Arrays.stream(poid).sum();
        double[][] mc = new double[mp.length][mp.length];
        for (int i = 0; i < mc.length; i++) {
            for (int j = 0; j < mc.length; j++) {
                if (i == j) {
                    mc[i][j] = -1;
                } else {
                    double sommePoid = 0;
                    for (int k = 0; k < mp[0].length; k++) {
                        if (mp[i][k] >= mp[j][k]) {
                            sommePoid += poid[k];
                        }
                    }
                    mc[i][j] = sommePoid / somme;
                }
            }
        }
        return mc;
    }

    public double[][] creatMdiscordance(double[][] mp, TextArea txt) {
        String str = "";
        double Max = getMax(mp);
        double Min = getMin(mp);
        double Gi;
        double Gj;
        double max;

        double[][] md = new double[mp.length][mp.length];
        for (int i = 0; i < md.length; i++) {

            for (int j = 0; j < md.length; j++) {
                max = 0;
                if (i == j) {
                    md[i][j] = -1;
                } else {

                    double delta = Max - Min;
                    str += "D(P" + (i + 1) + ",P" + (j + 1) + ")\n= max(";
                    for (int k = 0; k < mp[0].length; k++) {
                        Gi = mp[i][k];
                        Gj = mp[j][k];
                        if (Gi < Gj) {
                            str += "" + Gi + "-" + Gj + " ,";
                            if (mp[j][k] - mp[i][k] > max) {
                                max = mp[j][k] - mp[i][k];
                            }
                        } else {
                            str += "0 ,";
                        }
                    }
                    str += ") / (" + Max + "-" + Min + ")\n";
                    md[i][j] = max / delta;
                    str += "= " + max + " / " + delta + " = " + md[i][j] + "\n";

                    str += "=============================================================\n";

                }
            }
        }
        txt.setText(str);
        return md;

    }

    public double[][] creatMsurclassement(double[][] mc, double[][] md, double d, double c) {
        double[][] ms = new double[md.length][md.length];
        for (int i = 0; i < md.length; i++) {
            for (int j = 0; j < md.length; j++) {
                if (i != j) {
                    if (mc[i][j] >= c && md[i][j] <= d) {
                        ms[i][j] = 1;
                    } else {
                        ms[i][j] = 0;
                    }
                } else {
                    ms[i][j] = -1;
                }
            }
        }
        return ms;
    }

    public int[] sommeDeUn(double[][] ms, TextArea txt) {
        int[] sommes = new int[ms.length];
        String str = "";
        for (int i = 0; i < ms.length; i++) {
            int somme = 0;
            for (int j = 0; j < ms.length; j++) {
                somme += ms[i][j];
            }

            sommes[i] = somme + 1;
        }
        txt.setText(tri(sommes));
        return sommes;
    }

    public String tri(int[] vect) {
        int[] v = vect;
        String str = "";
        int pre ;
        int indis ;
        int[] tree = new int[v.length];
        for (int i = 0; i < tree.length; i++) {
            pre = v[i];
            indis = i;
            v[i] = -1;
            for (int j = 0; j < v.length; j++) {
                if (v[j] > pre) {
                    v[indis] = pre;
                    pre = v[j];
                    indis = j;
                    v[j] = -1;
                }
            }
            str += (i + 1) + "- P" + (indis + 1) + " = " + pre + "\n";
            
        }

        return str;
    }
}
//un tri le vect sommes
// les detay de discordandse

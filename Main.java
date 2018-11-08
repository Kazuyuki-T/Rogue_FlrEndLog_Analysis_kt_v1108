
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kazuyuki.T
 */
public class Main {
    public static void main(String[] args) {
        // csv読み込みに使用
        
        
        String csvFile = "test2.csv";
        
        
        // 格納フォルダの作成
        String[] csvName = csvFile.split("\\.", 0);
        File dir = new File(csvName[0]);
        if(dir.mkdir() == true){
            // succece
        } else {
            System.out.println("フォルダの作成に失敗しました");
        }
        
        BufferedReader br = null; // 読み込み文章
        String line = ""; // 1行分
        try {
            File fname = new File(csvFile);
            br = new BufferedReader(new FileReader(fname));
            line = br.readLine(); // 1行ずつ読み込み
            
            // ここでlineには要素のインデックスが入る
            for(int fn = 0; fn < 4; fn++){
                Logger.OutputFileLog(new String(dir + "/" + dir + "_" + fn + "flr.csv"), new String(line + System.getProperty("line.separator")), true);
            }
            
            line = br.readLine(); // 1行ずつ読み込み
            String[] lineSp;
            double[] ele;
            for(int row = 0; line != null; row++){
                lineSp = line.split(",", 0);
                ele = new double[23];
                
                for(int i = 0; i < lineSp.length ; i++){
                    ele[i] = Double.parseDouble(lineSp[i]);
                }
                int flr = (int)ele[0];
                // 階層ごとにファイル追加
                Logger.OutputFileLog(new String(dir + "/" + dir + "_" + flr + "flr.csv"), new String(line + System.getProperty("line.separator")), true);
                
                line = br.readLine();
            }
            
            br.close();
        } catch (IOException e) {
            System.out.println(e); // エラー吐き
            System.out.println("constractar error");
        }
    }
}

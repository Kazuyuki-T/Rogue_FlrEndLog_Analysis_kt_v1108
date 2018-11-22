
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
        
        
        String csvNames = "test2.csv";
        //String csvFile = "log_20181108_144102.csv";
        int mode = 1; // 0:階層ごと, 1:矢の個数ごと, 2:階層分割かつ矢の個数ごと, 3:2f限定矢個数毎
        
        String[] csvName = csvNames.split(",", 0);
        for(int i=0; i < csvName.length; i++){
            System.out.println("start - " + csvName[i]);
            divideCsvFile(csvName[i], mode);
            System.out.println("end - "+ csvName[i]);
        }
    }
    
    public static void divideCsvFile(String csvFile, int mode){
        // 格納フォルダの作成
        String[] csvName = csvFile.split("\\.", 0);
        File dir = new File(csvName[0]);
        
        if(dir.exists() == true) {
            // フォルダが存在するため，何もしない
        } else {
            if(dir.mkdir() == true){
                // succece
            } else {
                System.out.println("フォルダの作成に失敗しました");
                System.exit(0);
            }
        }
        
        BufferedReader br = null; // 読み込み文章
        String line = ""; // 1行分
        String index = ""; // csv先頭のインデックス
        try {
            File inputFilename = new File(csvFile); // 入力ファイル
            File outputFilename = null; // 出力ファイル
            br = new BufferedReader(new FileReader(inputFilename)); // 入力ファイルの読み込みバッファー
            index = br.readLine(); // 1行ずつ読み込み
            line = br.readLine(); // 1行ずつ読み込み
            String[] lineSp; // コンマ分割文字列，配列格納
            double[] ele; // コンマ分割数字列，配列格納
            boolean addLine; // 追記するか否か
            
            for(int row = 0; line != null; row++){
                lineSp = line.split(",", 0);
                ele = new double[23];
                addLine = true;
                
                for(int i = 0; i < lineSp.length ; i++){
                    ele[i] = Double.parseDouble(lineSp[i]);
                }
                
                if(mode == 0){ // 階層ごとに分割
                    int flr = (int)ele[0];
                    // ファイルが存在しないとき，初期作成＆インデックス追加
                    outputFilename = new File(new String(dir + "/" + dir + "_" + flr + "flr.csv"));
                }
                else if(mode == 1){ // 矢の個数毎に分割
                    int arn = (int)ele[5];
                    // ファイルが存在しないとき，初期作成＆インデックス追加
                    outputFilename = new File(new String(dir + "/" + dir + "_" + arn + "ar.csv"));
                }
                else if(mode == 2){ // 階層ごと分割かつ矢の個数ごと分割
                    int flr = (int)ele[0];
                    int arn = (int)ele[5];
                    outputFilename = new File(new String(dir + "/" + dir + "_" + flr + "flr" + arn + "ar.csv"));
                }
                else if(mode == 3){ // 2f限定矢個数ごと分割
                    int flr = (int)ele[0];
                    if(flr == 2){
                        int arn = (int)ele[5];
                        outputFilename = new File(new String(dir + "/" + dir + "_" + flr + "flr" + arn + "ar.csv"));
                    }
                    else{
                        addLine = false;
                    }
                }
                else { // その他
                    //outputFilename = new File(new String(dir + "/" + dir + "_" + "modeerror.csv"));
                    addLine = false;
                }               
                
                if(addLine == true){
                    // ファイルが存在しないとき→ラベル付け，存在する→追記
                    if(outputFilename.exists() == false) Logger.OutputFileLog(outputFilename.toString(), new String(index + System.getProperty("line.separator")), true);
                    Logger.OutputFileLog(outputFilename.toString(), new String(line + System.getProperty("line.separator")), true);
                }
                
                line = br.readLine();
                if(row % 1000 == 0 || line == null) System.out.println("row : " + row);
            }
            
            br.close();
        } catch (IOException e) {
            System.out.println(e); // エラー吐き
            System.out.println("constractar error");
        }
    }
}

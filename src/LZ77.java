/*
Name: Amr Ayman Hassan
ID: 20180186
*/

import java.io.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
  
public class LZ77 {
 
   public static void main(String args[]) throws FileNotFoundException, IOException
    { 
        String input = null;
        try {
        	FileInputStream file = new FileInputStream("input.txt");
            DataInputStream data = new DataInputStream(file);
            BufferedReader r1 = new BufferedReader(new InputStreamReader(data));
            
            input = r1.readLine();
            System.out.println("Code is: " + input);
 
            FileOutputStream f1 = new FileOutputStream("compress.txt");
            DataOutputStream d1 = new DataOutputStream(f1);
 
            int pointer = 0, len = 0;
            char chararcter = '0';
            String x, y = null;
            int i, z, j = 0, l = 0;
            
            for(i=0;i<=input.length();i++) {
            	x = null;
                x = input.substring(0, i);
                j = i + 1;
                
                do {
                	y = input.substring(i, j);
                    z = x.lastIndexOf(y);
                    
                    if(z != -1) {
                    	l = z;
                        j++;
                        if(j>input.length()) {
                        	i = input.length();
                            pointer = x.length()-l;
                            len = y.length();
                            chararcter = '0';
                            
                            d1.writeInt(pointer);
                            d1.writeInt(len);
                            d1.writeChar(chararcter);
                            break;
                            }
                        }
                    else {
                    	i = x.length() + (y.length()-1);
            	        if(y.length()==1){
            		    pointer = 0;
                        len = 0;
                        }
            	        else {
            	        	pointer = x.length()-l;
                            len = y.length()-1;
                            }
            	        chararcter = y.charAt(y.length()-1);
                        
            	        d1.writeInt(pointer);
                        d1.writeInt(len);
                        d1.writeChar(chararcter);
                        break;
                        }
                    }while(z != -1);
                }
            d1.close();
            data.close();
            }
        catch (Exception ex){}
        
        FileInputStream f2 = new FileInputStream("compress.txt");
        DataInputStream d2 = new DataInputStream(f2);
        BufferedReader r2 = new BufferedReader(new InputStreamReader(d2));
        FileOutputStream f3 = new FileOutputStream("decompress.txt");
        DataOutputStream d3 = new DataOutputStream(f3);
 
        int position = 0, length = 0;
        char nextSymbol;
        String decomp = "";
        System.out.println("Tags are:");
        while(f2.available()!=0) {
        	position = d2.readInt();
            length = d2.readInt();
            nextSymbol = d2.readChar();
            System.out.println("<" + position + "," + length + "," + nextSymbol + ">");
            if(position==0)
                decomp += nextSymbol;
            else {
            	if(nextSymbol!='0')
            		decomp += (decomp.substring((decomp.length() - position), ((decomp.length() - position) + length)) + nextSymbol);
                else
                    decomp += (decomp.substring((decomp.length() - position), ((decomp.length() - position) + length)));
            	}
            }
        d3.writeChars(decomp);
        System.out.println("Decompression code is: " + decomp);
        
        r2.close();
        d3.close();
        }
   }
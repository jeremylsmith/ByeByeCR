/*
ByeByeLF - Re-paragraph Wrapped Text
Copyright (C) 1998 - 2020 Jeremy Smith
-----

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
-----

INFORMATION
  www: https://github.com/jeremylsmith/byebyelf/
  e-mail: jeremy@decompiler.org
*/

import java.util.Collections;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Vector;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileInputStream;

public class ByeByeLF
{
	static PrintStream ps;
	static String Book;
	static String Author;
	static char c;
	static String byeout = "";
	static int crcount=0,finished=0,fullstopon=0;

	static void countme(int cols)
	{
		if((crcount<cols)||(fullstopon==1))
		{
			ps.print("\n");
		}
		else
		{
			ps.print(" ");
		}
		c=0;
		fullstopon=0;
		crcount=0;
	};

	static int TextFileAverageWidth(String data)
	{
		Vector lines;
		int average = 0;
		lines = StringToLines(data);

		for(int i = 0; i < lines.size(); i++)
		{
			average += ((String)lines.get(i)).length();
		}
		return average / lines.size();
	}

	static String ByeByeLF(String filename)
	{
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try
		{
			ps = new PrintStream(baos, true, "UTF-8");
		}
		catch (Exception e)
		{
		}
		
		byeout = "";
		String textfile = FileToString(filename);
		
	//	int cols = 50;
		int cols = TextFileAverageWidth(textfile);
		
		System.err.println("Average col width " + cols);

		for (int z = 0; z < textfile.length(); z++)
		{
			c = (char) textfile.charAt(z);

			if (c==10)
			//c now 0
				countme(cols);

		if (c==0x0d) c=0;

		if ((c=='.')||(c=='!')||(c=='"')||(c=='“')||(c=='”'))
		{
			fullstopon=1;
		}
		else
		if ((c!=32)&&(c!=0x0d)&&(c!=10)&&(c!=0))
		{		
			fullstopon=0;
		}

		if (c!=0) crcount++;

		if (c!=0)
		{
			ps.print(c);
		}
	}
	
	String data = new String(baos.toByteArray());
	return data;
}

    public static Vector StringToLines(String saveString) {
        Vector day = new Vector();
        String aliasArray[] = saveString.split("\\r?\\n");

        for (String s : aliasArray) {
            day.add(s);
        }
        return day;
    }
	
    static String FileToString(String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            byte[] buffer = new byte[32];
            StringBuilder sb = new StringBuilder();
            while (fis.read(buffer) != -1) {
                sb.append(new String(buffer));
                buffer = new byte[32];
            }
            sb.append(new String(buffer));
            fis.close();

            return sb.toString();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return "";
    }
	
	public static void main(String []args) throws Exception {
		if (args.length == 1)
			System.out.println(ByeByeLF(args[0]));
		else
		{
			System.out.println("ByeByeLF (c) 2020 Jeremy Smith");
			System.out.println("Reformat flow of text document with hard returns at (say) 80 columns");
			System.out.println("Usage: java ByeByeLF \"sample.txt\" >output.txt");
		}
	}
}


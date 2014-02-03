package se.didriksson.mattias.discgolfcards.program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;

public class FileHandler {

	public static List<String> readCardStrings(Context context) throws IOException{
		List<String> lines = new ArrayList<String>();
		AssetManager assets = context.getAssets();
		BufferedReader br = new BufferedReader(new InputStreamReader(assets.open("Cards.txt")));
		
		while(true)
		{
			String line = br.readLine();
			if(line == null)
				break;
			else
				lines.add(line);
		}
		
		return lines;
	
	}
	
	
	
	
	
	
	
	
	
	
}

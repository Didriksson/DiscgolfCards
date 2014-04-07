package se.didriksson.mattias.discgolfcards.program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import android.content.Context;
import android.content.res.AssetManager;

public class FileHandler {

	public static List<String> readCardStrings(Context context)
			throws IOException {
		List<String> lines = new ArrayList<String>();
		AssetManager assets = context.getAssets();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				assets.open("Cards.txt")));

		while (true) {
			String line = br.readLine();
			if (line == null)
				break;
			else
				lines.add(line);
		}

		return lines;

	}
	
	
	public static void addCardsFromFileToDatabase(Context context){
		
		Stack<Card> deckOfCards = new Stack<Card>();
		DatabaseHandler database = new DatabaseHandler(context);
		
		List<String> cardStrings = null;
		try {
			 cardStrings = (ArrayList<String>) readCardStrings(context);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(!(cardStrings == null) && (!cardStrings.isEmpty()))
		{
			String[] card = cardStrings.remove(0).split(";");
			deckOfCards.push(new Card(card[0],card[1]));
		}

		while(deckOfCards.size()>0){
			database = new DatabaseHandler(context);
			database.addCard(deckOfCards.pop());
		}
	}
	
	

}

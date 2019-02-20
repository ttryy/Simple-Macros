package de.ttryy.simplemacros.manager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JsonManager {

	private File jsonFile;

	public JsonManager(String path, String fileName) {

		jsonFile = new File(path + "\\" + fileName).getAbsoluteFile();
		File folder = new File(path).getAbsoluteFile();

		if (!folder.exists()) {
			folder.mkdir();
		}

		if (!jsonFile.exists()) {
			try {
				jsonFile.createNewFile();
				writeJsonElementToFile(new JsonArray());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public JsonElement getJsonObjectFromFile() {
		try {
			String fileContent = new String(Files.readAllBytes(Paths.get(jsonFile.getAbsolutePath())));
			return new JsonParser().parse(fileContent);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void writeJsonElementToFile(JsonElement element) {
		try {
			FileWriter writer = new FileWriter(jsonFile);
			new GsonBuilder().create().toJson(element, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

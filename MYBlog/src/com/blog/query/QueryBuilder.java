package com.blog.query;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class QueryBuilder {

	private static final Logger LOG = Logger.getLogger(QueryBuilder.class);
	private static final String FILENAME = "E:\\MyFirstBlog\\MYBlog\\query\\query.json";

	private String readFile(final String fileName) {
		
		LOG.debug("the filename is : " + fileName);
		
		String fileContent = null;
		StringBuilder fileData = new StringBuilder();
		File file = new File(fileName);
		LOG.debug("the file is : " + file);
		try (Scanner scanner = new Scanner(file)) {
			
			while (scanner.hasNextLine()) {
				fileContent = scanner.nextLine();
				fileData.append(fileContent);
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileData.toString();
	}

	public String getQuery(final int id) {
		String query = readFile(FILENAME);
		LOG.debug("the content of file is : " + query);
		return query.replaceAll("id_var", String.valueOf(id));
	}
}

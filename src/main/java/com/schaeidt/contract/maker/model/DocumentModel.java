package com.schaeidt.contract.maker.model;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.text.Paragraph;

public class DocumentModel {
	TextDocument odt_doc = null;
	private Map<String, String> variables = new HashMap<>();
	private Pattern pattern = Pattern.compile(Pattern.quote("${") + "(.*?)" + Pattern.quote("}"));
	
	public Set<String> getVariables(){
		return variables.keySet();
	}
	
	public void set(String key, String value) {
		variables.put(key, value);
	}

	public Boolean initialize(File template) {
		try {
			odt_doc = TextDocument.loadDocument(template);
			Iterator<Paragraph> i = odt_doc.getParagraphIterator();
			while (i.hasNext()) {
				Paragraph p = i.next();
				Matcher matcher = pattern.matcher(p.getTextContent());
				while (matcher.find()) {
					variables.put(matcher.group(1), "");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public void save(File file) {
		if (file != null && odt_doc != null) {
			try {
				Iterator<Paragraph> i = odt_doc.getParagraphIterator();
				while (i.hasNext()) {
					Paragraph p = i.next();
					String paragraphText = p.getTextContent();
					if (!paragraphText.isEmpty()) {
						Matcher matcher = pattern.matcher(paragraphText);
						while (matcher.find()) {
							String pattern = Pattern.quote("${" + matcher.group(1) + "}");
							paragraphText = paragraphText.replaceAll(pattern, variables.get(matcher.group(1)));
						}
						System.out.println(paragraphText);
						p.setTextContentNotCollapsed(paragraphText);
					}
				}
				odt_doc.save(file);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

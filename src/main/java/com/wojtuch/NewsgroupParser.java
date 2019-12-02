package com.wojtuch;

import com.wojtuch.models.NewsgroupsArticle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by wojlukas on 5/20/16.
 */
public class NewsgroupParser {

    public static void main(String[] args) throws IOException {
        NewsgroupParser parser = new NewsgroupParser("20_newsgroups");
        parser.parse();

        parser.getArticles().forEach((key, articles) -> {
            System.out.println(key+": "+articles.size());
            articles.forEach(a -> {
                Date d = a.getDate();
                if (d == null) {
                    System.out.println("\t"+a.getHeader("Subject"));
                    System.out.println("\t"+a.getHeader("Message-ID"));
                    System.out.println("\tNo date present.");
                }
            });
        });
    }

    private Path folder;
    private Map<String, List<NewsgroupsArticle>> articles = new HashMap<>();

    public NewsgroupParser(Path folder) {
        this.folder = folder.toAbsolutePath();
    }

    public NewsgroupParser(String folder) {
        this.folder = Paths.get(folder).toAbsolutePath();
    }

    public Map<String, List<NewsgroupsArticle>> getArticles() {
        return articles;
    }

    public void parse() throws IOException {
        try (DirectoryStream<Path> categoriesDirs = Files.newDirectoryStream(folder)) {
            for (Path category : categoriesDirs) {
                String newsgroups = category.getFileName().toString();
                articles.put(newsgroups, new ArrayList<>());

                try (DirectoryStream<Path> articleFiles = Files.newDirectoryStream(category)) {
                    for (Path articleFile : articleFiles) {
                        try (BufferedReader br = new BufferedReader(new FileReader(articleFile.toString()))){
                            NewsgroupsArticle article = new NewsgroupsArticle();
                            article.setLabel(newsgroups);
                            StringBuffer content = new StringBuffer();
                            String line;
                            boolean inContent = false;
                            while ((line = br.readLine()) != null) {
                                if (!inContent) {
                                    if (line.trim().length() == 0) {
                                        inContent = true;
                                        continue;
                                    }

                                    String[] split = line.split(": ", 2);
                                    if (split.length == 2) {
                                        article.addHeader(split[0], split[1]);
                                    }

                                }
                                else {
                                    content.append(line);
                                    content.append(" ");
                                }
                            }
                            article.setRawText(content.toString().trim());
                            articles.get(newsgroups).add(article);
                        }
                    }
                }
            }
        }
    }
}

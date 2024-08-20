package org.plscraper.scraper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.plscraper.entity.Match;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

public class PLScraper {
    private Connection connection = null;
    private String plWebUrl = "https://www.premierleague.com";
    private static String defaultSavePath = "pl_scraped.html";

    public PLScraper() {
        this.connection = Jsoup
                .connect(this.plWebUrl)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/124.0.0.0 " +
                        "Safari/537.36")
                .header("Accept-Language", "*");
    }

    public void setURL(String path) {
        this.connection.url(this.plWebUrl + "/" + path);
    }

    public Document getRequest() throws IOException {
        Document content = null;

        try {
            content = connection.get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    public Boolean getAndSave(String filePath) throws IOException {
        Document doc = getRequest();

        try (BufferedWriter bw =
                     new BufferedWriter(new FileWriter(filePath))) {
            bw.write(doc.html());
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean getAndSave() throws IOException {
        return getAndSave(defaultSavePath);
    }

    public HashSet<String> getTeams(int gamesCount) {
        final String awayTeamNameXPath = "/html/body/main/div/section[2]/div[2]/section/div[1]/div[2]/div[1]/div[3]/div[1]/a[2]/span[1]";
        final String homeTeamNameXPath = "/html/body/main/div/section[2]/div[2]/section/div[1]/div[2]/div[1]/div[1]/div[1]/a[2]/span[1]";

        HashSet<String> teams = new HashSet<>();

        IntStream.range(0, gamesCount)
                .mapToObj(i -> {
                    setURL("match/" + (i + 1));
                    try {
                        return getRequest();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .forEach(document -> {
                    teams.add(document.selectXpath(homeTeamNameXPath).text());
                    teams.add(document.selectXpath(awayTeamNameXPath).text());
                });

            return teams;
        }

        public List<Match> getMatches(int count) {
            final String awayTeamNameXPath =
                    "/html/body/main/div/section[2]/div[2]/section/div[1]/div[2]/div[1]/div[3]/div[1]/a[2]/span[1]";
            final String homeTeamNameXPath =
                    "/html/body/main/div/section[2]/div[2]/section/div[1]/div[2]/div[1]/div[1]/div[1]/a[2]/span[1]";
            final String resultXPath =
                    "/html/body/main/div/section[2]/div[2]/section/div[1]/div[2]/div[1]/div[2]/div[1]";
            final String attendanceXPath =
                    "/html/body/main/div/section[2]/div[2]/section/div[1]/div[1]/div[4]";


            return null;
        }
    }


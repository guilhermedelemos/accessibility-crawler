package io.github.guilhermedelemos.crawler.util;

import io.github.guilhermedelemos.crawler.model.Site;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class StrategySiteCSV {

    protected BufferedReader in;
    private String separator = ";";
    public static final int POS_RANK = 0;
    public static final int POS_URL = 1;
    public static final int POS_DATE = 2;

    public List<Site> read(String filename) throws IOException {
        try {
            this.in = Files.newBufferedReader(Paths.get(filename));
            if(this.in == null || !this.in.ready()) {
                return new ArrayList<>();
            }
            List<Site> sites = new ArrayList<>();
            String line;
            while ((line = this.in.readLine()) != null) {
                String[] data = line.split(this.separator);
                sites.add(
                        new Site(Integer.parseInt(data[POS_RANK]),
                        data[POS_URL],
                        new SimpleDateFormat("yyyy-MM-dd").parse(data[POS_DATE]))
                );
            }
            this.in.close();
            return sites;
        } catch(IOException | ParseException e) {
            Log.log(e.toString());
            return new ArrayList<>();
        }
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
}
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Main {
    private static List<String> getURLs(String URL) throws IOException {
        List<String> rt = new ArrayList<>();
        Document doc = Jsoup.connect(URL).get();
        Elements links = doc.select("a");
        for (var link : links) {
            String linkHref = link.attr("href");
            if (linkHref.endsWith(".pdf")) {
                String url = URL + linkHref;
                rt.add(url);
//                String FILE_NAME = linkHref.split("/")[1];
//                String path = "files/"+FILE_NAME;

            }
        }
        return rt;
    }
    private static void downLoadFiles(List<String> urls) {
        for (var url: urls) {
            String filename = url.split("/")[url.split("/").length-1];
            String path = "files/"+filename;
            Thread th = new Thread(()->{
                System.out.println("...Start downloading "+ filename);
                try(BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
                    FileOutputStream fileOutputStream = new FileOutputStream(path)) {
                    byte[] dataBuffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                    }
                    System.out.println("Completed " + filename);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            th.start();

        }
    }
    public static void main(String[] args) {

        List<String> urls = null;
        try {
            urls = getURLs("http://www.dre.vanderbilt.edu/~schmidt/cs891f/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        downLoadFiles(urls);
    }
}

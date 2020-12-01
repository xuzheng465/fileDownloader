public class MultithreadDownloader {
    private String url;
    private String filetype;
    private int numOfThreads;

    public MultithreadDownloader(String url, String filetype, int numOfThreads) {
        this.url = url;
        this.filetype = filetype;

    }
}

package utils;

import dto.BookCommonInfo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Fb2Writer {
    private static final String FIRST_LINE_IN_FILE =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "\t<FictionBook xmlns=\"http://www.gribuser.ru/xml/fictionbook/2.0\" xmlns:l=\"http://www.w3.org/1999/xlink\">\n";
    private static final String END_LINE_IN_FILE =
            "</body></FictionBook>";
    private FileWriter fileWriter = null;

    public Fb2Writer(String path) {

        File file = new File(path);
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(FIRST_LINE_IN_FILE);
        } catch (IOException e) {
            System.out.println("Cannot create a new file");
            e.printStackTrace();
        }
    }

    public void writeDescription(BookCommonInfo bookCommonInfo) {
        final String begin = "<description>\n" +
                "\t<title-info>\n" +
                "\t\t<genre>sf</genre>\n" +
                "\t\t<lang>ru</lang>\n" +
                "\t\t<src-lang>ru</src-lang>\n";
        final String middle = "\t</title-info>\n" +
                "\t<document-info>\n" +
                "\t\t<author>\n" +
                "\t\t<nickname>Seraza</nickname></author>\n" +
                "\t\t<program-used>Seraza`s programm to fb2 from site</program-used>\n" +
                "\t\t<id>Seraza's_v1</id>\n" +
                "\t\t<version>1</version>\n" +
                "\t</document-info>\n" +
                "</description>\n" +
                "<body>\n";
        StringBuilder res = new StringBuilder(begin);
        try {
            res.append("\t\t<author><first-name>");
            res.append(bookCommonInfo.getAuthor());
            res.append("</first-name></author>\n\t\t<book-title>");
            res.append(bookCommonInfo.getTitle());
            res.append("</book-title>\n\t\t<annotation><p>");
            res.append(bookCommonInfo.getAnnotation());
            res.append("</p></annotation>\n\t\t<translator><nickname>");
            res.append(bookCommonInfo.getAuthor());
            res.append("</nickname></translator>\n\t\t<sequence name=\"");
            res.append(bookCommonInfo.getTitle());
            res.append("\"/>\n");
            res.append(middle);
            res.append("<section><p><strong>");
            res.append(bookCommonInfo.getAuthor());
            res.append("</strong></p>");
            res.append("<title><p>");
            res.append(bookCommonInfo.getTitle());
            res.append("</p></title>\n<annotation><p>");
            res.append(bookCommonInfo.getAnnotation());
            res.append("</p>\n</annotation>\n</section>");
            fileWriter.write(res.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writePartTitle(String title) {
        StringBuilder res = new StringBuilder("<section><title><p>");
        try {
            res.append(title);
            res.append("</p></title>\n");
            fileWriter.write(res.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeText(String text) {
        StringBuilder res = new StringBuilder(text);
        try {
            fileWriter.write(res.append("</section>\n").toString());
        } catch (IOException e) {
            System.out.println("Cannot write text");
            e.printStackTrace();
        }
    }

    public void endWrite() {
        try {
            fileWriter.write(END_LINE_IN_FILE);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeAnyText(String text) {
        try {
            fileWriter.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

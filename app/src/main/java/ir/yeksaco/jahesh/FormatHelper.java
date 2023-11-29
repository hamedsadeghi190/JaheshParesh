package ir.yeksaco.jahesh;

import androidx.annotation.NonNull;

public class FormatHelper {
    @NonNull
    public static String MakeHtmlFile(String Data) {


        String str = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n" +
                "        \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "\t\n" +
                "    <style type=\"text/css\">\n" +

                "\t\t@font-face {\n" +
                "            font-family: Iran;\n" +
                "\t\t\t\n" +
                "            src: url(\"file:///android_asset/fonts/iran.ttf\")\n" +
                "        }\n" +
                "        @font-face {\n" +
                "            font-family: Yekan;\n" +
                "\t\t\n" +
                "            src: url(\"file:///android_asset/fonts/yekan.ttf\")\n" +
                "        }\n" +
                "        @font-face {\n" +
                "            font-family: Homa;\n" +
                "\t\t\t\n" +
                "            src: url(\"file:///android_asset/fonts/bhoma.TTF\")\n" +
                "        }\n" +
                "\n" +
                "        @font-face {\n" +
                "            font-family: Nazanin;\n" +
                "\t\t\t\n" +
                "            src: url(\"file:///android_asset/fonts/bnazanin.TTF\")\n" +
                "        }\n" +
                "\n" +
                "        body {\n" +
                "            font-family: CUSTOMFONTNAME;\n" +
                "            line-height: CUSTOMLINEHEGHT px;\n" +
                "            font-size: CUSTOMFONTSIZEpx;\n" +
                "            direction: rtl;\n" +
                "            font-weight: normal;\n" +
                "            text-align: justify;\n" +
                "            margin: 0px;\n" +
                "            padding: 7px;\n" +
                "        }\n" +
                "\t\t\n" +
                "\timg {\n" +
                "\t\twidth : 96%;\n" +
                "\t\tmax-width : 900px;\n" +
                "\t\tdisplay: block;\n" +
                "\t\tmargin-left: auto;\n" +
                "\t\tmargin-right: auto;\n" +
                "\t\tmargin-top : 5px;\n" +
                "\t\tmargin-down : 5px;\n" +
                "\t}\n" +
                "\n" +
                "    </style>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\n" +
                "\n" +
                "</head>\n" +
                "\n" +
                "<body dir=\"rtl\" style=\"font-family:'CUSTOMFONTNAME'; font-size:'CUSTOMFONTSIZEpx';text-align: justify;\">\n" +
                "BODYCONTENT\n" +
                "</body>\n" +
                "</html>";
//            str = str.replaceAll("CUSTOMFONTNAME", sharedpreferences.getString("FontName", "Iran"))
//                    .replaceAll("CUSTOMFONTSIZE", sharedpreferences.getString("FontSize", 18) + "")
//                    .replaceAll("CUSTOMFOmlkNTSIZEH3", (sharedpreferences.getInt("FontSize", 20) + ""))
//                    .replaceAll("BODYCONTENT", PersianReshape.reshape_browser(Data));
        str = str.replaceAll("CUSTOMFONTNAME", "Iran")
                .replaceAll("CUSTOMFONTSIZE", 16 + "")
                .replaceAll("CUSTOMFOmlkNTSIZEH3", 18 + "")
                .replaceAll("BODYCONTENT", PersianReshape.reshape_browser(Data));
        return str;
    }


}

package com.shengxun.ikanalyzer;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.StringReader;

public class Iktest {

    public static void main(String[] args) throws Exception {
        String str = "我很想看一下，这个房子挺好的啊。多少钱啊";
        IKAnalyzer ik = new IKAnalyzer();
        StringReader reader = new StringReader(str);
        //分词
        TokenStream ts = ik.tokenStream("", reader);
        ts.reset();
        CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
        while (ts.incrementToken()) {
            System.out.print("【" + term.toString() + "】");
        }
        reader.close();
    }

}

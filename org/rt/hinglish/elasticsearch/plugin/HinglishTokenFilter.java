/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rt.hinglish.elasticsearch.plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 *
 * @author Krishna
 */
public class HinglishTokenFilter extends TokenFilter
{
    private final CharTermAttribute termAttribute = addAttribute(CharTermAttribute.class);
    private static Set<List<Object>> lsRegexs;
    static
    {
        lsRegexs = new LinkedHashSet<>();
        lsRegexs.add(createRegEx("a{2,}", 	"a"));
        lsRegexs.add(createRegEx("e{2,}", 	"i"));
        lsRegexs.add(createRegEx("o{2,}", 	"u"));
        lsRegexs.add(createRegEx("ai", 		"e"));
        lsRegexs.add(createRegEx("(ou|au)", "o"));
        lsRegexs.add(createRegEx("(am|an)", "am"));
        lsRegexs.add(createRegEx("kh{1,}", 	"k"));
        lsRegexs.add(createRegEx("gh{1,}", 	"g"));
        lsRegexs.add(createRegEx("ch{2,}", 	"ch"));
        lsRegexs.add(createRegEx("jh{1,}", 	"j"));
        lsRegexs.add(createRegEx("th{1,}", 	"t"));
        lsRegexs.add(createRegEx("dh{1,}", 	"d"));
        lsRegexs.add(createRegEx("ph{1,}", 	"p"));
        lsRegexs.add(createRegEx("bh{1,}", 	"b"));
        lsRegexs.add(createRegEx("w", 		"v"));
        lsRegexs.add(createRegEx("sh{1}", 	"s"));
    };

    public static List<Object> createRegEx(final String strPattern, final String strReplacement)
    {
        List<Object> lsInput	= new ArrayList<>();
        lsInput.add(Pattern.compile(String.format(".*?%s.*", strPattern), Pattern.DOTALL | Pattern.CASE_INSENSITIVE));
        lsInput.add(Pattern.compile(strPattern, Pattern.DOTALL | Pattern.CASE_INSENSITIVE));
        lsInput.add(strReplacement);

        return lsInput;
    }

    public static void stemHinglish(CharTermAttribute termAtt)
    {
        char [] buffer                  = termAtt.buffer();
        String strInput 		= new String(termAtt.toString());
        //System.out.println("Before " + strInput + " " + termAtt.toString());
        Iterator itr			= lsRegexs.iterator();
        while (itr.hasNext())
        {
                List<Object> lsInputs 	= (List<Object>)itr.next();
                Matcher matcher		= ((Pattern)lsInputs.get(0)).matcher(strInput);
                if (matcher.matches())
                {
                        Matcher replMatcher	= ((Pattern)lsInputs.get(1)).matcher(strInput);
                        strInput		= replMatcher.replaceAll((String)lsInputs.get(2));
                }
        }

        //strInput = strInput.trim();
        for (int iCounter = 0; iCounter < strInput.length(); iCounter++)
        {
            buffer[iCounter] = strInput.charAt(iCounter);
        }
        termAtt.setLength(strInput.length());
        //System.out.println("After " + strInput + " " + termAtt.toString());
    }
    public HinglishTokenFilter(TokenStream input) {
        super(input);
    }
    
    @Override
    public boolean incrementToken() throws IOException {
        if (!input.incrementToken())
            return false;
        
        int tokenLength  = termAttribute.length();
        if (tokenLength > 0)
        {
            stemHinglish(termAttribute);
        }
        return true;
    }
    
}

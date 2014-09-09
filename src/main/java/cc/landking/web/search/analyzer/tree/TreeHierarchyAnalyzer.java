// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TreeHierarchyAnalyzer.java

package cc.landking.web.search.analyzer.tree;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.ElasticsearchIllegalArgumentException;
// Referenced classes of package com.landray.search.analysis.tokenizer.category:
//            TreeHierarchyTokenizer
import org.elasticsearch.common.settings.Settings;

public class TreeHierarchyAnalyzer extends Analyzer
{

    public TreeHierarchyAnalyzer(Settings indexSettings, Settings settings)
    {
        this.settings = settings;
        componentSettings = settings.getComponentSettings(getClass());
        bufferSize = componentSettings.getAsInt("buffer_size", Integer.valueOf(1024)).intValue();
        String delimiter = componentSettings.get("delimiter");
        if(delimiter == null)
        {
            this.delimiter = 'x';
        } else
        {
            if(delimiter.length() > 1)
                throw new ElasticsearchIllegalArgumentException("delimiter can only be a one char value");
            this.delimiter = delimiter.charAt(0);
        }
        String replacement = componentSettings.get("replacement");
        if(replacement == null)
        {
            this.replacement = this.delimiter;
        } else
        {
            if(replacement.length() > 1)
                throw new ElasticsearchIllegalArgumentException("replacement can only be a one char value");
            this.replacement = replacement.charAt(0);
        }
        skip = componentSettings.getAsInt("skip", Integer.valueOf(0)).intValue();
    }

//    public TokenStream tokenStream(String fieldName, Reader reader)
//    {
//        System.out.println("TreeHierarchyAnalyzer ");
//        TokenStream result = new TreeHierarchyTokenizer(reader, bufferSize, delimiter, replacement, skip);
//        return result;
//    }
	@Override
	protected TokenStreamComponents createComponents(String fieldName, final Reader in) {
		Tokenizer _splitokenizer =  new TreeHierarchyTokenizer(in, bufferSize, delimiter, replacement, skip);
		return new TokenStreamComponents(_splitokenizer);
	}

    private final int bufferSize;
    private final char delimiter;
    private final char replacement;
    private final int skip;
    protected final Settings settings;
    protected final Settings componentSettings;
}

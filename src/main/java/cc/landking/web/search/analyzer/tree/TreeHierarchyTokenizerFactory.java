// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TreeHierarchyTokenizerFactory.java

package cc.landking.web.search.analyzer.tree;

import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.ElasticsearchIllegalArgumentException;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractTokenizerFactory;

// Referenced classes of package com.landray.search.analysis.tokenizer.category:
//            TreeHierarchyTokenizer

public class TreeHierarchyTokenizerFactory extends AbstractTokenizerFactory
{

    public TreeHierarchyTokenizerFactory(Index index, Settings indexSettings, String name, Settings settings)
    {
        super(index, indexSettings, name, settings);
        bufferSize = settings.getAsInt("buffer_size", Integer.valueOf(TreeHierarchyTokenizer.DEFAULT_BUFFER_SIZE)).intValue();
        String delimiter = settings.get("delimiter");
        if(delimiter == null)
        {
            this.delimiter = TreeHierarchyTokenizer.DEFAULT_DELIMITER;
        } else
        {
            if(delimiter.length() > 1)
                throw new ElasticsearchIllegalArgumentException("delimiter can only be a one char value");
            this.delimiter = delimiter.charAt(0);
        }
        String replacement = settings.get("replacement");
        if(replacement == null)
        {
            this.replacement = this.delimiter;
        } else
        {
            if(replacement.length() > 1)
                throw new ElasticsearchIllegalArgumentException("replacement can only be a one char value");
            this.replacement = replacement.charAt(0);
        }
        skip = settings.getAsInt("skip", Integer.valueOf(0)).intValue();
    }

    public Tokenizer create(Reader reader)
    {
        return new TreeHierarchyTokenizer(reader, bufferSize, delimiter, replacement, skip);
    }

    private final int bufferSize;
    private final char delimiter;
    private final char replacement;
    private final int skip;
}

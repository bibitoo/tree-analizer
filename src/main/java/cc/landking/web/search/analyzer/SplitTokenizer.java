package cc.landking.web.search.analyzer;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

public class SplitTokenizer extends Tokenizer {
    private static final int DEFAULT_BUFFER_SIZE = 1024;
    public static final char DEFAULT_DELIMITER = 32;
    public static final int DEFAULT_SKIP = 0;
    private final char delimiter;
    private final char replacement;
    private final int skip;
    private final CharTermAttribute termAtt;
    private final OffsetAttribute offsetAtt;
    private final PositionIncrementAttribute posAtt;
    private int startPosition;
    private int finalOffset;
    private int skipped;
    private boolean endDelimiter;
    private StringBuilder resultToken;


    public SplitTokenizer(Reader input)
    {
        this(input, 1024, ' ', ' ', 0);
    }

    public SplitTokenizer(Reader input, int skip)
    {
        this(input, 1024, ' ', ' ', skip);
    }

    public SplitTokenizer(Reader input, int bufferSize, char delimiter)
    {
        this(input, bufferSize, delimiter, delimiter, 0);
    }

    public SplitTokenizer(Reader input, char delimiter, char replacement)
    {
        this(input, 1024, delimiter, replacement, 0);
    }

    public SplitTokenizer(Reader input, char delimiter, char replacement, int skip)
    {
        this(input, 1024, delimiter, replacement, skip);
    }

    public SplitTokenizer(Reader input, int bufferSize, char delimiter, char replacement, int skip)
    {
        super(input);
        termAtt = (CharTermAttribute)addAttribute(CharTermAttribute.class);
        offsetAtt = (OffsetAttribute)addAttribute(OffsetAttribute.class);
        posAtt = (PositionIncrementAttribute)addAttribute(PositionIncrementAttribute.class);
        startPosition = 0;
        finalOffset = 0;
        skipped = 0;
        endDelimiter = false;
        termAtt.resizeBuffer(bufferSize);
        this.delimiter = delimiter;
        this.replacement = replacement;
        this.skip = skip;
        resultToken = new StringBuilder(bufferSize);
    }

    public final boolean incrementToken()
        throws IOException
    {
        clearAttributes();
        if(resultToken.length() == 0)
            posAtt.setPositionIncrement(1);
        else
            posAtt.setPositionIncrement(0);
        resultToken = new StringBuilder(1024);
        int length = 0;
        boolean added = false;
        if(endDelimiter)
            endDelimiter = false;
        do
        {
            int c = input.read();
            if(c < 0)
                if(skipped > skip)
                {
                    length += resultToken.length();
                    termAtt.setLength(length);
                    finalOffset = correctOffset(startPosition + length);
                    offsetAtt.setOffset(correctOffset(startPosition), finalOffset);
                    if(added)
                    {
                        resultToken.setLength(0);
                        resultToken.append(termAtt.buffer(), 0, length);
                    }
                    return added;
                } else
                {
                    finalOffset = correctOffset(startPosition + length);
                    return false;
                }
            if(!added)
            {
                added = true;
                skipped++;
                if(skipped > skip)
                {
                    termAtt.append(c != delimiter ? (char)c : replacement);
                    length++;
                } else
                {
                    startPosition++;
                }
                continue;
            }
            if(c == delimiter)
            {
                if(skipped > skip)
                {
                    endDelimiter = true;
                    break;
                }
                skipped++;
                if(skipped > skip)
                {
                    termAtt.append(replacement);
                    length++;
                } else
                {
                    startPosition++;
                }
            } else
            if(skipped > skip)
            {
                termAtt.append((char)c);
                length++;
            } else
            {
                startPosition++;
            }
        } while(true);
        length += resultToken.length();
        termAtt.setLength(length);
        finalOffset = correctOffset(startPosition + length);
        offsetAtt.setOffset(correctOffset(startPosition), finalOffset);
        resultToken.setLength(0);
        resultToken.append(termAtt.buffer(), 0, length);
        return true;
    }

    public final void end()
    {
        offsetAtt.setOffset(finalOffset, finalOffset);
    }

    public void reset(Reader input)
        throws IOException
    {
        super.reset();
        resultToken.setLength(0);
        finalOffset = 0;
        endDelimiter = false;
        skipped = 0;
    }


}

package cc.landking.web.search.analyzer;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.ElasticsearchIllegalArgumentException;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;

public class LkAnalyzer extends Analyzer {

    Settings settings;
    Environment environment;

    private final int bufferSize;
    private final char delimiter;
    private final char replacement;
    private final int skip;

    public LkAnalyzer(Settings indexSetting,Settings settings, Environment environment) {
        super();
        this.settings=settings;
        this.environment= environment;
        
        bufferSize = settings.getAsInt("buffer_size", Integer.valueOf(1024)).intValue();
        String delimiter = settings.get("delimiter");
        if(delimiter == null)
        {
            this.delimiter = ',';
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

	@Override
	protected TokenStreamComponents createComponents(String fieldName, final Reader in) {
		Tokenizer _splitokenizer =  new SplitTokenizer(in, bufferSize, delimiter, replacement, skip);
		return new TokenStreamComponents(_splitokenizer);
	}

}

package cc.landking.web.search.analyzer;

import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractTokenizerFactory;
import org.elasticsearch.index.settings.IndexSettings;
import org.elasticsearch.ElasticsearchIllegalArgumentException;

public class SplitTokenizerFactory extends AbstractTokenizerFactory {
    private final int bufferSize;
    private final char delimiter;
    private final char replacement;
    private final int skip;
    @Inject
	public SplitTokenizerFactory(Index index, @IndexSettings Settings indexSettings,
			@Assisted String name, @Assisted Settings settings) {
		super(index, indexSettings, name, settings);
//		init(indexSettings, settings);
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
	public Tokenizer create(Reader reader) {
		return new SplitTokenizer(reader, bufferSize, delimiter, replacement, skip);
	}

}

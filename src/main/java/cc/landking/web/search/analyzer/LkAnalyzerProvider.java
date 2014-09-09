package cc.landking.web.search.analyzer;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;
import org.elasticsearch.index.settings.IndexSettings;
public class LkAnalyzerProvider extends AbstractIndexAnalyzerProvider<LkAnalyzer> {
	private final LkAnalyzer analyzer;

    @Inject
    public LkAnalyzerProvider(Index index, @IndexSettings Settings indexSettings, Environment env, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettings, name, settings);
        analyzer=new LkAnalyzer(indexSettings, settings, env);
    }

    @Override public LkAnalyzer get() {
        return this.analyzer;
	}

}

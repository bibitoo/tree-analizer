package cc.landking.web.search.analyzer.tree;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;
import org.elasticsearch.index.settings.IndexSettings;
public class LkTreeAnalyzerProvider extends AbstractIndexAnalyzerProvider<TreeHierarchyAnalyzer> {
	private final TreeHierarchyAnalyzer analyzer;

    @Inject
    public LkTreeAnalyzerProvider(Index index, @IndexSettings Settings indexSettings, Environment env, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettings, name, settings);
        analyzer=new TreeHierarchyAnalyzer(indexSettings, settings);
    }

    @Override public TreeHierarchyAnalyzer get() {
        return this.analyzer;
	}

}

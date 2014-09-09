package cc.landking.web.search.analyzer;

import org.elasticsearch.index.analysis.AnalysisModule.AnalysisBinderProcessor;

import cc.landking.web.search.analyzer.tree.TreeHierarchyTokenizerFactory;

public class LandkingAnalysisBinderProcessor extends AnalysisBinderProcessor {
	 @Override public void processAnalyzers(AnalyzersBindings analyzersBindings) {
	        analyzersBindings.processAnalyzer("landking-analyzer", LkAnalyzerProvider.class);
	        analyzersBindings.processAnalyzer("landking-tree-analyzer", LkAnalyzerProvider.class);
	        super.processAnalyzers(analyzersBindings);
	    }


	    @Override
	    public void processTokenizers(TokenizersBindings tokenizersBindings) {
	      tokenizersBindings.processTokenizer("landking-split-tokenizer", SplitTokenizerFactory.class);
	      tokenizersBindings.processTokenizer("landking-tree-tokenizer", TreeHierarchyTokenizerFactory.class);
	      super.processTokenizers(tokenizersBindings);
	    }
}

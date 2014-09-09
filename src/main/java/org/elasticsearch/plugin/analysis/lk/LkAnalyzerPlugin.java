package org.elasticsearch.plugin.analysis.lk;

import org.elasticsearch.plugins.AbstractPlugin;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.index.analysis.AnalysisModule;

import cc.landking.web.search.analyzer.LandkingAnalysisBinderProcessor;

public class LkAnalyzerPlugin extends AbstractPlugin {

	@Override  public String name() {
		return "landking-analyzer";
	}

	@Override  public String description() {
		return "landking analyzer for split and so";
	}
	@Override public void processModule(Module module) {
        if (module instanceof AnalysisModule) {
            AnalysisModule analysisModule = (AnalysisModule) module;
            analysisModule.addProcessor(new LandkingAnalysisBinderProcessor());
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rt.hinglish.elasticsearch.plugin;

import java.util.LinkedHashMap;
import java.util.Map;
import org.elasticsearch.index.analysis.TokenFilterFactory;
import org.elasticsearch.indices.analysis.AnalysisModule;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;
/**
 *
 * @author Krishna
 */
public class HinglishStemmerPlugin extends Plugin implements AnalysisPlugin
{    
    @Override
    public Map<String, AnalysisModule.AnalysisProvider<TokenFilterFactory>> getTokenFilters() 
    {
        Map<String, AnalysisModule.AnalysisProvider<TokenFilterFactory>> dictOut = new LinkedHashMap<>();
        dictOut.put("hinglish-token-filter", HinglishTokenFilterFactory::new);
        
        return dictOut;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rt.hinglish.elasticsearch.plugin;

import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;

/**
 *
 * @author Krishna
 */
public class HinglishTokenFilterFactory extends AbstractTokenFilterFactory
{

    public HinglishTokenFilterFactory(IndexSettings indexSettings, Environment e, String name, Settings settings) {
        super(indexSettings, name, settings);
    }

    @Override
    public TokenStream create(TokenStream stream) {
        return new HinglishTokenFilter(stream);
    }
    
}

package org.carrot2.text.preprocessing.filter;

import org.carrot2.text.preprocessing.LabelFilterProcessor;
import org.carrot2.text.preprocessing.PreprocessingContext;

/**
 * Defines the contract for label filtering components, which mark words and phrases that
 * should not be considered as candidates for cluster labels
 * 
 * @see LabelFilterProcessor
 */
public interface LabelFilter
{
    /**
     * Called to perform label filtering.
     * 
     * @param context contains words and phrases to be filtered
     * @param acceptedWords the filter should set to <code>false</code> those elements
     *            that correspond to the words to be filtered out
     * @param acceptedPhrases the filter should set to <code>false</code> those elements
     *            that correspond to the phrases to be filtered out
     */
    public void filter(PreprocessingContext context, boolean [] acceptedWords,
        boolean [] acceptedPhrases);

    /**
     * @return <code>true</code> if the filter is to be applied, <code>false</code> if the
     *         filter should be omitted by the {@link LabelFilterProcessor}.
     */
    public boolean isEnabled();
}

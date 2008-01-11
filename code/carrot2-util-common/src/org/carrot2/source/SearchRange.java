package org.carrot2.source;

/**
 * A single result window to fetch.
 */
public final class SearchRange
{
    /** Empty range. */
    private static final SearchRange [] EMPTY_RANGE = new SearchRange [0];

    /** Start index from which to search (inclusive). */
    public final int start;
    
    /** How many results to fetch. */
    public final int results;

    /*
     * 
     */
    public SearchRange(int start, int results) {
        this.start = start;
        this.results = results;
    }

    /**
     * Given an unconstrained start and results count, adjust it to the allowed
     * window and split into page buckets if necessary.   
     */
    public static SearchRange [] getSearchRanges(int start, int results,
        int maxStartIndex, int resultsPerPage)
    {
        // Sanity check.
        results = Math.max(results, 0);
        start = Math.max(start, 0);

        int startIndex = Math.min(start, maxStartIndex);
        final int endIndex = Math.min(start + results, maxStartIndex);

        final int resultsNeeded = endIndex - startIndex;
        if (resultsNeeded == 0) {
            return EMPTY_RANGE;
        }

        final int lastBucketSize = resultsNeeded % resultsPerPage; 
        final int bucketsNeeded = 
            resultsNeeded / resultsPerPage + (lastBucketSize > 0 ? 1 : 0);

        final SearchRange [] buckets = new SearchRange [bucketsNeeded];
        for (int i = 0; i < buckets.length; i++) {
            final int window = Math.min(resultsPerPage, endIndex - startIndex);
            buckets[i] = new SearchRange(startIndex, window);
            startIndex += window;
        }

        return buckets;
    }
}

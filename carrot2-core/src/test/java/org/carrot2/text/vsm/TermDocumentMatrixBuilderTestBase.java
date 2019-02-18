
/*
 * Carrot2 project.
 *
 * Copyright (C) 2002-2019, Dawid Weiss, Stanisław Osiński.
 * All rights reserved.
 *
 * Refer to the full license file "carrot2.LICENSE"
 * in the root folder of the repository checkout or at:
 * http://www.carrot2.org/carrot2.LICENSE
 */

package org.carrot2.text.vsm;

import org.carrot2.AbstractTest;
import org.carrot2.clustering.Document;
import org.carrot2.clustering.TestDocument;
import org.carrot2.language.LanguageComponents;
import org.carrot2.language.TestsLanguageComponentsFactoryVariant2;
import org.carrot2.text.preprocessing.CompletePreprocessingPipeline;
import org.carrot2.text.preprocessing.PreprocessingContext;
import org.junit.Before;

import java.util.stream.Stream;

/**
 * A base class for tests requiring that the main term-document document matrix is built.
 */
public class TermDocumentMatrixBuilderTestBase extends AbstractTest {
  /**
   * Matrix builder
   */
  protected TermDocumentMatrixBuilder matrixBuilder;

  /**
   * VSM processing context with all the data
   */
  protected VectorSpaceModelContext vsmContext;

  protected CompletePreprocessingPipeline preprocessingPipeline;

  @Before
  public void setUpMatrixBuilder() throws Exception {
    preprocessingPipeline = new CompletePreprocessingPipeline();
    preprocessingPipeline.labelFilterProcessor.get().minLengthLabelFilter.get().enabled.set(false);

    matrixBuilder = new TermDocumentMatrixBuilder();
    matrixBuilder.termWeighting.set(new TfTermWeighting());
    matrixBuilder.maxWordDf.set(1.0);
  }

  protected void buildTermDocumentMatrix(Stream<? extends Document> documents) {
    LanguageComponents languageComponents = LanguageComponents.get(TestsLanguageComponentsFactoryVariant2.NAME);

    PreprocessingContext context = preprocessingPipeline.preprocess(documents, null, languageComponents);

    vsmContext = new VectorSpaceModelContext(context);
    matrixBuilder.buildTermDocumentMatrix(vsmContext);
    matrixBuilder.buildTermPhraseMatrix(vsmContext);
  }

  protected static Stream<TestDocument> createDocumentsWithTitles(String... content) {
    return Stream.of(content).map(v -> new TestDocument(v));
  }
}

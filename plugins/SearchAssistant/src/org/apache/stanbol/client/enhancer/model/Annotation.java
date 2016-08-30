/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.stanbol.client.enhancer.model;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Represents an annotation in the Stanbol EnhancementStructure
 * 
 * @author efoncubierta
 * @author <a href="mailto:rharo@zaizi.com">Rafa Haro</a>
 * 
 */
public class Annotation extends Enhancement implements Comparable<Annotation>
{

	// properties
    private final String extractedFrom; // http://fise.iks-project.eu/ontology/extracted-from
    private final Double confidence; // http://fise.iks-project.eu/ontology/confidence

    /**
     * Constructor
     * 
     * @param resource Jena resource
     */
    protected Annotation(Resource resource)
    {
        super(resource);
        this.extractedFrom = resource.hasProperty(EnhancementStructureOntology.EXTRACTED_FROM) ? 
        		resource.getPropertyResourceValue(EnhancementStructureOntology.EXTRACTED_FROM).getURI() : null;
        this.confidence = resource.hasProperty(EnhancementStructureOntology.CONFIDENCE) ? 
        		resource.getProperty(EnhancementStructureOntology.CONFIDENCE).getDouble() : null;
    }

    /**
     * Get the fise:extracted-from property
     * 
     * @return fise:extracted-from property
     */
    public String getExtractedFrom()
    {
        return extractedFrom;
    }

    /**
     * Get the fise:confidence property
     * 
     * @return fise:confidence property
     */
    public Double getConfidence()
    {
        return confidence;
    }

    @Override
    public int compareTo(Annotation o)
    {
        if(this.equals(o))
            return 0;
        
        if(this.confidence > o.getConfidence())
            return -1;
        else 
            return 1;
    }
    
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Annotation [getExtractedFrom()=");
		builder.append(getExtractedFrom());
		builder.append(", getConfidence()=");
		builder.append(getConfidence());
		builder.append(", getUri()=");
		builder.append(getUri());
		builder.append(", getCreator()=");
		builder.append(getCreator());
		builder.append(", getCreated()=");
		builder.append(getCreated());
		builder.append(", getRelation()=");
		builder.append(getRelation());
		builder.append("]");
		return builder.toString();
	}
  	
}

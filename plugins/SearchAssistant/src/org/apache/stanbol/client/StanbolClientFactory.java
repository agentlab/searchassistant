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
package org.apache.stanbol.client;

import javax.ws.rs.core.UriBuilder;

import org.apache.stanbol.client.enhancer.impl.EnhancerImpl;
import org.apache.stanbol.client.entityhub.impl.EntityHubImpl;
import org.apache.stanbol.client.sparql.impl.SparqlImpl;


/**
 * Apache Stanbol Client Factory Interface
 * 
 * @author <a href="mailto:rharo@zaizi.com">Rafa Haro</a>
 * 
 */
public class StanbolClientFactory
{
    /**
     * Create an instance of the {@link Enhancer} client
     * 
     * @return Enhancer service
     */
	
	private final UriBuilder builder;
	
	public StanbolClientFactory(String endpoint){
		builder = UriBuilder.fromUri(endpoint);
	}
	
    public final Enhancer createEnhancerClient(){
    	return new EnhancerImpl(builder);
    }

    /**
     * Create an instance of the {@link EntityHub} client
     * 
     * @return Entityhub service
     */
    public final EntityHub createEntityHubClient(){
    	return new EntityHubImpl(builder);
    }
    
    /**
     * Create an instance of the {@link Sparql} client
     * 
     * @return StanbolSparqlService
     */
    public final Sparql createSparqlClient(){
    	return new SparqlImpl(builder);
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		StanbolClientFactory other = (StanbolClientFactory) obj;
		if (builder == null) {
			if (other.builder != null) {
				return false;
			}
		} else if (!builder.equals(other.builder)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((builder == null) ? 0 : builder.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("StanbolClientFactory [builder=");
		builder2.append(builder);
		builder2.append("]");
		return builder2.toString();
	}
}

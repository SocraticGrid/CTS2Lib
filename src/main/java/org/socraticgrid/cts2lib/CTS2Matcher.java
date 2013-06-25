/*-
 *
 ******************************************************************************************************
 * Copyright (C) 2013 by Cognitive Medical Systems, Inc
 * (http://www.cognitivemedciine.com) * * Licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except in compliance * with the
 * License. You may obtain a copy of the License at * *
 * http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law
 * or agreed to in writing, software distributed under the License is * distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. * See the License for the specific language governing permissions and
 * limitations under the License. *
 ******************************************************************************************************
 *
 ******************************************************************************************************
 * Socratic Grid contains components to which third party terms apply. To comply with
 * these terms, the following * notice is provided: * * TERMS AND CONDITIONS FOR USE,
 * REPRODUCTION, AND DISTRIBUTION * Copyright (c) 2008, Nationwide Health Information
 * Network (NHIN) Connect. All rights reserved. * Redistribution and use in source
 * and binary forms, with or without modification, are permitted provided that * the
 * following conditions are met: - Redistributions of source code must retain the
 * above copyright notice, this list of conditions and the *     following
 * disclaimer. * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the * following disclaimer in the
 * documentation and/or other materials provided with the distribution. * - Neither
 * the name of the NHIN Connect Project nor the names of its contributors may be used
 * to endorse or *     promote products derived from this software without specific
 * prior written permission. * * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS
 * AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED * WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A *
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, * PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION HOWEVER * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE. * * END OF TERMS AND CONDITIONS *
 ******************************************************************************************************
 */
package org.socraticgrid.cts2lib;

// import com.sun.security.ntlm.Client;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import edu.mayo.cts2.framework.model.entity.Designation;
import edu.mayo.cts2.framework.model.entity.EntityDescriptionMsg;
import edu.mayo.cts2.framework.model.entity.types.DesignationRole;

import edu.mayo.cts2.framework.model.mapversion.MapEntryDirectory;
import edu.mayo.cts2.framework.model.mapversion.MapEntryDirectoryEntry;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import org.socraticgrid.codeconversion.elements.CodeReference;
import org.socraticgrid.codeconversion.elements.CodeSearch;
import org.socraticgrid.codeconversion.elements.CodeSource;
import org.socraticgrid.codeconversion.elements.SearchOptions;
import org.socraticgrid.codeconversion.matchers.BaseMatcher;
import org.socraticgrid.codeconversion.matchers.MatchContract;

import java.io.StringReader;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;


/**
 * Match a search against a CTS 2.0 System via a Rest call. Currently only supports
 * literal target and source
 *
 *  TODO:   Move text to resources 
 * 
 * @author  Jerry Goodnough
 */
public class CTS2Matcher extends BaseMatcher
{

    private static final Logger logger = Logger.getLogger(CTS2Matcher.class
            .getName());
    private String CTS2Endpoint;
    private boolean includeSourceInfo = false;
    private Map<String, TargetSystemMappings> targetSystemMappings;
    private boolean usePreferedMatchOnly = false;

    /**
     * Get the value of CTS2Endpoint
     *
     * @return  the value of CTS2Endpoint
     */
    public String getCTS2Endpoint()
    {
        return CTS2Endpoint;
    }

    @Override
    public MatchContract getMatchContract()
    {
        return super.getMatchContract();
    }

    /**
     * Get the value of targetSystemMappings
     *
     * @return  the value of targetSystemMappings
     */
    public Map<String, TargetSystemMappings> getTargetSystemMappings()
    {
        return targetSystemMappings;
    }

    /**
     * Get the value of includeSourceInfo. When true this matcher will include data
     * source information if possible.
     *
     * @return  the value of includeSourceInfo
     */
    public boolean isIncludeSourceInfo()
    {
        return includeSourceInfo;
    }

    /**
     * Get the value of usePreferedMatchOnly. When true Only the preferred matches
     * will be place on the output list. Otherwise preferred matches will be added to
     * the output list first
     *
     * @return  the value of usePreferedMatchOnly
     */
    public boolean isUsePreferedMatchOnly()
    {
        return usePreferedMatchOnly;
    }

    @Override
    public boolean match(CodeSearch matchCd, List<CodeReference> matchingCodeList)
    {
        // TODO: Translate code Systems if required (Maybe by facade)

        // Filter out Searches that are not literal for now.
        int searchType = matchCd.getSearchType();

        int mask = SearchOptions.LITERAL_Code + SearchOptions.ANY_Display +
            SearchOptions.LITERAL_TargetSystem;

        if ((searchType & mask) != mask)
        {
            logger.log(Level.WARNING,
                "CTS2Matcher does not support search options of {0}", searchType);

            return true;
        }

        TargetSystemMappings tsm = this.targetSystemMappings.get(
                matchCd.getTargetSystem());

        if (tsm != null)
        {

            if (tsm.getSourceCodeSystems() != null)
            {
                SourceMapping sm = tsm.getSourceCodeSystems().get(
                        matchCd.getSystem());

                if (sm != null)
                {
                    this.internalLookup(matchCd, matchingCodeList, tsm, sm);
                }
                else
                {
                    logger.log(Level.FINE, "{0} does not support {1}",
                        new Object[]
                        {
                            matchCd.getTargetSystem(), matchCd.getSystem()
                        });
                }
            }
            else
            {
                logger.log(Level.WARNING,
                    "CTS2Matcher does not have a TargetSystemsMapping for {0}",
                    matchCd.getTargetSystem());
            }
        }
        else
        {
            logger.log(Level.WARNING,
                "CTS2Matcher does not support Target System of  {0}",
                matchCd.getTargetSystem());

        }

        return true;
    }

    /**
     * Set the value of CTS2Endpoint
     *
     * @param  CTS2Endpoint  new value of CTS2Endpoint
     */
    public void setCTS2Endpoint(String CTS2Endpoint)
    {
        this.CTS2Endpoint = CTS2Endpoint;
    }

    /**
     * Set the value of includeSourceInfo
     *
     * @param  includeSourceInfo  new value of includeSourceInfo
     */
    public void setIncludeSourceInfo(boolean includeSourceInfo)
    {
        this.includeSourceInfo = includeSourceInfo;
    }

    /**
     * Set the value of targetSystemMappings
     *
     * @param  targetSystemMappings  new value of targetSystemMappings
     */
    public void setTargetSystemMappings(
        Map<String, TargetSystemMappings> targetSystemMappings)
    {
        this.targetSystemMappings = targetSystemMappings;
    }

    /**
     * Set the value of usePreferedMatchOnly
     *
     * @param  usePreferedMatchOnly  new value of usePreferedMatchOnly
     */
    public void setUsePreferedMatchOnly(boolean usePreferedMatchOnly)
    {
        this.usePreferedMatchOnly = usePreferedMatchOnly;
    }

    /**
     */
    @PostConstruct
    protected void initialize()
    {

        // Build the match contract
        Iterator itr = targetSystemMappings.keySet().iterator();

        while (itr.hasNext())
        {
            contract.addTargetSystem((String) itr.next());
        }
    }

    protected String buildMapLookupURL(CodeSearch matchCd, SourceMapping sm)
    {


        StringBuilder baseURL = new StringBuilder();

        // Form the base endpoint
        if (sm.getEndPoint() != null)
        {
            baseURL.append(sm.getEndPoint());
        }
        else
        {
            baseURL.append(CTS2Endpoint);
        }

        // Add the map name
        baseURL.append("/map/");
        baseURL.append(sm.getMapName());

        // Add the version
        baseURL.append("/version/");
        baseURL.append(sm.getMapVersion());

        // Add the Query
        baseURL.append("/entries?targetentity=");
        baseURL.append(sm.getCodePrefix());
        baseURL.append(":");
        baseURL.append(matchCd.getCode());

        return baseURL.toString();
    }
    
     protected String buildEntityLookupURL(String code, TargetSystemMappings tsm)
    {


        StringBuilder baseURL = new StringBuilder();

        baseURL.append(CTS2Endpoint);
        

        // Add the map name
        baseURL.append("/codesystem/");
        baseURL.append(tsm.getTargetCTS2CodeSystemName());

        // Add the version
        baseURL.append("/version/");
        baseURL.append(tsm.getTargetCTS2CodeSystemVersion());

        // Add the Query
        baseURL.append("/entity/");
        baseURL.append(code);
        return baseURL.toString();
    }

    private void internalLookup(CodeSearch matchCd,
        List<CodeReference> matchingCodeList, TargetSystemMappings tsm, SourceMapping sm)
    {

        //
        String url = buildMapLookupURL(matchCd, sm);


        try
        {

            // get the code mapping
            Client client = Client.create();
            WebResource webResource = client.resource(url);
            ClientResponse response = webResource.accept("text/xml").get(
                    ClientResponse.class);

            if (response.getStatus() != 200)
            {
                logger.log(Level.SEVERE, "Failed : HTTP error code : {0}",
                    response.getStatus());
                return;
            }

            String output = response.getEntity(String.class);
            StringReader reader = new StringReader(output);


            MapEntryDirectory mapEntryDirectory = MapEntryDirectory
                .unmarshalMapEntryDirectory(reader);

            if (mapEntryDirectory.getEntryCount() > 0)
            {
                int size = mapEntryDirectory.getEntryCount();

                if (sm.isOutputAllMatches())
                {

                    // We have been ask for all matchs so return what we have.
                    for (int i = 0; i < size; i++)
                    {
                        MapEntryDirectoryEntry ent = mapEntryDirectory.getEntry(i);

                        String code = ent.getResourceName();


                        String text;

                        if (sm.isFetchDisplay())
                        {
                            text = this.internalDesciptionLookup(tsm, code);
                        }
                        else
                        {
                            text = matchCd.getDisplay();
                        }

                        String system = matchCd.getTargetSystem();

                        CodeReference cr = new CodeReference(system, code, text);

                        if (sm.getSource() != null)
                        {
                            CodeSource cs = new CodeSource();
                            cs.setSourceNote(sm.getSource());

                            Double d = ent.getMatchStrength();
                            double dcomp;

                            if (d == null)
                            {
                                dcomp = 0;
                            }
                            else
                            {
                                dcomp = d;
                            }

                            cs.setMatchStrength(dcomp);
                            cr.setSource(cs);
                        }

                        matchingCodeList.add(cr);

                    }
                }
                else
                {

                    // Find the strongest Match

                    double maxMatch = -1;

                    MapEntryDirectoryEntry ent;
                    MapEntryDirectoryEntry bestEntry = null;

                    for (int i = 0; i < size; i++)
                    {
                        ent = mapEntryDirectory.getEntry(i);

                        Double d = ent.getMatchStrength();
                        double dcomp;

                        if (d == null)
                        {
                            dcomp = 0;
                        }
                        else
                        {
                            dcomp = d;
                        }

                        if (dcomp > maxMatch)
                        {
                            bestEntry = ent;
                            maxMatch = dcomp;
                        }
                    }

                    String code = bestEntry.getResourceName();


                    String text;

                    if (sm.isFetchDisplay())
                    {
                        text = this.internalDesciptionLookup(tsm, code);
                    }
                    else
                    {
                        text = matchCd.getDisplay();
                    }

                    String system = matchCd.getTargetSystem();

                    CodeReference cr = new CodeReference(system, code, text);

                    if (sm.getSource() != null)
                    {
                        CodeSource cs = new CodeSource();
                        cs.setSourceNote(sm.getSource());
                        cs.setMatchStrength(maxMatch);
                        cr.setSource(cs);
                    }

                    matchingCodeList.add(cr);

                }
            }
        }
        catch (UniformInterfaceException | ClientHandlerException |
                MarshalException | ValidationException | IndexOutOfBoundsException e)
        {
            logger.log(Level.SEVERE,
                "Exception calling CTS 2.0 rest service on " + url, e);
        }


    }
    
    protected String internalDesciptionLookup(TargetSystemMappings tsm, String code)
    {
      
        String url = this.buildEntityLookupURL(code, tsm);
        String out = "Description not available";

        try
        {

            // get the code mapping
            Client client = Client.create();
            WebResource webResource = client.resource(url);
            ClientResponse response = webResource.accept("text/xml").get(
                    ClientResponse.class);

            if (response.getStatus() != 200)
            {
                logger.log(Level.SEVERE, "Failed : HTTP error code : {0}",
                    response.getStatus());
                return "Description Service Unavaliable";
            }

            String output = response.getEntity(String.class);
            StringReader reader = new StringReader(output);


            
            EntityDescriptionMsg entity = EntityDescriptionMsg.unmarshalEntityDescriptionMsg(reader);
            if (entity!=null)
            {    
                if ( entity.getEntityDescription() != null)
                {
                    if (entity.getEntityDescription().getClassDescription()!= null)
                    {
                        
                        Designation[] da=  entity.getEntityDescription().getClassDescription().getDesignation();
                        //Look for the preferred role
                        int pref = -1;
                        for (int i = 0; i<da.length; i++)
                        {
                            DesignationRole role = da[i].getDesignationRole();
                            if (role != null)
                            {
                                if (role ==DesignationRole.PREFERRED)
                                {
                                    //Set the Id and break the loop since we have found a preferred description
                                    pref = i;
                                    break;
                                }
                            }    
                        }
                        if (pref != -1)
                        {
                           out = da[pref].getValue().getContent();
                        }
                        else if (da.length>0)
                        {
                
                            //No preferred Description so we will use the first entry
                            //In the future the TargetSystemMap might be modified to 
                            //Support the preferred entry number
                             out = da[0].getValue().getContent();
                        }
                        else
                        {
                            out = "Description not in CTS 2.0 Service";
                        }
                    }
                }
            }
         
        }
        catch (UniformInterfaceException | ClientHandlerException |
                MarshalException | ValidationException | IndexOutOfBoundsException e)
        {
            logger.log(Level.SEVERE,
                "Exception calling CTS 2.0 rest service on " + url, e);
        }  
        
        return out;
    }
}

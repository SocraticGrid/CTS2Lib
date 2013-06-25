/*
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
 * notice, this list of conditions and the *     following disclaimer in the
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

/**
 * This class provide specific binding information for the CTS 2 Query for a specific
 * code system and configuration.
 *
 * @author  Jerry Goodnough
 */
public class SourceMapping
{
    private String codePrefix;
    private String endPoint;
    private boolean fetchDisplay = false;
    private String mapName;
    private String mapVersion;
    private boolean outputAllMatches = false;
    private String source;

    /**
     * Get the value of codePrefix.
     *
     * @return  the value of codePrefix
     */
    public String getCodePrefix()
    {
        return codePrefix;
    }

    /**
     * Get the value of endPoint.
     *
     * @return  the value of endPoint
     */
    public String getEndPoint()
    {
        return endPoint;
    }

    /**
     * Get the value of mapName.
     *
     * @return  the value of mapName
     */
    public String getMapName()
    {
        return mapName;
    }

    /**
     * Get the value of mapVersion.
     *
     * @return  the value of mapVersion
     */
    public String getMapVersion()
    {
        return mapVersion;
    }

    /**
     * Get the value of source.
     *
     * @return  the value of source
     */
    public String getSource()
    {
        return source;
    }

    /**
     * Get the value of fetchDisplay.
     *
     * @return  the value of fetchDisplay
     */
    public boolean isFetchDisplay()
    {
        return fetchDisplay;
    }

    /**
     * Get the value of outputAllMatches.
     *
     * @return  the value of outputAllMatches
     */
    public boolean isOutputAllMatches()
    {
        return outputAllMatches;
    }

    /**
     * Set the value of codePrefix. This prefix is used for the CTS 2.0 map search.
     * Currently CTS 2.0 is ignoring this code, but is it good form to set it. For
     * example ICD-9 might use icd9cm as the prefix.
     *
     * @param  codePrefix  new value of codePrefix
     */
    public void setCodePrefix(String codePrefix)
    {

        if (codePrefix.endsWith(":"))
        {
            codePrefix = codePrefix.substring(0, codePrefix.length() - 1);
        }

        this.codePrefix = codePrefix;
    }

    /**
     * Set the value of endPoint. The optional property when set allows this one
     * source to fetch data from an alternate CTS 2.0 endpoint
     *
     * @param  endPoint  new value of endPoint
     */
    public void setEndPoint(String endPoint)
    {
        this.endPoint = endPoint;
    }

    /**
     * Set the value of fetchDisplay. When set to true the Service should lookup the
     * display value for the translated code.
     *
     * @param  fetchDisplay  new value of fetchDisplay
     */
    public void setFetchDisplay(boolean fetchDisplay)
    {
        this.fetchDisplay = fetchDisplay;
    }

    /**
     * Set the value of mapName. This is the name used for the desired map on the CTS
     * 2.0 System
     *
     * @param  mapName  new value of mapName
     */
    public void setMapName(String mapName)
    {
        this.mapName = mapName;
    }

    /**
     * Set the value of mapVersion This is map version the name used for the desired
     * map on the CTS 2.0 System.
     *
     * @param  mapVersion  new value of mapVersion
     */
    public void setMapVersion(String mapVersion)
    {
        this.mapVersion = mapVersion;
    }

    /**
     * Set the value of outputAllMatches. When set to true the search will return all
     * matched. Otherwise only the strongest match is returned
     *
     * @param  outputAllMatches  new value of outputAllMatches
     */
    public void setOutputAllMatches(boolean outputAllMatches)
    {
        this.outputAllMatches = outputAllMatches;
    }

    /**
     * Set the value of source. This is s source note that is returned to any
     * searchers
     *
     * @param  source  new value of source
     */
    public void setSource(String source)
    {
        this.source = source;
    }


}

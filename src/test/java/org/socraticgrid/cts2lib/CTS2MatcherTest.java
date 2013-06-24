/*-
 *
 * *************************************************************************************************************
 *  Copyright (C) 2013 by Cognitive Medical Systems, Inc
 *  (http://www.cognitivemedciine.com) * * Licensed under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except in compliance *
 *  with the License. You may obtain a copy of the License at * *
 *  http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable
 *  law or agreed to in writing, software distributed under the License is *
 *  distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied. * See the License for the specific language
 *  governing permissions and limitations under the License. *
 * *************************************************************************************************************
 *
 * *************************************************************************************************************
 *  Socratic Grid contains components to which third party terms apply. To comply
 *  with these terms, the following * notice is provided: * * TERMS AND
 *  CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION * Copyright (c) 2008,
 *  Nationwide Health Information Network (NHIN) Connect. All rights reserved. *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that * the following conditions are met:
 *  - Redistributions of source code must retain the above copyright notice, this
 *  list of conditions and the *     following disclaimer. * - Redistributions in
 *  binary form must reproduce the above copyright notice, this list of
 *  conditions and the *     following disclaimer in the documentation and/or
 *  other materials provided with the distribution. * - Neither the name of the
 *  NHIN Connect Project nor the names of its contributors may be used to endorse
 *  or *     promote products derived from this software without specific prior
 *  written permission. * * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS
 *  AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED * WARRANTIES, INCLUDING,
 *  BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 *  A * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *  OR CONTRIBUTORS BE LIABLE FOR * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, *
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 *  OR BUSINESS INTERRUPTION HOWEVER * CAUSED AND ON ANY THEORY OF LIABILITY,
 *  WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 *  OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, * EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. * * END OF TERMS AND CONDITIONS *
 * *************************************************************************************************************
 */
package org.socraticgrid.cts2lib;

import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.socraticgrid.codeconversion.elements.CodeReference;
import org.socraticgrid.codeconversion.elements.CodeSearch;
import org.socraticgrid.codeconversion.matchers.MatchContract;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationContext;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;
import org.socraticgrid.codeconversion.elements.SearchOptions;


/**
 * @author  Jerry Goodnough
 */
@ContextConfiguration(locations = { "classpath:Test-SpringXMLConfig.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class CTS2MatcherTest
{
    @Autowired
    ApplicationContext ctx;

    public CTS2MatcherTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Test of getCTS2Endpoint method, of class CTS2Matcher.
     */
    @Test
    public void testGetCTS2Endpoint()
    {
        System.out.println("getCTS2Endpoint");

        CTS2Matcher instance = (CTS2Matcher) ctx.getBean("SimpleConfig");
        String expResult = "http://192.168.1.104:8080/cts2/map";
        String result = instance.getCTS2Endpoint();
        assertEquals(expResult, result);

    }

    /**
     * Test of getMatchContract method, of class CTS2Matcher.
     */
    @Test
    public void testGetMatchContract()
    {
        System.out.println("getMatchContract");

        CTS2Matcher instance = (CTS2Matcher) ctx.getBean("SimpleConfig");
        MatchContract result = instance.getMatchContract();
        assertNotNull(result);
        assertTrue(result.supportsTargetSystem("snomedct"));
    }

    /**
     * Test of getTargetSystemMappings method, of class CTS2Matcher.
     */
    @Test
    public void testGetTargetSystemMappings()
    {
        System.out.println("getTargetSystemMappings");

        CTS2Matcher instance = (CTS2Matcher) ctx.getBean("SimpleConfig");

        Map result = instance.getTargetSystemMappings();
           assertNotNull(result);


    }

    /**
     * Test of initialize method, of class CTS2Matcher.
     */
    @Test
    public void testInitialize()
    {
        System.out.println("initialize");

        CTS2Matcher instance = (CTS2Matcher) ctx.getBean("SimpleConfig");
        instance.initialize();

    }

    /**
     * Test of isIncludeSourceInfo method, of class CTS2Matcher.
     */
    @Test
    public void testIsIncludeSourceInfo()
    {
        System.out.println("isIncludeSourceInfo");

        CTS2Matcher instance = (CTS2Matcher) ctx.getBean("SimpleConfig");
        boolean expResult = false;
        boolean result = instance.isIncludeSourceInfo();
        assertEquals(expResult, result);

    }

    /**
     * Test of isUsePreferedMatchOnly method, of class CTS2Matcher.
     */
    @Test
    public void testIsUsePreferedMatchOnly()
    {
        System.out.println("isUsePreferedMatchOnly");

        CTS2Matcher instance = (CTS2Matcher) ctx.getBean("SimpleConfig");
        boolean expResult = false;
        boolean result = instance.isUsePreferedMatchOnly();
        assertEquals(expResult, result);

    }

    /**
     * Test of match method, of class CTS2Matcher.
     */
    @Test
    public void testMatch()
    {
        System.out.println("match");

        CodeSearch matchCd = new CodeSearch();
        matchCd.setTargetSystem("snomedct");
        matchCd.setSystem("icd9cm");
        matchCd.setSearchType(SearchOptions.LITERAL_Code+SearchOptions.ANY_Display+SearchOptions.LITERAL_TargetSystem);
        matchCd.setCode("414.01");
        List<CodeReference> matchingCodeList = new LinkedList<>() ;
        CTS2Matcher instance = (CTS2Matcher) ctx.getBean("SimpleConfig");
        

  
        boolean result = instance.match(matchCd, matchingCodeList);
        assertTrue(result);
        assertTrue(matchingCodeList.size()>0);
        assertTrue(matchingCodeList.get(0).getCode().compareTo("53741008")==0);
        //Expected code is 53741008
    }

    /**
     * Test of setCTS2Endpoint method, of class CTS2Matcher.
     */
    @DirtiesContext
    @Test
    public void testSetCTS2Endpoint()
    {
        System.out.println("setCTS2Endpoint");

        String CTS2Endpoint = "http://someendpoint";
        CTS2Matcher instance = (CTS2Matcher) ctx.getBean("SimpleConfig");
        instance.setCTS2Endpoint(CTS2Endpoint);

    }

    /**
     * Test of setIncludeSourceInfo method, of class CTS2Matcher.
     */
    @DirtiesContext
    @Test
    public void testSetIncludeSourceInfo()
    {
        System.out.println("setIncludeSourceInfo");

        boolean includeSourceInfo = false;
        CTS2Matcher instance = (CTS2Matcher) ctx.getBean("SimpleConfig");
        instance.setIncludeSourceInfo(includeSourceInfo);

    }

    /**
     * Test of setTargetSystemMappings method, of class CTS2Matcher.
     */
    @DirtiesContext
    @Test
    public void testSetTargetSystemMappings()
    {
        System.out.println("setTargetSystemMappings");

        Map<String, TargetSystemMappings> targetSystemMappings = null;
        CTS2Matcher instance = (CTS2Matcher) ctx.getBean("SimpleConfig");
        instance.setTargetSystemMappings(targetSystemMappings);

    }

    /**
     * Test of setUsePreferedMatchOnly method, of class CTS2Matcher.
     */
    @DirtiesContext
    @Test
    public void testSetUsePreferedMatchOnly()
    {
        System.out.println("setUsePreferedMatchOnly");

        boolean usePreferedMatchOnly = false;
        CTS2Matcher instance = (CTS2Matcher) ctx.getBean("SimpleConfig");
        instance.setUsePreferedMatchOnly(usePreferedMatchOnly);

    }
}

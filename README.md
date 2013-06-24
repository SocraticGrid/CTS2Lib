CTS2Lib
=======
 
 Author: Jerry Goodnough (jgoodnough@cognitivemedicine.com)
 
 Commiters: Jerry Goodnough (jgoodnough@cognitivemedicine.com)
 
 Notes:  
=================================================================================
 Implements SocraticGrid CodeConversion and relies on the CTS2.0 Framework.
 
 Tests currently rely on a working CTS2.0 Server with specific code sets loaded.
 
 Major Usage:
================================================================================= 

org.socraticgrid.cts2lib.CTS2Matcher implements CodeMatcher as is designed to run
in the context of the CodeConversion process. Example Spring configuration files 
are provided. By default a CTS2Matcher will not provide normalized descriptions for
the matching codes, since this requires an additional REST call to the entity 
service on the CTS2.0 server. If need this feature may be turned on.
 
Currently only SearchOptions.LITERAL_Code + SearchOptions.ANY_Display +
SearchOptions.LITERAL_TargetSystem are supported;
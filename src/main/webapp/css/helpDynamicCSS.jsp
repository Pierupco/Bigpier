
<%@ page contentType="text/css" %>


.label88 {
	color: <%=session.getAttribute("label.color") %>;
	font-family: <%=session.getAttribute("label.fond") %>, verdana,arial,sans-serif;
	font-size: <%=session.getAttribute("label.fond.size") %>;
}


.QuickView
{ 

display:<%=session.getAttribute("QuickView") %>;
}


.ReaderEnabled 
{ 

display:<%=session.getAttribute("ReaderEnabled") %>;
}

.EitherReader 
{ 

display:<%=session.getAttribute("EitherReader") %>;
}

.BothReaderEnabled 
{ 

display:<%=session.getAttribute("BothReaderEnabled") %>;
}

.PluginReaderEnabled
{ 

display:<%=session.getAttribute("PluginReaderEnabled") %>;
}


.JavaReaderEnabled 
{ 

display:<%=session.getAttribute("JavaReaderEnabled") %>;
}




.HasRemoteSignIn 
{ 

display: <%=session.getAttribute("HasRemoteSignIn") %>;
}


.ebrarySignIn 
{ 

display: <%=session.getAttribute("ebrarySignIn") %>;
}

.NotPrimis 
{ 

display: <%=session.getAttribute("notPrimis") %>;
}

.AYCE 
{ 

display: <%=session.getAttribute("AYCE") %>;
}



.PAYG 
{ 

display: <%=session.getAttribute("PAYG") %>;
}

.PAYGNotPrimis
{ 

display: <%=session.getAttribute("PAYGNotPrimis") %>;
}




.ShopOnly 
{ 

display: <%=session.getAttribute("ShopOnly") %>;
}


.NotShop 
{ 

display: <%=session.getAttribute("NotShop") %>;
}





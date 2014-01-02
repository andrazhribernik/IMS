<%@page import="ims.management.LoginManagement"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Set"%>
<%@page import="ims.management.UserManagement"%>
<%@page import="ims.entity.Image"%>
<%
    LoginManagement lm = new LoginManagement(session);
    lm.userPermissionForThisPage(response, new String[]{"contributor"});
%>
<!--
To change this template, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<html>
    <jsp:include page="templates/header.jsp" />
    <body>
        <jsp:include page="templates/navigationBar.jsp" />
            <div class="col-md-8 col-md-offset-2">
                <form role="form" action="./UploadImage"  method="POST" enctype="multipart/form-data">
                    <div class="form-group">
                      <label for="exampleInputPrice">Image price</label>
                      <input type="text" class="form-control" id="exampleInputPrice" placeholder="&euro;10.50" name="price">
                      <p class="help-block">Price in Euro.</p>
                      <%
                        if(request.getParameter("priceMessage") != null){ %>
                            <div class="alert alert-danger"><%= request.getParameter("priceMessage")%></div>
                        <%}
                      %>
                    </div>
                    
                    <div class="form-group">
                      <label for="exampleInputFile">Image input</label>
                      <input type="file" id="exampleInputFile" name="file">
                      <p class="help-block">Image should be in .jpg or .png format.</p>
                      <%
                        if(request.getParameter("fileMessage") != null){ %>
                            <div class="alert alert-danger"><%= request.getParameter("fileMessage")%></div>
                        <%}
                      %>
                    </div>

                    <button type="submit" class="btn btn-default">Upload</button>
                    <%
                        if(request.getParameter("successMessage") != null){ %>
                            <div class="alert alert-success"><%= request.getParameter("successMessage")%></div>
                        <%}
                      %>
                </form>
            </div> 
        </div>
    </body>
</html>

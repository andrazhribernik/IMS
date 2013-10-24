<%@page import="ims.entity.Image"%>
<%@page import="java.util.List"%>
<%@page import="ims.management.ImageManagement"%>
<!--
To change this template, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<html>
    <head>
        <title>IMS</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/myCss.css">
    </head>
    <body>
        <nav class="navbar navbar-inverse" role="navigation">
            <div class="navbar-header">  
                <a class="navbar-brand">Image management system </a>
            </div>
            <div class="">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="index.jsp">Images</a></li>
                    <li><a href="#">Link</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                     <li><a href="#">Log in</a></li>
                </ul>
            </div>
        </nav>
        <div class="row">
            <div class="col-md-12 text-center">
        <%  
            String idImage="";
            try{
                idImage =request.getParameter("imageId");
            }
            catch(Exception e){
                idImage = null;
            }
            ImageManagement im = new ImageManagement();
            Image currentImage = im.getImageById(idImage);
            if(currentImage == null){
            %>
                <div class="alert alert-danger">Wrong parameter.</div>
            
            <%
            }
            else{
        %>
        
                <img src="GetImage?imageId=<%=currentImage.getIdImage()%>&size=600"  class="img-thumbnail" />
                
            <%  
                }
            %>
            </div>
        </div>
            

    </body>
</html>

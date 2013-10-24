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
                <a class="navbar-brand" href="#">Image management system</a>
            </div>
            <div class="">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#">Link</a></li>
                    <li><a href="#">Link</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                     <li><a href="#">Log in</a></li>
                </ul>
            </div>
        </nav>
        <div class="row">
        <% 
            ImageManagement im = new ImageManagement();
            List<Image> images = im.getAllImages();
            
            for(int i=0; i<images.size(); i++){
                if(i%3==0){
        %>
        </div>
        <div class="row">
            <div class="col-md-3">
                <img width="300" height="188" src="GetImage?imageId=<%=images.get(i).getIdImage()%>&size=300"  class="img-rounded tumbnail-margin" />
            </div>
        <%
                }
                else{
                 %>
           
            <div class="col-md-3 col-md-offset-1">
                <img width="300" height="188" src="GetImage?imageId=<%=images.get(i).getIdImage()%>&size=300"  class="img-rounded tumbnail-margin" />
            </div>
        <%   
                }
            }
        %>
            
        </div>
    </body>
</html>

<%@page import="ims.entity.Image"%>
<%@page import="java.util.List"%>
<%@page import="ims.management.ImageManagement"%>
<!--
To change this template, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<html>
    <jsp:include page="templates/header.jsp" />
    <body>
        <jsp:include page="templates/navigationBar.jsp" />
        <div class="row text-center">
        <% 
            List<Image> images = (List<Image>)request.getAttribute("images");
            if(images == null){
                /*
                ImageManagement im = new ImageManagement();
                images = im.getAllImages();
                * */
            }
            for(int i=0; i<images.size(); i++){
                if(i%3==0){
        %>
        </div>
        <div class="row text-center">
            <div class="col-md-3">
                <a href="imageDetails.jsp?imageId=<%=images.get(i).getIdImage() %>">
                    <img width="300" height="188" src="GetImage?imageId=<%=images.get(i).getIdImage()%>&size=300"  class="img-rounded tumbnail-margin" />
                </a>
            </div>
        <%
                }
                else{
                 %>
           
            <div class="col-md-3 col-md-offset-1">
                <a href="imageDetails.jsp?imageId=<%=images.get(i).getIdImage() %>">
                    <img width="300" height="188" src="GetImage?imageId=<%=images.get(i).getIdImage()%>&size=300"  class="img-rounded tumbnail-margin" />
                </a>
            </div>
        <%   
                }
            }
        %>
            
        </div>
    </body>
</html>

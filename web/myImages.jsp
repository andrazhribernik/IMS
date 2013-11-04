<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Set"%>
<%@page import="ims.management.UserManagement"%>
<%@page import="ims.entity.Image"%>

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
            //only because we haven't implemented log in, yet
            session.setAttribute( "username", new String("user1") );
            
            Set<Image> imagesSet = (Set<Image>)request.getAttribute("images");
            ArrayList<Image> images = new ArrayList<Image>();
            images.addAll(imagesSet);
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
